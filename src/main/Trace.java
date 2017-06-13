package main;

import java.awt.Graphics2D;
import java.util.ArrayList;

public class Trace {
	ArrayList<Point> trace=new ArrayList<Point>();

	void addPoint(double x,double y){
		trace.add(new Point(x,y));
	}
	
	void traceDraw(Graphics2D g){
		if(trace.size()>0){
			int times=Track.times;
			for(int i=1;i<trace.size();i++){
				Point p1=trace.get(i-1);
				Point p2=trace.get(i);
				g.drawLine((int)p1.x*times, -(int)p1.y*times, (int)p2.x*times, -(int)p2.y*times);
			}
		}
	}
}
