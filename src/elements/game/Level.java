package elements.game;

import elements.Net;
import elements.Wall;
import elements.barrel.AutomaticBarrel;
import elements.barrel.Barrel;
import elements.barrel.SingleBarrel;
import elements.cat.Cat;
import fr.umlv.zen4.ApplicationContext;
import gui.GameUI;
import handlers.Collisions;

import java.util.ArrayList;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

public class Level {
	public static final float WIDTH = 50.0f; // standard width of a Level
	public static final float HEIGHT = 50.0f; // standard height of a Level
	
	private final World world = new World(new Vec2()); // world with no gravity
	private final GameUI gameUI;
	private final ApplicationContext context;
	private final ArrayList<Wall> walls = new ArrayList<>();
	private final ArrayList<Cat> cats = new ArrayList<>();
	private final ArrayList<Net> nets = new ArrayList<>();
	private final ArrayList<Barrel> barrels = new ArrayList<>();
	
	private static final int velocityIterations = 6;
	private static final int positionIterations = 2;
	private static final float timeStep = 1.0f / 60.0f;
	private int nbIterations = 0;
	
	private Level(ApplicationContext context, float width, float height) {
		this.context = context;
		this.gameUI = GameUI.createGameUI(width, height);
	}
	
	public static Level createLevel(ApplicationContext context, float width, float height) {
		Level level = new Level(context, width, height);
		level.setWorldCollisions();
		return level;
	}
	
	private void setWorldCollisions() {
		world.setContactListener(new Collisions());
	}
	
	public void update() {
		for(;;) {
			world.step(timeStep, velocityIterations, positionIterations);
			world.clearForces();
			this.gameUI.render(context, cats, walls, nets, barrels);
			
			if(nbIterations % 25 == 0) {
				for(Barrel barrel : barrels) {
					if(barrel.isActive()) {
						barrel.shootCat();
					}
				}
			}
			
			for(Cat cat : cats) {
				if(!cat.isAlive()) {
					return;
				}
			}
			nbIterations++;
		}
	}
	
	public void createLevelNet(float x, float y) {
		nets.add(Net.createNet(world, x, y));
	}
	
	public SingleBarrel createLevelSingleBarrel(float x, float y, String angleString) {
		SingleBarrel barrel = SingleBarrel.create(world, x, y, angleString);
		barrels.add(barrel);
		return barrel;
	}
	
	public AutomaticBarrel createLevelAutomaticBarrel(float x, float y, String angleString) {
		AutomaticBarrel barrel = AutomaticBarrel.create(world, x, y, angleString);
		barrels.add(barrel);
		return barrel;
	}
	
	public void addCatBarrel(Barrel barrel, String catType) {
		cats.add(barrel.addCat(world, catType));
	}
	
	public void createLevelWall(float x, float y) {
		walls.add(Wall.createWall(world, x, y));
	}
	
	public void createLevelBorders() {
		
		//Bottom border
		for(float x = Wall.WIDTH; x < WIDTH; x += (2 * Wall.WIDTH)) {
			walls.add(Wall.createWall(world, x, Wall.HEIGHT));
		}
		//Top border
		for(float x = Wall.WIDTH; x < WIDTH; x += (2 * Wall.WIDTH)) {
			walls.add(Wall.createWall(world, x, HEIGHT - Wall.HEIGHT));
		}
		//Left border
		for(float y = (3 * Wall.HEIGHT); y < (HEIGHT - Wall.HEIGHT); y += (2 * Wall.HEIGHT)) {
			walls.add(Wall.createWall(world, Wall.WIDTH, y));
		}
		//Right border
		for(float y = (3 * Wall.HEIGHT); y < (HEIGHT - Wall.HEIGHT); y += (2 * Wall.HEIGHT)) {
			walls.add(Wall.createWall(world, WIDTH - Wall.WIDTH, y));
		}
		
	}
	
}
