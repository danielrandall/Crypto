package client.view;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import javax.swing.JTabbedPane;
import java.awt.Color;

public class MenuFrame extends BaseFrame {
	
	/* Panels used for the tabs */
	private final MyFilesPanel myFilesPanel = new MyFilesPanel();
	private final FriendsPanel friendsPanel = new FriendsPanel();
	private final FriendRequestsPanel friendRequestsPanel =
											  new FriendRequestsPanel();
	private final FriendFilesPanel friendFilesPanel = new FriendFilesPanel();

	private JPanel contentPane;
	private final JTabbedPane tabbedOptionsPane = new JTabbedPane(JTabbedPane.TOP);

	/**
	 * Create the frame.
	 */
	public MenuFrame() {

		initGUI();
	}
	
	private void initGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 552, 498);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(1, 2, 0, 0));
		
		contentPane.add(tabbedOptionsPane);
		
		tabbedOptionsPane.addTab("My files", null, myFilesPanel, null);
		tabbedOptionsPane.addTab("My friends", null, friendsPanel, null);
		tabbedOptionsPane.addTab("Friend requests", null, friendRequestsPanel, null);
		tabbedOptionsPane.addTab("Friend's files", null, friendFilesPanel, null);
		
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
}	

