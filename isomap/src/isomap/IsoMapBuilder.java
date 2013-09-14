package isomap;

import isomap.TileSet.TileSetJson;
import isomap.rendering.data.IsoMapData;
import isomap.rendering.data.MapConstants;
import isomap.rendering.data.MapLayerData;

import com.artemis.World;
import com.google.gson.Gson;

/***
 * Builder qui charge le fichier JSON de la map et créer les entités qui correspondent.
 * Met à jour les constantes de la map (hauteur, largeur, etc...)
 * @author Simon
 *
 */
public class IsoMapBuilder {
	private IsoMapBuilder() {
	}
	public static void loadFromJSON(World world, String filepath){
		IsoMapData data = new Gson().fromJson(Utils.loadJSON(filepath), IsoMapData.class);
		
		//chargement des tilesets
		for(TileSetJson tilesetData : data.getTilesets()){
			ResourceManager.get().addTileSet(new TileSet(tilesetData));
		}
		
		//1er : créeation des différents calques de terrain.
		for(MapLayerData layer : data.getMapLayersByGroup("terrain")){
			System.out.println(layer.getLayerName());
			EntityFactory.createMapTerrainEntity(world, layer).addToWorld();
		}
		
		//2ieme : génération des entitées correspondants aux objets de la map (en cours)
		MapLayerData objLayer = data.getMapLayersByGroup("objects").get(0);
		
		MapConstants.MAP_HEIGHT = data.getHeight();
		MapConstants.MAP_WIDTH = data.getWidth();
		
		MapConstants.TILE_HEIGHT = data.getTileHeight();
		MapConstants.TILE_WIDTH = data.getTileWidth();
	}
		
}
