package Client.view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.CardLayout;
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

import Client.controller.AddFriendCommand;
import Client.controller.FileDownloadCommand;
import Client.controller.FileUploadCommand;

import java.awt.Font;
import java.io.File;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.BoxLayout;
import javax.swing.JList;

public class MenuFrame extends JFrame {

	private JPanel contentPane;
	private final JPanel actions = new JPanel();
	private final JPanel files = new JPanel();
	private final JTextField uploadFileTextField = new JTextField();
	private final JButton uploadChooseButton = new JButton("Choose");
	private final JButton btnUploadFile = new JButton("Upload file");
	private final JTextField fileToDownTextField = new JTextField();
	private final JLabel lblFileName = new JLabel("File name");
	private final JLabel lblDownloadLocation = new JLabel("Download location");
	private final JTextField downloadLocationTextField = new JTextField();
	private final JButton downloadChooseButton = new JButton("Choose");
	private final JLabel lblSecurityLevel = new JLabel("Security Level");
	private final JButton btnDownloadFile = new JButton("Download file");
	private final JLabel lblFileLocation = new JLabel("File location");
	private final JSpinner securityLevelSpinner = new JSpinner();
	private JTextField usernameToAddTextField;
	private final JLabel MinSecurityLabel = new JLabel("Min Security Level");
	private final JLabel MaxSecurityLabel = new JLabel("Max Security Level");
	private final JSpinner maxSecuritySpinner = new JSpinner();
	private final JSpinner minSecuritySpinner = new JSpinner();
	private JLabel lblUploadedFiles;
	private JList list;

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
		MaxSecurityLabel.setFont(new Font("Dialog", Font.BOLD, 10));
		MinSecurityLabel.setFont(new Font("Dialog", Font.BOLD, 10));
		securityLevelSpinner.setModel(new SpinnerNumberModel(1, 1, 5, 1));
		lblFileLocation.setFont(new Font("Dialog", Font.BOLD, 10));
		lblSecurityLevel.setFont(new Font("Dialog", Font.BOLD, 10));
		downloadLocationTextField.setColumns(10);
		lblDownloadLocation.setFont(new Font("Dialog", Font.BOLD, 10));
		lblFileName.setFont(new Font("Dialog", Font.BOLD, 10));
		fileToDownTextField.setColumns(10);
		
		Object[] objects = {this, uploadFileTextField, securityLevelSpinner};
		btnUploadFile.addActionListener(new GenericActionListener(new FileUploadCommand(), objects));
		
		btnUploadFile.setFont(new Font("Dialog", Font.BOLD, 12));
		uploadChooseButton.addActionListener(new FileActionListener());
		uploadChooseButton.setFont(new Font("Dialog", Font.BOLD, 10));
		uploadFileTextField.setColumns(10);
		initGUI();
	}
	
	private void initGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 552, 498);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(1, 2, 0, 0));
		
		contentPane.add(actions);
		
		usernameToAddTextField = new JTextField();
		usernameToAddTextField.setColumns(10);
		
		JLabel lblUsername = new JLabel("Username");
		lblUsername.setFont(new Font("Dialog", Font.BOLD, 10));
		
		JButton btnAddFriend = new JButton("Add friend");
		
		Object[] objects = {this, usernameToAddTextField, minSecuritySpinner, maxSecuritySpinner};
		btnAddFriend.addActionListener(new GenericActionListener(new AddFriendCommand(), objects));
		
		JSpinner minSecuritySpinner = new JSpinner();
		minSecuritySpinner.setModel(new SpinnerNumberModel(1, 1, 5, 1));
		GroupLayout gl_actions = new GroupLayout(actions);
		gl_actions.setHorizontalGroup(
			gl_actions.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_actions.createSequentialGroup()
					.addGroup(gl_actions.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_actions.createSequentialGroup()
							.addContainerGap()
							.addGroup(gl_actions.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_actions.createSequentialGroup()
									.addComponent(uploadFileTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addGap(18)
									.addComponent(uploadChooseButton))
								.addComponent(fileToDownTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
						.addGroup(gl_actions.createSequentialGroup()
							.addContainerGap()
							.addComponent(downloadLocationTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(downloadChooseButton, GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE))
						.addGroup(gl_actions.createSequentialGroup()
							.addContainerGap()
							.addComponent(lblDownloadLocation))
						.addGroup(gl_actions.createSequentialGroup()
							.addContainerGap()
							.addComponent(lblFileName))
						.addGroup(gl_actions.createSequentialGroup()
							.addContainerGap()
							.addComponent(lblFileLocation))
						.addGroup(gl_actions.createSequentialGroup()
							.addContainerGap()
							.addGroup(gl_actions.createParallelGroup(Alignment.LEADING)
								.addComponent(lblSecurityLevel)
								.addGroup(gl_actions.createSequentialGroup()
									.addGap(24)
									.addComponent(btnUploadFile, GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE))))
						.addGroup(gl_actions.createSequentialGroup()
							.addContainerGap()
							.addComponent(securityLevelSpinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_actions.createSequentialGroup()
							.addGap(34)
							.addComponent(btnDownloadFile))
						.addGroup(gl_actions.createSequentialGroup()
							.addContainerGap()
							.addGroup(gl_actions.createParallelGroup(Alignment.LEADING)
								.addComponent(usernameToAddTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblUsername))))
					.addContainerGap())
				.addGroup(gl_actions.createSequentialGroup()
					.addContainerGap(18, Short.MAX_VALUE)
					.addComponent(MinSecurityLabel)
					.addGap(18)
					.addComponent(MaxSecurityLabel)
					.addGap(26))
				.addGroup(Alignment.LEADING, gl_actions.createSequentialGroup()
					.addGap(43)
					.addComponent(minSecuritySpinner, GroupLayout.PREFERRED_SIZE, 44, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 78, Short.MAX_VALUE)
					.addComponent(maxSecuritySpinner, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)
					.addGap(64))
				.addGroup(gl_actions.createSequentialGroup()
					.addGap(67)
					.addComponent(btnAddFriend)
					.addContainerGap(96, Short.MAX_VALUE))
		);
		gl_actions.setVerticalGroup(
			gl_actions.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_actions.createSequentialGroup()
					.addComponent(lblFileLocation)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_actions.createParallelGroup(Alignment.BASELINE)
						.addComponent(uploadFileTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(uploadChooseButton, GroupLayout.PREFERRED_SIZE, 17, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblSecurityLevel)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(securityLevelSpinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(btnUploadFile, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
					.addGap(33)
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
					.addPreferredGap(ComponentPlacement.RELATED, 32, Short.MAX_VALUE)
					.addComponent(lblUsername)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(usernameToAddTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_actions.createParallelGroup(Alignment.LEADING)
						.addComponent(MaxSecurityLabel)
						.addComponent(MinSecurityLabel))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_actions.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_actions.createSequentialGroup()
							.addComponent(minSecuritySpinner, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED))
						.addGroup(gl_actions.createSequentialGroup()
							.addComponent(maxSecuritySpinner, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
							.addGap(12)))
					.addComponent(btnAddFriend)
					.addGap(40))
		);
		maxSecuritySpinner.setModel(new SpinnerNumberModel(1, 1, 5, 1));
		btnDownloadFile.setFont(new Font("Dialog", Font.BOLD, 12));
		
		Object[] objects2 = {this, fileToDownTextField, downloadLocationTextField};
	    btnDownloadFile.addActionListener(new GenericActionListener(new FileDownloadCommand(), objects2));
		
		downloadChooseButton.addActionListener(new FileActionListener());
		downloadChooseButton.setFont(new Font("Dialog", Font.BOLD, 10));
		actions.setLayout(gl_actions);
		
		contentPane.add(files);
		files.setLayout(new BoxLayout(files, BoxLayout.PAGE_AXIS));
		
		lblUploadedFiles = new JLabel("Uploaded files");
		files.add(lblUploadedFiles);
		
		list = new JList();
		files.add(list);
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
			
			if (arg0.getSource().equals(uploadChooseButton)) {
				
				chooseFile(uploadFileTextField, false);
			}
			
			if (arg0.getSource().equals(downloadChooseButton)) {
				
				chooseFile(downloadLocationTextField, true);
			}
		}
		
	}
}	

