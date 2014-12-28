package elements;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;

import elements.cat.Cat;

public class Net {
	public static final String USER_DATA = "Net";
	public static final int BIT_NET = 4;
	public static final float WIDTH = 1.5f; // standard width of a Net
	public static final float HEIGHT = 1.5f; // standard height of a Net
	private final Body body;
	
	private Net(Body body) {
		this.body = body;
	}
	
	public static Net createNet(World world, float x, float y) {
		BodyDef bodyDef = new BodyDef();
		bodyDef.position.set(x, y);
		bodyDef.type = BodyType.STATIC;
		Body body = world.createBody(bodyDef);
		createNetFixtures(body);
		Net net = new Net(body);
		return net;
	}
	
	private static void createNetFixtures(Body body) {
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(WIDTH, HEIGHT);
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = shape;
		fixtureDef.filter.categoryBits = BIT_NET;
		fixtureDef.filter.maskBits = Cat.BIT_CAT;
		body.createFixture(fixtureDef).setUserData(USER_DATA);
	}
	
	public Body getBody() {
		return body;
	}
	
	@Override
	public String toString() {
		return "Net : " + body.getPosition().x + " " + body.getPosition().toString();
	}
	
}
