package game;
import java.awt.Graphics;

public class Circle {
	double x;
	double y;
	double vx = 0;//right throw d
	double vy = 0;//throw up
	double ax = 0;
	double ay = 0;//gravity pull
	double r;
	
	int a;
	double cosA;
	double sinA;
	
	double launch_delay = 0;
	double launch_countdown = 30; 
	
	static int bulletNum = 0;
	static final double GRAVITY = 0;
	
	boolean held = false;
	boolean alive = true;
	
	public Circle(double x, double y, double r, int a) {
		this.x = x;
		this.y = y;
		this.r = r;
		this.a = a;
		cosA = Lookup.cos[a];
		sinA = Lookup.sin[a];
	}

	public void launch(Circle[] bullet) {
		if(launch_delay == 0) {
			//vel
			double speed = 10;					
			bullet[bulletNum].vx = speed * cosA;
			bullet[bulletNum].vy = speed * sinA;
			//starting pt
			bullet[bulletNum].x = x + (r+6) * cosA;
			bullet[bulletNum].y = y + (r+6) * sinA; 	

			launch_delay = launch_countdown;
			
			bulletNum++;
			if(bulletNum == bullet.length)
				bulletNum = 0;
		}
		launch_delay--;
	}
	
	public boolean toLeftOf(Circle c) {
		return sinA * (c.x - x) - cosA * (c.y - y) > 0;
	}
	
	public boolean toLeftOf(Brawler b) {
		return sinA * (b.x - x) - cosA * (b.y - y) > 0;
	}
	
	public boolean inFrontOf(Circle c) {
		return cosA * (c.x - x) + sinA * (c.y - y) > 0;
	}
	
	public boolean within(double distance, Circle c) {
		double dx = x - c.x;
		double dy = y - c.y;
		return dx * dx + dy * dy < distance * distance;//"distance" px
		
	}
	
	public void draw(Graphics g) {
		g.drawOval((int)(x-r), (int)(y-r), (int)(2.0*r), (int)(2.0*r));
		g.drawLine((int)(x), (int)(y), (int)(x + r*cosA), (int)(y + r*sinA));
	}
	
	public void move() {
		//accel
		vx += ax;
		vy += ay;//inc vel
		//move (pos)
		x += vx;
		y += vy;//change pos based on inc vel
	}
	public void setVelocity(double vx, double vy) {
		this.vx = vx;
		this.vy = vy;
	}
	public void setAcceleration(double ax, double ay) {
		this.ax = ax;
		this.ay = GRAVITY;
		//this.ay = ay;
	}
	public void moveBy(double dx, double dy) {
		x += dx;
		y += dy;
	}
	public void jump(double d) {
		setVelocity(0, -d);
	}
	public void toss(double vx, double vy) {
		setVelocity(vx, vy);
	}	
	public void goForward(double d) {
		double dx = d * cosA;
		double dy = d * sinA;
		moveBy(dx, dy);
	}	
	public void goBackward(double d) {
		double dx = -d * cosA;
		double dy = -d * sinA;
		moveBy(dx, dy);
	}
	
	public void setAngle(int angle) {
		a = angle;		
		if(a < 0) a += 360;
		if(a > 359) a -= 360;
		cosA = Lookup.cos[a];
		sinA = Lookup.sin[a]; 
	}
	
	public void turnLeft(int da) {
		a -= da;
		if(a < 0) a += 360;
		cosA = Lookup.cos[a];
		sinA = Lookup.sin[a];
	}
	public void turnRight(int da) {
		a += da;
		if(a > 359) a -= 360;
		cosA = Lookup.cos[a];
		sinA = Lookup.sin[a];
	}
	
	public boolean overlaps(Line l) {
		double d = l.distanceTo(x, y);
		return d*d < r*r;//d < r = is the circle on side of line or other || overlap
	}
	public void isPushedBackBy(Line l) {
		double d = l.distanceTo(x, y);
		double p = r - d;
		x += p * l.nx;
		y += p * l.ny;
	}
	
	public boolean overlaps(Circle c) {
		//double d = Math.sqrt((x - c.x) * (x - c.x) + (y - c.y) * (y - c.y));
		double d2 = (x - c.x) * (x - c.x) + (y - c.y) * (y - c.y);
		return d2 < (r + c.r) * (r + c.r);
		//return d < r + c.r;
	}
	
	public void pushes(Circle c) {
		double dx = x - c.x;
		double dy = y - c.y;
		double d = Math.sqrt(dx*dx + dy*dy);
		
		double ux = dx / d;
		double uy = dy / d;
		
		double ri = r + c.r;
		double p = ri - d;
		
		x += ux * p/2;
		y += uy * p/2;
		
		c.x -= ux * p/2;
		c.y -= uy * p/2;						
	}
	
	public void bounceOff(Circle c) {
		double dx = c.x - x;
		double dy = c.y - y;		
		double mag = Math.sqrt(dx*dx + dy*dy);
		
		double ux = dx / mag;
		//dx /= mag;
		double uy = dy / mag;
		//dy /= mag;
		double tx = -uy;
		double ty = -ux;		
		
		//distances
		double u = vx * ux + vy * uy;//vel in x * unit vec + vel in y + unit vec
		double t = vx * tx + vy* ty;
		
		/**** Other Circle ****/
		double cu = c.vx * ux + c.vy * uy;
		double ct = c.vx * tx + c.vy* ty;
		//this
		vx = 0.9 * (t * tx + cu * ux);//cu * ux = impact info from param
		vy = 0.9 * (t * ty + cu * uy);//90% of orig str ea bounce
		//OC
		c.vx = 0.9 * (ct * tx + u * ux);//impact info from this
		c.vy = 0.9 * (ct * ty + u * uy);
	}
	
	public void bounceOff(Line l) {
		double d = l.distanceTo(x, y);
		double p = r - d;
		
		x += 1.9 * (p * l.nx);
		y +=  1.9 * (p * l.ny);
		
		double mag = 1.9 * (vx * l.nx + l.ny * vy);
		
		double tx = mag * l.nx;
		double ty = mag * l.ny;
		
		vx -= tx;
		vy -= ty;
	}
	
	public void grabbedAt(int x, int y) {
		double dx = this.x - x;
		double dy = this.y - y;
		double d2 = dx * dx + dy * dy;
		double r2 = r * r;
		held = d2 < r2;
	}
	
	public void released() {
		held = false;
	}	
}
