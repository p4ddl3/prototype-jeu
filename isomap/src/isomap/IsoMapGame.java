package isomap;

import java.awt.event.KeyEvent;

import isomap.rendering.systems.CameraSystem;
import isomap.rendering.systems.SpatialRenderSystem;
import isomap.rendering.systems.TerrainRenderSystem;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import com.artemis.World;

/***
 * Classe principale du jeu. Initialise le monde Artemis, charge la map et crée les systèmes.
 * @author Simon
 *
 */



public class IsoMapGame extends BasicGame{

	private World world;
	private CameraSystem cameraSystem;
	private SpatialRenderSystem spatialRenderSystem;
	private TerrainRenderSystem terrainRenderSystem;
	public IsoMapGame(){
		super("IsoMap");
	}



	@Override
	public void init(GameContainer container) throws SlickException{
		
		world = new World();
		ResourceManager.get().loadAllImages();
		
		
		//internal systems
		cameraSystem = world.setSystem(new CameraSystem(container));
		
		//permet de céer les entités liées à la map
		IsoMapBuilder.loadFromJSON(world, "resources/map1.json", cameraSystem);
		
		
		
		
		//render systems
		terrainRenderSystem = world.setSystem(new TerrainRenderSystem(cameraSystem, container), true);
		terrainRenderSystem.setShowGrid(true);
		
		spatialRenderSystem = world.setSystem(new SpatialRenderSystem(container, cameraSystem), true);
		
		world.initialize();

	}

	@Override
	public void update(GameContainer container, int delta) throws SlickException {
		world.setDelta(delta);
		world.process();
	}
	
	@Override
	public void render(GameContainer container, Graphics g) throws SlickException {
		terrainRenderSystem.process();
		spatialRenderSystem.process();
		g.setColor(Color.red);
		g.drawRoundRect(cameraSystem.getLookAtX()-cameraSystem.getStartX()-5, cameraSystem.getLookAtY()-cameraSystem.getStartY() -5,10,10, 0);
		
	}
	
	public void mouseClicked(int button, int x, int y, int clickCount) {
	}
	
	public void keyPressed(int key, char c){
		if(c == KeyEvent.VK_ESCAPE){
			System.exit(0);
		}
	}
	
	public static void main(String[] args) throws SlickException{
		IsoMapGame map = new IsoMapGame();
		AppGameContainer container = new AppGameContainer(map);
		container.setAlwaysRender(true);
		//container.setDisplayMode(1600,900,true);
		container.setDisplayMode(800, 600, false);
		container.start();
	}
}
