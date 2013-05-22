package client.controller;

import javax.swing.JSlider;
import javax.swing.JTextField;

import client.model.CentralAuthority;
import client.view.UploadFileFrame;

public class FileUploadCommand implements Command {
	
	private UploadFileFrame frame;

	@Override
	public void execute(Object[] objects) {
		
		frame = (UploadFileFrame) objects[0];
		
		String fileLocation = ((JTextField) objects[1]).getText();
		int securityLevel = (Integer) ((JSlider) objects[2]).getValue();
		
		CentralAuthority.uploadFile(fileLocation, securityLevel);
		
		frame.fileUploaded();
		
	}
	
	

}
