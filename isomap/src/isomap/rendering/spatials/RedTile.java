package isomap.rendering.spatials;



import isomap.rendering.data.MapConstants;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Polygon;

import com.artemis.Entity;
import com.artemis.World;

public class RedTile extends Spatial{

	public RedTile(World world, Entity owner) {
		super(world, owner);
	}

	@Override
	public void initialize() {
	}

	@Override
	public void render(Graphics g, int x, int y) {
		g.setColor(Color.red);
		g.draw(new Polygon(new float[]{	0+x,0+y,
										(MapConstants.TILE_WIDTH/2)+x,(MapConstants.TILE_HEIGHT/2)+y,
										0+x,MapConstants.TILE_HEIGHT+y,
										-MapConstants.TILE_WIDTH/2+x,MapConstants.TILE_HEIGHT/2+y}));
		
		g.drawRect(x-3, y-3, 6, 6);
	}

}
