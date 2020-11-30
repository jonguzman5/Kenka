package game;
import java.awt.*;
import java.awt.event.*;
import java.applet.*;
import java.util.*;

public class Game extends GameApplet {
	public static final int s = 64;
	Random rnd = new Random(System.currentTimeMillis());
	Brawler brawler = new Brawler(0, 0, Brawler.RIGHT, 23);//char img w
	Zombie[] zombieSet1 = new Zombie[3];
	Zombie[] zombieSet2 = new Zombie[3];
	Zombie[] zombieSet3 = new Zombie[3];
	Line[] l = new Line[3];
	Circle[] bullet = new Circle[40];
	//64x12
	TileMap map = new TileMap("st", "st_map.map", s);

	public void init() {
		Camera.x = Camera.x_origin;
		Camera.y = Camera.y_origin;
		
		for(int i = 0; i < bullet.length; i++) {
			bullet[i] = new Circle(-1000, -1000, 3, 0);
			bullet[i].setAcceleration(0, Circle.GRAVITY);
			//bullet[i].setAcceleration(0, gravity);
		}

		setZombiePosition(zombieSet1, 60);
		setZombiePosition(zombieSet2, 200);
		setZombiePosition(zombieSet3, 1000);

		double[][] v = { 
				//{ 720, 720, 0, 720 },//bottom
				{ 4096, 720, 0, 720 },//bottom
				//{ 720, 0, 720, 720 },//right
				{ 4096, 0, 4096, 720 },//right
				{ 0, 720, 0, 0 },//left
		};

		for(int i = 0; i < v.length; i++) {
			l[i] = new Line(v[i][0], v[i][1], v[i][2], v[i][3]);
		}

		super.init();
	}
	
	public void setZombiePosition(Zombie[] zombies, int x) {
		for(int i = 0; i < zombies.length; i++) {
			zombies[i] = new Zombie(x, rnd.nextInt(480)+360, Zombie.DOWN, 23, 0);
		}
	}
	
	public void setBulletBehavior(Zombie[] zombies, Circle bullet) {
		for(int j = 0; j < zombies.length; j++) {
			 if(bullet.overlaps(zombies[j]))
				 zombies[j].alive = false;
		}
	}
	public void setZombieBehavior(Zombie[] zombies) {
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
				}
			}
		}

		for(int i = 0; i < zombies.length; i++) {
			for(int j = 0; j < l.length; j++) {
				if(zombies[i].overlaps(l[j])) {
					zombies[i].isPushedBackBy(l[j]);
				}
			}
		}
	}

	public void inGameLoop() {
		//----------------------------------Bullet--------------------------------------//
		for(int i = 0; i < bullet.length; i++) {
			bullet[i].move();
			setBulletBehavior(zombieSet1, bullet[i]);
			setBulletBehavior(zombieSet2, bullet[i]);
			setBulletBehavior(zombieSet3, bullet[i]);
		}
		//----------------------------------Zombie--------------------------------------//
		setZombieBehavior(zombieSet1);
		setZombieBehavior(zombieSet2);
		setZombieBehavior(zombieSet3);
		//----------------------------------Brawler--------------------------------------//		
		if(pressing[UP]) {
			if(map.clearAbove(brawler)) {     
				brawler.moveUp(s/12);
				Camera.moveUp(s/12);
			}
		}
	    if(pressing[DN]) {
	    	if(map.clearBelow(brawler)) {
	    		brawler.moveDown(s/12);
	    		Camera.moveDown(s/12);
	    	}
	    }
	    if(pressing[LT]) {
	    	if(map.clearLeftOf(brawler)) {
	    		brawler.moveLeft(s/12);
	    		Camera.moveLeft(s/12);
	    	}
	    }
	    if(pressing[RT]) {
	    	if(map.clearRightOf(brawler)) {
	    		brawler.moveRight(s/12);
	    		Camera.moveRight(s/12);
	    	}
	    }
	    if(pressing[SPACE]) 
	    	brawler.shoot(bullet);
	    
	    for(int j = 0; j < l.length; j++) {
	    	if(brawler.overlaps(l[j])) {
	    		brawler.isPushedBackBy(l[j]);
	    		Camera.isPushedBackBy(l[j]);
	    	}
	    }
	}
	
	public void renderZombies(Zombie[] zombies, Graphics g) {		
		for(int i = 0; i < zombies.length; i++) {
			if(zombies[i].alive)
				zombies[i].draw(g);
		}
	}

	public void paint(Graphics g) {		
		map.draw(g);		
		brawler.draw(g);
		//Camera.draw(g);
		if(brawler.x >= 60) 
			renderZombies(zombieSet1, g);
		if(brawler.x >= 200) 
			renderZombies(zombieSet2, g);
		if(brawler.x >= 1000) 
			renderZombies(zombieSet3, g);
		
		for(int i = 0; i < bullet.length; i++) {
			bullet[i].draw(g);
		}
		//for(int i = 0; i < l.length; i++) {
			//l[i].draw(g);
		//}
	}

}
