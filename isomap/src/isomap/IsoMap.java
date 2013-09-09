package isomap;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Vector2f;

import isomap.TileSet.TileSetJson;
import isomap.systems.CameraSystem;

import com.google.gson.Gson;


public class IsoMap {

	private int height;
	private int width;
	private int version;
	
	private int tilewidth;
	private int tileheight;
	
	private MapLayer[] layers;
	private TileSetJson[] tilesets;
	
	private CameraSystem cameraSystem;
	private boolean showGrid;
	
	private Graphics g;
	private GameContainer container;
	public IsoMap() {
	}
	public static IsoMap load(String filepath, GameContainer container, CameraSystem camera){
			IsoMap map =  new Gson().fromJson(Utils.loadJSON(filepath), IsoMap.class);
			map.cameraSystem = camera;
			map.g = container.getGraphics();
			map.container = container;
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
	public void setShowGrid(boolean show){
		showGrid = show;
	}
	public boolean isShowGrid(){
		return showGrid;
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
					img.draw(fx+offsetx, fy+offsety);
				}
			}
		}
	}
	public void render(int offsetx, int offsety){
		for(int l = 0; l < layers.length; l++){
			render(offsetx,offsety, l);
		}
		if(showGrid)
			drawGrid(offsetx,offsety);
	}
	public void drawGrid(int offsetx, int offsety){
		for(int x = 0; x < getWidth(); x++){
			for(int y = 0; y < getHeight() ; y++){
					float tileX = x*getTileWidth()/2 - y*getTileWidth()/2;
					float tileY = y*getTileHeight()/2 + x*getTileHeight()/2;
					
					float x1 = tileX+getTileWidth()/2+offsetx;
					float y1 = tileY+offsety;
					{
						float x2 = tileX+getTileWidth()+offsetx;
						float y2 = tileY+getTileHeight()/2+offsety;
						g.drawLine(x1, y1, x2, y2);
					}
					{
						float x2 = tileX+offsetx;
						float y2 = tileY+getTileHeight()/2+offsety;
						g.drawLine(x1, y1, x2, y2);
					}
			}
		}
	}
	public Vector2f screen2tile(float mouseX, float mouseY){
		float w = getTileWidth()/2;
		float h = getTileHeight()/2;
		
		mouseX += w;
		float x = (mouseX*h + mouseY*w)/(2*w*h);
		Vector2f v = new Vector2f();
		v.x = (float)Math.floor(x)-1;
		
		float y = (mouseX*-h + mouseY*w)/(2*w*h); 
		v.y = (float)Math.floor(y)+1;
		return v;
	}
	public Vector2f tile2screen(int a, int b){
		Vector2f v = new Vector2f();
		
		float w = getTileWidth()/2;
		float h = getTileHeight()/2;
		
		float x = a*w + b*-w;
		float y = a*h + b*h;
		v.x = x;
		v.y = y;
		return v;
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
