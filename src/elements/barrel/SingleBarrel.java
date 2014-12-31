package elements.barrel;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;

import elements.cat.Cat;

public class SingleBarrel extends AbstractBarrel {
	private Cat cat;
	
	private SingleBarrel(Body body, Vec2 angle) {
		super(body, angle);
	}
	
	/**
	 * Creates a SingleBarrel.
	 * @param world jbox2d world in which the SingleBarrel exists.
	 * @param x x coordinate of the SingleBarrel.
	 * @param y y coordinate of the SingleBarrel.
	 * @param angleString string corresponding to a valid angle.
	 * @return the new SingleBarrel.
	 */
	public static SingleBarrel create(World world, float x, float y, String angleString) {
		BodyDef bodyDef = new BodyDef();
		bodyDef.position.set(x, y);
		bodyDef.type = BodyType.STATIC;
		Body body = world.createBody(bodyDef);
		createFixtures(body);
		SingleBarrel singleBarrel = new SingleBarrel(body, parseAngle(angleString));
		return singleBarrel;
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
	void addCat(Cat cat) {
		this.cat = cat;
	}

	@Override
	public void shootCat() {
		cat.move(getAngle());
		setInactive();
	}

	@Override
	public String toString() {
		return "SingleBarrel : " + super.toString();
	}
	
}
