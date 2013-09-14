package isomap;

import java.util.HashMap;
import java.util.Map;

import org.newdawn.slick.Image;

/***
 * Cette classe permet de centraliser les ressources graphiques. Pour le moment elle permet d'associer un gid (un identifiant de tile)
 * avec l'image du tile.
 * @author Simon
 *
 */
public class ResourceManager {
	private static ResourceManager instance;
	private Map<Integer, Image> images;
	
	public static ResourceManager get(){
		if(instance != null)
			return instance;
		return instance = new ResourceManager();
	}
	
	private ResourceManager() {
		images = new HashMap<Integer, Image>();
	}
	
	/**
	 * Permet d'enregistrer le tileset dans le ResourceManager
	 * @param set le tileset à ajouter.
	 */
	public void addTileSet(TileSet set){
		
		int firstGid = set.getFirstGid();
		for(Image img : set.getImages()){
			images.put(firstGid++, img);
			
		}
	}
	/**
	 * retourne l'image associée à son identifiant gid.
	 * @param gid l'identifiant gid
	 * @return image associée
	 */
	public Image getImageByGid(int gid){
		return images.get(gid);
	}

}
