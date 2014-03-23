package com.example.proxshop;

public class PositionHandler {
	private final static double[] LATITUDINI = {45.500818, 45.501303, 45.501393, 45.500873} ;
	private final static double[] LONGITUDINI = {9.263376, 9.263370, 9.264427, 9.264527} ;
	public PositionHandler() {
		// TODO Auto-generated constructor stub
	}
	
	
	static public Boolean imInTheSquare(double lat, double lon){
		Boolean isTrue=false;
		
		if((45.500818<lat) && (lat< 45.501393) && (9.263370<lon) &&  (lon<9.264527)){
			isTrue=true;
		}
		
		
		
		return isTrue;
	}
	
	

}
