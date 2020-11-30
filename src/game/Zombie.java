package game;

public class Zombie extends Sprite {
	boolean alive = true;
	
	double cosA;
	double sinA;
	
	double x1;
	double y1;
	
	double x2;
	double y2;
	
	double nx;
	double ny;
	
	int a;
	double r;
	

	public static final String[] name = { 
			"z/z_up", 
			"z/z_dn", 
			"z/z_lt", 
			"z/z_rt" 
	};

	//double x, double y
	public Zombie(int x, int y, int action, double r, int a) {
		super(x, y, action, name, 10, 4, "png");
		this.r = r;
		this.a = a;
		cosA = Lookup.cos[a];
		sinA = Lookup.sin[a];
		computeNormal();
	}
	
	public void computeNormal() {
		double vx = x;//FIX
		double vy = y;//FIX		
		double mag = Math.sqrt((vx * vx) + (vy * vy));		
		double ux = vx / mag;
		double uy = vy / mag;
		nx = -uy;
		ny = ux;
	}
	
	public double distanceTo(double x, double y) {
		return nx * (x - x1) + ny * (y - y1);
	}

	public boolean overlaps(Line l) {
		double d = l.distanceTo(x, y);
		return d * d < r * r;
	}

	public void isPushedBackBy(Line l) {
		double d = l.distanceTo(x, y);
		double p = r - d;
		x += p * l.nx;
		y += p * l.ny;
	}

	public boolean overlaps(Zombie z) {
		double d2 = (x - z.x) * (x - z.x) + (y - z.y) * (y - z.y);
		return d2 < (r + z.r) * (r + z.r);
	}

	public void pushes(Zombie z) {
		double dx = x - z.x;
		double dy = y - z.y;
		double d = Math.sqrt(dx * dx + dy * dy);

		double ux = dx / d;
		double uy = dy / d;

		double ri = r + z.r;
		double p = ri - d;

		x += ux * p / 2;
		y += uy * p / 2;

		z.x -= ux * p / 2;
		z.y -= uy * p / 2;
	}

	public void moveBy(double dx, double dy) {
		x += dx;
		y += dy;
	}

	public boolean toLeftOf(Brawler b) {
		return sinA * (b.x - x) - cosA * (b.y - y) > 0;
	}

	public void turnLeft(int da) {
		a -= da;
		if (a < 0)
			a += 360;
		cosA = Lookup.cos[a];
		sinA = Lookup.sin[a];
	}

	public void turnRight(int da) {
		a += da;
		if (a > 359)
			a -= 360;
		cosA = Lookup.cos[a];
		sinA = Lookup.sin[a];
	}

	public void goForward(double d) {
		double dx = d * cosA;
		double dy = d * sinA;
		moveBy(dx, dy);
	}

	public void turnToward(Brawler b) {
		if (toLeftOf(b))
			turnLeft(4);
		else
			turnRight(4);
	}

	public void chase(Brawler b) {
		turnToward(b);
		goForward(4);
	}
}
