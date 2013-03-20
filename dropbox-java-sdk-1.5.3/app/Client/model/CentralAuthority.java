package Client.model;

import java.io.File;

import Client.view.View;

public class CentralAuthority {
	
	private static final String UPLOAD_FILE = "1";
	
	public static void options(ServerComms comms, View view) {
		
		String option = view.getCentralDecision();
		
		comms.toServer(option);
		
		if (option.equals(UPLOAD_FILE))
			uploadFile(comms, view);
		
	}
	
	
	private static void uploadFile(ServerComms comms, View view) {
		
		boolean fileAccepted = false;
		String fileLocation = null;
		File file = null;
		
		while (!fileAccepted) {
			fileLocation = view.getFileLocation();
			file = new File(fileLocation);
			if(file.exists())
				fileAccepted = true;
			else
				view.fileNotAccepted();
		}
		
		String securityLevel = view.getSecurityLevel();
		
		/* Send security level of file to server */
		comms.toServer(securityLevel);
		
		FileOperations.encryptFile(file, fileLocation, comms);
		
	}

}
