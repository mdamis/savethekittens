package elements.cat;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;

import elements.Net;

public abstract class AbstractCat implements Cat{

	public static final String USER_DATA = "Cat";
	public static final float RADIUS = 1.0f;
	private final Body body;
	private int nbLives;
	
	AbstractCat(Body body, int nbLives) {
		this.body = body;
		this.nbLives = nbLives;
	}
	
	protected void kill() {
		nbLives = 0;
		System.out.println("A cat just died.\nGame Over");
	}
	
	protected void killOnce() {
		nbLives--;
	}
	
	@Override
	public Body getBody() {
		return body;
	}
	
	@Override
	public boolean isAlive() {
		return nbLives > 0;
	}
	
	@Override
	public void move(Vec2 v) {
		body.setLinearVelocity(v);
	}
	
	@Override
	public void stop() {
		body.setLinearVelocity(new Vec2(0, 0));
		body.setActive(false);
	}
	
	@Override
	public boolean isActive() {
		return body.isActive();
	}
	
	@Override
	public void contactWithNet(Net net) {
		if(net.isFull()) {
			kill();
		}
		stop();
		net.setFull();
	}
	
	@Override
	public String toString() {
		return nbLives + " lives " + body.getPosition().x + " " + body.getPosition().y;
	}
}
