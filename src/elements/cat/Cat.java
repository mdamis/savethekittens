package elements.cat;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;

public interface Cat {
	public static final int BIT_CAT = 16;
	
	Body getBody();
	void stop();
	void move(Vec2 v);
	
}
