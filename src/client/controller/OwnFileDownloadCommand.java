package client.controller;

import client.model.Actions;
import client.view.MyFilesPanel;


public class OwnFileDownloadCommand implements Command {
	
	private MyFilesPanel panel;

	@Override
	public void execute(Object[] objects) {
		
		panel = (MyFilesPanel) objects[0];
		Object[] info = panel.getSelectedRowInfo();
		
		if (info != null) {
			
			String fileName = (String) info[0];
			Actions.downloadFile(fileName);
		}
		
	}

}
