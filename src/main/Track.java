package main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.JPanel;

public class Track extends JPanel {
	final static int times = 10;
	public static ArrayList<Wall> wallList = new ArrayList<Wall>();
	final int[][] walls = { { -40, 0, 40, 0 }, { -6, 0, -6, 22 }, { 6, 0, 6, 10 }, { -6, 22, 18, 22 },
			{ 6, 10, 30, 10 }, { 18, 22, 18, 50 }, { 30, 10, 30, 50 }, { 18, 50, 30, 50 } };

	final int[] endPoint = { 24, 37 };
	Car car;
	Trace trace;

	public Track() {
		wallList.add(new Wall(Wall.horizontal, 10, 6, 30));
		wallList.add(new Wall(Wall.horizontal, 22, -6, 18));
		wallList.add(new Wall(Wall.horizontal, 50, 18, 30));
		wallList.add(new Wall(Wall.vertical, -6, 0, 22));
		wallList.add(new Wall(Wall.vertical, 6, 0, 10));
		wallList.add(new Wall(Wall.vertical, 18, 22, 50));
		wallList.add(new Wall(Wall.vertical, 30, 10, 50));

		car=new Car();
		trace=new Trace();
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.translate(400, 600);
		Graphics2D g2 = (Graphics2D) g;
		g2.setStroke(new BasicStroke(3.0f));

		trackDraw(g2);
		car.carDraw(g2);
		g2.setColor(Color.GRAY);
		trace.traceDraw(g2);
	}

	public void trackDraw(Graphics2D g2) {
		g2.drawLine(walls[0][0] * times, -walls[0][1] * times, walls[0][2] * times, -walls[0][3] * times);

		g2.setColor(Color.BLUE);
		for (int i = 1; i < walls.length; i++) {
			g2.drawLine(walls[i][0] * times, -walls[i][1] * times, walls[i][2] * times, -walls[i][3] * times);
		}

		g2.setColor(Color.red);
		g2.fillOval(endPoint[0] * times, -endPoint[1] * times, 10, 10);
	}
	
	
}