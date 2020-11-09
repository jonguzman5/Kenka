package game;

public class Brawler extends Sprite {
	double r;
	
	public static final String[] name = {
			"b/b_up",
			"b/b_dn",
			"b/b_lt",
			"b/b_rt"
	};

	public Brawler(double x, double y, int action, double r) {
		super(x, y, action, name, 10, 4, "png");
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