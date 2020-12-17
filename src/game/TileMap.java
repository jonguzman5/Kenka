package game;

import java.awt.*;
import java.io.*;

public class TileMap {
	private String[] map;
	Image[] tile;
	String[] tile_name;
	Image background;
	String background_name;
	int s;
	int base = 33;
	int cap = 256;
	char[] unicodes = new char[cap];

	public TileMap(String[] map, Image[] tile, Image background, int scale) {
		this.map = map;
		this.tile = tile;
		this.background = background;
		this.s = scale;
	}

	public TileMap(String img_dir, String filename, int scale) {
		populateUnicodes();
		for(int i = 0; i < unicodes.length; i++)	System.out.println(i + ": " + unicodes[i]);
		loadMap(filename);
		loadAssets(img_dir);
		s = scale;
	}
	
	public void populateUnicodes() {
		char[] arr = new char[cap];
		for(int i = base; i < cap; i++) 
			arr[i-base] = (char)i;
		String str = new String(arr);
		str = str.replaceAll("\\p{C}", "");
		unicodes = str.toCharArray();
		unicodes[94] = 'Ā';//char after filtered(256)
	}

	public void loadMap(String filename) {
		// handler to access file, !createFile
		File file = new File(filename);
		try {
			BufferedReader input = new BufferedReader(new FileReader(file));
			int n = Integer.parseInt(input.readLine());// how many rows in the map
			map = new String[n];
			for (int row = 0; row < n; row++) {
				map[row] = input.readLine();
			}
			n = Integer.parseInt(input.readLine());// how many tile in the map
			tile = new Image[n];
			tile_name = new String[n];
			for (int i = 0; i < n; i++) {
				tile_name[i] = input.readLine();
			}

			background_name = input.readLine();

			input.close();
		} catch (IOException e) {};
	}

	public void loadAssets(String img_dir) {
		tile = new Image[tile_name.length];
		for (int i = 0; i < tile.length; i++) {
			tile[i] = getImage(img_dir + "/" + tile_name[i]);
		}
		background = getImage(background_name);
	}

	// ----------------------------------Brawler--------------------------------------//
	public boolean clearAbove(Brawler b) {
		int top = b.y;
		int left = b.x;
		int right = b.x + s - 1;// -1 bc 17 sqr
		return
				(
				valueAt((top - s / 4), left) == '³' ||//s-ground
				valueAt((top - s / 4), left) == '¬' ||//s-corner-shadow				
				valueAt((top - s / 4), left) == 'q' ||//s-wall-shadow
				valueAt((top - s / 4), left) == '#' ||				
				
				valueAt((top - s / 4), left) == 'Ä' ||//ssb-ground
				valueAt((top - s / 4), left) == 'Æ' ||//ssb-corner-shadow				
				valueAt((top - s / 4), left) == '±' ||//ssb-wall-shadow
				
				valueAt((top - s / 4), left) == '#' ||//mb-ground
				valueAt((top - s / 4), left) == '=' ||//mb-ground
				valueAt((top - s / 4), left) == '$' ||//mb-corner-shadow
				valueAt((top - s / 4), left) == '9' //||//mb-wall-shadow
				) 
				&& 
				(
				valueAt((top - s / 4), right) == '³' ||//s-ground
				valueAt((top - s / 4), right) == '¬' ||//s-corner-shadow				
				valueAt((top - s / 4), right) == 'q' ||//s-wall-shadow
						
				valueAt((top - s / 4), right) == 'Ä' ||//ssb-ground
				valueAt((top - s / 4), right) == 'Æ' ||//ssb-corner-shadow
				valueAt((top - s / 4), right) == '±' ||//ssb-wall-shadow
				
				valueAt((top - s / 4), right) == '#' ||//mb-ground
				valueAt((top - s / 4), right) == '=' ||//mb-ground				
				valueAt((top - s / 4), right) == '$' ||//mb-corner-shadow
				valueAt((top - s / 4), right) == '9' //||//mb-wall-shadow
				);
	}

	public boolean clearBelow(Brawler b) {
		int bottom = b.y + s - 1;// s||b.h
		int left = b.x;
		int right = b.x + s - 1;// -1 bc 17 sqr
		return (
				valueAt(bottom + b.vy + 1, left) == '³' ||
				valueAt(bottom + b.vy + 1, left) == '¬' ||
				valueAt(bottom + b.vy + 1, left) == 'q' ||
				
				valueAt(bottom + b.vy + 1, left) == 'Ä' ||
				valueAt(bottom + b.vy + 1, left) == 'Æ' ||
				valueAt(bottom + b.vy + 1, left) == '±' ||
				
				valueAt(bottom + b.vy + 1, left) == '#' ||
				valueAt(bottom + b.vy + 1, left) == '=' ||				
				valueAt(bottom + b.vy + 1, left) == '$' ||
				valueAt(bottom + b.vy + 1, left) == '9' 
				) 
				&& 
				(
				valueAt((bottom + b.vy + 1), right) == '³' ||
				valueAt((bottom + b.vy + 1), right) == '¬' ||
				valueAt((bottom + b.vy + 1), right) == 'q' ||
				
				
				valueAt((bottom + b.vy + 1), right) == 'Ä' ||
				valueAt((bottom + b.vy + 1), right) == 'Æ' ||
				valueAt((bottom + b.vy + 1), right) == '±' ||
				
				valueAt((bottom + b.vy + 1), right) == '#' ||
				valueAt((bottom + b.vy + 1), right) == '=' ||
				valueAt((bottom + b.vy + 1), right) == '$' ||
				valueAt((bottom + b.vy + 1), right) == '9' 
				);
	}

	public boolean clearLeftOf(Brawler b) {
		int top = b.y;
		int bottom = b.y + s - 1;
		int left = b.x;
		return (
				valueAt(top, (left - s / 8)) == '³' ||
				valueAt(top, (left - s / 8)) == '¬' ||
				valueAt(top, (left - s / 8)) == 'q' ||
				
				valueAt(top, (left - s / 8)) == 'Ä' ||
				valueAt(top, (left - s / 8)) == 'Æ' ||
				valueAt(top, (left - s / 8)) == '±' ||
				
				valueAt(top, (left - s / 8)) == '#' ||
				valueAt(top, (left - s / 8)) == '=' ||
				valueAt(top, (left - s / 8)) == '$' ||
				valueAt(top, (left - s / 8)) == '9' 
				) 
				&& 
				(
				valueAt(bottom, (left - s / 8)) == '³' ||
				valueAt(bottom, (left - s / 8)) == '¬' ||
				valueAt(bottom, (left - s / 8)) == 'q' ||
				
				valueAt(bottom, (left - s / 8)) == 'Ä' ||
				valueAt(bottom, (left - s / 8)) == 'Æ' ||
				valueAt(bottom, (left - s / 8)) == '±' ||
				
				valueAt(bottom, (left - s / 8)) == '#' ||
				valueAt(bottom, (left - s / 8)) == '=' ||
				valueAt(bottom, (left - s / 8)) == '$' ||
				valueAt(bottom, (left - s / 8)) == '9' 
				);
	}

	public boolean clearRightOf(Brawler b) {
		int top = b.y;
		int bottom = b.y + s - 1;
		int right = b.x + s - 1;
		return (
				valueAt(top, (right + s / 8)) == '³' ||
				valueAt(top, (right + s / 8)) == '¬' ||
				valueAt(top, (right + s / 8)) == 'q' ||
				
				valueAt(top, (right + s / 8)) == 'Ä' ||
				valueAt(top, (right + s / 8)) == 'Æ' ||
				valueAt(top, (right + s / 8)) == '±' ||
				
				valueAt(top, (right + s / 8)) == '#' ||
				valueAt(top, (right + s / 8)) == '=' ||
				valueAt(top, (right + s / 8)) == '$' ||
				valueAt(top, (right + s / 8)) == '9' 
				) 
				&& 
				(
				valueAt(bottom, (right + s / 8)) == '³' ||
				valueAt(bottom, (right + s / 8)) == '¬' ||
				valueAt(bottom, (right + s / 8)) == 'q' ||
				
				valueAt(bottom, (right + s / 8)) == 'Ä' ||
				valueAt(bottom, (right + s / 8)) == 'Æ' ||
				valueAt(bottom, (right + s / 8)) == '±' ||
				
				valueAt(bottom, (right + s / 8)) == '#' ||
				valueAt(bottom, (right + s / 8)) == '=' ||
				valueAt(bottom, (right + s / 8)) == '$' ||
				valueAt(bottom, (right + s / 8)) == '9' 
				);
	}

	// ----------------------------------Zombie--------------------------------------//
	public boolean clearAbove(Zombie z) {
		int top = z.y;
		int bottom = z.y + s - 1;
		int left = z.x;
		int right = z.x + s - 1;
		return (valueAt((top - s / 4), left) == '³') && (valueAt((top - s / 4), right) == '³');
	}

	public boolean clearBelow(Zombie z) {
		int top = z.y;
		int bottom = z.y + s - 1;
		int left = z.x;
		int right = z.x + s - 1;
		return (valueAt(bottom + z.vy + 1, left) == '³') && (valueAt((bottom + z.vy + 1), right) == '³');
	}

	public boolean clearLeftOf(Zombie z) {
		int top = z.y;
		int bottom = z.y + s - 1;
		int left = z.x;
		int right = z.x + s - 1;
		return (valueAt(top, (left - s / 8)) == '³') && (valueAt(bottom, (left - s / 8)) == '³');
	}

	public boolean clearRightOf(Zombie z) {
		int top = z.y;
		int bottom = z.y + s - 1;
		int left = z.x;
		int right = z.x + s - 1;
		return (valueAt(top, (right + s / 8)) == '³') && (valueAt(bottom, (right + s / 8)) == '³');
	}
	// ------------------------------------------------------------------------------//

	public char valueAt(int y, int x) {
		int row = y / s;
		int col = x / s;
		return map[row].charAt(col);
	}
	
	public char unicodeOf(char c) {
		char ucc = ' ';
		for(int i = 0; i < unicodes.length; i++) 
			if(c == unicodes[i]) 
				ucc = unicodes[i];
		return ucc;
	}
	
	public int getIndex(char c) {
		int index = 0;
		for(int i = 0; i < unicodes.length; i++) 
			if(c == unicodes[i]) 
				index = i;
		return index;
	}

	public void draw(Graphics g) {
		g.drawImage(background, 0, 0, null);
		//how many strs do i have
		for (int row = 0; row < map.length; row++) {
			for (int col = 0; col < map[row].length(); col++) {
				char c = map[row].charAt(col);
				if(c == unicodeOf(c)) {
		            g.drawImage(tile[getIndex(c)], s * col - Camera.x + Camera.x_origin, s * row - Camera.y + Camera.y_origin, s, s, null);
				}				
			}
		}

	}

	public Image getImage(String filename) {
		return Toolkit.getDefaultToolkit().getImage(filename);
	}
}
