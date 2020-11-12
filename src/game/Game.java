package game;
import java.awt.*;
import java.awt.event.*;
import java.applet.*;
import java.util.*;

public class Game extends GameApplet {
	public static final int s = 64;
	Random rnd = new Random(System.currentTimeMillis());
	Brawler brawler = new Brawler(60, 480, Brawler.RIGHT, 23);//char img w
	Zombie[] zombies = new Zombie[3];
	Line[] l = new Line[3];
	Circle[] bullet = new Circle[40];
	Image[] tile;
	//64x12
	TileMap map = new TileMap("st_map.map", s);

	public void init() {
		Camera.x = Camera.x_origin;
		Camera.x = Camera.y_origin;
		tile = new Image[128];
		for(int i = 0; i < 128; i++) {
			tile[i] = getImage("st/st_" + i + ".png");//not i+1 bc imgs start @ 0
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
		    //if(
		    	//(map.clearAbove(zombies[i])) &&
		    	//(map.clearBelow(zombies[i])) &&
		    	//(map.clearLeftOf(zombies[i])) && 
		    	//(map.clearRightOf(zombies[i]))
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
		if(pressing[UP]) {
			if(map.clearAbove(brawler))       
				brawler.moveUp(3);
		}
	    if(pressing[DN]) {
	    	if(map.clearBelow(brawler))
	    		brawler.moveDown(3);
	    }
	    if(pressing[LT]) {
	    	if(map.clearLeftOf(brawler))
	    		brawler.moveLeft(3);
	    		//brawler.moveLeft(-s/8);
	    }
	    if(pressing[RT]) {
	    	if(map.clearRightOf(brawler))
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
		map.draw(g);		
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

}
