package client.view;

import javax.swing.table.DefaultTableModel;

public class TableModel extends DefaultTableModel {
	
	public TableModel(Object[][] objects, String[] columnNames) {
		super(objects, columnNames);
	}

	@Override
	public boolean isCellEditable(int row, int col) {
		
		return false;
		
	}

}
