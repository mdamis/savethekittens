package gui;

import java.awt.Color;
import java.awt.geom.Rectangle2D;

import elements.game.Level;
import fr.umlv.zen4.ApplicationContext;

public class GameUI {
	private static final int SCALE = 12; // pixel per meter
	private static final int WIDTH = (int) (SCALE * Level.WIDTH);
	private static final int HEIGHT = (int) (SCALE * Level.HEIGHT);
	private final float width;
	private final float height;
	private static float kWidthBorder;
	private static float kHeightBorder;
	private static float WALL_BORDER = 1;
	
	private GameUI(float width, float height) {
		this.width = width;
		this.height = height;
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
	
	public void cleanScreen(ApplicationContext context) {
		context.renderFrame((graphics, contentLost) -> {
			graphics.setColor(Color.BLACK);
			graphics.fill(new Rectangle2D.Float(0, 0, width, height));
		});
	}
	
	public void renderWall(ApplicationContext context, float x, float y) {
		
		context.renderFrame((graphics, contentLost) -> {
			if(contentLost) {
				graphics.setColor(Color.BLACK);
				graphics.fill(new Rectangle2D.Float(0, 0, width, height));
			}
			
			graphics.setColor(Color.DARK_GRAY);
			graphics.fill(new Rectangle2D.Float(
					kWidthBorder + x * SCALE - SCALE,
					height - kHeightBorder - y * SCALE - SCALE,
					SCALE * 2,
					SCALE * 2
			));
			
			graphics.setColor(Color.LIGHT_GRAY);
			graphics.fill(new Rectangle2D.Float(
					kWidthBorder + x * SCALE - SCALE + WALL_BORDER,
					height - kHeightBorder - y * SCALE - SCALE + WALL_BORDER,
					(SCALE - WALL_BORDER) * 2,
					(SCALE - WALL_BORDER) * 2
			));
		});
	}
}
