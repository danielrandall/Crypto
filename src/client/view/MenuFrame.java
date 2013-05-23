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
		
		tabbedOptionsPane.addTab("My Files", null, myFilesPanel, null);
		tabbedOptionsPane.addTab("My Friends", null, friendsPanel, null);
		
	}
	
	private void run() {
		setVisible(true);		
	}
	
	
	/* Pass rows to the myFiles panel to put into the table */
	public void populateUploadedFiles(Object[][] uploadedFileRows) {
		
		myFilesPanel.populateUploadedFiles(uploadedFileRows);
		
	}
	
	public void populateFriends(Object[][] friends) {
		
		friendsPanel.populateFriends(friends);
		
	}
}	

