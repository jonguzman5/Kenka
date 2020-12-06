package game;

import java.awt.Graphics;

public class Brawler extends Sprite {
	boolean alive = true;
	int r;
	int a;
	
	double cosA;
	double sinA;
	
	public static final String[] name = {
			"b/b_up",
			"b/b_dn",
			"b/b_lt",
			"b/b_rt"
	};

	public Brawler(int x, int y, int action, int r, int a) {
		super(x - Camera.x + Camera.x_origin, y - Camera.y + Camera.y_origin, action, name, 10, 4, "png");
		this.r = r;
		this.a = a;
		cosA = Lookup.cos[a];
		sinA = Lookup.sin[a];
	}
	
	public double distanceTo(double x, double y) {
		double dx = x - this.x;
		double dy = y - this.y;
		return Math.sqrt((dx* dx) + (dy * dy));
	}
	
	public boolean overlaps(Line l) {
		int d = (int) l.distanceTo(x, y);
		return d*d < r*r;
	}

	public void isPushedBackBy(Line l) {
		int d = (int) l.distanceTo(x, y);
		int p = r - d;
		x += p * l.nx;
		y += p * l.ny;
	}
}