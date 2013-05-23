package client.controller;

import client.model.CentralAuthority;
import client.view.MenuFrame;
import client.view.MyFilesPanel;


public class FileDownloadCommand implements Command {
	
	private MenuFrame frame;
	private MyFilesPanel panel;

	@Override
	public void execute(Object[] objects) {
		
		panel = (MyFilesPanel) objects[0];
		Object[] info = panel.getSelectedRowInfo();
		
		if (info != null) {
			
			String fileName = (String) info[0];
			CentralAuthority.downloadFile(fileName);
		}
		
	}

}
