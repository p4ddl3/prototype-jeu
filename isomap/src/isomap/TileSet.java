package isomap;
import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;


public class TileSet {

	private TileSetJson json;
	private Image spriteSheet;
	private List<Image> images;
	public TileSet(TileSetJson json) {
		this.json = json;
		load();
	}
	
	/**
	 * Charge le spritesheet et le découpe en une multitude d'images.
	 */
	public void load(){
		try {
			spriteSheet = new Image(json.image);
		} catch (SlickException e) {
			e.printStackTrace();
		}
		images = new ArrayList<Image>();
		int startx = json.margin;
		int starty = json.margin;
		
		int tilewidth = json.tilewidth;
		int tileheight = json.tileheight;
		
		int endx = startx+tilewidth;
		int endy = starty+tileheight;
		Image img;
		while(endy<= json.imageheight){
			
			
			while( endx<= json.imagewidth){
				img = spriteSheet.getSubImage(startx, starty, tilewidth, tileheight);
				images.add(img);
				startx = endx+json.spacing;
				endx = startx+tilewidth;
			}
			startx = json.spacing;
			endx = startx+tilewidth;
			starty = endy+json.margin;
			endy = starty+tileheight;
		}
		
		
	}
	public List<Image> getImages(){
		return images;
	}
	/**
	 * retourne le gid du premier tile, tel qu'il est définit dans le fichier de la map. 
	 * @return
	 */
	public int getFirstGid(){
		return json.firstgid;
	}
	
	/**
	 * classe interne qui permet de faire le lien entre le JSON et l'objet.
	 * Refactoring dans pas longtemps en un TileSetData (pour respecter les conventions)
	 * @author Simon
	 *
	 */
	public static  class TileSetJson{
	public int firstgid;
	public String image;
	
	public int imageheight;
	public int imagewidth;
	
	public int margin;
	public int spacing;
	
	public int tileheight;
	public int tilewidth;
	
	public String name;
	}
	
	

}
