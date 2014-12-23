package elements.game;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

public class Level {
	private final int width;
	private final int height;
	private final World world = new World(new Vec2()); // world with no gravity
	
	private static final int velocityIterations = 6;
	private static final int positionIterations = 2;
	
	public Level(int width, int height) {
		this.width = width;
		this.height = height;
	}
	
	public void update(float dt) {
		world.step(dt, velocityIterations, positionIterations);
	}

	public World getWorld() {
		return world;
	}
	
}
