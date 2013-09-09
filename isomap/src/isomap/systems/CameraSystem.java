package isomap.systems;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;

import com.artemis.Aspect;
import com.artemis.Entity;
import com.artemis.systems.IntervalEntitySystem;
import com.artemis.utils.ImmutableBag;

public class CameraSystem extends IntervalEntitySystem{

	private int lookAtX;
	private int lookAtY;
	private GameContainer container;
	private Input input;
	
	
	boolean leftScrolling;
	boolean rightScrolling;
	
	boolean topScrolling;
	boolean bottomScrolling;
	
	private static int SCROLLING_SPEED = 5;
	private static int SCROLLING_BOX_WIDTH = 30;
	
	int width;
	int height;
	public CameraSystem(GameContainer container) {
		super(Aspect.getEmpty(), 20);
		this.container = container;
		this.input = container.getInput();
		lookAtX = container.getWidth()/2;
		lookAtY = container.getHeight()/2;
		
		width = container.getWidth();
		height = container.getHeight();
	}
	
	public void initialize(){
		
	}
	@Override
	protected void processEntities(ImmutableBag<Entity> entities) {
		int x= input.getMouseX();
		int y=  input.getMouseY();
		
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
		
		updateLookAt();
		
	}
	private void updateLookAt(){
		if(leftScrolling){
			lookAtX += SCROLLING_SPEED;
		}else if(rightScrolling){
			lookAtX -= SCROLLING_SPEED;
		}
		
		if(topScrolling){
			lookAtY += SCROLLING_SPEED;
		}else if(bottomScrolling){
			lookAtY -= SCROLLING_SPEED;
		}
	}
	public int getLookAtX(){
		return lookAtX;
	}
	public int getLookAtY(){
		return lookAtY;
	}
	public int getStartX(){
		return lookAtX - width/2;
	}
	public int getStartY(){
		return lookAtY - height/2;
	}
	public int getEndX(){
		return lookAtX + width/2;
	}
	public int getEndY(){
		return lookAtY + height/2;
	}
	

}
