package game;
import java.awt.*;
import java.awt.event.*;
import java.applet.*;
import java.util.*;

public class Game extends GameApplet {
	 Random rnd = new Random(System.currentTimeMillis());
	// boolean overlapped = false;
	Brawler brawler = new Brawler(60, 480, Brawler.RIGHT, 23);//char img w
	Zombie[] zombies = new Zombie[3];
	Line[] l = new Line[3];
	Circle[] bullet = new Circle[40];
	
	public static final int s = 60;
	
	//80x20
	String[]map = {
			  "#################################################################",
			  "#################################################################",
			  "###############fghij##########fghij##############################",
			  "#ABBC########a##############a####################################",
			  "#DEEF########b#d#klm########b#q#ABC##############################",
			  "#GHHI########c#e#nop########c#r#GHI##############################",
			  ".................................................................",
			  ".................................................................",
			  ".................................................................",
		      "........JK.............JK.............JK.........................",
		      "........LM.............LM.............LM.........................",
		      "........NO.............NO.............NO.........................",
	};

	Image[] tiles;

	public void init() {
		Camera.x = Camera.x_origin;
		Camera.x = Camera.y_origin;
		tiles = new Image[128];
		for(int i = 0; i < 128; i++) {
			tiles[i] = getImage("st/st_" + i + ".png");//not i+1 bc imgs start @ 0
		}
		
		for(int i = 0; i < bullet.length; i++) {
			bullet[i] = new Circle(-1000, -1000, 3, 0);
			bullet[i].setAcceleration(0, Circle.GRAVITY);
			//bullet[i].setAcceleration(0, gravity);
		}

		for(int i = 0; i < zombies.length; i++) {
			// zombies[i] = new BadCircle(rnd.nextInt(480)+50, rnd.nextInt(360)+50, 30, 0);
			zombies[i] = new Zombie(rnd.nextInt(480), rnd.nextInt(480)+360, Zombie.DOWN, 23, 0);
			// zombies[i].setAcceleration(0, Circle.GRAVITY);
			// zombies[i].setAcceleration(0, gravity);
		}

		double[][] v = { 
				{ 720, 720, 0, 720 }, // bottom
				{ 720, 0, 720, 720 }, // right
				{ 0, 720, 0, 0 }// left
		};

		for(int i = 0; i < v.length; i++) {
			l[i] = new Line(v[i][0], v[i][1], v[i][2], v[i][3]);
		}

		super.init();
	}

	public void inGameLoop() {
		//----------------------------------Camera--------------------------------------//
	    if(pressing[RT])  Camera.moveRight(3);
	    //if(pressing[LT])  Camera.moveLeft(3);
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
		//----------------------------------Zombie--------------------------------------//
		for(int i = 0; i < zombies.length; i++) {
		    int top = (int) zombies[i].y;
		    int bottom = (int) zombies[i].y + s-1;
		    int left = (int) zombies[i].x;
		    int right = (int) zombies[i].x + s-1;
		    //if(
		    	//(map[(top-s/8)/s].charAt(left/s) == '.') && (map[(top-s/8)/s].charAt(right/s) == '.') &&
		    	//(map[top/s].charAt((left-s/8)/s) == '.') && (map[bottom/s].charAt((left-s/8)/s) == '.')	&&
		    	//(map[top/s].charAt((left-s/8)/s) == '.') && (map[bottom/s].charAt((left-s/8)/s) == '.') && 
		    	//(map[top/s].charAt((right+s/8)/s) == '.') && (map[bottom/s].charAt((right+s/8)/s) == '.')
		    //){
		    	if(zombies[i].alive) {
					zombies[i].turnToward(brawler);
					zombies[i].chase(brawler);
					//zombies[i].launch(bullet);
		    	}
			//}
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
		//g.drawImage(ground, 0, 0, null);
	    for(int row = 0; row < map.length; row++) {
	        for(int col = 0; col < map[row].length(); col++) {
	          char c = map[row].charAt(col);
	          if(c != '.') {
	        	  if(c == '#')
	        		  g.drawImage(tiles[5], s*col - Camera.x + Camera.x_origin, s*row, s, s, null);
	        	  if(c == 'A')
	        		  g.drawImage(tiles[18], s*col - Camera.x + Camera.x_origin, s*row, s, s, null);
	        	  if(c == 'B')
	        		  g.drawImage(tiles[19], s*col - Camera.x + Camera.x_origin, s*row, s, s, null);
	        	  if(c == 'C')
	        		  g.drawImage(tiles[20], s*col - Camera.x + Camera.x_origin, s*row, s, s, null);
	        	  if(c == 'D')
	        		  g.drawImage(tiles[36], s*col - Camera.x + Camera.x_origin, s*row, s, s, null);
	        	  if(c == 'E')
	        		  g.drawImage(tiles[37], s*col - Camera.x + Camera.x_origin, s*row, s, s, null);
	        	  if(c == 'F')
	        		  g.drawImage(tiles[38], s*col - Camera.x + Camera.x_origin, s*row, s, s, null);
	        	  if(c == 'G')
	        		  g.drawImage(tiles[63], s*col - Camera.x + Camera.x_origin, s*row, s, s, null);
	        	  if(c == 'H')
	        		  g.drawImage(tiles[64], s*col - Camera.x + Camera.x_origin, s*row, s, s, null);
	        	  if(c == 'I')
	        		  g.drawImage(tiles[65], s*col - Camera.x + Camera.x_origin, s*row, s, s, null);
	        	  
	        	  if(c == 'a')
	        		  g.drawImage(tiles[48], s*col - Camera.x + Camera.x_origin, s*row, s, s, null);
	        	  if(c == 'b')
	        		  g.drawImage(tiles[74], s*col - Camera.x + Camera.x_origin, s*row, s, s, null);
	        	  if(c == 'c')
	        		  g.drawImage(tiles[93], s*col - Camera.x + Camera.x_origin, s*row, s, s, null);
	        	  if(c == 'd')
	        		  g.drawImage(tiles[9], s*col - Camera.x + Camera.x_origin, s*row, s, s, null);
	        	  if(c == 'e')
	        		  g.drawImage(tiles[94], s*col - Camera.x + Camera.x_origin, s*row, s, s, null);
	        	  if(c == 'f')
	        		  g.drawImage(tiles[49], s*col - Camera.x + Camera.x_origin, s*row, s, s, null);
	        	  if(c == 'g')
	        		  g.drawImage(tiles[50], s*col - Camera.x + Camera.x_origin, s*row, s, s, null);
	        	  if(c == 'h')
	        		  g.drawImage(tiles[51], s*col - Camera.x + Camera.x_origin, s*row, s, s, null);
	        	  if(c == 'i')
	        		  g.drawImage(tiles[52], s*col - Camera.x + Camera.x_origin, s*row, s, s, null);
	        	  if(c == 'j')
	        		  g.drawImage(tiles[53], s*col - Camera.x + Camera.x_origin, s*row, s, s, null);
	        	  if(c == 'k')
	        		  g.drawImage(tiles[76], s*col - Camera.x + Camera.x_origin, s*row, s, s, null);	        	  
	        	  if(c == 'l')
	        		  g.drawImage(tiles[77], s*col - Camera.x + Camera.x_origin, s*row, s, s, null);
	        	  if(c == 'm')
	        		  g.drawImage(tiles[78], s*col - Camera.x + Camera.x_origin, s*row, s, s, null);
	        	  if(c == 'n')
	        		  g.drawImage(tiles[95], s*col - Camera.x + Camera.x_origin, s*row, s, s, null);	        	  
	        	  if(c == 'o')
	        		  g.drawImage(tiles[96], s*col - Camera.x + Camera.x_origin, s*row, s, s, null);
	        	  if(c == 'p')
	        		  g.drawImage(tiles[97], s*col - Camera.x + Camera.x_origin, s*row, s, s, null);
	        	  if(c == 'q')
	        		  g.drawImage(tiles[83], s*col - Camera.x + Camera.x_origin, s*row, s, s, null);
	        	  if(c == 'r')
	        		  g.drawImage(tiles[101], s*col - Camera.x + Camera.x_origin, s*row, s, s, null);
	        	  
	        	  if(c == 'J')
	        		  g.drawImage(tiles[33], s*col - Camera.x + Camera.x_origin, s*row, s, s, null);
	        	  if(c == 'K')
	        		  g.drawImage(tiles[99], s*col - Camera.x + Camera.x_origin, s*row, s, s, null);
	        	  if(c == 'L')
	        		  g.drawImage(tiles[57], s*col - Camera.x + Camera.x_origin, s*row, s, s, null);
	        	  if(c == 'M')
	        		  g.drawImage(tiles[58], s*col - Camera.x + Camera.x_origin, s*row, s, s, null);
	        	  if(c == 'N')
	        		  g.drawImage(tiles[92], s*col - Camera.x + Camera.x_origin, s*row, s, s, null);
	        	  if(c == 'O')
	        		  g.drawImage(tiles[56], s*col - Camera.x + Camera.x_origin, s*row, s, s, null);
	          }
	          else {
	        	  g.drawImage(tiles[112], s*col - Camera.x + Camera.x_origin, s*row, s, s, null);
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

/*	
	public void mousePressed(MouseEvent e) {
		mx = e.getX();
		my = e.getY();

		for(int i = 0; i < l.length; i++)
			l[i].grabbedAt(mx, my);

		for(int i = 0; i < zombies.length; i++)
			zombies[i].grabbedAt(mx, my);
	}

	public void mouseDragged(MouseEvent e) {// can't drag if !pressed
		int nx = e.getX();// newx
		int ny = e.getY();// newy

		int dx = nx - mx;
		int dy = ny - my;

		mx = nx;
		my = ny;

		for(int i = 0; i < l.length; i++)
			l[i].draggedBy(dx, dy);
		for(int i = 0; i < zombies.length; i++) {
			if(zombies[i].held) {
				zombies[i].moveBy(dx, dy);
				// zombies[i].ay = 0;
			}
		}
	}

	public void mouseReleased(MouseEvent e) {
		for(int i = 0; i < l.length; i++)
			l[i].released();
		for(int i = 0; i < zombies.length; i++) {
			zombies[i].released();
			zombies[i].ay = 0.7;
		}
	}
*/
}
