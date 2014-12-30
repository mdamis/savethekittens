package elements.item;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;

import elements.cat.Cat;

public class Bomb {
	public static final String USER_DATA = "Bomb";
	public static final int BIT_BOMB = 32;
	public static final float RADIUS = 1.0f;
	public static final float RANGE = 5.0f;
	public static float blastPower = 300;
	private final Body body;
	private final int seconds;
	private int turns = 0;
	private boolean hasExploded = false;

	private Bomb(Body body, int seconds) {
		this.body = body;
		this.seconds = seconds;
	}
	
	public static Bomb create(World world, float x, float y, int seconds) {
		BodyDef bodyDef = new BodyDef();
		bodyDef.position.set(x, y);
		bodyDef.type = BodyType.STATIC;
		Body body = world.createBody(bodyDef);
		createFixtures(body);
		Bomb bomb = new Bomb(body, seconds);
		body.setUserData(bomb);
		body.setActive(false);
		return bomb;
	}
	
	private static void createFixtures(Body body) {
		CircleShape shape = new CircleShape();
		shape.setRadius(RANGE);
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = shape;
		fixtureDef.filter.categoryBits = BIT_BOMB;
		fixtureDef.filter.maskBits = Cat.BIT_CAT;
		body.createFixture(fixtureDef).setUserData(USER_DATA);
	}
	
	public Body getBody() {
		return body;
	}
	
	public void explode(int gameSeconds) {
		if(gameSeconds >= seconds && !hasExploded) {
			turns++;
			if(turns == 1) {
				body.setActive(true);
			} else if(turns > 5) {
				body.setActive(false);
				hasExploded = true;
			}
		}
	}
	
	public boolean hasExploded() {
		return hasExploded;
	}
	
	public boolean isActive() {
		return body.isActive();
	}
	
	@Override
	public String toString() {
		return "Bomb : " + body.getPosition().x + " " + body.getPosition().y;
	}

	public float getBlastPower() {
		return blastPower;
	}
	
}
