package game;
import java.awt.Graphics;

public class Line {
	double x1;
	double y1;
	
	double x2;
	double y2;
	
	double nx;
	double ny;
	
	double c;

	public Line(double x1, double y1, double x2, double y2) {
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
		computeNormal();
		c = x1 * nx + y1 * ny;
	}
	public void computeNormal() {
		double vx = x2 - x1;
		double vy = y2 - y1;
		
		double mag = Math.sqrt((vx * vx) + (vy * vy));
		
		double ux = vx / mag;
		double uy = vy / mag;
		
		nx = -uy;
		ny = ux;
	}
	
	public double distanceTo(double x, double y) {
		return nx * (x - x1) + ny * (y - y1);//if moving (translating: no recalculations, else)
		//return x * nx + y * ny - c;//if still
	}
	
	public void draw(Graphics g) {
		g.drawLine((int)x1, (int)y1, (int)x2, (int)y2);
	}
	
	int held_at = 0;//0:na,1:x1y1,2:x2y2
	
	public boolean isHeld() {
		return held_at != 0;
	}
	
	public void grabbedAt(int mx, int my) {
		double dx;
		double dy;
		dx = x1 - mx;
		dy = y1 - my;
		if(dx*dx + dy*dy < 49)//< r: 7^2
			held_at = 1;
		dx = x2 - mx;
		dy = y2 - my;	
		if(dx*dx + dy*dy < 49)
			held_at = 2;
	}
	
	public void draggedBy(int dx, int dy) {
		if(held_at == 1) {
			x1 += dx;
			y1 += dy;
		}
		if(held_at == 2) {
			x2 += dx;
			y2 += dy;
		}
		computeNormal();
	}
	
	public void released() {
		held_at = 0;
	}
}

