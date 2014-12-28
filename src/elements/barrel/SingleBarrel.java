package elements.barrel;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;

import elements.cat.Cat;

public class SingleBarrel implements Barrel {
	public static final String USER_DATA = "SingleBarrel";
	private final Body body;
	private final Vec2 angle;
	
	private SingleBarrel(Body body, Vec2 angle) {
		this.body = body;
		this.angle = angle;
	}
	
	public static SingleBarrel create(World world, float x, float y, String angleString) {
		BodyDef bodyDef = new BodyDef();
		bodyDef.position.set(x, y);
		bodyDef.type = BodyType.STATIC;
		Body body = world.createBody(bodyDef);
		createFixtures(body);
		SingleBarrel singleBarrel = new SingleBarrel(body, parseAngle(angleString));
		return singleBarrel;
	}

	private static Vec2 parseAngle(String angleString) {
		switch(angleString) {
		case "NORTH":
			return NORTH;
		case "SOUTH":
			return SOUTH;
		case "WEST":
			return WEST;
		case "EAST":
			return EAST;
		case "NW":
			return NW;
		case "NE":
			return NE;
		case "SW":
			return SW;
		case "SE":
			return SE;
		default:
			throw new IllegalArgumentException("No angle corresponding to angleString");
		}
	}

	private static void createFixtures(Body body) {
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(WIDTH, HEIGHT);
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = shape;
		fixtureDef.filter.categoryBits = BIT_BARREL;
		body.createFixture(fixtureDef).setUserData(USER_DATA);;
	}
	
	@Override
	public Body getBody() {
		return body;
	}
	
	@Override
	public String toString() {
		return "SingleBarrel : " + body.getPosition().x + " " + body.getPosition().y;
	}
}
