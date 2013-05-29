package client.model;

public class ClientFile {
	
	private String rev;
	private String fileName;
	private byte[] iv;
	private int securityLevel;
	
	public ClientFile(String rev, byte[] iv, int securityLevel) {
		this.rev = rev;
		this.iv = iv;
		this.securityLevel = securityLevel;
	}
	
	public String getRev() {
		return rev;
	}
	
	public byte[] getIV() {
		return iv;
	}
	
	public int getSecurityLevel() {
		return securityLevel;
	}

	public String getFileName() {
		return fileName;
	}
	
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

}
