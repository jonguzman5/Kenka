package game;

public class Lookup {
	//static: want 1 copy of ea, public: availability
	public static double[]cos = genCos();
	public static double[]sin = genSin();
	
	public static double[] genCos() {
		double[]cos = new double[360];
		for(int i = 0; i < 360; i++) 
			cos[i] = Math.cos(i * Math.PI / 180);
		return cos;
	}
	public static double[] genSin() {
		double[]sin = new double[360];
		for(int i = 0; i < 360; i++) 
			sin[i] = Math.sin(i * Math.PI / 180);
		return sin;
	}
	
}
