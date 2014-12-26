package elements.game;

import java.util.ArrayList;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

import elements.Wall;

public class Level {
	public static final float WIDTH = 50.0f; // standard width of a Level
	public static final float HEIGHT = 50.0f; // standard height of a Level
	private final World world = new World(new Vec2()); // world with no gravity
	private final ArrayList<Wall> walls = new ArrayList<>();
	
	private static final int velocityIterations = 6;
	private static final int positionIterations = 2;
	
	public Level() {}
	
	public void update(float dt) {
		world.step(dt, velocityIterations, positionIterations);
	}

	public World getWorld() {
		return world;
	}
	
	public ArrayList<Wall> getWalls() {
		return walls;
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
