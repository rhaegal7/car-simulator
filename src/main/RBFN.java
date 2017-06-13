package main;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class RBFN {
	int p;
	int J;

	double theta;
	double[] w;
	double[][] m;
	double[] sigma;

	double[][] x;
	double[] y;
	
	public RBFN(int J, double[] geneVector) {
		
		this.J = J;
		p = 5;
		
		try {
			List<String> lines = Files.readAllLines(Paths.get("train6D.txt"));
			x=new double[lines.size()][p];
			y=new double[lines.size()];
			for(int i=0;i<lines.size();i++){
				String[] line=lines.get(i).split(" ");
				for(int j=0;j<x[i].length;j++)
					x[i][j]=Double.parseDouble(line[j]);
				y[i]=Double.parseDouble(line[p]);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		theta = geneVector[0];

		w = Arrays.copyOfRange(geneVector, 1, J + 1);

		m = new double[J][p];
		for (int i = 0; i < J; i++) {
			m[i] = Arrays.copyOfRange(geneVector, J + 1 + i * p, J + 1 + i * p + p);
		}

		sigma = Arrays.copyOfRange(geneVector, 1 + J + J * p, 1 + J + J * p + J);
	}

	public void setPara(double[] geneVector) {
		theta = geneVector[0];

		w = Arrays.copyOfRange(geneVector, 1, J + 1);

		m = new double[J][p];
		for (int i = 0; i < J; i++) {
			m[i] = Arrays.copyOfRange(geneVector, J + 1 + i * p, J + 1 + i * p + p);
		}

		sigma = Arrays.copyOfRange(geneVector, 1 + J + J * p, 1 + J + J * p + J);
	}

	static public double basicFuntion(double[] xi, double[] mJ, double sigmaJ) {
		double temp = 0;
		for (int i = 0; i < xi.length; i++)
			temp = temp + (xi[i] - mJ[i]) * (xi[i] - mJ[i]);
		temp = -temp / (2 * sigmaJ * sigmaJ);
		temp = Math.exp(temp);

		return temp;
	}

	public double getF(double[] xi){
		double result=0;
		for(int i=0;i<J;i++){
			result=result+basicFuntion(xi, m[i], sigma[i])*w[i];
		}
		result=result+theta;
		
		return result;
	}
	
	public double fitness(){
		double result=0;

		for(int i=0;i<x.length;i++){
			double F=getF(x[i]);
			double temp=normalization(y[i])-normalization(F);
			result=result+temp*temp;
		}
		
		result=result/2.0;
		return result;
	}
	
	public double error(){
		
		return 0;
	}
	
	static public double[] randomGene(int J,int p){
		double[] gene=new double[(p+2)*J+1];
		gene[0]=Math.random(); //theta
		for(int i=1;i<J+1;i++){
			gene[i]=Math.random();
		}
		for(int i=J+1;i<1+(p+1)*J;i++){
			gene[i]=Math.random()*30;
		}
		for(int i=1+(p+1)*J;i<gene.length;i++)
			gene[i]=Math.random()*10;
		
		return gene;
	}
	
	public void print() {
		System.out.println("theta=" + theta);
		System.out.println("w:");
		for (double wj : w)
			System.out.print(wj + " ");
		System.out.println();
		System.out.println("m:");
		for (int i = 0; i < m.length; i++) {
			for (int j = 0; j < m[i].length; j++) {
				System.out.print(m[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println("sigma:");
		for (double sigmaj : sigma)
			System.out.print(sigmaj + " ");
		System.out.println();
	}

	private void printXY(){
		for(int i=0;i<x.length;i++){
			for(int j=0;j<x[i].length;j++)
				System.out.print(x[i][j]+" ");
			System.out.println(y[i]);
		}
	}
	
	static private double normalization(double input){
			input=(input+40.0)/80.0;//(-40,40) to (0,1)
		return input;
	}
	
	public static void main(String[] args) {
		double[] geneVector = randomGene(7,5);
		RBFN rbfn = new RBFN(7, geneVector);
		rbfn.print();
		System.out.println(rbfn.fitness());
	}

}
