package client.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import client.controller.UpdateCommand;

import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;
import java.awt.Dimension;

public class UpdateFrame extends BaseFrame {
	
	/* File being updated */
	private String fileToBeUpdated = "";
	private String fileOwner = "";

	private JPanel contentPane;
	private final JLabel lblFileToUpload = new JLabel("File to update with");
	private final JTextField fileNameTextField = new JTextField();
	private final JButton btnChoose = new JButton("Choose");
	private final JButton btnUpdateFile = new JButton("Update file");
	private final JLabel lblUpdateFile = new JLabel();
	private final JPanel buttonPanel = new JPanel();
	private final JButton btnCancel = new JButton("Cancel");
	private final Component verticalStrut = Box.createVerticalStrut(20);
	private final Component verticalStrut_1 = Box.createVerticalStrut(5);
	private final Component horizontalStrut = Box.createHorizontalStrut(20);
	private final JLabel lblfileName = new JLabel("");
	private final Component verticalStrut_2 = Box.createVerticalStrut(10);


	/**
	 * Create the frame.
	 */
	public UpdateFrame() {
		
		initGUI();
	}
	
	private void initGUI() {
		
		Object[] objects = {this};
		btnUpdateFile.addActionListener(new GenericActionListener(new UpdateCommand(), objects));		
		
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 450, 250);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("min(50dlu;default):grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,},
			new RowSpec[] {
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,}));
		
		contentPane.add(verticalStrut, "1, 3");
		
		contentPane.add(lblUpdateFile, "3, 3");
		lblUpdateFile.setFont(museosans_900_18p);
		lblfileName.setFont(museosans_900_18p);
		
		contentPane.add(lblfileName, "3, 5");
		
		contentPane.add(verticalStrut_2, "3, 9");
		
		lblFileToUpload.setFont(museosans_700_14p);
		
		contentPane.add(lblFileToUpload, "3, 11");
		fileNameTextField.setColumns(10);
		
		contentPane.add(fileNameTextField, "3, 13, fill, default");
		btnChoose.setFont(new Font("Dialog", Font.BOLD, 10));
		btnChoose.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				chooseFile(fileNameTextField, false);
			}
		});
		
		contentPane.add(btnChoose, "5, 13");
		
		contentPane.add(horizontalStrut, "7, 13");
		
		contentPane.add(verticalStrut_1, "3, 15");
		
		buttonPanel.setBackground(Color.WHITE);
		
		contentPane.add(buttonPanel, "3, 27, 3, 1, right, fill");
		buttonPanel.add(btnUpdateFile);
		btnUpdateFile.setFont(new Font("Dialog", Font.BOLD, 11));
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
		btnCancel.setFont(new Font("Dialog", Font.BOLD, 11));
		
		buttonPanel.add(btnCancel);
	}
	
	/* Method to invoke a file chooser */
	private void chooseFile(JTextField textField, boolean directoriesOnly) {
		
		JFileChooser jChoose = new JFileChooser();

		int returnVal = jChoose.showDialog(UpdateFrame.this, "Select");
		if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = jChoose.getSelectedFile();
            String fileName = file.getPath();
            textField.setText(fileName);
		}
	}
	
	/* Called when a file successfully uploads */
	public void fileUpdated() {
		
		fileNameTextField.setText("");
		setVisible(false);
		
	}
	
	/* Returns the file location that the user has selected */
	public String getFileLocation() {
		
		return fileNameTextField.getText();
		
	}
	
	public void setFileAndOwner(String fileToBeUpdated, String owner) {
		
		this.fileToBeUpdated = fileToBeUpdated;
		this.fileOwner = owner;
		
		lblUpdateFile.setText("Update " + this.fileOwner + "'s file: ");
		lblfileName.setText(this.fileToBeUpdated);
		
	}
	
	public String getFileOwner() {
		
		return fileOwner;
		
	}
	
	public String getFileToBeUpdated() {
		
		return fileToBeUpdated;
		
	}

	public String getNewFileLocation() {
		
		return fileNameTextField.getText();
		
	}

}
