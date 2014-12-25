package elements;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;

public class Wall {
	private static final float standardWidth = 1.0f;
	private static final float standardHeight = 1.0f;
	private final Body body;
	
	private Wall(Body body) {
		this.body = body;
	}
	
	public static Wall createStandardWall(World world, float x, float y) {
		BodyDef bodyDef = new BodyDef();
		bodyDef.position.set(x, y);
		bodyDef.type = BodyType.STATIC;
		Body body = world.createBody(bodyDef);
		createStandardWallFixtures(body);
		Wall wall = new Wall(body);
		return wall;
	}
	
	private static void createStandardWallFixtures(Body body) {
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(standardWidth, standardHeight);
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
