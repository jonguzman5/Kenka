package game;

import java.awt.*;
import java.awt.event.*;
import java.applet.*;
import java.util.*;

public class Game extends GameApplet {
	// ----------------------------------Utilities--------------------------------------//
	Random rnd = new Random(System.currentTimeMillis());
	public static final int s = 64;
	private static int lvl = 0;
	Line[] l = new Line[3];
	// ----------------------------------Map--------------------------------------//
	TileMap lvl1 = new TileMap("st", "st.map", s);// 64x12
	TileMap lvl2 = new TileMap("ssb", "ssb.map", s);// 64x12
	TileMap lvl3 = new TileMap("mb", "mb.map", s);// 64x12
	TileMap[] lvls = { lvl1, lvl2, lvl3 };
	TileMap map = lvls[lvl];
	// ----------------------------------Brawler--------------------------------------//
	Brawler brawler = new Brawler(100, 0, 0, Brawler.RIGHT);
	HealthBar bhb = new HealthBar(brawler, 0, 0, 100, 25);
	Circle[] bullet = new Circle[40];
	// ----------------------------------Enemies--------------------------------------//
	Enemies enemies = new Enemies();
	Circle[] ebullet = new Circle[2];
	Circle[] bossbullet = new Circle[2];
	
	Enemy[][] enemySets = enemies.enemySets[lvl];
	HealthBar[][] enemyHealthBars = enemies.enemyHealthBars[lvl];
	Enemy[] bosses = enemies.bosses;
	HealthBar[] bossHealthBars = enemies.bossHealthBars;

	// ----------------------------------Configuration--------------------------------------//
	public void changeLevel() {
		if (lvl < lvls.length-1) {
			lvl++;
			map = lvls[lvl];
			brawler.x = 0;
			enemySets = enemies.enemySets[lvl];
			setEnemyHealthBarPosition(0, enemySets[0], enemyHealthBars[0]);
			setEnemyHealthBarPosition(81, enemySets[1], enemyHealthBars[1]);
			setEnemyHealthBarPosition(162, enemySets[2], enemyHealthBars[2]);
			setEnemyHealthBarPosition(243, bosses[lvl], bossHealthBars[lvl]);
		}
	}

	public void init() {
		Camera.x = Camera.x_origin;
		Camera.y = Camera.y_origin;

		setBulletPosition(bullet);
		setBulletPosition(ebullet);
		setBulletPosition(bossbullet);

		setEnemyHealthBarPosition(0, enemySets[0], enemyHealthBars[0]);
		setEnemyHealthBarPosition(81, enemySets[1], enemyHealthBars[1]);
		setEnemyHealthBarPosition(162, enemySets[2], enemyHealthBars[2]);
		setEnemyHealthBarPosition(243, bosses[lvl], bossHealthBars[lvl]);

		double[][] v = { 
				{ 4096, 720, 0, 720 },//bottom
				{ 4096, 0, 4096, 720 },//right
				{ 0, 720, 0, 0 },//left
		};

		for (int i = 0; i < v.length; i++) {
			l[i] = new Line(v[i][0], v[i][1], v[i][2], v[i][3]);
		}

		super.init();
	}

	public void setBulletPosition(Circle[] bullet) {
		for (int i = 0; i < bullet.length; i++) {
			bullet[i] = new Circle(-1000, -1000, 3, 0);
			bullet[i].setAcceleration(0, Circle.GRAVITY);
		}
	}

	public void setEnemyHealthBarPosition(int hby, Enemy[] enemies, HealthBar[] healthbar) {
		for (int i = 0; i < enemies.length; i++) {
			healthbar[i] = new HealthBar(enemies[i], 620, hby, 100, 25);
			hby += 27;
		}
	}
	
	public void setEnemyHealthBarPosition(int hby, Enemy boss, HealthBar healthbar) {
		healthbar = new HealthBar(boss, 620, hby, 100, 25);
		hby += 27;
	}

	// ----------------------------------Brawler=>Enemy--------------------------------------//

	public void setBulletBehavior(Enemy[] enemies, Circle bullet) {
		for (int j = 0; j < enemies.length; j++) {
			if (enemies[j].alive && bullet.overlaps(enemies[j])) {
				enemies[j].takesHitFor(20);
			}	
		}
	}

	public void setBulletBehavior(Enemy boss, Circle bullet) {
		if (boss.alive && bullet.overlaps(boss))
			boss.takesHitFor(10);
	}

	// ----------------------------------Zombie=>Brawler--------------------------------------//

	public void setBulletBehavior(Brawler brawler, Circle bullet) {
		if (bullet.overlaps(brawler))
			brawler.takesHitFor(1);
	}

	public void setEnemyBehavior(Enemy[] enemies) {
		for (int i = 0; i < enemies.length; i++) {
			// if(
			// (map.clearAbove(enemies[i])) &&
			// (map.clearBelow(enemies[i])) &&
			// (map.clearLeftOf(enemies[i])) &&
			// (map.clearRightOf(enemies[i]))
			// ){
			if (enemies[i].alive) {
				if (!enemies[i].within(90, brawler)) {
					 enemies[i].turnToward(brawler);
					 enemies[i].chase(brawler);
					 enemies[i].punching = true;
					 enemies[i].launch(ebullet);
				}
			}
			// }
		}

		for (int i = 0; i < enemies.length - 1; i++) {
			for (int j = i + 1; j < enemies.length; j++) {
				if (enemies[i].overlaps(enemies[j])) {
					enemies[i].pushes(enemies[j]);
				}
			}
		}

		for (int i = 0; i < enemies.length; i++) {
			for (int j = 0; j < l.length; j++) {
				if (enemies[i].overlaps(l[j])) {
					enemies[i].isPushedBackBy(l[j]);
				}
			}
		}
	}

	public void setEnemyBehavior(Enemy boss) {
		if (boss.alive) {
			if (!boss.within(180, brawler)) {
				boss.turnToward(brawler);
				boss.chase(brawler);
				boss.punching = true;
				boss.launch(bossbullet);//PROBLEM: enemies[i] & boss = Enemy => bullet type conflict
			}
		}
		for (int j = 0; j < l.length; j++) {
			if (boss.overlaps(l[j])) {
				boss.isPushedBackBy(l[j]);
			}
		}
	}

	public void inGameLoop() {
		// ----------------------------------Bullet--------------------------------------//
		// -----------------Brawler=>Enemy-----------------//
		for (int i = 0; i < bullet.length; i++) {
			bullet[i].move();
			setBulletBehavior(enemySets[0], bullet[i]);
			setBulletBehavior(enemySets[1], bullet[i]);
			setBulletBehavior(enemySets[2], bullet[i]);
			setBulletBehavior(bosses[lvl], bullet[i]);
		}

		// -----------------Enemy=>Brawler-----------------//
		for (int i = 0; i < ebullet.length; i++) {
			ebullet[i].move();
			setBulletBehavior(brawler, ebullet[i]);
		}

		for (int i = 0; i < bossbullet.length; i++) {
			bossbullet[i].move();
			setBulletBehavior(brawler, bossbullet[i]);
		}
		// ----------------------------------Enemy--------------------------------------//
		setEnemyBehavior(enemySets[0]);
		setEnemyBehavior(enemySets[1]);
		setEnemyBehavior(enemySets[2]);
		setEnemyBehavior(bosses[lvl]);
		// ----------------------------------Brawler--------------------------------------//
		if (pressing[UP]) {
			if (map.clearAbove(brawler)) {
				brawler.moveUp(s / 12);
				Camera.moveUp(s / 12);
			}
		}
		if (pressing[DN]) {
			if (map.clearBelow(brawler)) {
				brawler.moveDown(s / 12);
				Camera.moveDown(s / 12);
			}
		}
		if (pressing[LT]) {
			if (map.clearLeftOf(brawler)) {
				brawler.moveLeft(s / 12);
				Camera.moveLeft(s / 12);
			}
		}
		if (pressing[RT]) {
			if (map.clearRightOf(brawler)) {
				brawler.moveRight(s / 12);
				Camera.moveRight(s / 12);
			}
		}
		if (pressing[SPACE])
			brawler.shoot(bullet);

		for (int j = 0; j < l.length; j++) {
			if (brawler.overlaps(l[j])) {
				brawler.isPushedBackBy(l[j]);
				Camera.isPushedBackBy(l[j]);
			}
		}
	}

	public int getBrawlerX() {
		return brawler.x;
	}

	public void renderEnemies(int x, Enemy[] enemies, HealthBar[] healthbar, Graphics g) {
		if (getBrawlerX() >= x)
			for (int i = 0; i < enemies.length; i++) {
				if (enemies[i].alive) {
					enemies[i].draw(g);
					healthbar[i].draw(g);
				}
			}
	}

	public void renderEnemies(int x, Enemy enemyBoss, HealthBar healthbar, Graphics g) {
		if (getBrawlerX() >= x) {
			if (enemyBoss.alive) {
				enemyBoss.drawBoss(g);
				healthbar.draw(g);
			}
		}
	}

	public void paint(Graphics g) {
		// System.out.println("x: " + getBrawlerX() + ", y: " + brawler.y);
		map.draw(g);

		if (brawler.alive) {
			brawler.draw(g);
			bhb.draw(g);
		}
		// Camera.draw(g);

		renderEnemies(0, enemySets[0], enemyHealthBars[0], g);
		renderEnemies(620, enemySets[1], enemyHealthBars[1], g);
		renderEnemies(1580, enemySets[2], enemyHealthBars[2], g);
		renderEnemies(2540, bosses[lvl], bossHealthBars[lvl], g);

		for (int i = 0; i < bullet.length; i++) {
			if(
				bullet[i].x < brawler.x + 100 &&
				bullet[i].y > brawler.y - 100 &&
				bullet[i].x > brawler.x - 100 &&
				bullet[i].y < brawler.y + 100
			)				
				bullet[i].draw(g);
		}
		for (int i = 0; i < ebullet.length; i++) {
		//only working bc ebullet.length = enemySets[i].count
			if(lvl == 0) {
				if(
						(ebullet[i].x < enemySets[lvl][i].x + 100) && 
						(ebullet[i].y > enemySets[lvl][i].y - 100) && 
						(ebullet[i].x > enemySets[lvl][i].x - 100) && 
						(ebullet[i].y < enemySets[lvl][i].y + 100)
				)	
					ebullet[i].draw(g);
			}
			else if(lvl == 1) {
				if(
						(ebullet[i].x < enemySets[lvl][i].x + 200) && 
						(ebullet[i].y > enemySets[lvl][i].y - 200) && 
						(ebullet[i].x > enemySets[lvl][i].x - 200) && 
						(ebullet[i].y < enemySets[lvl][i].y + 200)
				)	
					ebullet[i].draw(g);
			}
			else if(lvl == 2) {
				if(
						(ebullet[i].x < enemySets[lvl][i].x + 500) && 
						(ebullet[i].y > enemySets[lvl][i].y - 500) && 
						(ebullet[i].x > enemySets[lvl][i].x - 500) && 
						(ebullet[i].y < enemySets[lvl][i].y + 500)
				)	
					ebullet[i].draw(g);
			}
		}
		
		for (int i = 0; i < bossbullet.length; i++) {
			bossbullet[i].draw(g);
		}

		//if (brawler.x == 700) 
		if(!bosses[lvl].alive)
			changeLevel();

		//if (brawler.y == 640)
		if(!bosses[lvl].alive)
			changeLevel();
		
	}

}