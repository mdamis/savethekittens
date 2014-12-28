package elements.game;

import elements.Net;
import elements.Wall;
import elements.barrel.Barrel;
import elements.barrel.SingleBarrel;
import elements.cat.BasicCat;
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
	
	private Level(ApplicationContext context, float width, float height) {
		this.context = context;
		this.gameUI = GameUI.createGameUI(width, height);
	}
	
	public static Level createLevel(ApplicationContext context, float width, float height) {
		Level level = new Level(context, width, height);
		level.setWorldCollisions();
		level.createLevelBorders();
		level.createLevelBasicCat(25.0f, 25.0f);
		level.createLevelBasicCat(12.0f, 32.0f);
		level.createLevelSingleBarrel(25.0f, 25.0f, "NORTH");
		level.createLevelNet(23.0f, 19.0f);
		return level;
	}
	
	private void setWorldCollisions() {
		world.setContactListener(new Collisions());
	}
	
	public void update() {
		world.step(timeStep, velocityIterations, positionIterations);
		world.clearForces();
		this.gameUI.render(context, cats, walls, nets, barrels);
	}

	public World getWorld() {
		return world;
	}
	
	public ArrayList<Wall> getWalls() {
		return walls;
	}
	
	public ArrayList<Cat> getCats() {
		return cats;
	}
	
	private void createLevelBasicCat(float x, float y) {
		cats.add(BasicCat.createBasicCat(world, x, y));
	}
	
	private void createLevelNet(float x, float y) {
		nets.add(Net.createNet(world, x, y));
	}
	
	private void createLevelSingleBarrel(float x, float y, String angleString) {
		barrels.add(SingleBarrel.create(world, x, y, angleString));
	}
	
	private void createLevelBorders() {
		
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
