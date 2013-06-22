package client.view;

import javax.swing.table.DefaultTableModel;

public class TableModel extends DefaultTableModel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1272046034841973796L;

	public TableModel(Object[][] objects, String[] columnNames) {
		super(objects, columnNames);
	}

	@Override
	public boolean isCellEditable(int row, int col) {
		
		return false;
		
	}

}
