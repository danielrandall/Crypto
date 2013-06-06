package client.model;

import client.model.keystore.KeyStoreOperations;

public abstract class Model {
	
	protected static DropboxOperations dropboxOperations = new DropboxOperations();
	private static KeyStoreOperations keyStoreOperations = new KeyStoreOperations();
	private static ServerComms serverComms = new ServerComms();

}
