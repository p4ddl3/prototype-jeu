package isomap;

import java.util.HashMap;
import java.util.Map;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/***
 * Cette classe permet de centraliser les ressources graphiques. Pour le moment elle permet d'associer un gid (un identifiant de tile)
 * avec l'image du tile.
 * @author Simon
 *
 */
public class ResourceManager {
	private static ResourceManager instance;
	private Map<Integer, Image> tiles;
	private Map<String, Image> images;
	
	public static ResourceManager get(){
		if(instance != null)
			return instance;
		return instance = new ResourceManager();
	}
	
	private ResourceManager() {
		tiles = new HashMap<Integer, Image>();
		images = new HashMap<String, Image>();
	}
	
	/**
	 * Permet d'enregistrer le tileset dans le ResourceManager
	 * @param set le tileset à ajouter.
	 */
	public void addTileSet(TileSet set){
		
		int firstGid = set.getFirstGid();
		for(Image img : set.getImages()){
			tiles.put(firstGid++, img);
			
		}
	}
	/**
	 * retourne le tile associée à son identifiant gid.
	 * @param gid l'identifiant gid
	 * @return image du tile associé
	 */
	public Image getTileByGid(int gid){
		return tiles.get(gid);
	}
	
	public Image loadImage(String imageName, String imagePath){
		Image img = null;
		try {
			if(images.keySet().contains(imageName)){
				throw new NameAlreadyUsedException();
			}
			img = new Image(imagePath);
		} catch (SlickException e) {
			e.printStackTrace();
		} catch (NameAlreadyUsedException e) {
			e.printStackTrace();
			return null;
		}
		
		images.put(imageName, img);
		return img;
	}
	public Image getImage(String imageName){
		return images.get(imageName);
	}
	public void loadAllImages(){
		loadImage("locked-safe", "/resources/tileset/safe-locked.png");
	}
	private class NameAlreadyUsedException extends Exception{

		private static final long serialVersionUID = 1L;
		
	}

}
