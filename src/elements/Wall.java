package elements;

import java.util.Objects;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;

import elements.cat.Cat;

public class Wall {
	public static final String USER_DATA = "Wall";
	public static final int BIT_WALL = 2;
	public static final float WIDTH = 1.0f; // standard width of a Wall
	public static final float HEIGHT = 1.0f; // standard height of a Wall
	private final Body body;
	
	private Wall(Body body) {
		this.body = Objects.requireNonNull(body);
	}
	
	/**
	 * Creates a Wall.
	 * @param world jbox2d world in which the Wall exists.
	 * @param x x coordinate of the Wall.
	 * @param y y coordinate of the Wall.
	 * @return the new Wall.
	 */
	public static Wall createWall(World world, float x, float y) {
		BodyDef bodyDef = new BodyDef();
		bodyDef.position.set(x, y);
		bodyDef.type = BodyType.STATIC;
		Body body = world.createBody(bodyDef);
		createWallFixtures(body);
		Wall wall = new Wall(body);
		body.setUserData(wall);
		return wall;
	}
	
	private static void createWallFixtures(Body body) {
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(WIDTH, HEIGHT);
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = shape;
		fixtureDef.filter.categoryBits = BIT_WALL;
		fixtureDef.filter.maskBits = Cat.BIT_CAT;
		body.createFixture(fixtureDef).setUserData(USER_DATA);
	}
	
	/**
	 * Returns body.
	 * @return the body of a Wall.
	 */
	public Body getBody() {
		return body;
	}
	
	@Override
	public String toString() {
		return "Wall : " + body.getPosition().x + " " + body.getPosition().y;
	}
	
}
