package game;

import java.util.Random;

import org.omg.CORBA.Any;

public class Enemies {
	int setLen = 3;
	int setAm = 3;
	Random rnd = new Random(System.currentTimeMillis());
	
	
	public Zombie[] populateZombieArr() {
		int x = 500;
		Zombie[] arr = new Zombie[setLen];
		for(int i = 0; i < setLen; i++) {
			arr[i] = new Zombie(100, x, rnd.nextInt(480) + 360, Zombie.DOWN);
		}
		return arr;
	}
	
	public KungFuMan[] populateKungfumanArr() {		
		int x = 100;
		KungFuMan[] arr = new KungFuMan[setLen];
		for(int i = 0; i < setLen; i++) {
			arr[i] = new KungFuMan(100, x, rnd.nextInt(480) + 360, KungFuMan.DOWN);
		}
		return arr;
	}
	
	public Soldier[] populateSoldierArr() {		
		int x = 100;
		Soldier[] arr = new Soldier[setLen];
		for(int i = 0; i < setLen; i++) {
			arr[i] = new Soldier(100, x, rnd.nextInt(480) + 360, Soldier.DOWN);
		}
		return arr;
	}
	
	//----------------------------------Zombie--------------------------------------//
	Zombie z = new Zombie(200, Zombie.LEFT, 46, 0);
	Zombie[] zombieSet1 = populateZombieArr();
	Zombie[] zombieSet2 = populateZombieArr();
	Zombie[] zombieSet3 = populateZombieArr();
	
	Zombie[][] zombieSet = {zombieSet1, zombieSet2, zombieSet3};
	Zombie bossZombie = new Zombie(200, 3300, 495, Zombie.LEFT);
	
	HealthBar[] zhb1 = new HealthBar[setLen * setAm];
	HealthBar[] zhb2 = new HealthBar[setLen * setAm];
	HealthBar[] zhb3 = new HealthBar[setLen * setAm];
	HealthBar[][] zhb = {zhb1, zhb2, zhb3};
	HealthBar bzhb = new HealthBar(bossZombie, 520, 243, 200, 25);
	
	//----------------------------------KungFuMan--------------------------------------//
	
	KungFuMan[] kungfumanSet1 = populateKungfumanArr();
	KungFuMan[] kungfumanSet2 = populateKungfumanArr();
	KungFuMan[] kungfumanSet3 = populateKungfumanArr();
	KungFuMan[][] kungfumanSet = {kungfumanSet1, kungfumanSet2, kungfumanSet3};
	KungFuMan bossKungfuman = new KungFuMan(200, 3300, 495, KungFuMan.LEFT);
	
	HealthBar[] khb1 = new HealthBar[setLen * setAm];
	HealthBar[] khb2 = new HealthBar[setLen * setAm];
	HealthBar[] khb3 = new HealthBar[setLen * setAm];
	HealthBar[][] khb = {khb1, khb2, khb3};
	HealthBar bkhb = new HealthBar(bossKungfuman, 520, 243, 200, 25);
	
	//----------------------------------Soldier--------------------------------------//
	
	Soldier[] soldierSet1 = populateSoldierArr();
	Soldier[] soldierSet2 = populateSoldierArr();
	Soldier[] soldierSet3 = populateSoldierArr();
	Soldier[][] soldierSet = {soldierSet1, soldierSet2, soldierSet3};
	Soldier bossSoldier = new Soldier(200, 3300, 495, Soldier.LEFT);
	
	HealthBar[] shb1 = new HealthBar[setLen * setAm];
	HealthBar[] shb2 = new HealthBar[setLen * setAm];
	HealthBar[] shb3 = new HealthBar[setLen * setAm];
	HealthBar[][] shb = {shb1, shb2, shb3};
	HealthBar bshb = new HealthBar(bossSoldier, 520, 243, 200, 25);
	
	//----------------------------------Configuration--------------------------------------//
	Enemy[][][] enemySets = {zombieSet, kungfumanSet, soldierSet};
	HealthBar[][][] enemyHealthBars = {zhb, khb, shb};
	Enemy[] bosses = {bossZombie, bossKungfuman, bossSoldier};
	HealthBar[] bossHealthBars = {bzhb, bkhb, bshb};	
}
