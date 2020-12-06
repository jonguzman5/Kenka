package game;

import java.awt.Color;
import java.awt.Graphics;

public class HealthBar {
	int x;
	int y;
	int w;
	int h;
	Rect border;
	Rect redBar;
	Rect greenBar;
	
	public HealthBar(int x, int y, int w, int h) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		border = new Rect(x, y, w + 1, h + 1, Color.black);
		redBar = new Rect(x, y, w, h, Color.red);
		greenBar = new Rect(x, y, w, h, Color.green);
	}
	
	public void draw(Graphics g) {
		border.draw(g);
		
		redBar.draw(g);
		redBar.fill(g);
		
		greenBar.draw(g);
		greenBar.fill(g);
	}
}
