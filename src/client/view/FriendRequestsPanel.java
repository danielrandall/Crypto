package client.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import client.controller.AddFriendCommand;

public class FriendRequestsPanel extends JPanel {
	
	private final JPanel buttonPanel = new JPanel();
	private final JTable RequestTable = new JTable();
	private final JScrollPane tableScrollPane = new JScrollPane(RequestTable);
	
	/* Table information */
	private final String[] columnNames = {"Username", "Security Level"};
	private final JButton btnAcceptFriend = new JButton("Accept request");
	private final JButton btnRejectFriend = new JButton("Reject request");

	/**
	 * Create the panel.
	 */
	public FriendRequestsPanel() {

		initGUI();
		
	}
	
	private void initGUI() {
		
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		
		setBackground(Color.WHITE);
		
		buttonPanel.setBackground(Color.WHITE);
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
		
		tableScrollPane.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		RequestTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		RequestTable.setModel(new DefaultTableModel(
				new Object[][] {
						
				},
				columnNames
			) {
				Class[] columnTypes = new Class[] {
					String.class, Integer.class
				};
				public Class getColumnClass(int columnIndex) {
					return columnTypes[columnIndex];
				}
			});
			
		add(tableScrollPane);
		add(buttonPanel);
		
		Object[] objects = {this, RequestTable};
		
		btnAcceptFriend.addActionListener(new GenericActionListener(new AddFriendCommand(), objects));
		
		buttonPanel.add(btnAcceptFriend);
			
		setVisible(true);
		
	}
	
	/* Adds a given elements to a table.
	 * PRE: Element has the correct number of columns. */
	public void addElementToTable(Object[] element) {
		
		DefaultTableModel tableModel = (DefaultTableModel) RequestTable.getModel();
		tableModel.addRow(element);
		
	}
	
	/* Removes all of the selected rows in the table */
	public void removeSelectedRowsFromTable() {
		
		DefaultTableModel tableModel = (DefaultTableModel) RequestTable.getModel();
		int[] selectedRows = RequestTable.getSelectedRows();
		if (selectedRows.length > 0) {
            for (int i = selectedRows.length - 1; i >= 0; i--) {
                tableModel.removeRow(selectedRows[i]);
            }
        }
	}
	
	public Object[] getSelectedRowInfo() {
		
		DefaultTableModel tableModel = (DefaultTableModel) RequestTable.getModel();
		int[] selectedRows = RequestTable.getSelectedRows();
		Object[] info = null;
	
		if (selectedRows.length == 1) {
			int row = selectedRows[0];
			
			info = new Object[tableModel.getColumnCount()];
            for (int i = 0; i < tableModel.getColumnCount(); i++)
            	info[i] = tableModel.getValueAt(row, i);
		}
		
		return info;
	}
	
	
	/* Initalise the uploaded files table */
	public void populateFriendRequests(Object[][] uploadedFileRows) {
		
		for (int i = 0; i < uploadedFileRows.length; i++)
			addElementToTable(uploadedFileRows[i]);
		
	}

}
