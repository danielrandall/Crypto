package server.users.friends;

import java.io.Serializable;

public interface Permissions extends Serializable {
	
	public int getLowerBound();
	
	public int getUpperBound();

}
