package client.view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JButton;

import javax.swing.BoxLayout;
import java.awt.Component;
import javax.swing.Box;
import java.awt.Dimension;
import javax.swing.JPasswordField;

import client.controller.Controller;
import client.controller.LogInCommand;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.factories.FormFactory;
import java.awt.Color;
import javax.swing.ImageIcon;


/* TODO Make textfield borders rounded */

public class MainFrame extends BaseFrame {
	
	/* Frame to transfer to when a user logs in */
	private MenuFrame mFrame = new MenuFrame(this);
	/* Frame to transfer to when a user decides to register */
	private RegisterFrame rFrame = new RegisterFrame(this);

	private JPanel contentPane;
	private final JPanel mid = new JPanel();
	private final JPanel header = new JPanel();
	private final JPanel login = new JPanel();
	private final JPanel register = new JPanel();
	private final JLabel lblUsername = new JLabel("Username");
	private final JTextField usernameField = new JTextField();
	private final JLabel lblPassword = new JLabel("Password");
	private final JPasswordField passwordField = new JPasswordField();
	private final JButton btnLogIn = new JButton("Log In");
	private final JButton registerButton = new JButton("Register");
	private JLabel lblRegisterQuestion;
	private final Component verticalStrut = Box.createVerticalStrut(15);
	private final Component rigidArea = Box.createRigidArea(new Dimension(0, 5));
	private final JPanel logoPanel = new JPanel();
	private final JLabel lblAppWelcome = new JLabel("Welcome to this app");
	private final Component rigidArea_2 = Box.createRigidArea(new Dimension(20, 20));
	private final Component horizontalStrut = Box.createHorizontalStrut(20);
	private final JLabel lblLoginError = new JLabel("Incorrect username or password");
	private final JPanel welcomePanel = new JPanel();
	private final JLabel lblDropboxLogo = new JLabel("");
	private final Component rigidArea_1 = Box.createRigidArea(new Dimension(20, 20));
	private final Component verticalStrut_1 = Box.createVerticalStrut(5);

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		/* Set nicer look and feel */
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
					createFonts();
					MainFrame frame = new MainFrame();
					frame.start();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainFrame() {
		initGUI();
	}
	
	private void initGUI() {
		
		addWindowListener(this);
		
		registerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				register();
			}
		});
		registerButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		registerButton.setFont(new Font("Dialog", Font.BOLD, 11));
		
		Object[] objects1 = {this, usernameField, passwordField};
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//setBounds(100, 100, 600, 400);
		setBounds(100, 100, 450, 278);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(2, 1, 0, 0));
		header.setBackground(Color.WHITE);
		
		contentPane.add(header);
		header.setLayout(new GridLayout(0, 2, 0, 0));
		logoPanel.setBackground(Color.WHITE);
		
		header.add(logoPanel);
		logoPanel.setLayout(new BoxLayout(logoPanel, BoxLayout.X_AXIS));
		
		logoPanel.add(rigidArea_1);
		lblDropboxLogo.setIcon(new ImageIcon(MainFrame.class.getResource("/client/view/DropboxLog.png")));
		
		logoPanel.add(lblDropboxLogo);
		welcomePanel.setBackground(Color.WHITE);
		
		header.add(welcomePanel);
		welcomePanel.setLayout(new BoxLayout(welcomePanel, BoxLayout.X_AXIS));
		welcomePanel.add(rigidArea_2);
		lblAppWelcome.setHorizontalAlignment(SwingConstants.CENTER);
		welcomePanel.add(lblAppWelcome);
		lblAppWelcome.setFont(museosans_900_18p);
		mid.setBackground(Color.WHITE);
		
		contentPane.add(mid);
		mid.setLayout(new GridLayout(0, 2, 0, 0));
		login.setBackground(Color.WHITE);
		
		mid.add(login);
		login.setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("center:default"),
				ColumnSpec.decode("center:default"),
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				RowSpec.decode("13px"),
				RowSpec.decode("35px"),
				RowSpec.decode("13px"),
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				RowSpec.decode("35px"),
				RowSpec.decode("23px"),}));
		lblLoginError.setFont(museosans_700_14p);
		lblLoginError.setForeground(Color.RED);
		lblLoginError.setVisible(false);
		
		login.add(lblLoginError, "2, 2, 5, 1");
		
		login.add(verticalStrut_1, "1, 4");
		login.add(lblUsername, "2, 5, left, center");
		lblUsername.setVerticalAlignment(SwingConstants.BOTTOM);
		lblUsername.setFont(museosans_700_14p);
		//lblUsername.setFont(new Font("Dialog", Font.BOLD, 12));
		login.add(lblPassword, "4, 4, 1, 2, left, center");
		lblPassword.setVerticalAlignment(SwingConstants.BOTTOM);
		lblPassword.setFont(museosans_700_14p);
		lblPassword.setLabelFor(passwordField);
		
		lblUsername.setLabelFor(usernameField);
		login.add(usernameField, "2, 6, left, center");
		
		/*
		Border rounded = new LineBorder(new Color(210,210,210), 1, true);
		Border empty = new EmptyBorder(0, 3, 0, 0);
		Border border = new CompoundBorder(rounded, empty);
		*/
		
		usernameField.setAlignmentX(Component.LEFT_ALIGNMENT);
		usernameField.setFont(new Font("Dialog", Font.PLAIN, 12));
		usernameField.setHorizontalAlignment(SwingConstants.LEFT);
		usernameField.setColumns(10);
		
		login.add(passwordField, "4, 6, left, center");
		passwordField.setAlignmentX(0.15f);
		passwordField.setHorizontalAlignment(SwingConstants.LEFT);
		passwordField.setColumns(10);
		
		login.add(horizontalStrut, "1, 7, 1, 2");
		btnLogIn.setHorizontalAlignment(SwingConstants.RIGHT);
		login.add(btnLogIn, "4, 7, 1, 2, right, center");
		btnLogIn.addActionListener(new GenericActionListener(new LogInCommand(), objects1));
		
		btnLogIn.setFont(new Font("Dialog", Font.BOLD, 11));
		register.setBackground(Color.WHITE);
		
		mid.add(register);
		register.setLayout(new BoxLayout(register, BoxLayout.Y_AXIS));
		
		register.add(verticalStrut);
		
		lblRegisterQuestion = new JLabel("Not already a member?");
		lblRegisterQuestion.setFont(museosans_700_14p);
		lblRegisterQuestion.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblRegisterQuestion.setHorizontalAlignment(SwingConstants.CENTER);
		register.add(lblRegisterQuestion);
		
		register.add(rigidArea);
		register.add(registerButton);
	}
	
	/* Initialises the application.
	 * Establishes a connection with the server
	 */
	private void start() {
		
		setVisible(true);
		Controller.setup();
		
	}
	
	/* Called when the user decides to register an account */
	private void register() {
		
		usernameField.setText("");
		passwordField.setText("");
		lblLoginError.setVisible(false);
		setVisible(false);
		
		rFrame.setVisible(true);
		
	}
	
	/* Called when username and password verification succeeds */
	public void login(Object[][] uploadedFileRows, Object[][] friends,
			          Object[][] friendRequests, Object[][] friendFiles) {
		
		usernameField.setText("");
		passwordField.setText("");
		lblLoginError.setVisible(false);
		setVisible(false);
		
		mFrame.populateUploadedFiles(uploadedFileRows);
		mFrame.populateFriends(friends);
		mFrame.populateFriendRequests(friendRequests);
		mFrame.populateFriendFiles(friendFiles);
		
		mFrame.setVisible(true);
	}
	
	/* Called when username and password verification fails.
	 * Results in an error message being displayed to the user
	 */
	public void loginFail() {
		
		lblLoginError.setVisible(true);
		
	}
	
	
	public String getUsername() {
		
		return usernameField.getText();
		
	}
	
	/* TODO do it properly */
	public char[] getPassword() {
		
		return passwordField.getPassword();
		
	}
	
	
}
