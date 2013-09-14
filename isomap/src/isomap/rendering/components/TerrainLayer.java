package isomap.rendering.components;



import isomap.rendering.data.MapLayerData;

import com.artemis.Component;


/**
 * Calque de terrain.
 * @author Simon
 *
 */
public class TerrainLayer extends Component{

	private MapLayerData data;
	public TerrainLayer(MapLayerData data) {
		this.data = data;
	}
	public int getHeight(){
		return data.getHeight();
	}
	public int getWidth(){
		return data.getWidth();
	}
	public int get(int x, int y){
		if(y < getHeight() && y >= 0 && x < getWidth() && x >=0){
			return data.getData()[y*getHeight()+x];
		}
		else{
			System.err.println("("+x+","+y+") out of layer bounds");
			return -1;
		}
	}
	public String getProperty(String prop){
		return data.getProperty(prop);
	}
	public String getLayerName(){
		return data.getLayerName();
	}


}
