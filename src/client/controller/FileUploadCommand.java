package client.controller;

import java.io.File;

import client.model.CentralAuthority;
import client.view.MyFilesPanel;
import client.view.UploadFileFrame;

public class FileUploadCommand implements Command {
	
	private UploadFileFrame frame;
	private MyFilesPanel panel;

	@Override
	public void execute(Object[] objects) {
		
		frame = (UploadFileFrame) objects[0];
		panel = (MyFilesPanel) objects[1];
		
		String fileLocation = frame.getFileLocation();
		
		if (fileLocation == null || fileLocation.equals(""))
			return;
		
		int securityLevel = frame.getSecurityLevel();
		
		CentralAuthority.uploadFile(fileLocation, securityLevel);

		Object[] fileInfo = {new File(fileLocation).getName(), securityLevel};
		panel.addElementToTable(fileInfo);
		
		frame.fileUploaded();
		
	}
	
	

}
