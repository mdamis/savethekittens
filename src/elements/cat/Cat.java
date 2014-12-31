package elements.cat;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;

import elements.Net;
import elements.item.Bomb;

public interface Cat {
	public static final int BIT_CAT = 16;
	
	/**
	 * Returns body.
	 * @return the body of a Cat.
	 */
	Body getBody();
	
	/**
	 * Returns true if the Cat is alive.
	 * @return true if the Cat is alive, false otherwise. 
	 */
	boolean isAlive();
	
	/**
	 * Stops the Cat.
	 * Sets the body of the Cat to inactive.
	 */
	void stop();
	
	/**
	 * Moves the Cat.
	 * @param v the direction towards which the Cat moves.
	 */
	void move(Vec2 v);
	
	/**
	 * Handles the collision between the Cat and a Wall.
	 */
	void contactWithWall();
	
	/**
	 * Handles the collision between the Cat and the net taken as argument.
	 * @param net net with which the Cat collided.
	 */
	void contactWithNet(Net net);
	
	/**
	 * Handles the collision between the Cat and the bomb taken as argument.
	 * @param bomb bomb with which the Cat collided.
	 */
	void contactWithBomb(Bomb bomb);
	
	/**
	 * Returns true if the Cat is active.
	 * @return true if the body of the Cat is active, false otherwise.
	 */
	boolean isActive();
	
	/**
	 * Returns true if the Cat is in the Level.
	 * @return true if the Cat is in the Level, false otherwise.
	 */
	boolean isInLevel();
	
}
