package main;

public class FuzzySystem {

	public static double calTheta(double RF, double front) {
		int sign = 1;
		if (RF < 0){
			RF=-1*RF;
			sign = -1;
		}
		double[] strength = new double[9];
		strength[0]=rule1(RF,front);
		strength[1]=rule2(RF,front);
		strength[2]=rule3(RF,front);
		strength[3]=rule4(RF,front);
		strength[4]=rule5(RF,front);
		strength[5]=rule6(RF,front);
		strength[6]=rule7(RF,front);
		strength[7]=rule8(RF,front);
		strength[8]=rule9(RF,front);
		
		double[] thetaList={30,20,10,20,16,10,20,16,10};
		double theta=0;
		for(int i=0;i<9;i++)
			theta=theta+strength[i]*thetaList[i];
		
		return sign*theta;

	}

	private static double rule1(double RF, double front) {
		try {
			return Math.min(smallRF(RF), smallFront(front));
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	private static double rule2(double RF, double front) {
		try {
			return Math.min(smallRF(RF), mediumFront(front));
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	private static double rule3(double RF, double front) {
		try {
			return Math.min(smallRF(RF), largeFront(front));
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	private static double rule4(double RF, double front) {
		try {
			return Math.min(mediumRF(RF), smallFront(front));
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	private static double rule5(double RF, double front) {
		try {
			return Math.min(mediumRF(RF), mediumFront(front));
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	private static double rule6(double RF, double front) {
		try {
			return Math.min(mediumRF(RF), largeFront(front));
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	private static double rule7(double RF, double front) {
		try {
			return Math.min(largeRF(RF), smallFront(front));
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	private static double rule8(double RF, double front) {
		try {
			return Math.min(largeRF(RF), mediumFront(front));
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	private static double rule9(double RF, double front) {
		try {
			return Math.min(largeRF(RF), largeFront(front));
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	private static double smallRF(double x) throws Exception {
		if (x < 0)
			throw new Exception();
		else if (x < 2)
			return 1.0;
		else if (x < 6)
			return -0.25 * x + 1.5;
		else
			return 0;
	}

	private static double mediumRF(double x) throws Exception {
		if (x < 0)
			throw new Exception();
		else if (x < 4)
			return 0;
		else if (x < 10)
			return x / 6.0 - 2.0 / 3.0;
		else if (x < 20)
			return 1.0;
		else if (x < 25)
			return -0.2 * x + 5;
		else
			return 0;

	}

	private static double largeRF(double x) throws Exception {
		if (x < 0)
			throw new Exception();
		else if (x < 20)
			return 0;
		else if (x < 40)
			return 0.05 * x - 1;
		else
			return 1.0;
	}

	private static double smallFront(double x) throws Exception {
		if (x < 0)
			throw new Exception();
		else if (x < 8)
			return 1.0;
		else if (x < 12)
			return -0.25 * x + 3;
		else
			return 0;
	}

	private static double mediumFront(double x) throws Exception {
		if (x < 0)
			throw new Exception();
		else if (x < 10)
			return 0;
		else if (x < 15)
			return 0.2 * x - 2.0;
		else if (x < 20)
			return 1.0;
		else if (x < 25)
			return -0.2 * x + 5;
		else
			return 0;

	}

	private static double largeFront(double x) throws Exception {
		if (x < 0)
			throw new Exception();
		else if (x < 20)
			return 0;
		else if (x < 40)
			return 0.05 * x - 1;
		else
			return 1.0;
	}

}
