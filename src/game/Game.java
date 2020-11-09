package game;
import java.awt.*;
import java.awt.event.*;
import java.applet.*;
import java.util.*;

public class Game extends GameApplet {
	 Random rnd = new Random(System.currentTimeMillis());
	// boolean overlapped = false;
	Brawler brawler = new Brawler(360, 700, Brawler.UP, 23);//char img w
	Zombie[] zombies = new Zombie[3];
	Line[] l = new Line[3];
	Circle[] bullet = new Circle[40];
	
	public static final int s = 16;
	
	//60x30
	String[]map = {
			  "WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW",
			  "WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW",
		      "WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW",
		      "WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW",
		      "WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW",
		      "WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW",
		      "WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW",
		      "WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW",
		      "WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW",
		      "WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW",
		      "WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW",
		      "WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW",
		      "WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW",
		      "WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW",
		      "WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW",
		      "WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW",
		      "WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW",
			  "WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW",
		      "WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW",
		      "WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW",
		      "WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW",
		      "WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW",
		      "WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW",
		      "WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW",
		      "WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW",
		      "WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW",
		      "WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW",
		      "WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW",
		      "WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW",
		      "WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW",
		      ".............................................",
		      ".............................................",
		      ".............................................",
		      ".............................................",
		      ".............................................",
		      ".............................................",
		      ".............................................",
		      ".............................................",
		      ".............................................",
		      ".............................................",
		      ".............................................",
		      ".............................................",
		      ".............................................",
		      ".............................................",
		      ".............................................",
	};
	
	Image wall = getImage("st/st_5.png");
	Image ground = getImage("st/st_112.png");

	public void init() {
		double gravity = 0.3;
		for(int i = 0; i < bullet.length; i++) {
			bullet[i] = new Circle(-1000, -1000, 3, 0);
			//bullet[i].setAcceleration(0, Circle.GRAVITY);
			 bullet[i].setAcceleration(0, gravity);
		}

		for(int i = 0; i < zombies.length; i++) {
			// zombies[i] = new BadCircle(rnd.nextInt(480)+50, rnd.nextInt(360)+50, 30, 0);
			zombies[i] = new Zombie(rnd.nextInt(480)+50, rnd.nextInt(360)+50, Zombie.DOWN, 23, 0);
			// zombies[i].setAcceleration(0, Circle.GRAVITY);
			// zombies[i].setAcceleration(0, gravity);
		}

		double[][] v = { 
				//{ 720, 720, 20, 720 }, // bottom
				{ 720, 720, 0, 720 }, // bottom
				{ 720, 0, 720, 720 }, // right
				//{ 20, 720, 20, 0 }// left
				{ 0, 720, 0, 0 }// left
		};

		for(int i = 0; i < v.length; i++) {
			l[i] = new Line(v[i][0], v[i][1], v[i][2], v[i][3]);
		}

		super.init();
	}

	public void inGameLoop() {
		//----------------------------------Camera--------------------------------------//
	    if(pressing[LT])  Camera.moveLeft(2);
	    if(pressing[RT])  Camera.moveRight(2);
	    if(pressing[UP])  Camera.moveUp(2);;
	    if(pressing[DN])  Camera.moveDown(2);
		//----------------------------------Bullet--------------------------------------//
		for(int i = 0; i < bullet.length; i++) {
			bullet[i].move();
			// if(bullet[i].overlaps(c))
			// c.alive = false;
			for(int j = 0; j < zombies.length; j++) {
				// if(bullet[i].overlaps(zombies[j]))
				// zombies[j].alive = false;
			}
		}
		//----------------------------------Bad Circle--------------------------------------//
		for(int i = 0; i < zombies.length; i++) {
			if(zombies[i].alive) {
				zombies[i].turnToward(brawler);
				zombies[i].chase(brawler);
				//zombies[i].launch(bullet);
			}
		}

		for(int i = 0; i < zombies.length - 1; i++) {
			for(int j = i + 1; j < zombies.length; j++) {
				if(zombies[i].overlaps(zombies[j])) {
					zombies[i].pushes(zombies[j]);
					//zombies[i].bounceOff(zombies[j]);
				}
			}
		}

		for(int i = 0; i < zombies.length; i++) {
			for(int j = 0; j < l.length; j++) {
				if(zombies[i].overlaps(l[j])) {
					zombies[i].isPushedBackBy(l[j]);
					//zombies[i].bounceOff(l[j]);
				}
			}
		}
		//----------------------------------Brawler--------------------------------------//		
	    int top = (int) brawler.y;
	    int bottom = (int) brawler.y + s-1;
	    int left = (int) brawler.x;
	    int right = (int) brawler.x + s-1;
	    
		if(pressing[UP]) {
			if((map[(top-s/8)/s].charAt(left/s) == '.') && (map[(top-s/8)/s].charAt(right/s) == '.'))       
				brawler.moveUp(3);
		}
	    if(pressing[DN]) {
	    	if((map[top/s].charAt((left-s/8)/s) == '.') && (map[bottom/s].charAt((left-s/8)/s) == '.'))
	    		brawler.moveDown(3);
	    }
	    if(pressing[LT]) {
	    	if((map[top/s].charAt((left-s/8)/s) == '.') && (map[bottom/s].charAt((left-s/8)/s) == '.'))
	    		brawler.moveLeft(3);
	    		//brawler.moveLeft(-s/8);
	    }
	    if(pressing[RT]) {
	    	if((map[top/s].charAt((right+s/8)/s) == '.') && (map[bottom/s].charAt((right+s/8)/s) == '.'))
	    		brawler.moveRight(3);
	    		//brawler.moveRight(+s/8);
	    }
	    if(pressing[SPACE]) 
	    	brawler.shoot(bullet);
	    
	    for(int j = 0; j < l.length; j++) {
	    	if(brawler.overlaps(l[j]))
	    		brawler.isPushedBackBy(l[j]);
	    }
	}

	public void paint(Graphics g) {
		g.drawImage(ground, 0, 0, null);
	    for(int row = 0; row < map.length; row++) {
	        for(int col = 0; col < map[row].length(); col++) {
	          char c = map[row].charAt(col);
	          if(c == 'W') { 
	            g.drawImage(wall, s*col, s*row, s, s, null);
	          }
	          if(c == '.') { 
	            g.drawImage(ground, s*col, s*row, s, s, null);
	          } 
	        }
	      }
		
		
		brawler.draw(g);
		
		for(int i = 0; i < zombies.length; i++) {
			if(zombies[i].alive)
				zombies[i].draw(g);
		}
		for(int i = 0; i < bullet.length; i++) {
			bullet[i].draw(g);
		}
		for(int i = 0; i < l.length; i++) {
			l[i].draw(g);
		}
	}

//	public void mousePressed(MouseEvent e) {
//		mx = e.getX();
//		my = e.getY();
//
//		for(int i = 0; i < l.length; i++)
//			l[i].grabbedAt(mx, my);
//
//		for(int i = 0; i < zombies.length; i++)
//			zombies[i].grabbedAt(mx, my);
//	}
//
//	public void mouseDragged(MouseEvent e) {// can't drag if !pressed
//		int nx = e.getX();// newx
//		int ny = e.getY();// newy
//
//		int dx = nx - mx;
//		int dy = ny - my;
//
//		mx = nx;
//		my = ny;
//
//		for(int i = 0; i < l.length; i++)
//			l[i].draggedBy(dx, dy);
//		for(int i = 0; i < zombies.length; i++) {
//			if(zombies[i].held) {
//				zombies[i].moveBy(dx, dy);
//				// zombies[i].ay = 0;
//			}
//		}
//	}
//
//	public void mouseReleased(MouseEvent e) {
//		for(int i = 0; i < l.length; i++)
//			l[i].released();
//		for(int i = 0; i < zombies.length; i++) {
//			zombies[i].released();
//			zombies[i].ay = 0.7;
//		}
//	}

}
