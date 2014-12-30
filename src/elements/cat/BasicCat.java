package elements.cat;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;

import elements.Net;
import elements.Wall;
import elements.item.Bomb;

public class BasicCat extends AbstractCat {
	public static final int NB_LIVES = 1;
	
	private BasicCat(Body body) {
		super(body, NB_LIVES);
	}

	public static BasicCat create(World world, float x, float y) {
		BodyDef bodyDef = new BodyDef();
		bodyDef.position.set(x, y);
		bodyDef.type = BodyType.DYNAMIC;
		Body body = world.createBody(bodyDef);
		createFixtures(body);
		BasicCat basicCat = new BasicCat(body);
		body.setUserData(basicCat);
		return basicCat;
	}
	
	private static void createFixtures(Body body) {
		CircleShape shape = new CircleShape();
		shape.setRadius(RADIUS);
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = shape;
		fixtureDef.filter.categoryBits = BIT_CAT;
		fixtureDef.filter.maskBits = Wall.BIT_WALL | Net.BIT_NET | Bomb.BIT_BOMB;
		body.createFixture(fixtureDef).setUserData(USER_DATA);
	}
	
	@Override
	public String toString() {
		return "BasicCat : " + super.toString();
	}

	@Override
	public void contactWithWall() {
		kill();
	}
	
}
