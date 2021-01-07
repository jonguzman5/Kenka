package game;

import java.awt.Graphics;

public class Brawler extends Character {
	
	public static final String[] pose = {
			"b/b_up",
			"b/b_dn",
			"b/b_lt",
			"b/b_rt"
	};

	public Brawler(int hit_points, int x, int y, int action) {
		super(hit_points, x - Camera.x + Camera.x_origin, y - Camera.y + Camera.y_origin, action, pose, 10, 4, "png");
	}

}