package elements.game;

import elements.Net;
import elements.Wall;
import elements.barrel.AutomaticBarrel;
import elements.barrel.Barrel;
import elements.barrel.SingleBarrel;
import elements.cat.Cat;
import elements.item.Bomb;
import fr.umlv.zen4.ApplicationContext;
import fr.umlv.zen4.MotionEvent;
import fr.umlv.zen4.MotionEvent.Action;
import gui.GameUI;
import handlers.Collisions;

import java.util.ArrayList;
import java.util.Objects;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

public class Level {
	public static final float WIDTH = 50.0f; // standard width of a Level
	public static final float HEIGHT = 50.0f; // standard height of a Level
	
	private final World world = new World(new Vec2()); // world with no gravity
	private final GameUI gameUI;
	private final ApplicationContext context;
	private final ArrayList<Wall> walls = new ArrayList<>();
	private final ArrayList<Cat> cats = new ArrayList<>();
	private final ArrayList<Net> nets = new ArrayList<>();
	private final ArrayList<Barrel> barrels = new ArrayList<>();
	private Bomb bomb = null;
	private final int nbBombs;
	
	private static final int velocityIterations = 6;
	private static final int positionIterations = 2;
	private static final float timeStep = 1.0f / 60.0f;
	private int nbIterations = 0;
	
	private Level(ApplicationContext context, float width, float height, int nbBombs) {
		this.context = Objects.requireNonNull(context);
		this.gameUI = GameUI.createGameUI(width, height);
		this.nbBombs = Objects.requireNonNull(nbBombs);
	}
	
	/**
	 * Creates a Level.
	 * @param context current context.
	 * @param width width of the current window.
	 * @param height height of the current window.
	 * @param nbBombs number of Bombs for this Level. (0 or 1)
	 * @return the new Level.
	 */
	public static Level createLevel(ApplicationContext context, float width, float height, int nbBombs) {
		Level level = new Level(context, width, height, nbBombs);
		level.setWorldCollisions();
		return level;
	}
	
	private void setWorldCollisions() {
		world.setContactListener(new Collisions());
	}
	
	/**
	 * Plays the level :
	 * Wait for the player to plant or not a bomb.
	 * Wait for the player to click on the start button.
	 * Updates the elements of the world and renders the gameUI.
	 * @return true if RETRY pressed, false if QUIT pressed.
	 */
	public boolean play() {
		
		boolean isStarted = false;
		boolean isPlanted = false;
		int seconds = 1;
		Vec2 bombPosition = new Vec2();
		float x = 0;
		float y = 0;
		
		do {
			if(nbBombs == 1) {
				gameUI.render(context, cats, walls, nets, barrels, bomb, seconds);
			} else {
				gameUI.render(context, cats, walls, nets, barrels, bomb, 0);
			}
			
			if(isPlanted) {
				gameUI.previewBomb(context, x, y);
			}
			
			MotionEvent event;
			try {
				event = context.waitAndBlockUntilAMotion();
			} catch(InterruptedException e) {
				throw new AssertionError(e);
			}
			
			if(event.getAction() == Action.UP) {
				
				if(gameUI.actionStartButton(event.getX(), event.getY())) {
					isStarted = true;
					continue;
				}
				
				if(nbBombs == 1) {
					if(gameUI.isInLevel(event.getX(), event.getY())) {
						x = event.getX();
						y = event.getY();
						
						bombPosition = gameUI.convertUIPostionToLevelPosition(x, y);
						isPlanted = true;
						gameUI.previewBomb(context, x, y);
					} else if(gameUI.actionPlusButton(event.getX(), event.getY())) {
						seconds++;
					} else if(gameUI.actionMinusButton(event.getX(), event.getY())) {
						if(seconds > 1) {
							seconds--;
						}
					}else {
						seconds = 1;
						isPlanted = false;
					}
				}
			}
			
		} while(!isStarted);
		
		if(isPlanted) {
			bomb = Bomb.create(world, bombPosition.x, bombPosition.y, seconds);
			gameUI.render(context, cats, walls, nets, barrels, bomb, seconds);
		}
		
		return update();
	}
	
	private boolean update() {
		long start = System.nanoTime();
		
		for(;;) {
			world.step(timeStep, velocityIterations, positionIterations);
			world.clearForces();
			
			long now = (System.nanoTime() - start) / 1_000_000_000;
			long seconds = 0;
			
			if(bomb != null) {
				bomb.explode(now);
				seconds = bomb.getSeconds() - now;
			}
			gameUI.render(context, cats, walls, nets, barrels, bomb, seconds);
			
			try {
				Thread.sleep(0, 500);
			} catch (InterruptedException e) {
				return false;
			}
			
			if(nbIterations % 25 == 0) {
				for(Barrel barrel : barrels) {
					if(barrel.isActive()) {
						barrel.shootCat();
					}
				}
			}
		
			if(isComplete()) {
				System.out.println("Victory");
				gameUI.victory(context);
				return false;
			}
			
			if(isLost()) {
				System.out.println("Game Over");
				return gameUI.gameOver(context);
			}
			
			nbIterations++;
		}
	}
	
	private boolean isComplete() {
		for(Net net : nets) {
			if(!net.isFull()) {
				return false;
			}
		}
		return true;
	}
	
	private boolean isLost() {
		for(Cat cat : cats) {
			if(!cat.isAlive() || !cat.isInLevel()) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Creates a Net and adds it to the list of Net.
	 * @param x x coordinate of the Net.
	 * @param y y coordinate of the Net.
	 */
	public void createLevelNet(float x, float y) {
		nets.add(Net.createNet(world, x, y));
	}
	
	/**
	 * Creates a SingleBarrel and adds it to the list of Barrel.
	 * @param x x coordinate of the SingleBarrel.
	 * @param y y coordinate of the SingleBarrel.
	 * @param angleString string corresponding to a valid angle.
	 * @return the new Barrel.
	 */
	public SingleBarrel createLevelSingleBarrel(float x, float y, String angleString) {
		SingleBarrel barrel = SingleBarrel.create(world, x, y, angleString);
		barrels.add(barrel);
		return barrel;
	}
	
	/**
	 * Creates an AutomaticBarrel and adds it to the list of Barrel.
	 * @param x x coordinate of the AutomaticBarrel.
	 * @param y y coordinate of the AutomaticBarrel.
	 * @param angleString string corresponding to a valid angle.
	 * @return the new AutomaticBarrel.
	 */
	public AutomaticBarrel createLevelAutomaticBarrel(float x, float y, String angleString) {
		AutomaticBarrel barrel = AutomaticBarrel.create(world, x, y, angleString);
		barrels.add(barrel);
		return barrel;
	}
	
	/**
	 * Creates a Cat and adds it to the list of Cat.
	 * @param barrel barrel which will launch the Cat.
	 * @param catType string corresponding to a valid Cat type.
	 */
	public void addCatBarrel(Barrel barrel, String catType) {
		cats.add(barrel.addCat(world, catType));
	}
	
	/**
	 * Creates a Wall and adds it to the list of Wall.
	 * @param x x coordinate of the Wall.
	 * @param y y coordinate of the Wall.
	 */
	public void createLevelWall(float x, float y) {
		walls.add(Wall.createWall(world, x, y));
	}
	
	/**
	 * Creates the borders of a Level by adding Walls around it.
	 */
	public void createLevelBorders() {
		
		//Bottom border
		for(float x = Wall.WIDTH; x < WIDTH; x += (2 * Wall.WIDTH)) {
			walls.add(Wall.createWall(world, x, Wall.HEIGHT));
		}
		//Top border
		for(float x = Wall.WIDTH; x < WIDTH; x += (2 * Wall.WIDTH)) {
			walls.add(Wall.createWall(world, x, HEIGHT - Wall.HEIGHT));
		}
		//Left border
		for(float y = (3 * Wall.HEIGHT); y < (HEIGHT - Wall.HEIGHT); y += (2 * Wall.HEIGHT)) {
			walls.add(Wall.createWall(world, Wall.WIDTH, y));
		}
		//Right border
		for(float y = (3 * Wall.HEIGHT); y < (HEIGHT - Wall.HEIGHT); y += (2 * Wall.HEIGHT)) {
			walls.add(Wall.createWall(world, WIDTH - Wall.WIDTH, y));
		}
		
	}
	
}
