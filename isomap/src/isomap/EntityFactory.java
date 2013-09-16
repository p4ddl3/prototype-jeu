package isomap;

import java.util.Map;

import isomap.rendering.components.Position2D;
import isomap.rendering.components.SpatialForm;
import isomap.rendering.components.TerrainLayer;
import isomap.rendering.data.MapLayerData;

import com.artemis.Entity;
import com.artemis.World;


/**
 * Centralisation de la création des entités
 * @author Simon
 *
 */
public class EntityFactory {

	private EntityFactory() {

	}
	/**
	 * Crée un calque de terrain
	 * @param world
	 * @param data
	 * @return
	 */
	public static Entity createMapTerrainEntity(World world, MapLayerData data){
		Entity e = world.createEntity();
		e.addComponent(new TerrainLayer(data));
		return e;
	}
	public static Entity createObjectSafe(World world, int x, int y, Map<String, String> content){
		Entity e = world.createEntity();
		e.addComponent(new SpatialForm("locked-safe"));
		e.addComponent(new Position2D(x,y));
		return e;
	}

}
