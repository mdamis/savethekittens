package elements.barrel;

import java.util.ArrayList;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;

import elements.cat.Cat;

public class AutomaticBarrel extends AbstractBarrel {
	private final ArrayList<Cat> cats = new ArrayList<>();
	
	private AutomaticBarrel(Body body, Vec2 angle) {
		super(body, angle);
	}
	
	public static AutomaticBarrel create(World world, float x, float y, String angleString) {
		BodyDef bodyDef = new BodyDef();
		bodyDef.position.set(x, y);
		bodyDef.type = BodyType.STATIC;
		Body body = world.createBody(bodyDef);
		createFixtures(body);
		AutomaticBarrel automaticBarrel = new AutomaticBarrel(body, parseAngle(angleString));
		return automaticBarrel;
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
		cats.add(cat);
	}
	
	@Override
	public void shootCat() {
		cats.remove(0).move(getAngle());
		if(cats.size() <= 0) {
			setInactive();
		}
	}
	
	@Override
	public String toString() {
		return "AutomaticBarrel : " + super.toString();
	}

}
