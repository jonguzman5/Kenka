package game;


public class BadCircle extends Circle {

	public BadCircle(double x, double y, double r, int a) {
		super(x, y, r, a);
		
	}
	public void turnToward(Circle c) {
		if(toLeftOf(c))
			turnLeft(4);
		else
			turnRight(4);
	}
	
	public void turnToward(Brawler b) {
		if(toLeftOf(b))
			turnLeft(4);
		else
			turnRight(4);
	}
	
	public void chase(Circle c) {
		turnToward(c);
		goForward(4);
	}
	
	public void chase(Brawler b) {
		turnToward(b);
		goForward(4);
	}

}

