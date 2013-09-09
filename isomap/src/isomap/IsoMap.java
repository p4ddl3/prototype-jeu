package isomap;
import org.newdawn.slick.Image;

import isomap.TileSet.TileSetJson;

import com.google.gson.Gson;


public class IsoMap {

	private int height;
	private int width;
	private int version;
	
	private int tilewidth;
	private int tileheight;
	
	private MapLayer[] layers;
	private TileSetJson[] tilesets;
	
	public IsoMap() {
	}
	public static IsoMap load(String filepath){
			IsoMap map =  new Gson().fromJson(Utils.loadJSON(filepath), IsoMap.class);
			for(int i =0; i < map.tilesets.length; i++){
				ResourceManager.get().addTileSet(new TileSet(map.tilesets[i]));
			}
			return map;
	}
	
	public int getHeight(){
		return height;
	}
	public int getWidth(){
		return width;
	}
	public int getVersion(){
		return version;
	}
	public int getTileWidth(){
		return tilewidth;
	}
	public int getTileHeight(){
		return tileheight;
	}
	public int getLayersCount(){
		return layers.length;
	}
	
	public int get(int x, int y, int layer){
		if(layer >= 0 && layer < layers.length){
			return layers[layer].get(x, y);
		}else{
			System.err.println(layer +" isn't a layer");
			return -1;
		}
		
	}
	public void render(int offsetx, int offsety, int layer){
		Image img;
		for(int x = 0; x < getWidth(); x++){
			for(int y = 0; y < getHeight() ; y++){
				img = ResourceManager.get().getImageByGid(get(x,y,layer));
				if(img != null){
					float fx = x*getTileWidth()/2 - y*getTileWidth()/2;
					float fy = y*getTileHeight()/2 + x*getTileHeight()/2;
					img.draw(fx+400, fy+200);
				}
			}
		}
	}
	public void render(int offsetx, int offsety){
		for(int l = 0; l < layers.length; l++){
			render(offsetx,offsety, l);
		}
	}
	
	public static class MapLayer{
		private int[] data;
		private int height;
		private int width;
		public MapLayer(){
		}
		
		public int getHeight(){
			return height;
		}
		public int getWidth(){
			return width;
		}
		public int get(int x, int y){
			if(y < height && y >= 0 && x < width && x >=0){
				return data[y*height+x];
			}
			else{
				System.err.println("("+x+","+y+") out of layer bounds");
				return -1;
			}
		}
	}

}
