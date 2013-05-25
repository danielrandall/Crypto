package client.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import client.controller.DeleteFileCommand;
import client.controller.FileDownloadCommand;

public class FriendFilesPanel extends JPanel {
	
	private final JPanel buttonPanel = new JPanel();
	private final JTable fileTable = new JTable();
	private final JScrollPane tableScrollPane = new JScrollPane(fileTable);
	
	/* Table information */
	private final String[] columnNames = {"File Name", "Owner"};
	private final JButton btnDeleteFile = new JButton("Delete file");
	private final JButton btnDownloadFile = new JButton("Download file");

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
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
		
		tableScrollPane.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		fileTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		fileTable.setModel(new DefaultTableModel(
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
		
		Object[] objects = {this, fileTable};
		btnDeleteFile.addActionListener(new GenericActionListener(new DeleteFileCommand(), objects));
		btnDeleteFile.setFont(new Font("Dialog", Font.BOLD, 11));
		
		Object[] objects1 = {this, fileTable};
		btnDownloadFile.addActionListener(new GenericActionListener(new FileDownloadCommand(), objects1));
		btnDownloadFile.setFont(new Font("Dialog", Font.BOLD, 11));
		
		buttonPanel.add(btnDeleteFile);
		
		buttonPanel.add(btnDownloadFile);
			
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

}
