package isomap.rendering.systems;

import isomap.ResourceManager;
import isomap.rendering.components.TerrainLayer;
import isomap.rendering.data.MapConstants;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Vector2f;

import com.artemis.Aspect;
import com.artemis.Entity;
import com.artemis.EntitySystem;
import com.artemis.utils.ImmutableBag;


/**
 * Se charge du rendu des calques de terrains (à appeler en premier dans le render de Slick)
 * @author Simon
 *
 */
public class TerrainRenderSystem extends EntitySystem{
	
	private Graphics g;
	private CameraSystem camera;
	
	private boolean showGrid;
	private List<TerrainLayer> layers;
	
	/**
	 * Représente la distance d'affichage des tiles par rapport au centre d'affichage. Chez moi 28 tiles permet de recouvrir tout
	 * l'écran, même en full-screen. (en gros, permet d'afficher un carré de (28*2)*(28*2) (~= 2500 tiles) tile maximum plutôt que la map entière)
	 */
	private static int TILE_DISPLAYING_DISTANCE = 28;
	
	private int startX;
	private int endX;
	
	private int startY;
	private int endY;
	

	@SuppressWarnings("unchecked")
	public TerrainRenderSystem(CameraSystem camera, GameContainer container) {
		super(Aspect.getAspectForAll(TerrainLayer.class));
		this.camera = camera;
		this.g = container.getGraphics();
		showGrid = false;
		layers = new ArrayList<TerrainLayer>();
	}

	/**
	 * Affichage de la grille au niveau du terrain.
	 * @param show
	 */
	public void setShowGrid(boolean show){
		showGrid = show;
	}
	public boolean isShowGrid(){
		return showGrid;
	}
	
	/**
	 * Les claques sont gérés dans une liste, pour garder leur ordre d'arrivée (de bas en haut pour le rendu)
	 */
	protected void inserted(Entity e){
		layers.add(e.getComponent(TerrainLayer.class));
		System.out.println("terrain added");
	}
	
	/**
	 * Appelé via terrainRenderSystem.process()
	 */
	@Override
	protected void processEntities(ImmutableBag<Entity> entities) {
		//on récupère les coordonnées du tile central
		Vector2f vector = camera.screen2tile(camera.getLookAtX()+MapConstants.TILE_WIDTH/2, camera.getLookAtY());
		int x = (int)vector.x;
		int y= (int)vector.y;
		
		//puis on calcule les dimensions du carré de tiles à afficher
		startX = x - TILE_DISPLAYING_DISTANCE;
		endX = x + TILE_DISPLAYING_DISTANCE;
		
		startY = y - TILE_DISPLAYING_DISTANCE;
		endY = y + TILE_DISPLAYING_DISTANCE;
		
		//on dessine les calques
		for(TerrainLayer layer : layers){
			render(-camera.getStartX(), -camera.getStartY(), layer);
		}
		//enfin, éventuellement la grille
		if(showGrid){
			drawGrid(-camera.getStartX(), -camera.getStartY());
		}
	}
	
	public void removed(Entity e){
		layers.remove(e.getComponent(TerrainLayer.class));
	}
	/**
	 * Affichage du claque tl
	 */
	public void render(int offsetx, int offsety, TerrainLayer tl){
		Image img;
		int mw = MapConstants.MAP_WIDTH;
		int mh = MapConstants.MAP_HEIGHT;
		
		int tw = MapConstants.TILE_WIDTH;
		int th = MapConstants.TILE_HEIGHT;
		
		int first_x= (startX<0)?0:startX;
		int first_y = (startY<0)?0:startY;
		
		int end_x = (endX>mw)?mw:endX;
		int end_y = (endY>mh)?mh:endY;
		for(int x = first_x; x < end_x; x++){
			for(int y = first_y; y < end_y ; y++){
				img = ResourceManager.get().getImageByGid(tl.get(x,y));
				if(img != null){
					float fx = x*tw/2 - y*tw/2;
					float fy = y*th/2 + x*th/2;
					img.draw(fx+offsetx-tw/2, fy+offsety);
				}
			}
		}
	}
	public void drawGrid(int offsetx, int offsety){
		g.setColor(Color.white);
		
		int mw = MapConstants.MAP_WIDTH;
		int mh = MapConstants.MAP_HEIGHT;
		
		int tw = MapConstants.TILE_WIDTH;
		int th = MapConstants.TILE_HEIGHT;
		
		
		for(int y = 0; y < mh; y++){
			int x1 = tw/2*-y+offsetx;
			int y1 = th/2*y+offsety;
			
			int x2 = (tw/2)*mw+offsetx + tw/2*-y;
			int y2 = (th/2)*mh+offsety + th/2*y;
			g.drawLine(x1, y1, x2, y2);
			g.drawLine(offsetx*2-x1, y1, offsetx*2-x2, y2);
		}
	}

	@Override
	protected boolean checkProcessing() {
		return true;
	}

}
