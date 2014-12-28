package elements.barrel;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.World;

import elements.cat.Cat;

public interface Barrel {
	public static final float FORCE = 20.0f;
	public static final Vec2 NORTH = new Vec2(0, FORCE);
	public static final Vec2 SOUTH = new Vec2(0, -FORCE);
	public static final Vec2 WEST = new Vec2(-FORCE, 0);
	public static final Vec2 EAST = new Vec2(FORCE, 0);
	public static final Vec2 NW = new Vec2(-FORCE, FORCE);
	public static final Vec2 NE = new Vec2(FORCE, FORCE);
	public static final Vec2 SW = new Vec2(-FORCE, -FORCE);
	public static final Vec2 SE = new Vec2(FORCE, -FORCE);
	
	public static final float WIDTH = 1.5f;
	public static final float HEIGHT = 1.5f;
	public static final int BIT_BARREL = 8;
	
	Body getBody();
	boolean isActive();
	Cat shootCat(World world);

}
