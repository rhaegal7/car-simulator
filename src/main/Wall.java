package main;

public class Wall {
	public static int horizontal=1;
	public static int vertical=2;
	public int direction;
	public double coordinate;
	public double start;
	public double end;
	
	public Wall(int direction, double coordinate, double start, double end) {
		super();
		this.direction = direction;
		this.coordinate = coordinate;
		this.start = start;
		this.end = end;
	}

	public void print(){
		if(direction==horizontal)
			System.out.println("horizontal "+coordinate+" "+start+" "+end);
		else
			System.out.println("vertical "+coordinate+" "+start+" "+end);
	}
}
