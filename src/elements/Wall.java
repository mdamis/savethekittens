package elements;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;

public class Wall {
	public static final float WIDTH = 1.0f; // standard width of a Wall
	public static final float HEIGHT = 1.0f; // standard height of a Wall
	private final Body body;
	
	private Wall(Body body) {
		this.body = body;
	}
	
	public static Wall createWall(World world, float x, float y) {
		BodyDef bodyDef = new BodyDef();
		bodyDef.position.set(x, y);
		bodyDef.type = BodyType.STATIC;
		Body body = world.createBody(bodyDef);
		createWallFixtures(body);
		Wall wall = new Wall(body);
		return wall;
	}
	
	private static void createWallFixtures(Body body) {
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(WIDTH, HEIGHT);
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = shape;
		body.createFixture(fixtureDef);
	}
	
	public Body getBody() {
		return body;
	}
	
	@Override
	public String toString() {
		return body.getPosition().x + " " + body.getPosition().toString();
	}
	
}
