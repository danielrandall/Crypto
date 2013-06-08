package client.controller;

import client.model.Actions;
import client.view.MyFilesPanel;
import client.view.UploadFileFrame;

public class FileUploadCommand implements Command {
	
	private UploadFileFrame frame;
	private MyFilesPanel panel;

	public void execute(Object[] objects) {
		
		frame = (UploadFileFrame) objects[0];
		panel = (MyFilesPanel) objects[1];
		
		String fileLocation = frame.getFileLocation();
		
		if (fileLocation == null || fileLocation.equals(""))
			return;
		
		int securityLevel = frame.getSecurityLevel();
		
		
		
		String newFileName = Actions.uploadFile(fileLocation, securityLevel);

		Object[] fileInfo = {newFileName, securityLevel};
		panel.addElementToTable(fileInfo);
		
		frame.fileUploaded();
		/*
		String fileLocation = "testfiles/multipage111.pdf";
		int securityLevel = 5;
		
		long startTime = 0;
		
		for (int i = 0; i < 100; i++) {
			if (i == 0)
				startTime = System.nanoTime();;
			String newFileName = Actions.uploadFile(fileLocation, securityLevel);
		}
		
		long endTime = System.nanoTime();

		long duration = endTime - startTime;
		
		System.out.println("startTime = " + startTime);
		System.out.println("");
		System.out.println("endTime = " + endTime);
		System.out.println("");
		System.out.println("duration = " + duration);
		double seconds = (double)duration / 1000000000.0;
		System.out.println("");
		System.out.println("duration in seconds = " + seconds);
		*/
	}
	
	

}
