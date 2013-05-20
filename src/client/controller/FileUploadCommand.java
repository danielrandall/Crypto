package client.controller;

import javax.swing.JSpinner;
import javax.swing.JTextField;

import client.model.CentralAuthority;
import client.view.MenuFrame;

public class FileUploadCommand implements Command {
	
	private MenuFrame frame;

	@Override
	public void execute(Object[] objects) {
		
		frame = (MenuFrame) objects[0];
		
		String fileLocation = ((JTextField) objects[1]).getText();
		int securityLevel = (Integer) ((JSpinner) objects[2]).getValue();
		
		CentralAuthority.uploadFile(fileLocation, securityLevel);
		
	}
	
	

}
