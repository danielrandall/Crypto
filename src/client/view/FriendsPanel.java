package client.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import client.controller.DeleteFriendCommand;

public class FriendsPanel extends JPanel {
	
	private Frame friendFrame = new AddNewFriendFrame();
	
	private final JButton btnAddFriend = new JButton("Add Friend");
	private final JPanel buttonPanel = new JPanel();
	private final JTable friendTable = new JTable();
	private final JScrollPane tableScrollPane = new JScrollPane(friendTable);
	
	/* Table information */
	private final String[] columnNames = {"Friend", "Security Level"};
	private final JButton btnDeleteFriend = new JButton("Delete friend");

	/**
	 * Create the panel.
	 */
	public FriendsPanel() {
		
		initGUI();

	}
	
private void initGUI() {
		
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		
		setBackground(Color.WHITE);
		
		btnAddFriend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				friendFrame.setVisible(true);
			}
		});
		
		btnAddFriend.setFont(new Font("Dialog", Font.BOLD, 12));
		
		buttonPanel.setBackground(Color.WHITE);
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
		buttonPanel.add(btnAddFriend);
		
		tableScrollPane.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		friendTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		friendTable.setModel(new DefaultTableModel(
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
		
		Object[] objects = {this, friendTable};
		
		btnDeleteFriend.addActionListener(new GenericActionListener(new DeleteFriendCommand(), objects));
		
		buttonPanel.add(btnDeleteFriend);
			
		setVisible(true);
		
	}
	
	/* Adds a given elements to a table.
	 * PRE: Element has the correct number of columns. */
	public void addElementToTable(Object[] element) {
		
		DefaultTableModel tableModel = (DefaultTableModel) friendTable.getModel();
		tableModel.addRow(element);
		
	}
	
	/* Removes all of the selected rows in the table */
	public void removeSelectedRowsFromTable() {
		
		DefaultTableModel tableModel = (DefaultTableModel) friendTable.getModel();
		int[] selectedRows = friendTable.getSelectedRows();
		if (selectedRows.length > 0) {
            for (int i = selectedRows.length - 1; i >= 0; i--) {
                tableModel.removeRow(selectedRows[i]);
            }
        }
	}
	
	public Object[] getSelectedRowInfo() {
		
		DefaultTableModel tableModel = (DefaultTableModel) friendTable.getModel();
		int[] selectedRows = friendTable.getSelectedRows();
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
	public void populateUploadedFiles(Object[][] uploadedFileRows) {
		
		for (int i = 0; i < uploadedFileRows.length; i++)
			addElementToTable(uploadedFileRows[i]);
		
	}

}
