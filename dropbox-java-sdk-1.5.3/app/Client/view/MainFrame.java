package Client.view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import java.awt.GridLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;

import Client.controller.Controller;
import Client.controller.LogInCommand;
import Client.controller.RegisterCommand;

public class MainFrame extends JFrame {
	
	private MenuFrame mFrame = new MenuFrame();

	private JPanel contentPane;
	private final JPanel mid = new JPanel();
	private final JPanel footer = new JPanel();
	private final JPanel header = new JPanel();
	private final JPanel login = new JPanel();
	private final JPanel register = new JPanel();
	private final JLabel lblUsername = new JLabel("Username");
	private final JTextField usernameBox = new JTextField();
	private final JLabel lblPassword = new JLabel("Password");
	private final JTextField passwordBox = new JTextField();
	private final JButton logInButton = new JButton("Log In");
	private final JButton registerButton = new JButton("Register");
	private final JTextField registerUsername = new JTextField();
	private final JLabel label = new JLabel("Password");
	private final JTextField registerPassword = new JTextField();
	private final JLabel label_1 = new JLabel("Username");

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
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
		label_1.setVerticalAlignment(SwingConstants.TOP);
		label_1.setFont(new Font("Dialog", Font.BOLD, 10));
		registerPassword.setColumns(10);
		label.setFont(new Font("Dialog", Font.BOLD, 10));
		registerUsername.setColumns(10);
		registerButton.setFont(new Font("Dialog", Font.BOLD, 10));
		
		Object[] objects = {this, registerUsername, registerPassword};
		registerButton.addActionListener(new GenericActionListener(new RegisterCommand(), objects));
		
		Object[] objects2 = {this, usernameBox, passwordBox};
		logInButton.addActionListener(new GenericActionListener(new LogInCommand(), objects2));
		
		logInButton.setFont(new Font("Dialog", Font.BOLD, 10));
		passwordBox.setColumns(10);
		lblPassword.setFont(new Font("Dialog", Font.BOLD, 10));
		usernameBox.setColumns(10);
		lblUsername.setVerticalAlignment(SwingConstants.TOP);
		lblUsername.setFont(new Font("Dialog", Font.BOLD, 10));
		initGUI();
	}
	private void initGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(0, 1, 0, 0));
		
		contentPane.add(header);
		
		contentPane.add(mid);
		mid.setLayout(new GridLayout(0, 2, 0, 0));
		
		mid.add(login);
		GroupLayout gl_login = new GroupLayout(login);
		gl_login.setHorizontalGroup(
			gl_login.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_login.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_login.createParallelGroup(Alignment.LEADING)
						.addComponent(usernameBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblUsername)))
				.addGroup(gl_login.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblPassword)
					.addPreferredGap(ComponentPlacement.RELATED, 62, Short.MAX_VALUE)
					.addComponent(logInButton)
					.addGap(20))
				.addGroup(gl_login.createSequentialGroup()
					.addContainerGap()
					.addComponent(passwordBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(89, Short.MAX_VALUE))
		);
		gl_login.setVerticalGroup(
			gl_login.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_login.createSequentialGroup()
					.addGroup(gl_login.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_login.createSequentialGroup()
							.addContainerGap()
							.addComponent(logInButton))
						.addGroup(gl_login.createSequentialGroup()
							.addComponent(lblUsername, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(usernameBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(lblPassword)))
					.addGap(1)
					.addComponent(passwordBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		login.setLayout(gl_login);
		
		mid.add(register);
		GroupLayout gl_register = new GroupLayout(register);
		gl_register.setHorizontalGroup(
			gl_register.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_register.createSequentialGroup()
					.addGroup(gl_register.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_register.createSequentialGroup()
							.addGroup(gl_register.createParallelGroup(Alignment.LEADING)
								.addComponent(registerUsername, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addGroup(gl_register.createSequentialGroup()
									.addComponent(label, GroupLayout.PREFERRED_SIZE, 54, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED, 60, GroupLayout.PREFERRED_SIZE)))
							.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(registerButton))
						.addComponent(label_1, GroupLayout.PREFERRED_SIZE, 58, GroupLayout.PREFERRED_SIZE)
						.addComponent(registerPassword, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		gl_register.setVerticalGroup(
			gl_register.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_register.createSequentialGroup()
					.addGap(39)
					.addComponent(registerButton)
					.addGap(28))
				.addGroup(Alignment.LEADING, gl_register.createSequentialGroup()
					.addComponent(label_1, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_register.createSequentialGroup()
						.addComponent(registerUsername, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(label, GroupLayout.PREFERRED_SIZE, 13, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(registerPassword, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(13, Short.MAX_VALUE))
		);
		register.setLayout(gl_register);
		
		contentPane.add(footer);
	}

	public void login() {
		
		usernameBox.setText("");
		passwordBox.setText("");
		setVisible(false);
		
		mFrame.setVisible(true);
	}

}
