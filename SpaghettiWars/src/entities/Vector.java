package entities;

public class Vector {
	
	private double dx;
	private double dy;
	
	public Vector(double dx, double dy){
		this.dx = dx;
		this.dy = dy;
	}
	
	public double getDeltaX(){
		return dx;
	}
	
	public double getDeltaY(){
		return dy;
	}
	
	public void setDeltaX(double dx){
		this.dx = dx;
	}
	
	public void setDeltaY(double dy){
		this.dy = dy;
	}
	
	public double getLength(){
		return Math.sqrt(dx*dx + dy*dy);
	}
	
	public void setLengthTo(double length){
		if(dx != 0 && dy!= 0){
			dx = (dx/Math.abs(dx))*length/Math.sqrt(2);
			dy = (dy/Math.abs(dy))*length/Math.sqrt(2);
		}else{
			dx = dx/Math.abs(dx)*length;
			dy = dy/Math.abs(dy)*length;
		}
	}
	
	public void multiplyLengthBy(double length){
		dx = length*dx;
		dy = length*dy;
	}
}
