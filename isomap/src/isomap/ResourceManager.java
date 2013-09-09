package isomap;

import java.util.HashMap;
import java.util.Map;

import org.newdawn.slick.Image;

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
	public void addTileSet(TileSet set){
		
		int firstGid = set.getFirstGid();
		for(Image img : set.getImages()){
			images.put(firstGid++, img);
			
		}
	}
	public Image getImageByGid(int gid){
		return images.get(gid);
	}

}
