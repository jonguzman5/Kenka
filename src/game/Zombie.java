package game;

public class Zombie extends Enemy {
  
  public static final String[] pose = {
      "z/z_up",
      "z/z_dn",
      "z/z_lt",
      "z/z_rt"
  };

  public Zombie(int hit_points, int x, int y, int action) {
    super(hit_points, x, y, action, pose, 10, 4, "png");
  }

}
