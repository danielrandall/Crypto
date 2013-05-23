package client.view;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.BoxLayout;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import client.controller.DeleteFileCommand;
import client.controller.FileDownloadCommand;

public class MyFilesPanel extends JPanel {
	
	/* Frame to transfer to when the user wishes to upload a file */
	private Frame uploadFileFrame = new UploadFileFrame(this);
	
	private final JButton btnUploadFile = new JButton("Upload file");
	private final JPanel buttonPanel = new JPanel();
	private final JTable fileTable = new JTable();
	private final JScrollPane tableScrollPane = new JScrollPane(fileTable);
	
	/* Table information */
	private final String[] columnNames = {"File Name", "Security Level"};
	private final JButton btnDeleteFile = new JButton("Delete file");
	private final JButton btnDownloadFile = new JButton("Download file");

	/**
	 * Create the panel.
	 */
	public MyFilesPanel() {

		initGUI();
		
	}
	
	private void initGUI() {
		
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		
		setBackground(Color.WHITE);
		
		btnUploadFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				uploadFileFrame.setVisible(true);
			}
		});
		
		btnUploadFile.setFont(new Font("Dialog", Font.BOLD, 12));
		
		buttonPanel.setBackground(Color.WHITE);
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
		buttonPanel.add(btnUploadFile);
		
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
		
		Object[] objects1 = {this, fileTable};
		btnDownloadFile.addActionListener(new GenericActionListener(new FileDownloadCommand(), objects1));
		
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
	public void populateUploadedFiles(Object[][] uploadedFileRows) {
		
		for (int i = 0; i < uploadedFileRows.length; i++)
			addElementToTable(uploadedFileRows[i]);
		
	}

}
