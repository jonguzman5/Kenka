package game;

import java.awt.Color;
import java.awt.Graphics;

public class HealthBar extends Rect {

	Character character;

	public HealthBar(Character character, int x, int y, int w, int h) {
		super(x, y, w, h);
		this.character = character;
	}

	public void draw(Graphics g) {
		color = Color.RED;
		fill(g);

		int width = (int) (w * character.health / (float) character.hit_points);

		g.setColor(Color.GREEN);
		g.fillRect(x, y, width, h);

		color = Color.BLACK;
		super.draw(g);
	}
}
