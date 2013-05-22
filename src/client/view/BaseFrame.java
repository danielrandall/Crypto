package client.view;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JFrame;

import client.controller.Controller;

public abstract class BaseFrame extends JFrame implements WindowListener {
	
	protected static Font museosans_900_10p;
	protected static Font museosans_700_14p;
	protected static Font museosans_900_18p;
	
	private void stop() {
		
		Controller.exit();
		
	}
	
	protected static void createFonts() {
		
		/* Create font */
		File ms_500_file = new File("fonts/museosans_500.ttf");
		Font museosans_500 = null;
		File ms_700_file = new File("fonts/museosans_700.ttf");
		Font museosans_700 = null;
		File ms_900_file = new File("fonts/museosans_900.ttf");
		Font museosans_900 = null;

		try {
			museosans_500 = Font.createFont(Font.TRUETYPE_FONT, ms_500_file);
			museosans_700 = Font.createFont(Font.TRUETYPE_FONT, ms_700_file);
			museosans_900 = Font.createFont(Font.TRUETYPE_FONT, ms_900_file);
		} catch (FontFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/* Set size of created font */
		museosans_900_10p = museosans_900.deriveFont(10f);
		museosans_700_14p = museosans_700.deriveFont(14f);
		museosans_900_18p = museosans_700.deriveFont(18f);
		
	}
	
	@Override
	public void windowClosing(WindowEvent arg0) {
		stop();				
	}

	@Override
	public void windowActivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosed(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeactivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowOpened(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}


}
