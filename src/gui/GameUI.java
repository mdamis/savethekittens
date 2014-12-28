package gui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import elements.Net;
import elements.Wall;
import elements.barrel.Barrel;
import elements.cat.Cat;
import elements.game.Level;
import fr.umlv.zen4.ApplicationContext;

public class GameUI {
	private static final float SCALE = 12.0f; // pixel per meter
	private static final float WIDTH = SCALE * Level.WIDTH;
	private static final float HEIGHT = SCALE * Level.HEIGHT;
	private static final float WALL_BORDER = 1;
	private static final Color BACKGROUND_COLOR = Color.WHITE;
	
	private static float kWidthBorder;
	private static float kHeightBorder;
	
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
	}
	
	public static GameUI createGameUI(float width, float height) {
		GameUI gameUI = new GameUI(width, height);
		gameUI.initializeGameUIConstants();
		return gameUI;
	}
	
	public void render(ApplicationContext context, ArrayList<Cat> cats, 
			ArrayList<Wall> walls, ArrayList<Net> nets, ArrayList<Barrel> barrels) {
		context.renderFrame((graphics, contentLost) -> {
			if (contentLost) {
				graphics.setColor(Color.BLACK);
				graphics.fill(new Rectangle2D.Float(0, 0, width, height));
			}
			
			cleanScreen();
			renderBackground();
			renderWalls(walls);
			renderNets(nets);
			renderBarrels(barrels);
			renderCats(cats);
			graphics.drawImage(bufferedImage, null, 0, 0);
		});
	}
	
	private void cleanScreen() {
		gui.setColor(Color.BLACK);
		gui.fill(new Rectangle2D.Float(0, 0, width, height));
	}
	
	private void renderBackground() {
		gui.setColor(Color.CYAN);
		gui.fill(new Rectangle2D.Float(kWidthBorder, 0, WIDTH, height));
		
		gui.setColor(BACKGROUND_COLOR);
		gui.fill(new Rectangle2D.Float(kWidthBorder, kHeightBorder, WIDTH, HEIGHT));
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
}
