package elements.cat;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;

import elements.Net;
import elements.item.Bomb;

public interface Cat {
	public static final int BIT_CAT = 16;
	
	Body getBody();
	boolean isAlive();
	void stop();
	void move(Vec2 v);
	void contactWithWall();
	void contactWithNet(Net net);
	boolean isActive();
	void contactWithBomb(Bomb bomb);
	boolean isInLevel();
	
}
