package main;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

public class Car{
	public static final double MAX_DISTANCE=10000000;
	public final double b = 3; // 模型車的長度
	
	private double x = 0;
	private double y = 0;
	private double fai = 90; // 模型車與水平軸的角度
	private double theta = 0; // 模型車方向盤所打的角度

	private double front;
	private double right;
	private double left;

	public ArrayList<Wall> wallList = new ArrayList<Wall>();

	public Car() {
		wallList.add(new Wall(Wall.horizontal, 10, 6, 30));
		wallList.add(new Wall(Wall.horizontal, 22, -6, 18));
		wallList.add(new Wall(Wall.horizontal, 50, 18, 30));
		wallList.add(new Wall(Wall.vertical, -6, 0, 22));
		wallList.add(new Wall(Wall.vertical, 6, 0, 10));
		wallList.add(new Wall(Wall.vertical, 18, 22, 50));
		wallList.add(new Wall(Wall.vertical, 30, 10, 50));
		updateSensor();
	}

	public void carDraw(Graphics2D g2) {
		int times = Track.times;
		double faiPI = fai * Math.PI / 180;
		g2.drawOval((int) ((x - b / 2.0) * times), -(int) ((y + b / 2.0) * times), (int) (b * times),
				(int) (b * times));
		g2.drawLine((int) (x * times), -(int) (y * times), (int) ((x + b * Math.cos(faiPI)) * times),
				-(int) ((y + b * Math.sin(faiPI)) * times));
	}

	public double nextX(double x, double fai, double theta) {
		double faiPI = Math.toRadians(fai);
		double thetaPI = Math.toRadians(theta);
		double nextX = x + Math.cos(faiPI + thetaPI) + Math.sin(thetaPI) * Math.sin(faiPI);
		return (int) Math.round(nextX * 10000000) / 10000000.0;
	}

	public double nextY(double y, double fai, double theta) {
		double faiPI = Math.toRadians(fai);
		double thetaPI = Math.toRadians(theta);
		double nextY = y + Math.sin(faiPI + thetaPI) - Math.sin(thetaPI) * Math.cos(faiPI);
		return (int) Math.round(nextY * 10000000) / 10000000.0;
	}

	public double nextFai(double fai, double theta) {
		double faiPI = Math.toRadians(fai);
		double thetaPI = Math.toRadians(theta);
		double nextFaiPI = faiPI - Math.asin(2 * Math.sin(thetaPI) / b);
		double nextFai = Math.toDegrees(nextFaiPI);
		if (nextFai < -90)
			nextFai = nextFai + 360;
		if (nextFai > 270)
			nextFai = nextFai - 360;
		return nextFai;
	}

	public double calRight(){
		double right=Car.MAX_DISTANCE;
		double newFai=fai-45;
		if(newFai<-90)
			newFai=newFai+360;
		for(Wall wall:wallList){
			double temp=carToWall(newFai, wall);
			if(temp<right)
				right=temp;
		}
		
		return right;
	}
	
	public double calLeft(){
		double left=Car.MAX_DISTANCE;
		double newFai=fai+45;
		if(newFai>270)
			newFai=newFai-360;
		for(Wall wall:wallList){
			double temp=carToWall(newFai, wall);
			if(temp<left)
				left=temp;
		}
		
		return left;
	}
	
	public double calFront(){
		double front=Car.MAX_DISTANCE;
		for(Wall wall:wallList){
			double temp=carToWall(fai, wall);
			if(temp<front)
				front=temp;
		}
		
		return front;
	}
	
	void updateVariable(){
		x=nextX(x, fai, theta);
		y=nextY(y, fai, theta);
		fai=nextFai(fai,theta);
		
		
	}
	
	void updateSensor(){
		right=calRight();
		left=calLeft();
		front=calFront();
		
	}
	
	/**
	 * @param newTheta
	 * @param walls
	 * @return car的射线与墙的交点
	 */
	private Point calPoint(double newTheta, Wall wall) {

		if (Math.abs(90 - newTheta) < 0.01) {// 垂直且向上的情况
			if (wall.direction == Wall.horizontal) {
				if (x > wall.start && x < wall.end && y < wall.coordinate)
					return new Point(x, wall.coordinate);
				else
					return null;
			}
		}

		if (Math.abs(-90 - newTheta) < 0.01 || Math.abs(270 - newTheta) < 0.01) {// 垂直且向下的情况
			if (wall.direction == Wall.horizontal) {
				if (x > wall.start && x < wall.end && y < wall.coordinate)
					return new Point(x, wall.coordinate);
				else
					return null;
			}
		}

		double thetaPI = Math.toRadians(newTheta);
		Point point = null;
		double a = y - Math.tan(thetaPI) * x; // y=tan(θ) * x + a

		if (wall.direction == Wall.horizontal) {
			double yWall = wall.coordinate;
			double xWall = (yWall - a) / Math.tan(thetaPI);
			if (xWall > wall.start && xWall < wall.end && isSameDirection(xWall, yWall, thetaPI))
				return new Point(xWall, yWall);
			else
				return null;
		}

		if (wall.direction == Wall.vertical) {
			double xWall = wall.coordinate;
			double yWall = Math.tan(thetaPI) * xWall + a;
			if (yWall > wall.start && yWall < wall.end && isSameDirection(xWall, yWall, thetaPI))
				return new Point(xWall, yWall);
			else
				return null;
		}

		System.out.println("Wrong!");
		return null;
	}

	private boolean isSameDirection(double xWall, double yWall, double thetaPI) {
		double xOuter = x + b * Math.cos(thetaPI);
		double yOuter = y + b * Math.sin(thetaPI);

		return ((xWall - xOuter) > 0) == ((xOuter - x) > 0) && ((yWall - yOuter) > 0) == ((yOuter - y) > 0);

	}

	private double carToWall(double newFai,Wall wall){
		Point point=calPoint(newFai,wall);
		if(point==null)
			return Car.MAX_DISTANCE;
		else return point.distance(x,y);
	}
	
	
	public static void main(String[] args) {
		System.out.println("Car.main");
		Car car = new Car();
		car.setY(30);
		System.out.println(car.calRight()+" "+car.calLeft()+" "+car.calFront());
		

	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getFai() {
		return fai;
	}

	public void setFai(double fai) {
		this.fai = fai;
	}

	public double getTheta() {
		return theta;
	}

	public void setTheta(double theta) {
		this.theta = theta;
	}

	public double getFront() {
		return front;
	}

	public void setFront(double front) {
		this.front = front;
	}

	public double getRight() {
		return right;
	}

	public void setRight(double right) {
		this.right = right;
	}

	public double getLeft() {
		return left;
	}

	public void setLeft(double left) {
		this.left = left;
	}

	

}
