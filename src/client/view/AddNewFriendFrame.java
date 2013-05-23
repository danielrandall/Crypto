package client.view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;

import client.controller.AddFriendCommand;

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

public class AddNewFriendFrame extends BaseFrame {

	private JPanel contentPane;
	private final JLabel lblUsername = new JLabel("Username");
	private final JLabel lblHeader = new JLabel("Add a new friend");
	private final JTextField usernameTextField = new JTextField();
	private final JLabel lblSecurityLevel = new JLabel("Max Security Level");
	private final JSlider sliderSecurityLevel = new JSlider();
	private final JPanel panel = new JPanel();
	private final JButton btnAddFriend = new JButton("Add friend");
	private final JButton button_1 = new JButton("Cancel");

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AddNewFriendFrame frame = new AddNewFriendFrame();
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
	public AddNewFriendFrame() {
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
				RowSpec.decode("default:grow"),}));
		
		contentPane.add(lblHeader, "4, 2");
		lblUsername.setFont(new Font("Dialog", Font.BOLD, 10));
		
		contentPane.add(lblUsername, "4, 6");
		usernameTextField.setColumns(10);
		
		contentPane.add(usernameTextField, "4, 8, fill, default");
		lblSecurityLevel.setFont(new Font("Dialog", Font.BOLD, 10));
		
		contentPane.add(lblSecurityLevel, "4, 10");
		sliderSecurityLevel.setSnapToTicks(true);
		sliderSecurityLevel.setPaintTicks(true);
		sliderSecurityLevel.setPaintLabels(true);
		sliderSecurityLevel.setMinimum(1);
		sliderSecurityLevel.setMaximum(5);
		sliderSecurityLevel.setMajorTickSpacing(1);
		sliderSecurityLevel.setFont(null);
		
		contentPane.add(sliderSecurityLevel, "4, 12");
		panel.setBackground(Color.WHITE);
		
		contentPane.add(panel, "4, 16, right, fill");
		btnAddFriend.setHorizontalAlignment(SwingConstants.RIGHT);
		btnAddFriend.setFont(new Font("Dialog", Font.BOLD, 12));
		Object[] objects = {this, usernameTextField, sliderSecurityLevel};
		btnAddFriend.addActionListener(new GenericActionListener(new AddFriendCommand(), objects));
		
		panel.add(btnAddFriend);
		button_1.setHorizontalAlignment(SwingConstants.RIGHT);
		button_1.setFont(new Font("Dialog", Font.BOLD, 12));
		
		panel.add(button_1);
	}

}
