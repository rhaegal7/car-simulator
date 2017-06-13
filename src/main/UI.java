package main;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class UI extends JFrame implements Runnable {
	Track panel = new Track();
	JScrollPane scrollPane;
	JTextArea output = new JTextArea();
	Car car = panel.car;
	FileWriter outputFW;
	BufferedWriter bw;
	
	JPanel input;
	JLabel jNumLabel = new JLabel("隱藏層類神經元數目：");
	JTextField jNumText = new JTextField();
	JLabel iterationLabel = new JLabel("疊代次數：");
	JTextField iterationText = new JTextField();
	JLabel groupNumLabel = new JLabel("族群大小：");
	JTextField groupNumText = new JTextField();
	JLabel mutationLabel = new JLabel("突變機率：");
	JTextField mutationText = new JTextField();
	JLabel coitusLabel = new JLabel("交配機率：");
	JTextField coitusText = new JTextField();
	
	Thread thread;
	
	JButton startCar;
	
	RBFN rbfn;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UI frame = new UI();
					frame.setVisible(true);

					frame.thread = new Thread(frame);
					

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public UI() {
		setTitle("電腦模擬車");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1500, 800);
		getContentPane().setLayout(null);

		panel.setBounds(0, 0, 800, 761);
		getContentPane().add(panel);
		panel.setLayout(null);
		output.setText("        X軸            Y軸            前方距離     右方距離    左方距離   方向盤角度\r\n");
		output.setEditable(false);
		output.setBounds(0, 0, 380, 761);

		scrollPane = new JScrollPane(output);
		scrollPane.setBounds(1000, 0, 480, 761);
		getContentPane().add(scrollPane);
		
		input = new JPanel();
		input.setBounds(800, 0, 200, 761);
		getContentPane().add(input);
		input.setLayout(null);
		
		jNumLabel.setBounds(0, 14, 168, 18);		
		input.add(jNumLabel);
		jNumText.setText("7");
		
		jNumText.setBounds(120, 50, 79, 24);
		jNumText.setColumns(10);
		input.add(jNumText);
		
		iterationLabel.setBounds(0, 90, 120, 18);
		input.add(iterationLabel);
		iterationText.setText("2000");
		
		iterationText.setColumns(10);
		iterationText.setBounds(120, 90, 79, 24);		
		input.add(iterationText);
		
		groupNumLabel.setBounds(0, 130, 120, 18);
		input.add(groupNumLabel);
		groupNumText.setText("2000");
		
		groupNumText.setColumns(10);
		groupNumText.setBounds(120, 130, 79, 24);		
		input.add(groupNumText);
		
		mutationLabel.setBounds(0, 170, 120, 18);		
		input.add(mutationLabel);
		
		coitusLabel.setBounds(0, 210, 120, 18);		
		input.add(coitusLabel);
		mutationText.setText("0.0333");
		
		mutationText.setColumns(10);
		mutationText.setBounds(120, 170, 79, 24);
		input.add(mutationText);
		coitusText.setText("0.6");
		
		coitusText.setColumns(10);
		coitusText.setBounds(120, 210, 79, 24);		
		input.add(coitusText);
		
		startCar = new JButton("開車");
		startCar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					thread.start();
			}
		});
		startCar.setBounds(44, 658, 95, 27);
		input.add(startCar);

		try {
			outputFW = new FileWriter("train6D.txt");
			bw = new BufferedWriter(outputFW);
		} catch (IOException e) {
			e.printStackTrace();
		}

//		double[] gene={0.375034209949407, 0.38064967519747633, 0.456403499582624, 0.3678581524418278, 0.41611598060617333, 0.4529912571485561, 0.4140923932848332, 0.2718494975485498, 0.42107457386877384, 0.37132385508856774, 0.3093748243677517,2.167310433069346, 20.63388444530575, 5.405230918424902, 20.27584696615224, 0.49835051302747824,18.08164444534467, 30.0, 14.823695061348825, 18.86285308629986, 0.0,7.659718138314026, 6.068547885615864, 15.311531241482774, 30.0, 5.332482080244725,18.53567075536556, 30.0, 15.894320757564602, 22.279585644505147, 0.4403478952464825,13.807876626857453, 12.024907656608594, 19.56538411809563, 6.739543291757649, 6.7641926319944945,2.5411285839101927, 22.98075110480496, 19.13416843235306, 2.691355503170802, 0.6864329394913323,19.50197951127937, 3.752461083799984, 19.09917635315649, 13.591753360341148, 3.381955929989868,14.401167441234453, 0.5807946042058497, 28.81619909115076, 11.384078398505025, 15.993445665746506,21.840134804203146, 26.485471685951566, 30.0, 25.476582972665042, 7.581670419940045,4.447086863503242, 0.4043972017345318, 9.293043103070108, 17.02479862725851, 9.836667921662226,4.7404654539718525, 8.481384704916811, 10.0, 8.896809892841866, 1.2006678569889448, 10.0, 1.1664689862380893, 4.677693218273717, 0.8975296337299354, 0.0};
//		rbfn=new RBFN(10,gene);
	}
	@Override
	public void run() {
		while (true) {
			panel.trace.addPoint(car.getX(), car.getY());
			
			if (car.getY() >= 37.0) {
				
				output.append("到达终点");
				
				try {
					bw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			}
			try {
				double RF = car.getRight() - car.getLeft();
				double theta = FuzzySystem.calTheta(RF, car.getFront());
				car.setTheta(theta);

				String tempStr = getOut();

				try {
					bw.write(tempStr);
				} catch (IOException e) {
					e.printStackTrace();
				}

				SwingUtilities.invokeLater(() -> {
					panel.repaint();
					output.append(tempStr);

				});

				car.updateVariable();
				car.updateSensor();
				
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

	private String trim7(double in) {
		if (in < 10)
			return String.format("%.7f", in);
		else
			return String.format("%.6f", in);
	}

	private String getOut() {
		return trim7(car.getX()) + " " + trim7(car.getY()) + " " + trim7(car.getFront()) + " " + trim7(car.getRight())
				+ " " + trim7(car.getLeft()) + " " + trim7(car.getTheta()) + "\r\n";
	}
	
	private String getOut2(){
		return trim7(car.getFront()) + " " + trim7(car.getRight())
		+ " " + trim7(car.getLeft()) + " " + trim7(car.getTheta()) + "\r\n";
	}
}
