package elements.game;

import java.util.List;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

import elements.cat.Cat;
import elements.item.Item;

public class Level {
	private final int width;
	private final int height;
	private final List<Cat> cats;
	private final List<Item> items;
	private final World world = new World(new Vec2()); // world with no gravity
	
	private final static int velocityIterations = 6;
	private final static int positionIterations = 2;
	
	public Level(int width, int height, List<Cat> cats, List<Item> items) {
		this.width = width;
		this.height = height;
		this.cats = cats;
		this.items = items;
	}
	
	public void update(float dt) {
		world.step(dt, velocityIterations, positionIterations);
	}
	
}
