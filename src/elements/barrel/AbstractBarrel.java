package elements.barrel;

import java.util.Objects;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.World;

import elements.cat.BasicCat;
import elements.cat.BouncingCat;
import elements.cat.Cat;

public abstract class AbstractBarrel implements Barrel {
	public static final String USER_DATA = "Barrel";
	private final Body body;
	private final Vec2 angle;
	
	protected AbstractBarrel(Body body, Vec2 angle) {
		this.body = Objects.requireNonNull(body);
		this.angle = Objects.requireNonNull(angle);
	}
	
	protected static Vec2 parseAngle(String angleString) {
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
	
	@Override
	public Cat addCat(World world, String catType) {
		switch(catType) {
		case "BasicCat":
			Cat basicCat = BasicCat.create(world, getBody().getPosition().x, getBody().getPosition().y);
			addCat(basicCat);
			return basicCat;
		case "BouncingCat":
			Cat bouncingCat = BouncingCat.create(world, getBody().getPosition().x, getBody().getPosition().y);
			addCat(bouncingCat);
			return bouncingCat;
		default:
			throw new IllegalArgumentException("No cat corresponding to catType");
		}	
	}
	
	abstract void addCat(Cat cat);
	
	protected void setInactive() {
		body.setActive(false);
	}
	
	@Override
	public boolean isActive() {
		return body.isActive();
	}
	
	@Override
	public Body getBody() {
		return body;
	}
	
	protected Vec2 getAngle() {
		return angle;
	}
	
	@Override
	public String toString() {
		return "SingleBarrel : " + body.getPosition().x + " " + body.getPosition().y;
	}
	
}
