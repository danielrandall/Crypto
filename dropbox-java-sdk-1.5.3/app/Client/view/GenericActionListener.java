package Client.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Client.controller.Command;

public class GenericActionListener implements ActionListener {
	
	private Command command;
	Object[] objects;
	
	public GenericActionListener(Command command, Object[] objects) {
		
			this.command = command;	
			this.objects = objects;
		
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		
		command.execute(objects);
		
	}
	
	

}
