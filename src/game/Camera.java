package game;
import java.awt.Color;
import java.awt.Graphics;


public class Camera {
	public static int x;
	public static int y;
	public static int r;
	
	public static int vx;
	public static int vy;
	public static int ay;
	
	public static int x_origin = 60;
	public static int y_origin = 480;
	
	public static void moveLeft(int dx) {
		x -= dx;
		//vx = -dx;
	}

	public static void moveRight(int dx) {
		x += dx;
		//vx = dx;
	}

	public static void moveUp(int dy) {
		y -= dy;
		//vy = -dy;
	}

	public static void moveDown(int dy) {
		y += dy;
		//vy = dy;
	}

	public static void isPushedBackBy(Line l) {
		int d = (int) l.distanceTo(x, y);
		int p = r - d;
		x += p * l.nx;
		y += p * l.ny;
	}
	
	public static void draw(Graphics g) {
		g.setColor(Color.GREEN);
		g.drawRect(x_origin, y_origin, 32, 32);
	}
}
