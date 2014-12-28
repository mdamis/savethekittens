package elements.cat;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;

import elements.Net;
import elements.Wall;

public class BasicCat implements Cat {
	public static final String USER_DATA = "BasicCat";
	public static final float RADIUS = 1.0f;
	private final Body body;
	private int nbLives = 1;
	
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
		body.setUserData(basicCat);
		return basicCat;
	}
	
	private static void createBasicCatFixtures(Body body) {
		CircleShape shape = new CircleShape();
		shape.setRadius(RADIUS);
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = shape;
		fixtureDef.restitution = 0.5f;
		fixtureDef.filter.categoryBits = BIT_CAT;
		fixtureDef.filter.maskBits = Wall.BIT_WALL | Net.BIT_NET;
		body.createFixture(fixtureDef).setUserData(USER_DATA);
	}
	
	private void kill() {
		this.nbLives = 0;
		System.out.println("A cat just died.\nGame Over");
	}
	
	@Override
	public boolean isActive() {
		return body.isActive();
	}
	
	@Override
	public Body getBody() {
		return body;
	}
	
	@Override
	public boolean isAlive() {
		return nbLives != 0;
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
		return "BasicCat : " + body.getPosition().x + " " + body.getPosition().y;
	}

	@Override
	public void contactWithWall() {
		this.kill();
	}
	
	@Override
	public void contactWithNet(Net net) {
		if(net.isFull()) {
			this.kill();
		}
		this.stop();
		this.body.setActive(false);
		net.setFull();
	}
	
}
