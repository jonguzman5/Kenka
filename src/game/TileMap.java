package game;
import java.awt.*;
import java.io.*;

public class TileMap {	
	private String[] map;
	Image[] tile;
	String[] tile_name;
	//Image background;
	String background_name;
	int s;
	
	public TileMap(String[] map, Image[] tile, int scale) {//, Image background
		this.map = map;
		this.tile = tile;
		//this.background = background;
		this.s = scale;
	}
	
	public TileMap(String filename, int scale) {
		loadMap(filename);
		loadAssets();
		s = scale;
	}
	
	public void loadMap(String filename) {
		//handler to access file, !createFile
		File file = new File(filename);	
		try {
			BufferedReader input = new BufferedReader(new FileReader(file));
			int n = Integer.parseInt(input.readLine());//how many rows in the map
			map = new String[n];
			for(int row = 0; row < n; row++) {
				map[row] = input.readLine();
			}
			n = Integer.parseInt(input.readLine());//how many tile in the map
			tile = new Image[n];
			tile_name = new String[n];
			for(int i = 0; i < n; i++) {
				tile_name[i] = input.readLine();
			}
			input.close();
		}
		catch(IOException e){};	
	}
	
	public void loadAssets() {
		tile = new Image[tile_name.length];
		for(int i = 0; i < tile.length; i++) {
			tile[i] = getImage(tile_name[i]);
		}
		//background = getImage(background_name);
	}
	//----------------------------------Brawler--------------------------------------//
	public boolean clearAbove(Brawler b) {
		int top = b.y;
		int left = b.x;
		int right = b.x + s - 1;// -1 bc 17 sqr
		return (valueAt((top-s/4), left) == '.') && (valueAt((top-s/4), right) == '.');
	}
	
	public boolean clearBelow(Brawler b) {
		int bottom = b.y + s - 1;// s||b.h
		int left = b.x;
		int right = b.x + s - 1;// -1 bc 17 sqr
		return (valueAt(bottom+b.vy+1, left) == '.') && (valueAt((bottom+b.vy+1), right) == '.');
	}
	
	public boolean clearLeftOf(Brawler b) {
		int top = b.y;
		int bottom = b.y + s - 1;
		int left = b.x;
		return (valueAt(top, (left-s/8)) == '.') && (valueAt(bottom, (left-s/8)) == '.');
	}
	
	public boolean clearRightOf(Brawler b) {
		int top = b.y;
		int bottom = b.y + s - 1;
		int right = b.x + s - 1;
		return (valueAt(top, (right+s/8)) == '.') && (valueAt(bottom, (right+s/8)) == '.');
	}
	//----------------------------------Zombie--------------------------------------//
	  public boolean clearAbove(Zombie z) {
	    int top = z.y;
	    int bottom = z.y + s - 1;
	    int left = z.x;
	    int right = z.x + s - 1;
	    return (valueAt((top-s/4), left) == '.') && (valueAt((top-s/4), right) == '.');
	  }
	  
	  public boolean clearBelow(Zombie z) {
	    int top = z.y;
	    int bottom = z.y + s - 1;
	    int left = z.x;
	    int right = z.x + s - 1;
	    return (valueAt(bottom+z.vy+1, left) == '.') && (valueAt((bottom+z.vy+1), right) == '.');
	  }
	  
	  public boolean clearLeftOf(Zombie z) {
	    int top = z.y;
	    int bottom = z.y + s - 1;
	    int left = z.x;
	    int right = z.x + s - 1;
	    return (valueAt(top, (left-s/8)) == '.') && (valueAt(bottom, (left-s/8)) == '.');
	  }
	  
	  public boolean clearRightOf(Zombie z) {
	    int top = z.y;
	    int bottom = z.y + s - 1;
	    int left = z.x;
	    int right = z.x + s - 1;
	    return (valueAt(top, (right+s/8)) == '.') && (valueAt(bottom, (right+s/8)) == '.');
	  }
	  //------------------------------------------------------------------------------//
	
	public char valueAt(int y, int x) {
		int row = y/s;
		int col = x/s;
		return map[row].charAt(col);
	}
	
	public void draw (Graphics g) {
		//g.drawImage(background, 0, 0, null);
		
		// how many strs do i have
	      for(int row = 0; row < map.length; row++) {
	          for(int col = 0; col < map[row].length(); col++) {
	            char c = map[row].charAt(col);
	            if(c != '.') {
		              if(c == '#')
		                g.drawImage(tile[5], s*col - Camera.x + Camera.x_origin, s*row - Camera.y + Camera.y_origin, s, s, null);
		              if(c == 'A')
		                g.drawImage(tile[18], s*col - Camera.x + Camera.x_origin, s*row - Camera.y + Camera.y_origin, s, s, null);
		              if(c == 'B')
		                g.drawImage(tile[19], s*col - Camera.x + Camera.x_origin, s*row - Camera.y + Camera.y_origin, s, s, null);
		              if(c == 'C')
		                g.drawImage(tile[20], s*col - Camera.x + Camera.x_origin, s*row - Camera.y + Camera.y_origin, s, s, null);
		              if(c == 'D')
		                g.drawImage(tile[36], s*col - Camera.x + Camera.x_origin, s*row - Camera.y + Camera.y_origin, s, s, null);
		              if(c == 'E')
		                g.drawImage(tile[37], s*col - Camera.x + Camera.x_origin, s*row - Camera.y + Camera.y_origin, s, s, null);
		              if(c == 'F')
		                g.drawImage(tile[38], s*col - Camera.x + Camera.x_origin, s*row - Camera.y + Camera.y_origin, s, s, null);
		              if(c == 'G')
		                g.drawImage(tile[63], s*col - Camera.x + Camera.x_origin, s*row - Camera.y + Camera.y_origin, s, s, null);
		              if(c == 'H')
		                g.drawImage(tile[64], s*col - Camera.x + Camera.x_origin, s*row - Camera.y + Camera.y_origin, s, s, null);
		              if(c == 'I')
		                g.drawImage(tile[65], s*col - Camera.x + Camera.x_origin, s*row - Camera.y + Camera.y_origin, s, s, null);

		              if(c == 'a')
		                g.drawImage(tile[48], s*col - Camera.x + Camera.x_origin, s*row - Camera.y + Camera.y_origin, s, s, null);
		              if(c == 'b')
		                g.drawImage(tile[74], s*col - Camera.x + Camera.x_origin, s*row - Camera.y + Camera.y_origin, s, s, null);
		              if(c == 'c')
		                g.drawImage(tile[93], s*col - Camera.x + Camera.x_origin, s*row - Camera.y + Camera.y_origin, s, s, null);
		              if(c == 'd')
		                g.drawImage(tile[9], s*col - Camera.x + Camera.x_origin, s*row - Camera.y + Camera.y_origin, s, s, null);
		              if(c == 'e')
		                g.drawImage(tile[94], s*col - Camera.x + Camera.x_origin, s*row - Camera.y + Camera.y_origin, s, s, null);
		              if(c == 'f')
		                g.drawImage(tile[49], s*col - Camera.x + Camera.x_origin, s*row - Camera.y + Camera.y_origin, s, s, null);
		              if(c == 'g')
		                g.drawImage(tile[50], s*col - Camera.x + Camera.x_origin, s*row - Camera.y + Camera.y_origin, s, s, null);
		              if(c == 'h')
		                g.drawImage(tile[51], s*col - Camera.x + Camera.x_origin, s*row - Camera.y + Camera.y_origin, s, s, null);
		              if(c == 'i')
		                g.drawImage(tile[52], s*col - Camera.x + Camera.x_origin, s*row - Camera.y + Camera.y_origin, s, s, null);
		              if(c == 'j')
		                g.drawImage(tile[53], s*col - Camera.x + Camera.x_origin, s*row - Camera.y + Camera.y_origin, s, s, null);
		              if(c == 'k')
		                g.drawImage(tile[76], s*col - Camera.x + Camera.x_origin, s*row - Camera.y + Camera.y_origin, s, s, null);
		              if(c == 'l')
		                g.drawImage(tile[77], s*col - Camera.x + Camera.x_origin, s*row - Camera.y + Camera.y_origin, s, s, null);
		              if(c == 'm')
		                g.drawImage(tile[78], s*col - Camera.x + Camera.x_origin, s*row - Camera.y + Camera.y_origin, s, s, null);
		              if(c == 'n')
		                g.drawImage(tile[95], s*col - Camera.x + Camera.x_origin, s*row - Camera.y + Camera.y_origin, s, s, null);
		              if(c == 'o')
		                g.drawImage(tile[96], s*col - Camera.x + Camera.x_origin, s*row - Camera.y + Camera.y_origin, s, s, null);
		              if(c == 'p')
		                g.drawImage(tile[97], s*col - Camera.x + Camera.x_origin, s*row - Camera.y + Camera.y_origin, s, s, null);
		              if(c == 'q')
		                g.drawImage(tile[83], s*col - Camera.x + Camera.x_origin, s*row - Camera.y + Camera.y_origin, s, s, null);
		              if(c == 'r')
		                g.drawImage(tile[101], s*col - Camera.x + Camera.x_origin, s*row - Camera.y + Camera.y_origin, s, s, null);

		              if(c == 'J')
		                g.drawImage(tile[33], s*col - Camera.x + Camera.x_origin, s*row - Camera.y + Camera.y_origin, s, s, null);
		              if(c == 'K')
		                g.drawImage(tile[99], s*col - Camera.x + Camera.x_origin, s*row - Camera.y + Camera.y_origin, s, s, null);
		              if(c == 'L')
		                g.drawImage(tile[57], s*col - Camera.x + Camera.x_origin, s*row - Camera.y + Camera.y_origin, s, s, null);
		              if(c == 'M')
		                g.drawImage(tile[58], s*col - Camera.x + Camera.x_origin, s*row - Camera.y + Camera.y_origin, s, s, null);
		              if(c == 'N')
		                g.drawImage(tile[92], s*col - Camera.x + Camera.x_origin, s*row - Camera.y + Camera.y_origin, s, s, null);
		              if(c == 'O')
		                g.drawImage(tile[56], s*col - Camera.x + Camera.x_origin, s*row - Camera.y + Camera.y_origin, s, s, null);
		            }
		            else {
		              g.drawImage(tile[112], s*col - Camera.x + Camera.x_origin, s*row - Camera.y + Camera.y_origin, s, s, null);
		            }
	          }
	        }

	}
	
	public Image getImage(String filename) {
		return Toolkit.getDefaultToolkit().getImage(filename);
	}
}

