package game;

public class KungFuMan extends Enemy {
	
	public static final String[] pose = {
			"k/k_up",
			"k/k_dn",
			"k/k_lt",
			"k/k_rt"
	};

	public KungFuMan(int hit_points, int x, int y, int action) {
		super(hit_points, x, y, action, pose, 10, 4, "png");
	}
}