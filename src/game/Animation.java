package game;

import java.awt.Image;
import java.awt.Toolkit;

public class Animation {
	private Image[] image;
	private int current = 0;
	private int delay;
	private int duration;
	
	public Animation(String name, int duration, int count, String ext) {
		image = new Image[count];
		for(int i = 0; i < count; i++) {
			image[i] = Toolkit.getDefaultToolkit().getImage(name + "_" + i + "." + ext);
		}
		this.duration = duration;
		delay = duration;
	}
	
	public Image getCurrentImage() {
		if(delay == 0) {
			current++;
			if(current == image.length-1) current = 1;//[-1] = exclude punch @move
			delay = duration;
		}
		delay--;
		return image[current];
	}
	
	public Image getStillImage() {
		return image[0];
	}
	
	public Image getCombatImage() {
		return image[image.length-1];
	}
}
