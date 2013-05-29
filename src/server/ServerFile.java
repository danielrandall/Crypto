package server;

public class ServerFile {
	
	private String rev;
	private byte[] iv;
	private int securityLevel;
	
	public ServerFile(String rev, byte[] iv, int securityLevel) {
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

}
