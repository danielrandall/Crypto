package Linking;

public class Interval {
	
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
