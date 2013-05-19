package Client.view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.factories.FormFactory;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class RegisterFrame extends JFrame {
	
	/* Frame to transfer to when a user cancels registration */
	private MainFrame mainFrame = new MainFrame();
	/* Menu to transfer to if a user successfully completes registration */
	private MenuFrame menuFrame = new MenuFrame();

	private JPanel contentPane;
	private JLabel lblUsername;
	private JTextField usernameField;
	private JLabel lblUsernameError;
	private JLabel lblPassword;
	private JTextField passwordField;
	private JLabel lblReenterPassword;
	private JTextField reenterPasswordField;
	private JLabel lblPasswordError;
	private JPanel buttonPanel;
	private JButton btnRegister;
	private JButton btnCancel;

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
					RegisterFrame frame = new RegisterFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public RegisterFrame() {
		initGUI();
	}
	private void initGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new FormLayout(new ColumnSpec[] {
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
				RowSpec.decode("default:grow"),}));
		
		lblUsername = new JLabel("Username");
		contentPane.add(lblUsername, "2, 4, right, default");
		
		usernameField = new JTextField();
		contentPane.add(usernameField, "4, 4, left, default");
		usernameField.setColumns(10);
		
		lblUsernameError = new JLabel("Username already in use");
		lblUsernameError.setForeground(Color.RED);
		contentPane.add(lblUsernameError, "6, 4, left, default");
		
		lblPassword = new JLabel("Password");
		contentPane.add(lblPassword, "2, 8, right, default");
		
		passwordField = new JTextField();
		contentPane.add(passwordField, "4, 8, left, default");
		passwordField.setColumns(10);
		
		lblReenterPassword = new JLabel("Re-enter password");
		contentPane.add(lblReenterPassword, "2, 12, right, default");
		
		reenterPasswordField = new JTextField();
		contentPane.add(reenterPasswordField, "4, 12, left, default");
		reenterPasswordField.setColumns(10);
		
		lblPasswordError = new JLabel("Passwords do not match");
		lblPasswordError.setForeground(Color.RED);
		contentPane.add(lblPasswordError, "6, 12, left, default");
		
		buttonPanel = new JPanel();
		contentPane.add(buttonPanel, "6, 20, fill, fill");
		
		btnRegister = new JButton("Register");
		buttonPanel.add(btnRegister);
		
		btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cancel();
			}

		});
		buttonPanel.add(btnCancel);
	}
	
	private void register() {
		
		usernameField.setText("");
		passwordField.setText("");
		reenterPasswordField.setText("");
		setVisible(false);
		
		menuFrame.setVisible(true);
		
	}
	
	private void cancel() {
		
		usernameField.setText("");
		passwordField.setText("");
		reenterPasswordField.setText("");
		setVisible(false);
		
		mainFrame.setVisible(true);
		
	}

}
