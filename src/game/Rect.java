package game;

import java.awt.Color;
import java.awt.Graphics;

public class Rect {
	int x;
	int y;
	
	int w;
	int h;
	
	Color color;
	
	public Rect (int x, int y, int w, int h) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}
	
	public void draw(Graphics g) {
		g.setColor(color);
		g.drawRect(x, y, w, h);
	}
	
	public void fill(Graphics g) {
		g.setColor(color);
		g.fillRect(x, y, w, h);
	}
}
