package isomap.rendering.spatials;

import isomap.ResourceManager;
import isomap.rendering.components.Position2D;
import isomap.rendering.data.MapConstants;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import com.artemis.Entity;
import com.artemis.World;

public class SpatialFromImage extends Spatial{

	private Image img;
	private String imageName;
	private int imgOffsetX;
	private int imgOffsetY;
	public SpatialFromImage (World world, Entity owner, String imageName) {
		super(world, owner);
		this.imageName = imageName;
	}

	@Override
	public void initialize() {
		img = ResourceManager.get().getImage(imageName);
		imgOffsetX = ((img.getWidth()/MapConstants.TILE_WIDTH)-1)*MapConstants.TILE_WIDTH;
		imgOffsetY = ((img.getHeight()/MapConstants.TILE_HEIGHT)-1)*MapConstants.TILE_HEIGHT;
	}

	@Override
	public void render(Graphics g, int x, int y) {
		img.draw(x-MapConstants.TILE_WIDTH/2-imgOffsetX,y-imgOffsetY);
		g.setColor(Color.red);
		//DEBUG
		//g.drawRect(x-3, y-3, 6, 6);
	}

}
