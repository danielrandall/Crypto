package client.controller;

import client.model.CentralAuthority;
import client.view.FriendFilesPanel;

public class FriendFileDownloadCommand implements Command {

	@Override
	public void execute(Object[] objects) {
		
		FriendFilesPanel panel = (FriendFilesPanel) objects[0];
		
		Object[] info = panel.getSelectedRowInfo();
		
		if (info != null) {
			
			String fileName = (String) info[0];
			String owner = (String) info[1];
			
			CentralAuthority.downloadFriendFile(fileName, owner);
			
		}
		
	}

}
