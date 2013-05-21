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

public class MainFrame extends BaseFrame {
	
	/* Frame to transfer to when a user logs in */
	private MenuFrame mFrame = new MenuFrame();
	/* Frame to transfer to when a user decides to register */
	private RegisterFrame rFrame = new RegisterFrame(this);

	private JPanel contentPane;
	private final JPanel mid = new JPanel();
	private final JPanel footer = new JPanel();
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
	private final Component verticalStrut = Box.createVerticalStrut(20);
	private final Component rigidArea = Box.createRigidArea(new Dimension(0, 5));
	private final JPanel panel = new JPanel();
	private final JLabel lblFancyApplicationName = new JLabel("fancy application name");
	private final Component rigidArea_2 = Box.createRigidArea(new Dimension(20, 20));
	private final Component horizontalStrut = Box.createHorizontalStrut(20);
	private final JLabel lblLoginError = new JLabel("The username or password you entered is incorrect");

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
		registerButton.setFont(new Font("Dialog", Font.BOLD, 10));
		
		Object[] objects1 = {this, usernameField, passwordField};
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(0, 1, 0, 0));
		
		contentPane.add(header);
		header.setLayout(new GridLayout(0, 2, 0, 0));
		
		header.add(panel);
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		
		panel.add(rigidArea_2);
		lblFancyApplicationName.setFont(new Font("Dialog", Font.BOLD, 14));
		
		panel.add(lblFancyApplicationName);
		
		contentPane.add(mid);
		mid.setLayout(new GridLayout(0, 2, 0, 0));
		
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
				RowSpec.decode("13px"),
				RowSpec.decode("35px"),
				RowSpec.decode("13px"),
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				RowSpec.decode("35px"),
				RowSpec.decode("23px"),}));
		lblLoginError.setFont(new Font("DejaVu Sans", Font.PLAIN, 10));
		lblLoginError.setForeground(Color.RED);
		lblLoginError.setVisible(false);
		
		login.add(lblLoginError, "2, 2, 5, 1");
		login.add(lblUsername, "2, 3, left, center");
		lblUsername.setVerticalAlignment(SwingConstants.BOTTOM);
		lblUsername.setFont(new Font("Dialog", Font.PLAIN, 10));
		login.add(lblPassword, "4, 3, left, center");
		lblPassword.setVerticalAlignment(SwingConstants.BOTTOM);
		lblPassword.setFont(new Font("Dialog", Font.PLAIN, 10));
		lblPassword.setLabelFor(passwordField);
		
		login.add(horizontalStrut, "1, 4");
		
		lblUsername.setLabelFor(usernameField);
		login.add(usernameField, "2, 4, left, center");
		usernameField.setAlignmentX(Component.LEFT_ALIGNMENT);
		usernameField.setFont(new Font("Dialog", Font.PLAIN, 12));
		usernameField.setHorizontalAlignment(SwingConstants.LEFT);
		usernameField.setColumns(10);
		login.add(passwordField, "4, 4, left, center");
		passwordField.setAlignmentX(0.15f);
		passwordField.setHorizontalAlignment(SwingConstants.LEFT);
		passwordField.setColumns(10);
		btnLogIn.setHorizontalAlignment(SwingConstants.RIGHT);
		login.add(btnLogIn, "4, 5, 1, 2, right, center");
		btnLogIn.addActionListener(new GenericActionListener(new LogInCommand(), objects1));
		
		btnLogIn.setFont(new Font("Dialog", Font.BOLD, 10));
		
		mid.add(register);
		register.setLayout(new BoxLayout(register, BoxLayout.Y_AXIS));
		
		register.add(verticalStrut);
		
		lblRegisterQuestion = new JLabel("Not already a member?");
		lblRegisterQuestion.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblRegisterQuestion.setHorizontalAlignment(SwingConstants.CENTER);
		register.add(lblRegisterQuestion);
		
		register.add(rigidArea);
		register.add(registerButton);
		
		contentPane.add(footer);
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
	public void login() {
		
		usernameField.setText("");
		passwordField.setText("");
		lblLoginError.setVisible(false);
		setVisible(false);
		
		mFrame.setVisible(true);
	}
	
	/* Called when username and password verification fails.
	 * Results in an error message being displayed to the user
	 */
	public void loginFail() {
		
		lblLoginError.setVisible(true);
		
	}
	
	
}
