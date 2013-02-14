package Server;

public class ServerProtocol {
	
	private static final int REQUEST = 0;
	private static final int ACCEPTING_FILE = 1;
	private static final int PROCESSING = 2;
	
    private int state = REQUEST;
	
	public String processInput(String input) {
		
		
		
		if (state == REQUEST) {
			if (input.equals("sendFile")) {
				state = ACCEPTING_FILE;
				return "Accepting file...";
			}
			if (input.equals("getFile")) {
				state = PROCESSING;
				//get file
				return "";
			}
		}
		
		if (state == ACCEPTING_FILE) {
			if (input.equals("reset")) {
				state = REQUEST;
				return "Send request...";
			}
			if (input.equals("end")) {
				state = REQUEST;
				return "Send request...";
			}
		}
		
		return input;
	}

}
