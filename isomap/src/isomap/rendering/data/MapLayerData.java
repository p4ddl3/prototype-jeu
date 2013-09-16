package isomap.rendering.data;

import java.util.HashMap;

public class MapLayerData{
	private int[] data;
	private int height;
	private int width;
	private HashMap<?,?> properties;
	private String name;
	private String type;
	private HashMap<?,?>[] objects;
	public MapLayerData(){
	}
	
	public int getHeight(){
		return height;
	}
	public int getWidth(){
		return width;
	}

	public String getProperty(String prop){
		if(properties == null)
			return null;
		return (String)properties.get(prop);
	}
	public String getLayerName(){
		return name;
	}
	public int[] getData(){
		return data;
	}
	public String getGroup(){
		return getProperty("group");
	}
	public HashMap<?,?>[] getObjects(){
		return objects;
	}
}