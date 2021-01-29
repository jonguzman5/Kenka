package game;

public class Soldier extends Enemy {
	
	public static final String[] pose = {
			"s/s_up",
			"s/s_dn",
			"s/s_lt",
			"s/s_rt"
	};

	public Soldier(int hit_points, int x, int y, int action) {
		super(hit_points, x, y, action, pose, 10, 5, "png");
	}
}