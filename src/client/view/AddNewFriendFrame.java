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
				RowSpec.decode("default:grow"),}));
		
		contentPane.add(lblHeader, "4, 2");
		lblUsernameError.setForeground(Color.RED);
		lblUsernameError.setVisible(false);
		
		contentPane.add(lblUsernameError, "4, 6");
		lblUsername.setFont(new Font("Dialog", Font.BOLD, 10));
		
		contentPane.add(lblUsername, "4, 8");
		usernameTextField.setColumns(10);
		
		contentPane.add(usernameTextField, "4, 10, fill, default");
		lblSecurityLevel.setFont(new Font("Dialog", Font.BOLD, 10));
		
		contentPane.add(lblSecurityLevel, "4, 12");
		sliderSecurityLevel.setSnapToTicks(true);
		sliderSecurityLevel.setPaintTicks(true);
		sliderSecurityLevel.setPaintLabels(true);
		sliderSecurityLevel.setMinimum(1);
		sliderSecurityLevel.setMaximum(5);
		sliderSecurityLevel.setMajorTickSpacing(1);
		sliderSecurityLevel.setFont(null);
		
		contentPane.add(sliderSecurityLevel, "4, 14");
		btnPanel.setBackground(Color.WHITE);
		
		contentPane.add(btnPanel, "4, 18, right, fill");
		btnAddFriend.setHorizontalAlignment(SwingConstants.RIGHT);
		btnAddFriend.setFont(new Font("Dialog", Font.BOLD, 12));
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
	
	public void friendAdded() {
		
		lblUsernameError.setVisible(false);
		usernameTextField.setText("");
		sliderSecurityLevel.setValue(sliderSecurityLevel.getMaximum());
		setVisible(false);
		
	}
	
	public void userDoesNotExist() {
		
		lblUsernameError.setVisible(true);
		
	}

}
