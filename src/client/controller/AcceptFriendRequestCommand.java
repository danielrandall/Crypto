package client.controller;

import client.model.Actions;
import client.view.FriendFilesPanel;
import client.view.FriendRequestsPanel;

public class AcceptFriendRequestCommand implements Command {
	
	private FriendRequestsPanel panel;

	@Override
	public void execute(Object[] objects) {
		
		panel = (FriendRequestsPanel) objects[0];
		FriendFilesPanel friendFilesPanel = (FriendFilesPanel) objects[1];
		
		Object[] info = panel.getSelectedRowInfo();
		
		if (info != null) {
			
			String username = (String) info[0];
			String[][] newFiles = Actions.acceptFriendRequest(username);
			
			friendFilesPanel.populateFriendFiles(newFiles);
			panel.friendAdded();
		}
	
			
	}
	

}
