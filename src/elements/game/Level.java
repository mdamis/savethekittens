package elements.game;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

import elements.Wall;

public class Level {
	public static final float WIDTH = 32.0f; // standard width of a Level
	public static final float HEIGHT = 32.0f; // standard height of a Level
	private final World world = new World(new Vec2()); // world with no gravity
	
	private static final int velocityIterations = 6;
	private static final int positionIterations = 2;
	
	public Level() {}
	
	public void update(float dt) {
		world.step(dt, velocityIterations, positionIterations);
	}

	public World getWorld() {
		return world;
	}
	
	public void createLevelBorders() {
		
		//Bottom border
		for(float x = Wall.WIDTH; x < WIDTH; x += (2 * Wall.WIDTH)) {
			Wall.createWall(world, x, Wall.HEIGHT);
		}
		//Top border
		for(float x = Wall.WIDTH; x < WIDTH; x += (2 * Wall.WIDTH)) {
			Wall.createWall(world, x, HEIGHT - Wall.HEIGHT);
		}
		//Left border
		for(float y = (3 * Wall.HEIGHT); y < (HEIGHT - Wall.HEIGHT); y += (2 * Wall.HEIGHT)) {
			Wall.createWall(world, Wall.WIDTH, y);
		}
		//Right border
		for(float y = (3 * Wall.HEIGHT); y < (HEIGHT - Wall.HEIGHT); y += (2 * Wall.HEIGHT)) {
			Wall.createWall(world, WIDTH - Wall.WIDTH, y);
		}
		
	}
	
}
