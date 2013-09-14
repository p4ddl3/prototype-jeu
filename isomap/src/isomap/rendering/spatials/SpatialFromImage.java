package isomap.rendering.spatials;

import isomap.rendering.components.Position2D;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import com.artemis.Entity;
import com.artemis.World;

public class SpatialFromImage extends Spatial{

	private Position2D position;
	private Image img;
	private String pathFile;
	public SpatialFromImage (World world, Entity owner, String imageFile) {
		super(world, owner);
		pathFile = imageFile;
	}

	@Override
	public void initialize() {
		position = owner.getComponent(Position2D.class);
		try{
			img = new Image(pathFile);
		}catch(SlickException se){
			se.printStackTrace();
		}
	}

	@Override
	public void render(Graphics g, int offsetx, int offsety) {
		img.draw(position.x+offsetx, position.y+offsety);
	}

}
