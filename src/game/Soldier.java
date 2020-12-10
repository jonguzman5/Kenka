package game;

public class Soldier extends Character {
	boolean alive = true;
	int r;
	int a;
	
	double cosA;
	double sinA;
	
	public static final String[] pose = {
			"s/s_up",
			"s/s_dn",
			"s/s_lt",
			"s/s_rt"
	};

	public Soldier(int hit_points, int x, int y, int action, int r, int a) {
		super(hit_points, x, y, action, pose, 10, 4, "png");
		this.r = r;
		this.a = a;
		cosA = Lookup.cos[a];
		sinA = Lookup.sin[a];
	}
}