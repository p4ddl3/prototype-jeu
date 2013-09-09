package isomap;

import isomap.systems.CameraSystem;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

import com.artemis.World;


public class IsoMapGame extends BasicGame{

	private IsoMap map;
	private World world;
	private CameraSystem cameraSystem;
	public IsoMapGame(){
		super("IsoMap");
	}



	@Override
	public void init(GameContainer container) throws SlickException{
		
		world = new World();
		cameraSystem = world.setSystem(new CameraSystem(container));
		map = IsoMap.load("resources/big-map.json",container, cameraSystem);
		map.setShowGrid(true);
		world.initialize();

	}

	@Override
	public void update(GameContainer container, int delta) throws SlickException {
		world.setDelta(delta);
		world.process();
	}
	
	@Override
	public void render(GameContainer container, Graphics g) throws SlickException {
		map.render(cameraSystem.getStartX(), cameraSystem.getStartY());
	}
	
	public void mouseClicked(int button, int x, int y, int clickCount) {
		Vector2f v = map.screen2tile(x-cameraSystem.getStartX(), y-cameraSystem.getStartY());
		System.out.println(v);
	}
	
	public static void main(String[] args) throws SlickException{
		IsoMapGame map = new IsoMapGame();
		AppGameContainer container = new AppGameContainer(map);
		container.setAlwaysRender(true);
		container.setDisplayMode(800, 600, false);
		container.start();
	}
}
