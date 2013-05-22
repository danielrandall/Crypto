package client.view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import client.controller.FileUploadCommand;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.factories.FormFactory;
import javax.swing.JLabel;
import java.awt.Font;

import javax.swing.JFileChooser;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Component;
import javax.swing.Box;
import javax.swing.JSlider;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.awt.Color;


/* This frame does not implement the window listener of BaseFrame as we do not
 * wish for the application to exit when this window closes. */
public class UploadFileFrame extends BaseFrame {

	private JPanel contentPane;
	private final JLabel lblFileToUpload = new JLabel("File to upload");
	private final JTextField fileNameTextField = new JTextField();
	private final JButton btnChoose = new JButton("Choose");
	private final JLabel lblSecurityLevel = new JLabel("Security Level");
	private final JButton btnUploadFile = new JButton("Upload file");
	private final JLabel lblUploadFile = new JLabel("Upload a file");
	private final JPanel buttonPanel = new JPanel();
	private final JButton btnCancel = new JButton("Cancel");
	private final Component horizontalStrut = Box.createHorizontalStrut(20);
	private final JSlider sliderSecurityLevel = new JSlider();
	private final Component verticalStrut = Box.createVerticalStrut(20);
	private final Component verticalStrut_1 = Box.createVerticalStrut(5);


	/**
	 * Create the frame.
	 */
	public UploadFileFrame() {
		initGUI();
	}
	private void initGUI() {
		
		Object[] objects = {this, fileNameTextField, sliderSecurityLevel};
		btnUploadFile.addActionListener(new GenericActionListener(new FileUploadCommand(), objects));		
		
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
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
				RowSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,}));
		
		contentPane.add(verticalStrut, "1, 3");
		
		contentPane.add(lblUploadFile, "3, 3");
		lblUploadFile.setFont(museosans_900_18p);
		lblFileToUpload.setFont(museosans_700_14p);
		
		contentPane.add(lblFileToUpload, "3, 7");
		fileNameTextField.setColumns(10);
		
		contentPane.add(fileNameTextField, "3, 9, fill, default");
		btnChoose.setFont(new Font("Dialog", Font.BOLD, 10));
		btnChoose.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				chooseFile(fileNameTextField, false);
			}
		});
		
		contentPane.add(btnChoose, "5, 9");
		
		contentPane.add(verticalStrut_1, "3, 11");
		lblSecurityLevel.setFont(museosans_700_14p);
		
		contentPane.add(lblSecurityLevel, "3, 13, default, bottom");
		
		contentPane.add(horizontalStrut, "7, 13");
		sliderSecurityLevel.setMinimum(1);
		sliderSecurityLevel.setMaximum(5);
		sliderSecurityLevel.setMajorTickSpacing(1);
		sliderSecurityLevel.setPaintTicks(true);
		sliderSecurityLevel.setPaintLabels(true);
		sliderSecurityLevel.setFont(museosans_500_12p);
		
		contentPane.add(sliderSecurityLevel, "3, 15, default, top");
		buttonPanel.setBackground(Color.WHITE);
		
		contentPane.add(buttonPanel, "3, 21, 3, 1, right, fill");
		buttonPanel.add(btnUploadFile);
		btnUploadFile.setFont(new Font("Dialog", Font.BOLD, 12));
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
		btnCancel.setFont(new Font("Dialog", Font.BOLD, 12));
		
		buttonPanel.add(btnCancel);
	}
	
	/* Method to invoke a file chooser */
	private void chooseFile(JTextField textField, boolean directoriesOnly) {
		
		JFileChooser jChoose = new JFileChooser();
		/*
		if (directoriesOnly)
			 jChoose.setFileSelectionMode( JFileChooser.DIRECTORIES_ONLY);
		*/
		int returnVal = jChoose.showDialog(UploadFileFrame.this, "Select");
		if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = jChoose.getSelectedFile();
            String fileName = file.getPath();
            textField.setText(fileName);
		}
	}

}
