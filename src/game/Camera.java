package game;

public class Camera {
	public static int x;
	public static int y;
	
	public static int x_origin = 0;
	public static int y_origin = 0;
	
	public static void moveLeft(double dx) {
		x -= dx;
	}

	public static void moveRight(double dx) {
		x += dx;
	}

	public static void moveUp(double dy) {
		y -= dy;
	}

	public static void moveDown(double dy) {
		y += dy;
	}

}
