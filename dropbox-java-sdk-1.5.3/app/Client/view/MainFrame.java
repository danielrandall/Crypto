package Client.view;

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

import Client.controller.Controller;
import Client.controller.LogInCommand;
import javax.swing.BoxLayout;
import java.awt.Component;
import javax.swing.Box;
import java.awt.FlowLayout;
import java.awt.Dimension;
import javax.swing.JPasswordField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MainFrame extends JFrame {
	
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
	private final JTextField usernameBox = new JTextField();
	private final JLabel lblPassword = new JLabel("Password");
	private final JPasswordField passwordBox = new JPasswordField();
	private final JButton logInButton = new JButton("Log In");
	private final JButton registerButton = new JButton("Register");
	private JLabel lblRegisterQuestion;
	private final Component verticalStrut = Box.createVerticalStrut(20);
	private final Component rigidArea = Box.createRigidArea(new Dimension(0, 5));
	private final JPanel passwordPanel = new JPanel();
	private final JPanel usernamePanel = new JPanel();
	private final JPanel logInFormPanel = new JPanel();
	private final JPanel logInButtonPanel = new JPanel();
	private final Component horizontalGlue = Box.createHorizontalGlue();
	private final Component rigidArea_1 = Box.createRigidArea(new Dimension(190, 0));
	private final JPanel panel = new JPanel();
	private final JLabel lblFancyApplicationName = new JLabel("fancy application name");
	private final Component rigidArea_2 = Box.createRigidArea(new Dimension(20, 20));

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		/* Set nicer look and feel */
		
		/*
			try {
				UIManager.setLookAndFeel(
				        UIManager.getSystemLookAndFeelClassName());
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (InstantiationException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IllegalAccessException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (UnsupportedLookAndFeelException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			*/
			
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

	private void start() {
		
		setVisible(true);
		Controller.setup();
		
	}

	/**
	 * Create the frame.
	 */
	public MainFrame() {
		registerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				register();
			}
		});
		registerButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		registerButton.setFont(new Font("Dialog", Font.BOLD, 10));
		
		Object[] objects1 = {this, usernameBox, passwordBox};
		logInFormPanel.add(usernamePanel);
		usernamePanel.add(lblUsername);
		lblUsername.setVerticalAlignment(SwingConstants.BOTTOM);
		lblUsername.setFont(new Font("Dialog", Font.PLAIN, 10));
		usernameBox.setAlignmentX(Component.LEFT_ALIGNMENT);
		usernameBox.setFont(new Font("Dialog", Font.PLAIN, 12));
		usernameBox.setHorizontalAlignment(SwingConstants.LEFT);
		usernamePanel.add(usernameBox);
		usernameBox.setColumns(10);
		usernamePanel.setLayout(new BoxLayout(usernamePanel, BoxLayout.PAGE_AXIS));
		logInFormPanel.add(passwordPanel);
		passwordPanel.add(lblPassword);
		lblPassword.setVerticalAlignment(SwingConstants.BOTTOM);
		lblPassword.setFont(new Font("Dialog", Font.PLAIN, 10));
		passwordBox.setAlignmentX(0.15f);
		passwordBox.setHorizontalAlignment(SwingConstants.LEFT);
		passwordPanel.add(passwordBox);
		passwordBox.setColumns(10);
		FlowLayout flowLayout = (FlowLayout) logInButtonPanel.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		logInButtonPanel.setBorder(null);
		
		logInFormPanel.add(logInButtonPanel);
		
		logInButtonPanel.add(rigidArea_1);
		logInButtonPanel.add(logInButton);
		logInButton.addActionListener(new GenericActionListener(new LogInCommand(), objects1));
		
		logInButton.setFont(new Font("Dialog", Font.BOLD, 10));
		
		logInButtonPanel.add(horizontalGlue);
		initGUI();
	}
	private void initGUI() {
		lblUsername.setLabelFor(usernameBox);
		lblPassword.setLabelFor(passwordBox);
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
		login.setLayout(new BoxLayout(login, BoxLayout.Y_AXIS));
		
		login.add(logInFormPanel);
		passwordPanel.setLayout(new BoxLayout(passwordPanel, BoxLayout.PAGE_AXIS));
		
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

	public void login() {
		
		usernameBox.setText("");
		passwordBox.setText("");
		setVisible(false);
		
		mFrame.setVisible(true);
	}
	
	
	public void register() {
		
		usernameBox.setText("");
		passwordBox.setText("");
		setVisible(false);
		
		rFrame.setVisible(true);
		
	}
}
