package isomap.rendering.data;

import isomap.TileSet.TileSetJson;

import java.util.ArrayList;
import java.util.List;


/**
 * Tas de donn�es qui permet de faire le lien entre le JSON et la Map. Classe utilis�e par IsoMapBuilder.
 * @author Simon
 *
 */
public class IsoMapData {
	private int height;
	private int width;
	private int version;
	
	private int tilewidth;
	private int tileheight;
	
	private TileSetJson[] tilesets;
	
	private MapLayerData[] layers;
	public IsoMapData() {

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
	public int  getTileWidth(){
		return tilewidth;
	}
	public int getTileHeight(){
		return tileheight;
	}
	public TileSetJson[] getTilesets(){
		return tilesets;
	}
	public MapLayerData[] getAllLayers(){
		return layers;
	}
	/**
	 * permet de trier les diff�rents calques selon leur groupe d'appartenance (terrain, object, etc...)
	 * Ca permet de d�couper proprement les donn�es de la map, et de dispatcher les infos sur diff�rents entit�s/syst�mes
	 * @param group
	 * @return
	 */
	public List<MapLayerData> getMapLayersByGroup(String group){
		List<MapLayerData> list = new ArrayList<MapLayerData>();
		for(MapLayerData layer : layers){
			if(layer.getGroup().equals(group)){
				list.add(layer);
			}
		}
		return list;
	}

}
