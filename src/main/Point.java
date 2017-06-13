package main;

public class Point {
	public double x;
	public double y;
	
	public Point(double x,double y){
		this.x=x;
		this.y=y;
	}

	public double distance(double x2,double y2){
		double distance=Math.sqrt(Math.pow(x-x2,2)+Math.pow(y-y2, 2));
		return distance;
	}
	
}
