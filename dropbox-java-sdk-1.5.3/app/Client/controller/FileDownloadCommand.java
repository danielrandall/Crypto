package Client.controller;

import javax.swing.JTextField;

import Client.model.CentralAuthority;
import Client.view.MenuFrame;

public class FileDownloadCommand implements Command {
	
	private MenuFrame frame;

	@Override
	public void execute(Object[] objects) {
		
		frame = (MenuFrame) objects[0];
		
		String fileToDownload = ((JTextField) objects[1]).getText();
		String fileDownloadLocation = ((JTextField) objects[2]).getText();
		
		CentralAuthority.downloadFile(fileToDownload, fileDownloadLocation);
		
	}

}
