package client.controller;

import client.model.CentralAuthority;
import client.view.FriendsPanel;

public class DeleteFriendCommand implements Command {

	@Override
	public void execute(Object[] objects) {
		
		FriendsPanel panel = (FriendsPanel) objects[0];
		
		Object[] friendInfo = panel.getSelectedRowInfo();
		String friendName = (String) friendInfo[0];
		
		CentralAuthority.revokeUser(friendName);
		
		panel.removeSelectedRowsFromTable();
		
	}

}
