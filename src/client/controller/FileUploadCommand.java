package client.controller;

import java.io.File;

import client.model.Actions;
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
		
		String newFileName = Actions.uploadFile(fileLocation, securityLevel);

		Object[] fileInfo = {newFileName, securityLevel};
		panel.addElementToTable(fileInfo);
		
		frame.fileUploaded();
		
	}
	
	

}
