package utilities;

public class Position {
	
	private double x;
	private double y;
	
	public Position(double x, double y){
		this.x = x;
		this.y = y;
	}
	
	public double getX(){
		return x;
	}
	
	public double getY(){
		return y;
	}
	
	//may or may not need this thing for something...
	public double distanceTo(Position pos){
		return Math.sqrt(Math.pow(this.x - pos.getX(), 2) + Math.pow(this.y - pos.getY(), 2));
	}
}
