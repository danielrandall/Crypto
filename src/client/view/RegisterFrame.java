package client.view;

import java.awt.Frame;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;


import client.controller.RegisterCommand;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.factories.FormFactory;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;
import javax.swing.SwingConstants;
import java.awt.Component;
import javax.swing.Box;
import java.awt.Font;

public class RegisterFrame extends BaseFrame {
	
	/* Frame to transfer to when a user cancels registration */
	private Frame prevFrame;
	/* Menu to transfer to if a user successfully completes registration */
	private MenuFrame menuFrame = new MenuFrame();
	

	private JPanel contentPane;
	private JLabel lblUsername;
	private JTextField usernameField;
	private JLabel lblUsernameError;
	private JLabel lblPassword;
	private JPasswordField passwordField;
	private JLabel lblReenterPassword;
	private JPasswordField reenterPasswordField;
	private JLabel lblPasswordError;
	private JPanel buttonPanel;
	private JButton btnRegister;
	private JButton btnCancel;
	private JLabel lblHeader;
	private final Component verticalStrut = Box.createVerticalStrut(15);

	/**
	 * Create the frame.
	 */
	public RegisterFrame(Frame frame) {
		prevFrame = frame;
		initGUI();
	}
	
	private void initGUI() {
		addWindowListener(this);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 278);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),},
			new RowSpec[] {
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
				RowSpec.decode("default:grow"),}));
		
		lblHeader = new JLabel("Create a new account");
		lblHeader.setHorizontalAlignment(SwingConstants.LEFT);
		lblHeader.setFont(museosans_900_18p);
		contentPane.add(lblHeader, "4, 2, 4, 6, default, top");
		contentPane.add(verticalStrut, "2, 4");
		
		lblUsername = new JLabel("Username");
		lblUsername.setFont(museosans_700_14p);
		contentPane.add(lblUsername, "4, 8, left, default");
		
		usernameField = new JTextField();
		contentPane.add(usernameField, "6, 8, left, default");
		usernameField.setColumns(10);
		
		lblUsernameError = new JLabel("Username already in use");
		lblUsernameError.setForeground(Color.RED);
		lblUsernameError.setVisible(false);
		lblUsernameError.setFont(museosans_700_14p);
		contentPane.add(lblUsernameError, "8, 8, left, default");
		
		lblPassword = new JLabel("Password");
		lblPassword.setFont(museosans_700_14p);
		contentPane.add(lblPassword, "4, 10, left, default");
		
		passwordField = new JPasswordField();
		contentPane.add(passwordField, "6, 10, left, default");
		passwordField.setColumns(10);
		
		lblReenterPassword = new JLabel("Re-enter password");
		lblReenterPassword.setFont(museosans_700_14p);
		contentPane.add(lblReenterPassword, "4, 12, left, default");
		
		reenterPasswordField = new JPasswordField();
		contentPane.add(reenterPasswordField, "6, 12, left, default");
		reenterPasswordField.setColumns(10);
		
		lblPasswordError = new JLabel("Passwords do not match");
		lblPasswordError.setForeground(Color.RED);
		lblPasswordError.setVisible(false);
		lblPasswordError.setFont(museosans_700_14p);
		contentPane.add(lblPasswordError, "8, 12, left, default");
		
		Object[] objects = {this, usernameField, passwordField, reenterPasswordField};
		
		buttonPanel = new JPanel();
		buttonPanel.setBackground(Color.WHITE);
		contentPane.add(buttonPanel, "8, 16, fill, fill");
		btnRegister = new JButton("Register");
		btnRegister.setFont(new Font("Dialog", Font.BOLD, 12));
		btnRegister.addActionListener(new GenericActionListener(new RegisterCommand(), objects));
		buttonPanel.add(btnRegister);
		
		btnCancel = new JButton("Cancel");
		btnCancel.setFont(new Font("Dialog", Font.BOLD, 12));
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cancel();
			}

		});
		buttonPanel.add(btnCancel);
	}
	
	private void cancel() {
		
		usernameField.setText("");
		passwordField.setText("");
		reenterPasswordField.setText("");
		setVisible(false);
		
	    prevFrame.setVisible(true);
		
	}
	
	/* Called when registration is completed successfully.
	 * The user is then taken to a new window
	 */
	public void register() {
		
		usernameField.setText("");
		passwordField.setText("");
		reenterPasswordField.setText("");
		lblPasswordError.setVisible(false);
		lblUsernameError.setVisible(false);
		setVisible(false);
		
		menuFrame.setVisible(true);
		
	}
	
	/* Called when there is a problem with the entered passwords.
	 * Right now this is only called when the passwords entered do not match
	 * and results in an error message being displayed to the user
	 */
	public void passwordError() {
		
		passwordField.setText("");
		reenterPasswordField.setText("");
		lblPasswordError.setVisible(true);
		
	}
	
	
	/* Called when there is a problem with the username entered.
	 * Right now this is only called when the username is already in use
	 * and results in an error message being displayed to the user
	 */
	public void usernameError() {
		
		usernameField.setText("");
		passwordField.setText("");
		reenterPasswordField.setText("");
		lblUsernameError.setVisible(true);
		
	}

	public String getUsername() {
		
		return usernameField.getText();
		
	}
	
	public char[] getPassword() {
		
		return passwordField.getPassword();
		
	}
	
	public char[] getReenterPassword() {
		
		return reenterPasswordField.getPassword();
		
	}

}
