package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import org.jbox2d.common.Vec2;

import elements.Net;
import elements.Wall;
import elements.barrel.Barrel;
import elements.cat.Cat;
import elements.game.Level;
import elements.item.Bomb;
import fr.umlv.zen4.ApplicationContext;
import fr.umlv.zen4.MotionEvent;
import fr.umlv.zen4.MotionEvent.Action;

public class GameUI {
	private static final float SCALE = 12.0f; // pixel per meter
	private static final float WIDTH = SCALE * Level.WIDTH;
	private static final float HEIGHT = SCALE * Level.HEIGHT;
	private static final float WALL_BORDER = 1;
	private static final Color BACKGROUND_COLOR = Color.WHITE;
	
	private static float kWidthBorder;
	private static float kHeightBorder;
	private static float kWidthButton;
	
	private final float width;
	private final float height;
	private final BufferedImage bufferedImage;
	private final Graphics2D gui;
	
	private GameUI(float width, float height) {
		this.width = width;
		this.height = height;
		this.bufferedImage = new BufferedImage((int)width, (int)height, BufferedImage.TYPE_INT_RGB);
		this.gui = bufferedImage.createGraphics();
	}
	
	private void initializeGameUIConstants() {
		kWidthBorder = (width - WIDTH) / 2;
		kHeightBorder = (height - HEIGHT) / 2;
		kWidthButton = WIDTH / 3;
	}
	
	/**
	 * Creates a GameUI.
	 * @param width width of the current window.
	 * @param height height of the current window.
	 * @return the new GameUI.
	 */
	public static GameUI createGameUI(float width, float height) {
		GameUI gameUI = new GameUI(width, height);
		gameUI.initializeGameUIConstants();
		return gameUI;
	}
	
	/**
	 * Renders the Level.
	 * @param context current context.
	 * @param cats cats of the Level.
	 * @param walls walls of the Level.
	 * @param nets nets of the Level.
	 * @param barrels barrels of the Level.
	 * @param bomb bomb of the Level.
	 * @param seconds seconds left before explosion.
	 */
	public void render(ApplicationContext context, ArrayList<Cat> cats, 
			ArrayList<Wall> walls, ArrayList<Net> nets, ArrayList<Barrel> barrels,
			Bomb bomb, int seconds) {
		context.renderFrame((graphics, contentLost) -> {
			if (contentLost) {
				graphics.setColor(Color.BLACK);
				graphics.fill(new Rectangle2D.Float(0, 0, width, height));
			}
			
			cleanScreen();
			renderBackground();
			renderText("START", kWidthBorder, height - kHeightBorder, WIDTH, kHeightBorder);
			if(seconds >= 1) {
				renderSecondsButton(seconds);
			}
			renderWalls(walls);
			renderNets(nets);
			renderBarrels(barrels);
			renderCats(cats);
			renderBomb(bomb);
			graphics.drawImage(bufferedImage, null, 0, 0);
		});
	}
	
	private void cleanScreen() {
		gui.setColor(Color.BLACK);
		gui.fill(new Rectangle2D.Float(0, 0, width, height));
	}
	
	private void renderBackground() {
		gui.setColor(new Color(40, 50, 75));
		gui.fill(new Rectangle2D.Float(kWidthBorder, 0, WIDTH, height));
		
		gui.setColor(BACKGROUND_COLOR);
		gui.fill(new Rectangle2D.Float(kWidthBorder, kHeightBorder, WIDTH, HEIGHT));
	}
	
	private void renderSecondsButton(int seconds) {
		renderText("-", kWidthBorder, 0, kWidthButton, kHeightBorder);
		renderText(String.valueOf(seconds), kWidthBorder + kWidthButton, 0, kWidthButton, kHeightBorder);
		renderText("+", kWidthBorder + 2 * kWidthButton, 0, kWidthButton, kHeightBorder);	
	}
	
	private void renderText(String text, float x, float y, float width, float height) {
		gui.setColor(Color.WHITE);
		Font font = new Font("Helvetica Neue", Font.BOLD, 30);
		gui.setFont(font);
		FontMetrics fm = gui.getFontMetrics(font);
		
		Rectangle2D rect = fm.getStringBounds(text, gui);
		float textHeight = (float) rect.getHeight(); 
		float textWidth = (float) rect.getWidth();
		x += (width  - textWidth)  / 2;
		y += (height - textHeight) / 2 + fm.getAscent();
		gui.drawString(text, x, y);
	}
	
	private void renderCats(ArrayList<Cat> cats) {
		for(Cat cat : cats) {
			
			if(cat.isActive()) {
				float x = cat.getBody().getPosition().x;
				float y = cat.getBody().getPosition().y;
				
				gui.setColor(Color.MAGENTA);
				gui.fill(new Ellipse2D.Float(
						kWidthBorder + x * SCALE - SCALE,
						height - kHeightBorder - y * SCALE - SCALE,
						SCALE * 2,
						SCALE * 2
				));
			}
			
		}
	}
	
	private void renderWalls(ArrayList<Wall> walls) {
		for(Wall wall : walls) {
			float x = wall.getBody().getPosition().x;
			float y = wall.getBody().getPosition().y;
				
			gui.setColor(Color.DARK_GRAY);
			gui.fill(new Rectangle2D.Float(
					kWidthBorder + x * SCALE - SCALE,
					height - kHeightBorder - y * SCALE - SCALE,
					SCALE * 2,
					SCALE * 2
			));
				
			gui.setColor(Color.LIGHT_GRAY);
			gui.fill(new Rectangle2D.Float(
					kWidthBorder + x * SCALE - SCALE + WALL_BORDER,
					height - kHeightBorder - y * SCALE - SCALE + WALL_BORDER,
					(SCALE - WALL_BORDER) * 2,
					(SCALE - WALL_BORDER) * 2
			));
		}
	}
	
	private void renderBarrels(ArrayList<Barrel> barrels) {
		for(Barrel barrel : barrels) {
			
			if(barrel.isActive()) {
				float x = barrel.getBody().getPosition().x;
				float y = barrel.getBody().getPosition().y;
				
				gui.setColor(Color.GREEN);
				gui.fill(new Rectangle2D.Float(
						kWidthBorder + x * SCALE - SCALE * Barrel.WIDTH,
						height - kHeightBorder - y * SCALE - SCALE * Barrel.HEIGHT,
						SCALE * 2 * Barrel.WIDTH,
						SCALE * 2 * Barrel.HEIGHT
				));
			}
			
		}
	}
	
	private void renderNets(ArrayList<Net> nets) {
		for(Net net : nets) {
			float x = net.getBody().getPosition().x;
			float y = net.getBody().getPosition().y;
			
			if(net.isFull()) {
				gui.setColor(Color.YELLOW);
			} else {
				gui.setColor(Color.DARK_GRAY);
			}
			
			gui.fill(new Rectangle2D.Float(
					kWidthBorder + x * SCALE - SCALE * Net.WIDTH,
					height - kHeightBorder - y * SCALE - SCALE * Net.HEIGHT,
					SCALE * 2 * Net.WIDTH,
					SCALE * 2 * Net.HEIGHT
			));
		}
	}
	
	private void renderBomb(Bomb bomb) {
		
		if(bomb == null) {
			return;
		}
		
		float x = bomb.getBody().getPosition().x;
		float y = bomb.getBody().getPosition().y;
		
		float scale = Bomb.RADIUS * SCALE;
		
		if(!bomb.hasExploded()) {
			if(bomb.isActive()) {
				gui.setColor(new Color(255, 50, 50));
				gui.fill(new Ellipse2D.Float(
						kWidthBorder + x * scale - Bomb.RANGE * SCALE,
						height - kHeightBorder - y * scale - Bomb.RANGE * SCALE,
						Bomb.RANGE * 2 * SCALE,
						Bomb.RANGE * 2 * SCALE
				));
			}
			
			gui.setColor(Color.BLACK);
			
			gui.fill(new Ellipse2D.Float(
					kWidthBorder + x * scale - scale,
					height - kHeightBorder - y * scale - scale,
					scale * 2,
					scale * 2
			));
		}
		
	}

	/**
	 * Renders the victory screen.
	 * @param context current context.
	 */
	public void victory(ApplicationContext context) {
		endLevelMessage(context, "LEVEL COMPLETE", Color.BLACK, Color.MAGENTA);
	}

	/**
	 * Renders the game over screen.
	 * @param context current context.
	 */
	public void gameOver(ApplicationContext context) {
		endLevelMessage(context, "GAME OVER", Color.MAGENTA, Color.BLACK);
	}
	
	private void endLevelMessage(ApplicationContext context, String message, Color backgroundColor, Color textColor) {
		context.renderFrame((graphics, contentLost) -> {
			if (contentLost) {
				graphics.setColor(Color.BLACK);
				graphics.fill(new Rectangle2D.Float(0, 0, width, height));
			}
			
			gui.setColor(backgroundColor);
			gui.fill(new Rectangle2D.Float(0, 0, width, height));
		
			gui.setColor(textColor);
			Font font = new Font("Helvetica Neue", Font.BOLD, 50);
			gui.setFont(font);
			FontMetrics fm   = gui.getFontMetrics(font);
			Rectangle2D rect = fm.getStringBounds(message, gui);
			float textHeight = (float) rect.getHeight(); 
			float textWidth = (float) rect.getWidth();
			float x = (width - textWidth) / 2;
			float y = (height - textHeight) / 2 + fm.getAscent();
			gui.drawString(message, x, y);
			graphics.drawImage(bufferedImage, null, 0, 0);
		});
		
		MotionEvent event;
		do {
			try {
				event = context.waitAndBlockUntilAMotion();
			} catch (InterruptedException e) {
				throw new AssertionError(e);
			}
		} while(event.getAction() != Action.UP);
		
	}

	/**
	 * Returns true if there is an action on the start button. 
	 * @param x x coordinate of the event.
	 * @param y y coordinate of the event.
	 * @return true if there is an action on the start button, false otherwise.
	 */
	public boolean actionStartButton(float x, float y) {
		if((x >= kWidthBorder && x <= width - kWidthBorder) &&
				(y >= height - kHeightBorder && y <= height)) {
			return true;
		}
		return false;
	}
	
	/**
	 * Returns true if the coordinates of the event are between the level borders.
	 * @param x x coordinate of the event.
	 * @param y y coordinate of the event.
	 * @return true if the coordinates are between the level borders, false otherwise.
	 */
	public boolean isInLevel(float x, float y) {
		if((x >+ kWidthBorder && x <= width - kWidthBorder) &&
				(y >= kHeightBorder && y <= height - kHeightBorder)) {
			return true;
		}
		return false;
	}

	/**
	 * Returns true if there is an action on the plus button.
	 * @param x x coordinate of the event.
	 * @param y y coordinate of the event.
	 * @return true if there is an action on the plus button, false otherwise.
	 */
	public boolean actionPlusButton(float x, float y) {
		if((x >+ kWidthBorder + 2 * kWidthButton && x <= width - kWidthBorder) &&
				(y >= 0 && y <= kHeightBorder)) {
			return true;
		}
		return false;
	}
	
	/**
	 * Returns true if there is an action on the minus button.
	 * @param x x coordinate of the event.
	 * @param y y coordinate of the event.
	 * @return true if there is an action on the minus button, false otherwise.
	 */
	public boolean actionMinusButton(float x, float y) {
		if((x >+ kWidthBorder && x <= kWidthBorder + kWidthButton) &&
				(y >= 0 && y <= kHeightBorder)) {
			return true;
		}
		return false;
	}
	
	/**
	 * Converts the coordinates in the window to coordinates in the level.
	 * @param x x coordinate in the window.
	 * @param y y coordinate in the window.
	 * @return a Vec2 of the new coordinates.
	 */
	public Vec2 convertUIPostionToLevelPosition(float x, float y) {
		float newX = (x - kWidthBorder) / SCALE;
		float newY = (height - y - kHeightBorder) / SCALE;
		return new Vec2(newX, newY);
	}
	
	/**
	 * Renders a preview of the Bomb.
	 * @param context current context.
	 * @param x x coordinate of the Bomb.
	 * @param y y coordinate of the Bomb.
	 */
	public void previewBomb(ApplicationContext context, float x, float y) {
		context.renderFrame((graphics, contentLost) -> {
			if (contentLost) {
				graphics.setColor(Color.BLACK);
				graphics.fill(new Rectangle2D.Float(0, 0, width, height));
			}
			float scale = Bomb.RADIUS * SCALE;
	
			gui.setColor(new Color(255, 50, 50));
			gui.fill(new Ellipse2D.Float(
				x - Bomb.RANGE * SCALE,
				y - Bomb.RANGE * SCALE,
				Bomb.RANGE * 2 * SCALE,
				Bomb.RANGE * 2 * SCALE
			));
				
			gui.setColor(Color.BLACK);
			gui.fill(new Ellipse2D.Float(
				x - scale,
				y - scale,
				scale * 2,
				scale * 2
			));
			graphics.drawImage(bufferedImage, null, 0, 0);
		});
	}
}
