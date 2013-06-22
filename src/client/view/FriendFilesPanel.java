package client.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import client.controller.DeleteFileCommand;
import client.controller.FriendFileDownloadCommand;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.Box;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class FriendFilesPanel extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2067989960916445599L;

	private UpdateFrame updateFrame = new UpdateFrame();
	
	private final JPanel buttonPanel = new JPanel();
	private final JTable fileTable = new JTable();
	private final JScrollPane tableScrollPane = new JScrollPane(fileTable);
	
	/* Table information */
	private final String[] columnNames = {"File Name", "Owner"};
	private final JButton btnDeleteFile = new JButton("Delete file");
	private final JButton btnDownloadFile = new JButton("Download file");
	private final Component rigidArea = Box.createRigidArea(new Dimension(0, 50));
	private final JButton btnUpdate = new JButton("Update file");

	/**
	 * Create the panel.
	 */
	public FriendFilesPanel() {
		
		initGUI();

	}
	
	private void initGUI() {
		
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		
		setBackground(Color.WHITE);
		
		buttonPanel.setBackground(Color.WHITE);
		
		tableScrollPane.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		fileTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		fileTable.setModel(new DefaultTableModel(
				new Object[][] {
						
				},
				columnNames
			) {
				/**
				 * 
				 */
				private static final long serialVersionUID = -6312900869039524928L;
				
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
		
		Object[] objects = {this, fileTable};
		
		Object[] objects1 = {this, fileTable};
		GridBagLayout gbl_buttonPanel = new GridBagLayout();
		gbl_buttonPanel.columnWidths = new int[]{123, 0};
		gbl_buttonPanel.rowHeights = new int[]{24, 24, 0, 0, 0, 0, 0, 0, 0};
		gbl_buttonPanel.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gbl_buttonPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		buttonPanel.setLayout(gbl_buttonPanel);
		btnDeleteFile.addActionListener(new GenericActionListener(new DeleteFileCommand(), objects));
		
		GridBagConstraints gbc_rigidArea = new GridBagConstraints();
		gbc_rigidArea.insets = new Insets(0, 0, 5, 0);
		gbc_rigidArea.gridx = 0;
		gbc_rigidArea.gridy = 1;
		buttonPanel.add(rigidArea, gbc_rigidArea);
		btnDownloadFile.addActionListener(new GenericActionListener(new FriendFileDownloadCommand(), objects1));
		btnDownloadFile.setFont(new Font("Dialog", Font.BOLD, 11));
		
		GridBagConstraints gbc_btnDownloadFile = new GridBagConstraints();
		gbc_btnDownloadFile.insets = new Insets(0, 0, 5, 0);
		gbc_btnDownloadFile.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnDownloadFile.anchor = GridBagConstraints.WEST;
		gbc_btnDownloadFile.gridx = 0;
		gbc_btnDownloadFile.gridy = 3;
		buttonPanel.add(btnDownloadFile, gbc_btnDownloadFile);
		btnDeleteFile.setFont(new Font("Dialog", Font.BOLD, 11));
		
		GridBagConstraints gbc_btnDeleteFile = new GridBagConstraints();
		gbc_btnDeleteFile.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnDeleteFile.anchor = GridBagConstraints.WEST;
		gbc_btnDeleteFile.insets = new Insets(0, 0, 5, 0);
		gbc_btnDeleteFile.gridx = 0;
		gbc_btnDeleteFile.gridy = 4;
		buttonPanel.add(btnDeleteFile, gbc_btnDeleteFile);
		
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnNewButton.insets = new Insets(0, 0, 5, 0);
		gbc_btnNewButton.gridx = 0;
		gbc_btnNewButton.gridy = 5;
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String fileToBeUpdated = (String) getSelectedRowInfo()[0];
				String owner = (String) getSelectedRowInfo()[1];
				if (owner != null)
					updateFrame.setFileAndOwner(fileToBeUpdated, owner);
					updateFrame.setVisible(true);
			}
		});
		btnUpdate.setFont(new Font("Dialog", Font.BOLD, 11));
		buttonPanel.add(btnUpdate, gbc_btnNewButton);
			
		setVisible(true);
		
	}
	
	/* Adds a given elements to a table.
	 * PRE: Element has the correct number of columns. */
	public void addElementToTable(Object[] element) {
		
		DefaultTableModel tableModel = (DefaultTableModel) fileTable.getModel();
		tableModel.addRow(element);
		
	}
	
	/* Removes all of the selected rows in the table */
	public void removeSelectedRowsFromTable() {
		
		DefaultTableModel tableModel = (DefaultTableModel) fileTable.getModel();
		int[] selectedRows = fileTable.getSelectedRows();
		if (selectedRows.length > 0) {
            for (int i = selectedRows.length - 1; i >= 0; i--) {
                tableModel.removeRow(selectedRows[i]);
            }
        }
	}
	
	public Object[] getSelectedRowInfo() {
		
		DefaultTableModel tableModel = (DefaultTableModel) fileTable.getModel();
		int[] selectedRows = fileTable.getSelectedRows();
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
	public void populateFriendFiles(Object[][] uploadedFileRows) {
		
		if (uploadedFileRows == null)
			return;

		for (int i = 0; i < uploadedFileRows.length; i++) {
			if (uploadedFileRows[i][0] == null)
				continue;
			else
				addElementToTable(uploadedFileRows[i]);
		}
	}
	
	public void exit() {
		
		DefaultTableModel tableModel = (DefaultTableModel) fileTable.getModel();
		tableModel.setRowCount(0);
		
	}

}
