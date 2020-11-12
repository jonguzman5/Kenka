package game;

public class Brawler extends Sprite {
	double r;
	
	public static final String[] name = {
			"b/b_up",
			"b/b_dn",
			"b/b_lt",
			"b/b_rt"
	};

	//double x, double y
	public Brawler(int x, int y, int action, double r) {
		super(x - Camera.x + Camera.x_origin, y, action, name, 10, 4, "png");
		this.r = r;
	}
	
	public boolean overlaps(Line l) {
		double d = l.distanceTo(x, y);
		return d*d < r*r;
	}

	public void isPushedBackBy(Line l) {
		double d = l.distanceTo(x, y);
		double p = r - d;
		x += p * l.nx;
		y += p * l.ny;
	}
}