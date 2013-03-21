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

import Client.controller.FileUploadCommand;

import java.awt.Font;
import java.io.File;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

public class MenuFrame extends JFrame {

	private JPanel contentPane;
	private final JPanel actions = new JPanel();
	private final JPanel panel_1 = new JPanel();
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

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
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
		setBounds(100, 100, 482, 338);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(1, 2, 0, 0));
		
		contentPane.add(actions);
		GroupLayout gl_actions = new GroupLayout(actions);
		gl_actions.setHorizontalGroup(
			gl_actions.createParallelGroup(Alignment.LEADING)
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
							.addGap(34)
							.addComponent(btnDownloadFile))
						.addGroup(gl_actions.createSequentialGroup()
							.addContainerGap()
							.addComponent(downloadLocationTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(downloadChooseButton, GroupLayout.DEFAULT_SIZE, 81, Short.MAX_VALUE))
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
							.addComponent(securityLevelSpinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap())
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
					.addContainerGap(21, Short.MAX_VALUE))
		);
		btnDownloadFile.setFont(new Font("Dialog", Font.BOLD, 12));
		
		downloadChooseButton.addActionListener(new FileActionListener());
		downloadChooseButton.setFont(new Font("Dialog", Font.BOLD, 10));
		actions.setLayout(gl_actions);
		
		contentPane.add(panel_1);
	}
	
	private void run() {
		setVisible(true);		
	}
	
	private void chooseFile(JTextField textField, boolean directoriesOnly) {
		
		JFileChooser jChoose = new JFileChooser();
		
		if (directoriesOnly)
			 jChoose.setFileSelectionMode( JFileChooser.DIRECTORIES_ONLY);
		
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

