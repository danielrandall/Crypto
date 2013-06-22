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
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.Box;
import java.awt.Dimension;

public class FriendsPanel extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5797972076111277241L;

	private Frame friendFrame = new AddNewFriendFrame();
	
	private final JButton btnAddFriend = new JButton("Add Friend");
	private final JPanel buttonPanel = new JPanel();
	private final JTable friendTable = new JTable();
	private final JScrollPane tableScrollPane = new JScrollPane(friendTable);
	
	/* Table information */
	private final String[] columnNames = {"Friend", "Security Level"};
	private final JButton btnDeleteFriend = new JButton("Delete friend");
	private final Component rigidArea = Box.createRigidArea(new Dimension(0, 50));

	/**
	 * Create the panel.
	 */
	public FriendsPanel() {
		
		initGUI();

	}
	
	private void initGUI() {
		
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		
		setBackground(Color.WHITE);
		
		buttonPanel.setBackground(Color.WHITE);
		GridBagLayout gbl_buttonPanel = new GridBagLayout();
		gbl_buttonPanel.columnWidths = new int[]{118, 0};
		gbl_buttonPanel.rowHeights = new int[]{24, 24, 0, 0, 0, 0, 0, 0};
		gbl_buttonPanel.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gbl_buttonPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		buttonPanel.setLayout(gbl_buttonPanel);
		
		tableScrollPane.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		friendTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		friendTable.setModel(new DefaultTableModel(
				new Object[][] {
						
				},
				columnNames
			) {
				/**
				 * 
				 */
				private static final long serialVersionUID = 3069666961602779225L;
				@SuppressWarnings("rawtypes")
				Class[] columnTypes = new Class[] {
					String.class, Integer.class
				};

				public Class<?> getColumnClass(int columnIndex) {
					return columnTypes[columnIndex];
				}
			});
			
		add(tableScrollPane);
		add(buttonPanel);
		
		Object[] objects = {this};
		btnDeleteFriend.addActionListener(new GenericActionListener(new DeleteFriendCommand(), objects));
		
		btnAddFriend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				friendFrame.setVisible(true);
			}
		});
		
		GridBagConstraints gbc_rigidArea = new GridBagConstraints();
		gbc_rigidArea.insets = new Insets(0, 0, 5, 0);
		gbc_rigidArea.gridx = 0;
		gbc_rigidArea.gridy = 1;
		buttonPanel.add(rigidArea, gbc_rigidArea);
		
		btnAddFriend.setFont(new Font("Dialog", Font.BOLD, 11));
		GridBagConstraints gbc_btnAddFriend = new GridBagConstraints();
		gbc_btnAddFriend.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnAddFriend.anchor = GridBagConstraints.WEST;
		gbc_btnAddFriend.insets = new Insets(0, 0, 5, 0);
		gbc_btnAddFriend.gridx = 0;
		gbc_btnAddFriend.gridy = 5;
		buttonPanel.add(btnAddFriend, gbc_btnAddFriend);
		
		btnDeleteFriend.setFont(new Font("Dialog", Font.BOLD, 11));
		GridBagConstraints gbc_btnDeleteFriend = new GridBagConstraints();
		gbc_btnDeleteFriend.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnDeleteFriend.anchor = GridBagConstraints.WEST;
		gbc_btnDeleteFriend.gridx = 0;
		gbc_btnDeleteFriend.gridy = 6;
		buttonPanel.add(btnDeleteFriend, gbc_btnDeleteFriend);
			
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
	public void populateFriends(Object[][] uploadedFileRows) {
		
		for (int i = 0; i < uploadedFileRows.length; i++)
			addElementToTable(uploadedFileRows[i]);
		
	}
	
	public void exit() {
		
		DefaultTableModel tableModel = (DefaultTableModel) friendTable.getModel();
		tableModel.setRowCount(0);
		
	}

}
