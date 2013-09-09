package isomap;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;


public class IsoMapGame extends BasicGame{

	private IsoMap map;
	public IsoMapGame(){
		super("IsoMap");
	}



	@Override
	public void init(GameContainer container) throws SlickException {
		map = IsoMap.load("resources/map1.json");

	}

	@Override
	public void update(GameContainer container, int delta) throws SlickException {
	}
	
	@Override
	public void render(GameContainer container, Graphics g) throws SlickException {
		//map.render(400, 200);
		map.render(400, 200,0);
		g.setColor(Color.blue);
		g.fillRect(500, 300, 100, 30);
		map.render(400, 200,1);
		//ResourceManager.get().getImageByGid(21).draw(50,50);
	}
	
	
	public static void main(String[] args) throws SlickException{
		IsoMapGame map = new IsoMapGame();
		AppGameContainer container = new AppGameContainer(map);
		container.setAlwaysRender(true);
		container.setDisplayMode(800, 600, false);
		container.start();
	}
}
