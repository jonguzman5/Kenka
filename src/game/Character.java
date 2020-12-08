package game;

import java.awt.Graphics;

public class Character extends Sprite {
	boolean alive = true;

	int hit_points; 

	int health;

	public Character(int hit_points, int x, int y, int action, String[] pose, int duration, int count, String extension) {
		super(x, y, action, pose, duration, count, extension);
		this.hit_points = hit_points;
		this.health = this.hit_points;
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

	public void draw(Graphics g) {
		if (alive)
			super.draw(g);
	}

}