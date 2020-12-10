package game;

import java.awt.Graphics;

public class KungFuMan extends Character {
	boolean alive = true;
	int r;
	int a;
	
	double cosA;
	double sinA;
	
	public static final String[] pose = {
			"k/k_up",
			"k/k_dn",
			"k/k_lt",
			"k/k_rt"
	};

	public KungFuMan(int hit_points, int x, int y, int action, int r, int a) {
		super(hit_points, x, y, action, pose, 10, 4, "png");
		this.r = r;
		this.a = a;
		cosA = Lookup.cos[a];
		sinA = Lookup.sin[a];
	}
}