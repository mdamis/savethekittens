package elements.cat;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;

public class BasicCat implements Cat {
	public static final float RADIUS = 1.0f;
	private final Body body;
	
	private BasicCat(Body body) {
		this.body = body;
	}

	public static BasicCat createBasicCat(World world, float x, float y) {
		BodyDef bodyDef = new BodyDef();
		bodyDef.position.set(x, y);
		bodyDef.type = BodyType.DYNAMIC;
		Body body = world.createBody(bodyDef);
		createBasicCatFixtures(body);
		BasicCat basicCat = new BasicCat(body);
		return basicCat;
	}
	
	private static void createBasicCatFixtures(Body body) {
		CircleShape shape = new CircleShape();
		shape.setRadius(RADIUS);
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = shape;
		body.createFixture(fixtureDef);
	}
	
	@Override
	public Body getBody() {
		return body;
	}
	
	@Override
	public void move(Vec2 v) {
		body.setLinearVelocity(v);
	}
	
	@Override
	public void stop() {
		body.setLinearVelocity(new Vec2(0, 0));
	}
	
	@Override
	public String toString() {
		return "BasicCat : " + body.getPosition().x + " " + body.getPosition().toString();
	}
	
}
