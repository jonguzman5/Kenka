package game;

import java.awt.Graphics;

public class Enemy extends Character {
	
	public Enemy(int hit_points, int x, int y, int action, String[] pose, int duration, int count, String extension) {
		super(hit_points, x, y, action, pose, duration, count, extension);
	}

	public boolean overlaps(Enemy e) {
		double d2 = (x - e.x) * (x - e.x) + (y - e.y) * (y - e.y);
		return d2 < (r + e.r) * (r + e.r);
	}

	public void pushes(Enemy e) {
		double dx = x - e.x;
		double dy = y - e.y;
		double d = Math.sqrt(dx * dx + dy * dy);

		double ux = dx / d;
		double uy = dy / d;

		double ri = r + e.r;
		double p = ri - d;

		x += ux * p / 2;
		y += uy * p / 2;

		e.x -= ux * p / 2;
		e.y -= uy * p / 2;
	}

	public void moveBy(double dx, double dy) {
		x += dx;
		y += dy;
	}

	public boolean toLeftOf(Brawler b) {
		return sinA * (b.x - x) - cosA * (b.y - y) > 0;
	}
	
	public boolean within(double distance, Brawler b) {
		double dx = x - b.x;
		double dy = y - b.y;
		return dx * dx + dy * dy < distance * distance;
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
		if (toLeftOf(b)) {
			turnLeft(4);
		}
		else {
			turnRight(4);
		}
	}

	public void chase(Brawler b) {
		turnToward(b);
		goForward(4);
	}
	
	public void launch(Circle[] bullet) {
		if(launch_delay == 0) {
			//vel
			double speed = 5;					
			bullet[bulletNum].vx = speed * cosA;
			bullet[bulletNum].vy = speed * sinA;
			//starting pt
			bullet[bulletNum].x = x + (r+6) * cosA;
			bullet[bulletNum].y = y + (r+6) * sinA;
			
			//if(bullet[bulletNum].x >= 500 || bullet[bulletNum].y >= 500) {
				//bullet[bulletNum].vx = 0;
				//bullet[bulletNum].vy = 0;
			//}

			launch_delay = launch_countdown;
			
			bulletNum++;
			if(bulletNum == bullet.length)
				bulletNum = 0;
		}
		launch_delay--;
	}

	public void draw(Graphics g) {
		super.draw(g);
		//g.drawOval((int)(x - r) - Camera.x + Camera.x_origin + 15, (int)(y - r) - Camera.y + Camera.y_origin + 30, (int)r * 2, (int)r * 2);
	}
	
	public void drawBoss(Graphics g) {
		super.drawBoss(g);
	}

}
