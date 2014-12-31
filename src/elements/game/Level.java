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
		this.context = context;
		this.gameUI = GameUI.createGameUI(width, height);
		this.nbBombs = nbBombs;
	}
	
	public static Level createLevel(ApplicationContext context, float width, float height, int nbBombs) {
		Level level = new Level(context, width, height, nbBombs);
		level.setWorldCollisions();
		return level;
	}
	
	private void setWorldCollisions() {
		world.setContactListener(new Collisions());
	}
	
	public void play() {
		
		boolean isStarted = false;
		boolean isPlanted = false;
		int seconds = 1;
		Vec2 bombPosition = new Vec2();
		float x = 0;
		float y = 0;
		
		do {
			gameUI.render(context, cats, walls, nets, barrels, bomb);
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
					} else if(gameUI.actionSecondsButtons(event.getX(), event.getY())) {
						
					} else {
						seconds = 1;
						isPlanted = false;
					}
				}
			}
			
		} while(!isStarted);
		
		if(isPlanted) {
			bomb = Bomb.create(world, bombPosition.x, bombPosition.y, seconds);
			gameUI.render(context, cats, walls, nets, barrels, bomb);
		}
		
		update();
	}
	
	private void update() {
		for(;;) {
			world.step(timeStep, velocityIterations, positionIterations);
			world.clearForces();
			gameUI.render(context, cats, walls, nets, barrels, bomb);
			
			if(nbIterations % 25 == 0) {
				for(Barrel barrel : barrels) {
					if(barrel.isActive()) {
						barrel.shootCat();
					}
				}
			}
			
			if(bomb != null) {
				bomb.explode(nbIterations / 60);
			}
		
			if(isComplete()) {
				System.out.println("Victory");
				gameUI.victory(context);
				return;
			}
			
			if(isLost()) {
				System.out.println("Game Over");
				gameUI.gameOver(context);
				return;
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
			if(!cat.isAlive()) {
				return true;
			}
		}
		return false;
	}
	
	public void createLevelNet(float x, float y) {
		nets.add(Net.createNet(world, x, y));
	}
	
	public SingleBarrel createLevelSingleBarrel(float x, float y, String angleString) {
		SingleBarrel barrel = SingleBarrel.create(world, x, y, angleString);
		barrels.add(barrel);
		return barrel;
	}
	
	public AutomaticBarrel createLevelAutomaticBarrel(float x, float y, String angleString) {
		AutomaticBarrel barrel = AutomaticBarrel.create(world, x, y, angleString);
		barrels.add(barrel);
		return barrel;
	}
	
	public void addCatBarrel(Barrel barrel, String catType) {
		cats.add(barrel.addCat(world, catType));
	}
	
	public void createLevelWall(float x, float y) {
		walls.add(Wall.createWall(world, x, y));
	}
	
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
