package game;

import java.awt.Graphics;

public class Character extends Sprite {
	boolean alive = true;

	int hit_points; 
	int health;
	
	int r;
	int a;
	
	double cosA;
	double sinA;
	
	double launch_delay = 0;
	double launch_countdown = 30; 
	
	static int bulletNum = 0;

	public Character(int hit_points, int x, int y, int action, String[] pose, int duration, int count, String extension) {
		super(x, y, action, pose, duration, count, extension);
		this.hit_points = hit_points;
		this.health = this.hit_points;
		this.r = 23;//23 = char img w
		this.a = 0;//init angle !matter
		cosA = Lookup.cos[a];
		sinA = Lookup.sin[a];
	}

	public void takesHitFor(int dhealth) {
		health -= dhealth;

		if (health <= 0)
			alive = false;
	}

	public void heal(int dhealth) {
		health += dhealth;

		if (health > hit_points)
			health = hit_points;
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
	
	public void draw(Graphics g) {
		if (alive)
			super.draw(g);
	}
	
	public void drawBoss(Graphics g) {
		if (alive)
			super.drawBoss(g);
	}

}