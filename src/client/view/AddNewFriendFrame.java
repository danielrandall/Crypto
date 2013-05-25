package client.view;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;

import client.controller.FriendRequestCommand;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.factories.FormFactory;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JSlider;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Component;
import javax.swing.Box;

public class AddNewFriendFrame extends BaseFrame {
	
	/* Panel to pass along to update the friends list with the new friend */
	private JPanel panel;

	private JPanel contentPane;
	private final JLabel lblUsername = new JLabel("Username");
	private final JLabel lblHeader = new JLabel("Add a new friend");
	private final JTextField usernameTextField = new JTextField();
	private final JLabel lblSecurityLevel = new JLabel("Max Security Level");
	private final JSlider sliderSecurityLevel = new JSlider();
	private final JPanel btnPanel = new JPanel();
	private final JButton btnAddFriend = new JButton("Add friend");
	private final JButton btnCancel = new JButton("Cancel");
	
	
	private final JLabel lblUsernameError = new JLabel("Username does not exist");
	private static final String USERNAME_DOES_NOT_EXIST = "Username does not exist";
	private static final String CANNOT_ADD_YOURSELF = "You can not add youself";
	
	private final Component verticalStrut = Box.createVerticalStrut(20);
	private final Component verticalStrut_1 = Box.createVerticalStrut(5);

	/**
	 * Create the frame.
	 */
	public AddNewFriendFrame(JPanel friendPanel) {
		panel = friendPanel;
		initGUI();
	}
	private void initGUI() {
		
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,},
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
				RowSpec.decode("default:grow"),}));
		
		contentPane.add(verticalStrut, "2, 2");
		
		lblHeader.setFont(museosans_900_18p);
		contentPane.add(lblHeader, "4, 2");
		
		lblUsernameError.setFont(museosans_700_14p);
		lblUsernameError.setForeground(Color.RED);
		lblUsernameError.setVisible(false);
		
		contentPane.add(lblUsernameError, "4, 6");
		lblUsername.setFont(museosans_700_14p);
		
		contentPane.add(lblUsername, "4, 8");
		usernameTextField.setColumns(10);
		
		contentPane.add(usernameTextField, "4, 10, fill, default");
		lblSecurityLevel.setFont(museosans_700_14p);
		
		contentPane.add(lblSecurityLevel, "4, 14");
		
		contentPane.add(verticalStrut_1, "4, 16");
		sliderSecurityLevel.setSnapToTicks(true);
		sliderSecurityLevel.setPaintTicks(true);
		sliderSecurityLevel.setPaintLabels(true);
		sliderSecurityLevel.setMinimum(1);
		sliderSecurityLevel.setMaximum(5);
		sliderSecurityLevel.setMajorTickSpacing(1);
		sliderSecurityLevel.setFont(museosans_500_12p);
		
		contentPane.add(sliderSecurityLevel, "4, 18");
		btnPanel.setBackground(Color.WHITE);
		
		contentPane.add(btnPanel, "4, 22, right, fill");
		btnAddFriend.setHorizontalAlignment(SwingConstants.RIGHT);
		btnAddFriend.setFont(new Font("Dialog", Font.BOLD, 11));
		Object[] objects = {this, panel};
		btnAddFriend.addActionListener(new GenericActionListener(new FriendRequestCommand(), objects));
		
		btnPanel.add(btnAddFriend);
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
			}
		});
		btnCancel.setHorizontalAlignment(SwingConstants.RIGHT);
		btnCancel.setFont(new Font("Dialog", Font.BOLD, 12));
		
		btnPanel.add(btnCancel);
	}
	
	
	public String getUsername() {
		
		return usernameTextField.getText();
		
	}
	
	public int getSecurityLevel() {
		
		return sliderSecurityLevel.getValue();
		
	}
	
	/* Called when a friend request has been successfully sent */
	public void friendAdded() {
		
		lblUsernameError.setVisible(false);
		usernameTextField.setText("");
		sliderSecurityLevel.setValue(sliderSecurityLevel.getMaximum());
		setVisible(false);
		
	}
	
	/* Called when the user attempts to add a non-existent user */
	public void userDoesNotExist() {
		
		lblUsernameError.setText(USERNAME_DOES_NOT_EXIST);
		lblUsernameError.setVisible(true);
		
	}
	
	/* Called when the user attempts to add themselves */
	public void canNotAddYourself() {
		
		lblUsernameError.setText(CANNOT_ADD_YOURSELF);
		lblUsernameError.setVisible(true);
		
	}

}
