package elements;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;

public class Wall {
	private static final float stantardWidth = 1.0f;
	private static final float standardHeight = 1.0f;
	
	private Wall() {}
	
	public static void createStandardWall(World world, float x, float y) {
		BodyDef bodyDef = new BodyDef();
		bodyDef.position.set(x, y);
		bodyDef.type = BodyType.STATIC;
		Body body = world.createBody(bodyDef);
		createStandardWallFixtures(body);
	}
	
	private static void createStandardWallFixtures(Body body) {
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(stantardWidth, standardHeight);
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = shape;
		body.createFixture(fixtureDef);
	}
	
}
