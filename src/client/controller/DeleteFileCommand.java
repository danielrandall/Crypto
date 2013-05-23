package client.controller;

import client.model.CentralAuthority;
import client.view.MyFilesPanel;

public class DeleteFileCommand implements Command {
	
	private MyFilesPanel panel;
//	private static final int ROWS_TO_DELETE = 1;

	@Override
	public void execute(Object[] objects) {
		
		panel = (MyFilesPanel) objects[0];
		Object[][] info = panel.getSelectedRowInfo();
		
		if (info != null) {
			
			for (int i = 0; i < info.length; i ++) {
				
				String fileName = (String) info[i][0];
				CentralAuthority.removeFile(fileName);
				
			}
			
			/* Tell the panel to remove the selected rows from the GUI */
			panel.removeSelectedRowsFromTable();
			
		}
		
	}

}
