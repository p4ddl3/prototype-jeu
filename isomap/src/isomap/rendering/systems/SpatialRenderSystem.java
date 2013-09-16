package isomap.rendering.systems;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;

import isomap.rendering.components.Position2D;
import isomap.rendering.components.SpatialForm;
import isomap.rendering.data.MapConstants;
import isomap.rendering.spatials.RedTile;
import isomap.rendering.spatials.Spatial;
import isomap.rendering.spatials.SpatialFromImage;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.systems.EntityProcessingSystem;

public class SpatialRenderSystem extends EntityProcessingSystem{

	private ComponentMapper<SpatialForm> 	spatialFormMapper;
	private ComponentMapper<Position2D> 	positionMapper;
	
	private CameraSystem camera;
	private Graphics g;
	
	@SuppressWarnings("unchecked")
	public SpatialRenderSystem(GameContainer container, CameraSystem camera) {
		super(Aspect.getAspectForAll(SpatialForm.class, Position2D.class));
		g = container.getGraphics();
		this.camera = camera;
	}

	public void initialize(){
		spatialFormMapper = world.getMapper(SpatialForm.class);
		positionMapper = world.getMapper(Position2D.class);
	}
	
	@Override
	protected void process(Entity e) {
		Position2D position = positionMapper.get(e);
		SpatialForm form = spatialFormMapper.get(e);
		Spatial spatial = getSpatial(form, e);
		Vector2f vector = camera.tile2screen(position.x, position.y);
		spatial.render(g, ((int)vector.x)-camera.getStartX(), ((int)vector.y)-camera.getStartY());
	}
	private Spatial getSpatial(SpatialForm form, Entity e){
		Spatial spa = null;
		switch(form.getForm()){
		case "locked-safe":
			spa = new SpatialFromImage(world, e, form.getForm());
			//spa = new RedTile(world, e);
			spa.initialize();
			break;
		}
		return spa;
	}

}
