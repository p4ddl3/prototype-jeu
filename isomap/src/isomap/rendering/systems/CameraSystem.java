package isomap.rendering.systems;

import isomap.rendering.data.MapConstants;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Vector2f;

import com.artemis.Aspect;
import com.artemis.Entity;
import com.artemis.systems.IntervalEntitySystem;
import com.artemis.utils.ImmutableBag;

public class CameraSystem extends IntervalEntitySystem{

	private int lookAtX;
	private int lookAtY;
	private Input input;
	
	
	boolean leftScrolling;
	boolean rightScrolling;
	
	boolean topScrolling;
	boolean bottomScrolling;
	
	private static int SCROLLING_SPEED = 1;
	private static int SCROLLING_BOX_WIDTH = 30;
	
	private int width;
	private int height;
	
	private boolean activeX;
	private boolean activeY;
	
	private Vector2f lookAtTilePosition;
	public CameraSystem(GameContainer container) {
		super(Aspect.getEmpty(), 100);
		this.input = container.getInput();
		lookAtX = 0;
		lookAtY = 0;
		
		width = container.getWidth();
		height = container.getHeight();
		

		lookAtTilePosition = screen2tile(lookAtX+MapConstants.TILE_WIDTH/2, lookAtY);
		activeY = true;
		activeX = true;
	}
	
	public void initialize(){
		
	}
	/**
	 * Rafraichissement de l'input
	 */
	@Override
	protected void processEntities(ImmutableBag<Entity> entities) {
		if(activeX){
			int x= input.getMouseX();
			if(x <= SCROLLING_BOX_WIDTH){
				rightScrolling = false;
				leftScrolling = true;
			}else if(x >= width-SCROLLING_BOX_WIDTH){
				rightScrolling = true;
				leftScrolling = false;
			}else{
				rightScrolling = false;
				leftScrolling = false;
			}
		}
		if(activeY){
			int y=  input.getMouseY();
			if(y <= SCROLLING_BOX_WIDTH){
				bottomScrolling = false;
				topScrolling = true;
			}else if(y >= height-SCROLLING_BOX_WIDTH){
				bottomScrolling = true;
				topScrolling = false;
			}else{
				bottomScrolling = false;
				topScrolling = false;
			}
		}
		updateLookAt();
		fixBoundaries();
		
	}
	/**
	 *Mise à jour de la position de la caméra (les lookAt) en fonction de l'input (case par case)
	 */
	private void updateLookAt(){
		if(activeX){
			if(leftScrolling){
				lookAtX -= SCROLLING_SPEED*MapConstants.TILE_WIDTH;
			}else if(rightScrolling){
				lookAtX += SCROLLING_SPEED*MapConstants.TILE_WIDTH;
			}
		}
		if(activeY){
			if(topScrolling){
				lookAtY -= SCROLLING_SPEED*MapConstants.TILE_HEIGHT;
			}else if(bottomScrolling){
				lookAtY += SCROLLING_SPEED*MapConstants.TILE_HEIGHT;
			}
		}
	}
	/**
	 * Apres le calcul de la nouvelle position, on vérifie que le centre de l'écran et toujours dans la map
	 */
	private void fixBoundaries(){
		lookAtTilePosition = screen2tile(lookAtX+MapConstants.TILE_WIDTH/2, lookAtY);
		if(lookAtTilePosition.x < 0){
			lookAtTilePosition.x = 0;
		}
		else if(lookAtTilePosition.x > MapConstants.MAP_WIDTH-1){
			lookAtTilePosition.x = MapConstants.MAP_WIDTH-1;
		}
		
		if(lookAtTilePosition.y < 0){
			lookAtTilePosition.y = 0;
		}
		else if(lookAtTilePosition.y > MapConstants.MAP_HEIGHT-1){
			lookAtTilePosition.y = MapConstants.MAP_HEIGHT-1;
		}
		
		lookAtTilePosition = tile2screen((int)lookAtTilePosition.x, (int)lookAtTilePosition.y);
		lookAtX = (int)lookAtTilePosition.x;
		lookAtY = (int)lookAtTilePosition.y;
		
	}
	public int getLookAtX(){
		return lookAtX;
	}
	public int getLookAtY(){
		return lookAtY;
	}
	public int getStartX(){
		return getLookAtX() - width/2;
	}
	public int getStartY(){
		return getLookAtY() - height/2;
	}
	public int getEndX(){
		return getLookAtX() + width/2;
	}
	public int getEndY(){
		return getLookAtY() + height/2;
	}
	public boolean isActiveX(){
		return activeX;
	}
	public boolean isActiveY(){
		return activeY;
	}
	public Vector2f getLookAtTilePosition(){
		return lookAtTilePosition;
	}
	/**
	 * Permet de faire la correspondance entre un vecteur (x,y) de l'input et les coordonnées a,b du tile qui 'recoit' le clic
	 * @param mouseX
	 * @param mouseY
	 * @return
	 */
	public Vector2f screen2tile(float mouseX, float mouseY){
		float w = MapConstants.TILE_WIDTH/2;
		float h = MapConstants.TILE_HEIGHT/2;
		
		mouseX += w;
		float x = (mouseX*h + mouseY*w)/(2*w*h);
		Vector2f v = new Vector2f();
		v.x = (float)Math.floor(x)-1;
		
		float y = (mouseX*-h + mouseY*w)/(2*w*h); 
		v.y = (float)Math.floor(y)+1;
		return v;
	}
	
	/**
	 * Inverse de screen2tile. Retourne les coordonnées (x,y) du coin nord du tile (a,b)
	 * @param a
	 * @param b
	 * @return
	 */
	public Vector2f tile2screen(int a, int b){
		Vector2f v = new Vector2f();
		
		float w = MapConstants.TILE_WIDTH/2;
		float h = MapConstants.TILE_HEIGHT/2;
		
		float x = a*w + b*-w;
		float y = a*h + b*h;
		v.x = x;
		v.y = y;
		return v;
	}
}
