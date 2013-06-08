package client.model;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

public interface Storage {
	
	public String[] uploadFile(String path, InputStream inputStream,
			int length);
	
	public String downloadFile(String fileName, OutputStream stream);
	
	public String removeFile(String fileName);
	
	public void setup(String username);
	
	public String downloadFriendFile(String fileName, String friendName, OutputStream stream);
	
	public String[][] getUploadedFiles();
	
	public String[][] getFriendFiles();
	
	public Map<String, ClientFile> findFileNames(Map<String, ClientFile> files);

}
