package game;
import java.awt.*;
import java.awt.event.*;
import java.applet.*;
import java.util.*;

public class Game extends GameApplet {
	public static final int s = 64;
	
	Line[] l = new Line[3];
	TileMap map = new TileMap("st", "st_map.map", s);//64x12
	Random rnd = new Random(System.currentTimeMillis());
	
	int setLen = 3;
	int setAm = 3;
	HealthBar bhb = new HealthBar(0, 0, 100, 25);
	HealthBar[] zhb = new HealthBar[setLen*setAm];
	HealthBar bzhb = new HealthBar(520, 243, 200, 25);
	
	Brawler brawler = new Brawler(0, 0, Brawler.RIGHT, 23, 0);//23 = char img w
	Zombie[] zombieSet1 = new Zombie[setLen];
	Zombie[] zombieSet2 = new Zombie[setLen];
	Zombie[] zombieSet3 = new Zombie[setLen];
	Zombie bossZombie = new Zombie(3300, 495, Zombie.LEFT, 46, 0);
		
	Circle[] bullet = new Circle[40];
	Circle[] zbullet = new Circle[2];
	Circle[] bzbullet = new Circle[10];

	public void init() {
		Camera.x = Camera.x_origin;
		Camera.y = Camera.y_origin;
		
		setBulletPosition(bullet);
		setBulletPosition(zbullet);
		setBulletPosition(bzbullet);

		setZombiePosition(zombieSet1, getBrawlerX());
		setZombiePosition(zombieSet2, getBrawlerX());
		setZombiePosition(zombieSet3, getBrawlerX());
		//setZombiePosition(bossZombie, getBrawlerX());

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
	
	public void setBulletPosition(Circle[] bullet) {
		for(int i = 0; i < bullet.length; i++) {
			bullet[i] = new Circle(-1000, -1000, 3, 0);
			bullet[i].setAcceleration(0, Circle.GRAVITY);
		}
	}
	
	public void setZombiePosition(Zombie[] zombies, int x) {
		for(int i = 0; i < zombies.length; i++) {
			zombies[i] = new Zombie(x, rnd.nextInt(480)+360, Zombie.DOWN, 23, 0);
		}
	}
	
	//public void setZombiePosition(Zombie bossZombie, int x) {
		//bossZombie = new Zombie(x + 760, 495, Zombie.LEFT, 46, 0); 
	//}
	
	//----------------------------------Brawler=>Zombie--------------------------------------//
	
	public void setBulletBehavior(Zombie[] zombies, Circle bullet) {
		for(int j = 0; j < zombies.length; j++) {
			if(zombies[j].alive) {
				 if(bullet.overlaps(zombies[j])) {
					 zhb[j].greenBar.w -= 10;//hits every zombie
					 zgbw[j] = zhb[j].greenBar.w;
					 if(zhb[j].greenBar.w == 0) {
						 zombies[j].alive = false;
					 }		
				 }
			}
		}
	}
	
	public void setBulletBehavior(Zombie bossZombie, Circle bullet) {
		if(bossZombie.alive) {
			 if(bullet.overlaps(bossZombie)) {
				 if(bzhb.greenBar.w == 0) {
					 bossZombie.alive = false;
				 }
				 bzhb.greenBar.w -= 2;
			 }
		}
	}
	
	//----------------------------------Zombie=>Brawler--------------------------------------//
	
	public void setBulletBehavior(Brawler brawler, Circle bullet) {
		 if(bullet.overlaps(brawler)) {
			 if(bhb.greenBar.w == 0) {
				 brawler.alive = false;
			 }
			 bhb.greenBar.w -= 1;
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
					//zombies[i].turnToward(brawler);
					//zombies[i].chase(brawler);
					//zombies[i].launch(zbullet);
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
	
	public void setZombieBehavior(Zombie bossZombie) {
    	if(bossZombie.alive) {
    		bossZombie.turnToward(brawler);
    		bossZombie.chase(brawler);
    		//bossZombie.launch(bzbullet);
    	}
    	for(int j = 0; j < l.length; j++) {
			if(bossZombie.overlaps(l[j])) {
				bossZombie.isPushedBackBy(l[j]);
			}
    	}
	}

	public void inGameLoop() {
		//----------------------------------Bullet--------------------------------------//
		//-----------------Brawler=>Zombie-----------------//
		for(int i = 0; i < bullet.length; i++) {
			bullet[i].move();
			setBulletBehavior(zombieSet1, bullet[i]);
			setBulletBehavior(zombieSet2, bullet[i]);
			setBulletBehavior(zombieSet3, bullet[i]);
			setBulletBehavior(bossZombie, bullet[i]);
		}
		
		//-----------------Zombie=>Brawler-----------------//
		for(int i = 0; i < zbullet.length; i++) {
			zbullet[i].move();
			setBulletBehavior(brawler, zbullet[i]);
		}
		
		for(int i = 0; i < bzbullet.length; i++) {
			bzbullet[i].move();
			setBulletBehavior(brawler, bzbullet[i]);
		}
		//----------------------------------Zombie--------------------------------------//
		setZombieBehavior(zombieSet1);
		setZombieBehavior(zombieSet2);
		setZombieBehavior(zombieSet3);
		setZombieBehavior(bossZombie);
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
	
	public int getBrawlerX() {
		return brawler.x;
	}
	
	static int[] zgbw = {100, 100, 100};
	public void renderZombies(int x, int hby, Zombie[] zombies, Graphics g) {
		int brawlerX = getBrawlerX();
		if(brawlerX >= x) {
			for(int i = 0; i < zombies.length; i++) {
				if(zombies[i].alive) {
					zombies[i].draw(g);
					zhb[i] = new HealthBar(620, hby, 100, 25);
					zhb[i].greenBar.w = zgbw[i];
					zhb[i].draw(g);
					hby += 27;
				}
			}
		}
	}
	
	public void renderZombies(int x, Zombie bossZombie, Graphics g) {
		int brawlerX = getBrawlerX();
		if(brawlerX >= x) {
			if(bossZombie.alive) {
				bossZombie.drawBossZombie(g);
				bzhb.draw(g);
			}
		}
	}

	public void paint(Graphics g) {
		//System.out.println("x: " + getBrawlerX() + ", y: " + brawler.y);
		map.draw(g);		
		
		if(brawler.alive) {
			brawler.draw(g);		
			bhb.draw(g);
		}
		//Camera.draw(g);
		
		renderZombies(0, 0, zombieSet1, g);
		renderZombies(970, 81, zombieSet2, g);
		renderZombies(1940, 162, zombieSet3, g);
		renderZombies(2540, bossZombie, g);
			
		for(int i = 0; i < bullet.length; i++) {
			bullet[i].draw(g);
		}
		for(int i = 0; i < zbullet.length; i++) {
			zbullet[i].draw(g);
		}
		for(int i = 0; i < bzbullet.length; i++) {
			bzbullet[i].draw(g);
		}
		//for(int i = 0; i < l.length; i++) {
			//l[i].draw(g);
		//}
	}

}