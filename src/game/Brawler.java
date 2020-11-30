package game;

import java.awt.Graphics;

public class Brawler extends Sprite {
	int r;
	
	public static final String[] name = {
			"b/b_up",
			"b/b_dn",
			"b/b_lt",
			"b/b_rt"
	};

	//double x, double y
	public Brawler(int x, int y, int action, int r) {
		super(x - Camera.x + Camera.x_origin, y - Camera.y + Camera.y_origin, action, name, 10, 4, "png");
		this.r = r;
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
	
	@Override
	public void shoot(Circle[] bullet) {
		if(action == UP) 
			gun.setAngle(270);
		if(action == DOWN) 
			gun.setAngle(90);
		if(action == LEFT) 
			gun.setAngle(180);
		if(action == RIGHT) 
			gun.setAngle(0);
		gun.x = x - Camera.x + Camera.x_origin;
		gun.y = y - Camera.y + Camera.y_origin + 20;
		
		gun.launch(bullet);
	}
}