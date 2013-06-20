package client.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Panel;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import client.controller.IgnoreFriendRequestCommand;
import client.controller.AcceptFriendRequestCommand;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.Box;
import java.awt.Dimension;

public class FriendRequestsPanel extends JPanel {
	
	private final JPanel friendFilesPanel;
	private final JPanel buttonPanel = new JPanel();
	private final JTable requestTable = new JTable();
	private final JScrollPane tableScrollPane = new JScrollPane(requestTable);
	
	/* Table information */
	private final String[] columnNames = {"Username", "Security Level"};
	private final JButton btnAcceptFriend = new JButton("Accept request");
	private final JButton btnIgnoreFriend = new JButton("Ignore request");
	private final Component rigidArea = Box.createRigidArea(new Dimension(0, 50));

	/**
	 * Create the panel.
	 */
	public FriendRequestsPanel(JPanel panel) {

		friendFilesPanel = panel;
		initGUI();
		
	}
	
	private void initGUI() {
		
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		
		setBackground(Color.WHITE);
		
		buttonPanel.setBackground(Color.WHITE);
		
		tableScrollPane.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		requestTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		requestTable.setModel(new DefaultTableModel(
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
		
		Object[] objects = {this, friendFilesPanel};
		Object[] ignoreObjects = {this};
		
		GridBagLayout gbl_buttonPanel = new GridBagLayout();
		gbl_buttonPanel.columnWidths = new int[]{130, 0};
		gbl_buttonPanel.rowHeights = new int[]{24, 24, 0, 0, 0, 0, 0};
		gbl_buttonPanel.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gbl_buttonPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		buttonPanel.setLayout(gbl_buttonPanel);
		btnIgnoreFriend.addActionListener(new GenericActionListener(new IgnoreFriendRequestCommand(), ignoreObjects));
		
		btnAcceptFriend.addActionListener(new GenericActionListener(new AcceptFriendRequestCommand(), objects));
		
		GridBagConstraints gbc_rigidArea = new GridBagConstraints();
		gbc_rigidArea.insets = new Insets(0, 0, 5, 0);
		gbc_rigidArea.gridx = 0;
		gbc_rigidArea.gridy = 0;
		buttonPanel.add(rigidArea, gbc_rigidArea);
		
		btnAcceptFriend.setFont(new Font("Dialog", Font.BOLD, 11));
		GridBagConstraints gbc_btnAcceptFriend = new GridBagConstraints();
		gbc_btnAcceptFriend.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnAcceptFriend.anchor = GridBagConstraints.WEST;
		gbc_btnAcceptFriend.insets = new Insets(0, 0, 5, 0);
		gbc_btnAcceptFriend.gridx = 0;
		gbc_btnAcceptFriend.gridy = 4;
		buttonPanel.add(btnAcceptFriend, gbc_btnAcceptFriend);
		
		btnIgnoreFriend.setFont(new Font("Dialog", Font.BOLD, 11));
		GridBagConstraints gbc_btnIgnoreFriend = new GridBagConstraints();
		gbc_btnIgnoreFriend.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnIgnoreFriend.anchor = GridBagConstraints.WEST;
		gbc_btnIgnoreFriend.gridx = 0;
		gbc_btnIgnoreFriend.gridy = 5;
		buttonPanel.add(btnIgnoreFriend, gbc_btnIgnoreFriend);
			
		setVisible(true);
		
	}
	
	/* Adds a given elements to a table.
	 * PRE: Element has the correct number of columns. */
	public void addElementToTable(Object[] element) {
		
		DefaultTableModel tableModel = (DefaultTableModel) requestTable.getModel();
		tableModel.addRow(element);
		
	}
	
	/* Removes all of the selected rows in the table */
	public void removeSelectedRowsFromTable() {
		
		DefaultTableModel tableModel = (DefaultTableModel) requestTable.getModel();
		int[] selectedRows = requestTable.getSelectedRows();
		if (selectedRows.length > 0) {
            for (int i = selectedRows.length - 1; i >= 0; i--) {
                tableModel.removeRow(selectedRows[i]);
            }
        }
	}
	
	public Object[] getSelectedRowInfo() {
		
		DefaultTableModel tableModel = (DefaultTableModel) requestTable.getModel();
		int[] selectedRows = requestTable.getSelectedRows();
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

	public void friendAdded() {
		
		removeSelectedRowsFromTable();
		
	}

	public void friendIgnored() {
		
		removeSelectedRowsFromTable();
		
	}
	
	public void exit() {
		
		DefaultTableModel tableModel = (DefaultTableModel) requestTable.getModel();
		tableModel.setRowCount(0);
		
	}

}
