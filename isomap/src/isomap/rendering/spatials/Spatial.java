package isomap.rendering.spatials;

import org.newdawn.slick.Graphics;

import com.artemis.Entity;
import com.artemis.World;

public abstract class Spatial {

	protected Entity owner;
	protected World world;
	public Spatial(World world, Entity owner) {
		this.owner = owner;
		this.world = world;
	}
	public abstract void initialize();
	public abstract void render(Graphics g, int offsetx, int offsety);

}
