package game;

import java.awt.Graphics;

public class Sprite {
	int x;
	int y;
	int vx = 0;
	int vy = 0;	
	int ay = 0; 
	
	int action;

	boolean moving = false;

	public final static int UP = 0;
	public final static int DOWN = 1;
	public final static int LEFT = 2;
	public final static int RIGHT = 3;
	public final static int JUMP = 4;

	Circle gun;

	Animation[] animation;
	
	public Sprite(int x, int y, int action, String[] name, int duration, int count, String extension) {
		this.x = x;
		this.y = y;
		this.action = action;
		gun = new Circle(x, y, 1, 0);

		animation = new Animation[name.length];

		for (int i = 0; i < name.length; i++)
			animation[i] = new Animation(name[i], duration, count, extension);
	}

	public void shoot(Circle[] bullet) {
		if(action == UP) 
			gun.setAngle(270);
		if(action == DOWN) 
			gun.setAngle(90);
		if(action == LEFT) 
			gun.setAngle(180);
		if(action == RIGHT) 
			gun.setAngle(0);
		gun.x = x;
		gun.y = y+20;
		//gun.x = x - Camera.x + Camera.x_origin;
		//gun.y = y - Camera.y + Camera.y_origin + 20;
		
		gun.launch(bullet);
	}
	
	public void moveLeft(int dx) {
		x -= dx;
		action = LEFT;
		moving = true;
	}

	public void moveRight(int dx) {
		x += dx;
		action = RIGHT;
		moving = true;
	}

	public void moveUp(int dy) {
		y -= dy;
		action = UP;
		moving = true;
	}

	public void moveDown(int dy) {
		y += dy;
		action = DOWN;
		moving = true;
	}

	public void draw(Graphics g) {
		if (moving)
			g.drawImage(animation[action].getCurrentImage(), x - Camera.x + Camera.x_origin, y - Camera.y + Camera.y_origin, 30, 60, null);		
		else
			g.drawImage(animation[action].getStillImage(), x - Camera.x + Camera.x_origin, y - Camera.y + Camera.y_origin, 30, 60, null);
		moving = false;
	}

}