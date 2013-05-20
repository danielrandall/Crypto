package client.model.users.friends;

public class Interval implements Permissions {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8259409064838243549L;
	private int lowerBound;
	private int upperBound;
	
	
	public Interval(int lower, int higher) {
		
		lowerBound = lower;
		upperBound = higher;
		
	}
	
	public int getLowerBound() {
		
		return lowerBound;
		
	}
	
	public int getUpperBound() {
		
		return upperBound;
		
	}

}
