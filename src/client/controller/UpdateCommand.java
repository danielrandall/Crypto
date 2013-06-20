package client.controller;

import client.model.Actions;
import client.view.UpdateFrame;

public class UpdateCommand implements Command {

	@Override
	public void execute(Object[] objects) {
		
		UpdateFrame frame = (UpdateFrame) objects[0];
		
		String newFileLocation = frame.getNewFileLocation();
		String fileName = frame.getFileToBeUpdated();
		String owner = frame.getFileOwner();
			
		Actions.updateFile(fileName, owner, newFileLocation);
		
		frame.fileUpdated();
			
	}

}
