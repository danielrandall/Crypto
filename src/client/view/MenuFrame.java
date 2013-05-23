package client.view;

import java.awt.EventQueue;
import java.awt.Frame;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;


import java.awt.Font;
import java.io.File;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import client.controller.FileDownloadCommand;
import javax.swing.JTabbedPane;
import java.awt.Color;

public class MenuFrame extends BaseFrame {
	
	/* Frame to transfer to when the user wishes to upload a file */
	Frame uploadFileFrame = new UploadFileFrame();
	
	/* Panels used for the tabs */
	private final MyFilesPanel myFilesPanel = new MyFilesPanel();
	private final FriendsPanel friendsPanel = new FriendsPanel();

	private JPanel contentPane;
	private final JPanel actions = new JPanel();
	private final JButton btnUploadFile = new JButton("Upload file");
	private final JTextField fileToDownTextField = new JTextField();
	private final JLabel lblFileName = new JLabel("File name");
	private final JLabel lblDownloadLocation = new JLabel("Download location");
	private final JTextField downloadLocationTextField = new JTextField();
	private final JButton downloadChooseButton = new JButton("Choose");
	private final JButton btnDownloadFile = new JButton("Download file");
	private final JSpinner minSecuritySpinner = new JSpinner();
	private final JTabbedPane tabbedOptionsPane = new JTabbedPane(JTabbedPane.TOP);

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		try {
		    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
		        if ("Nimbus".equals(info.getName())) {
		            UIManager.setLookAndFeel(info.getClassName());
		            break;
		        }
		    }
		} catch (Exception e) {
		    // If Nimbus is not available, you can set the GUI to another look and feel.
		}
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MenuFrame frame = new MenuFrame();
					frame.run();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MenuFrame() {
		downloadLocationTextField.setColumns(10);
		lblDownloadLocation.setFont(new Font("Dialog", Font.BOLD, 10));
		lblFileName.setFont(new Font("Dialog", Font.BOLD, 10));
		fileToDownTextField.setColumns(10);
		btnUploadFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				uploadFileFrame.setVisible(true);
			}
		});
		
		
		
		btnUploadFile.setFont(new Font("Dialog", Font.BOLD, 12));
		initGUI();
	}
	
	private void initGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 552, 498);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(1, 2, 0, 0));
		
		contentPane.add(tabbedOptionsPane);
		
		tabbedOptionsPane.addTab("My Files", null, myFilesPanel, null);
		tabbedOptionsPane.addTab("My Friends", null, friendsPanel, null);
		
		
		contentPane.add(actions);
		
		GroupLayout gl_actions = new GroupLayout(actions);
		gl_actions.setHorizontalGroup(
			gl_actions.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_actions.createSequentialGroup()
					.addGroup(gl_actions.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_actions.createSequentialGroup()
							.addContainerGap()
							.addComponent(fileToDownTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(135))
						.addGroup(gl_actions.createSequentialGroup()
							.addContainerGap()
							.addComponent(downloadLocationTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(downloadChooseButton, GroupLayout.DEFAULT_SIZE, 123, Short.MAX_VALUE))
						.addGroup(gl_actions.createSequentialGroup()
							.addContainerGap()
							.addComponent(lblDownloadLocation))
						.addGroup(gl_actions.createSequentialGroup()
							.addContainerGap()
							.addComponent(lblFileName))
						.addGroup(gl_actions.createSequentialGroup()
							.addGap(34)
							.addComponent(btnDownloadFile)))
					.addContainerGap())
		);
		gl_actions.setVerticalGroup(
			gl_actions.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_actions.createSequentialGroup()
					.addGap(151)
					.addComponent(lblFileName)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(fileToDownTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblDownloadLocation)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_actions.createParallelGroup(Alignment.BASELINE)
						.addComponent(downloadLocationTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(downloadChooseButton, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnDownloadFile)
					.addContainerGap(223, Short.MAX_VALUE))
		);
		btnDownloadFile.setFont(new Font("Dialog", Font.BOLD, 12));
		
		Object[] objects2 = {this, fileToDownTextField, downloadLocationTextField};
	    btnDownloadFile.addActionListener(new GenericActionListener(new FileDownloadCommand(), objects2));
		
		downloadChooseButton.addActionListener(new FileActionListener());
		downloadChooseButton.setFont(new Font("Dialog", Font.BOLD, 10));
		actions.setLayout(gl_actions);
	}
	
	private void run() {
		setVisible(true);		
	}
	
	private void chooseFile(JTextField textField, boolean directoriesOnly) {
		
		JFileChooser jChoose = new JFileChooser();
		/*
		if (directoriesOnly)
			 jChoose.setFileSelectionMode( JFileChooser.DIRECTORIES_ONLY);
		*/
		int returnVal = jChoose.showDialog(MenuFrame.this, "Select");
		if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = jChoose.getSelectedFile();
            String fileName = file.getPath();
            textField.setText(fileName);
		}
	}
	
	
	private class FileActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {

			
			if (arg0.getSource().equals(downloadChooseButton)) {
				
				chooseFile(downloadLocationTextField, true);
			}
		}
		
	}
	
	/* Pass rows to the myFiles panel to put into the table */
	public void populateUploadedFiles(Object[][] uploadedFileRows) {
		
		myFilesPanel.populateUploadedFiles(uploadedFileRows);
		
	}
}	

