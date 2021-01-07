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
	Circle[] bossbullet = new Circle[10];
	Enemy[][] enemySets = enemies.enemySets[lvl];
	HealthBar[][] enemyHealthBars = enemies.enemyHealthBars[lvl];
	Enemy boss = enemies.bosses[lvl];
	HealthBar bossHealthBars = enemies.bossHealthBars[lvl];

	// ----------------------------------Configuration--------------------------------------//
	public void changeLevel() {
		if (lvl < lvls.length-1) {
			lvl++;
			map = lvls[lvl];
			brawler.x = 0;
			enemySets = enemies.enemySets[lvl];
			//enemyHealthBars = enemies.enemyHealthBars[lvl];
			boss = enemies.bosses[lvl];
			//bossHealthBars = enemies.bossHealthBars[lvl];
		}
	}

	public void init() {
		Camera.x = Camera.x_origin;
		Camera.y = Camera.y_origin;

		setBulletPosition(bullet);
		setBulletPosition(ebullet);
		setBulletPosition(bossbullet);

		setEnemyPosition(getBrawlerX() + 40, 0, enemySets[0], enemyHealthBars[0]);
		setEnemyPosition(getBrawlerX() + 40, 81, enemySets[1], enemyHealthBars[1]);
		setEnemyPosition(getBrawlerX() + 40, 162, enemySets[2], enemyHealthBars[2]);

		double[][] v = { 
				{ 4096, 720, 0, 720 }, // bottom
				{ 4096, 0, 4096, 720 }, // right
				{ 0, 720, 0, 0 },// left
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

	public void setEnemyPosition(int x, int hby, Enemy[] enemies, HealthBar[] healthbar) {
		for (int i = 0; i < enemies.length; i++) {
			//enemies[i] = new Enemy(100, x, rnd.nextInt(480) + 360, Zombie.DOWN, Zombie.pose, 10, 4, "png");
			healthbar[i] = new HealthBar(enemies[i], 620, hby, 100, 25);
			hby += 27;
		}
	}

	// ----------------------------------Brawler=>Enemy--------------------------------------//

	public void setBulletBehavior(Enemy[] enemies, Circle bullet) {
		for (int j = 0; j < enemies.length; j++)
			if (enemies[j].alive && bullet.overlaps(enemies[j])) {
				enemies[j].takesHitFor(20);
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
			if (!boss.within(90, brawler)) {
				boss.turnToward(brawler);
				boss.chase(brawler);
				// bossZombie.launch(bossbullet);
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
//			setBulletBehavior(boss, bullet[i]);
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
		setEnemyBehavior(boss);
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
		renderEnemies(2540, boss, bossHealthBars, g);

		for (int i = 0; i < bullet.length; i++) {
			// System.out.println("x: " + bullet[i].x + ", y:" + bullet[i].y);
			// if(bullet[i].withinPunchRange(bullet[i].x, bullet[i].y))
			bullet[i].draw(g);

		}
		for (int i = 0; i < ebullet.length; i++) {
			ebullet[i].draw(g);
		}
		for (int i = 0; i < bossbullet.length; i++) {
			bossbullet[i].draw(g);
		}

		if (brawler.x == 700) {
			changeLevel();
		}

		if (brawler.y == 640) {
			changeLevel();
		}
	}

}
