package game;
import java.awt.*;
import java.awt.event.*;
import java.applet.*;
import java.util.*;

public class GameApplet extends Applet implements Runnable, KeyListener, MouseListener, MouseMotionListener {
	Image off_screen_image; 
	Graphics off_g;
	Thread t;
	
	boolean[] pressing = new boolean[1024]; 

	public static final int UP = KeyEvent.VK_UP;
	public static final int DN = KeyEvent.VK_DOWN;
	public static final int LT = KeyEvent.VK_LEFT;
	public static final int RT = KeyEvent.VK_RIGHT;
	
	public static final int _W = KeyEvent.VK_W;
	public static final int _A = KeyEvent.VK_A;
	public static final int _S = KeyEvent.VK_S;
	public static final int _D = KeyEvent.VK_D;
	public static final int CTRL = KeyEvent.VK_CONTROL;
	public static final int SHFT = KeyEvent.VK_SHIFT;
	
	public static final int SPACE = KeyEvent.VK_SPACE;
	
	//mouse locs
	int mx = 0;
	int my = 0;
	
	
	public void init() {
		off_screen_image = this.createImage(2000, 1200);//screen size
		off_g = off_screen_image.getGraphics();
		
		requestFocus();
		addKeyListener(this);
		addMouseListener(this);
		addMouseMotionListener(this);
		t = new Thread(this);
		t.start();
	}
	
	@Override
	public void run() {
		 while(true) {
			inGameLoop();
			repaint();
			try {
				t.sleep(15);
			}
			catch(InterruptedException x){}
		}
	}	
	
	public void inGameLoop() {}
	
	@Override public void mouseMoved(MouseEvent e) {}	
	@Override public void mouseDragged(MouseEvent e) {}
	@Override public void mousePressed(MouseEvent e) {}
	@Override public void mouseReleased(MouseEvent e) {}
	@Override public void mouseClicked(MouseEvent e) {}
	@Override public void mouseEntered(MouseEvent e) {}
	@Override public void mouseExited(MouseEvent e) {}

	@Override
	public final void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		pressing[key] = true;
	}
	@Override
	public final void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		pressing[key] = false;
	}
	@Override public final void keyTyped(KeyEvent e) {}
	
	public void update(Graphics g) {
		off_g.clearRect(0, 0, 2000, 1200);//=off_screen_image
		paint(off_g);
		g.drawImage(off_screen_image, 0, 0, null);
	}
	
	public Image getImage(String filename) {
		return Toolkit.getDefaultToolkit().getImage(filename);
	}
	
}
