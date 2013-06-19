package client.view;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import javax.swing.JTabbedPane;
import java.awt.Color;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.Box;

import client.controller.Controller;

import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MenuFrame extends BaseFrame {
	
	/* Panels used for the tabs */
	private final BaseFrame prevFrame;
	private final MyFilesPanel myFilesPanel = new MyFilesPanel();
	private final FriendsPanel friendsPanel = new FriendsPanel();
	private final FriendRequestsPanel friendRequestsPanel =
											  new FriendRequestsPanel();
	private final FriendFilesPanel friendFilesPanel = new FriendFilesPanel();

	private JPanel contentPane;
	private final JTabbedPane tabbedOptionsPane = new JTabbedPane(JTabbedPane.TOP);
	private final JLabel lblHeader = new JLabel("App name");
	private final JPanel headerPanel = new JPanel();
	private final JButton btnLogOut = new JButton("Log out");
	private final JPanel buttonPanel = new JPanel();
	private final Component rigidArea = Box.createRigidArea(new Dimension(100, 0));
	private final JPanel logoPanel = new JPanel();
	private final Component rigidArea_1 = Box.createRigidArea(new Dimension(10, 0));

	/**
	 * Create the frame.
	 */
	public MenuFrame(BaseFrame frame) {

	    prevFrame = frame;
		initGUI();
	}
	
	private void initGUI() {
		
		addWindowListener(this);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 552, 498);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.PAGE_AXIS));
		headerPanel.setBackground(Color.WHITE);
		headerPanel.setAlignmentY(Component.TOP_ALIGNMENT);
		
		contentPane.add(headerPanel);
		headerPanel.setLayout(new GridLayout(1, 2, 0, 0));
		logoPanel.setBackground(Color.WHITE);
		
		headerPanel.add(logoPanel);
		logoPanel.setLayout(new BoxLayout(logoPanel, BoxLayout.X_AXIS));
		logoPanel.add(rigidArea_1);
		logoPanel.add(lblHeader);
		lblHeader.setHorizontalAlignment(SwingConstants.LEFT);
		lblHeader.setFont(museosans_900_18p);
		buttonPanel.setBackground(Color.WHITE);
		
		headerPanel.add(buttonPanel);
		GridBagLayout gbl_buttonPanel = new GridBagLayout();
		gbl_buttonPanel.columnWidths = new int[]{88, 0, 0, 0, 0, 0, 0};
		gbl_buttonPanel.rowHeights = new int[]{25, 0};
		gbl_buttonPanel.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_buttonPanel.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		buttonPanel.setLayout(gbl_buttonPanel);
		
		GridBagConstraints gbc_rigidArea = new GridBagConstraints();
		gbc_rigidArea.insets = new Insets(0, 0, 0, 5);
		gbc_rigidArea.gridx = 4;
		gbc_rigidArea.gridy = 0;
		buttonPanel.add(rigidArea, gbc_rigidArea);
		btnLogOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				close();
			}
		});
		btnLogOut.setAlignmentX(Component.RIGHT_ALIGNMENT);
		GridBagConstraints gbc_btnLogOut = new GridBagConstraints();
		gbc_btnLogOut.anchor = GridBagConstraints.EAST;
		gbc_btnLogOut.gridx = 5;
		gbc_btnLogOut.gridy = 0;
		buttonPanel.add(btnLogOut, gbc_btnLogOut);
		btnLogOut.setHorizontalAlignment(SwingConstants.LEFT);
		btnLogOut.setFont(new Font("Dialog", Font.BOLD, 11));
		
		contentPane.add(tabbedOptionsPane);
		
		tabbedOptionsPane.addTab("My files", null, myFilesPanel, "Files" +
				"which you have uploaded to share");
		tabbedOptionsPane.addTab("My friends", null, friendsPanel, "Friends" +
				"who you have allowed access");
		tabbedOptionsPane.addTab("Friend requests", null, friendRequestsPanel,
				"People who wish to share their files with you");
		tabbedOptionsPane.addTab("Friend's files", null, friendFilesPanel,
				"Files which people have shared with you");
		
	}
	
	
	/* Pass rows to the myFiles panel to put into the table */
	public void populateUploadedFiles(Object[][] uploadedFileRows) {
		
		myFilesPanel.populateUploadedFiles(uploadedFileRows);
		
	}
	
	public void populateFriends(Object[][] friends) {
		
		friendsPanel.populateFriends(friends);
		
	}
	
	public void populateFriendRequests(Object[][] friendRequests) {
		
		friendRequestsPanel.populateFriendRequests(friendRequests);
		
	}
	
	public void populateFriendFiles(Object[][] friendFiles) {
		
		friendFilesPanel.populateFriendFiles(friendFiles);
		
	}
	
	public void close() {
		
		this.setVisible(false);
		
		myFilesPanel.exit();
		friendFilesPanel.exit();
		friendsPanel.exit();
		friendRequestsPanel.exit();
		
		Controller.exit();
		Controller.setup();
		
		prevFrame.setVisible(true);
		
	}
}	

