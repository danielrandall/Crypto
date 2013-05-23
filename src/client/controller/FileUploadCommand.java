package client.controller;

import client.model.CentralAuthority;
import client.view.UploadFileFrame;

public class FileUploadCommand implements Command {
	
	private UploadFileFrame frame;

	@Override
	public void execute(Object[] objects) {
		
		frame = (UploadFileFrame) objects[0];
		
		String fileLocation = frame.getFileLocation();
		
		if (fileLocation == null || fileLocation.equals(""))
			return;
		
		int securityLevel = frame.getSecurityLevel();
		
		CentralAuthority.uploadFile(fileLocation, securityLevel);
		
		frame.fileUploaded();
		
	}
	
	

}
