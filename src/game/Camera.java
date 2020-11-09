package game;

public class Camera {
	static double x;
	static double y;
	
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
