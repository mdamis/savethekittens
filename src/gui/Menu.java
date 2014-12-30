package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

import fr.umlv.zen4.ApplicationContext;
import fr.umlv.zen4.MotionEvent;
import fr.umlv.zen4.MotionEvent.Action;


public class Menu {
	public final float width;
	public final float height;
	
	public static float kWidthHalf;
	public static float kHeightHalf;
	public static float kWidthFifth;
	public static float kWidthSixth;
	public static float kHeightEighth;

	private Menu(float width, float height) {
		this.width = width;
		this.height = height;
	}

	private void initializeMenuConstants() {
		kWidthHalf = width / 2;
		kHeightHalf = height / 2;
		kWidthFifth = width / 5;
		kWidthSixth = width / 6;
		kHeightEighth = height / 8;
	}
	
	public static Menu createMenu(float width, float height) {
		Menu menu = new Menu(width, height);
		menu.initializeMenuConstants();
		return menu;
	}
	
	public void menuSelection(ApplicationContext context) {
		renderMenu(context);
		
		for(;;) {
			MotionEvent event;
			try {
				event = context.waitAndBlockUntilAMotion();
			} catch(InterruptedException e) {
				throw new AssertionError(e);
			}
			
			if(event.getAction() == Action.UP &&
					(event.getX() > kWidthFifth && event.getX() < width - kWidthFifth) &&
					(event.getY() > 4 * kHeightEighth && event.getY() < 5 * kHeightEighth)) {
				whiteScreenAnimation(context);
				levelSelection(context);
				return;
			}
			
			if(event.getAction() == Action.UP &&
					(event.getX() > kWidthFifth && event.getX() < width - kWidthFifth) &&
					(event.getY() > 6 * kHeightEighth && event.getY() < 7 * kHeightEighth)) {
				context.exit(0);
			}
		}
	}
	
	public void levelSelection(ApplicationContext context) {
		int levelNumber = 1;
		int nbLevels = 10;
		
		for(;;) {
			renderLevelSelection(context, levelNumber);
			MotionEvent event;
			try {
				event = context.waitAndBlockUntilAMotion();
			} catch(InterruptedException e) {
				throw new AssertionError(e);
			}
			
			if(event.getAction() == Action.UP &&
					(event.getX() > (2*kWidthSixth) && event.getX() < (3*kWidthSixth)) &&
					(event.getY() > 5 * kHeightEighth && event.getY() < 6 * kHeightEighth)) {
				if(levelNumber == 1) {
					levelNumber = nbLevels;
				} else {
					levelNumber--;
				}
			}
			
			if(event.getAction() == Action.UP &&
					(event.getX() > (3*kWidthSixth) && event.getX() < (4*kWidthSixth)) &&
					(event.getY() > 5 * kHeightEighth && event.getY() < 6 * kHeightEighth)) {
				if(levelNumber == nbLevels) {
					levelNumber = 1;
				} else {
					levelNumber++;
				}
			}
		}
	}

	private void renderMenu(ApplicationContext context) {
		animate(context);
		renderMenuText(context);
	}
	
	private void animate(ApplicationContext context) {
		int speed = 25;
		int border = 1;
		
		for (int i = 0; i <= width+speed; i+=speed) {

			int x = i;
			int y = i;
			
			context.renderFrame((graphics, contentLost) -> {
				if (contentLost) {
					graphics.setColor(Color.CYAN);
					graphics.fill(new Rectangle2D.Float(0, 0, width, height));
				}
				
				graphics.setColor(Color.CYAN);
				graphics.fill(new Rectangle2D.Float(0, 0, width, height));
				
				graphics.setColor(Color.WHITE);
				graphics.fill(new Rectangle2D.Float(kWidthFifth, 0, 3*kWidthFifth, y));
				
				graphics.setColor(Color.LIGHT_GRAY);
				graphics.fill(new Rectangle2D.Float(0, 4*kHeightEighth, x, kHeightEighth));
				
				graphics.setColor(Color.DARK_GRAY);
				graphics.fill(new Rectangle2D.Float(width - x, 5*kHeightEighth, x, kHeightEighth));
				
				graphics.setColor(Color.BLACK);
				graphics.fill(new Rectangle2D.Float(0, 6*kHeightEighth, x, kHeightEighth));
				
				graphics.setColor(Color.BLACK);
				graphics.fill(new Rectangle2D.Float(0, 4*kHeightEighth, x, border));
				graphics.fill(new Rectangle2D.Float(width - x, 5*kHeightEighth, x, border));
				graphics.fill(new Rectangle2D.Float(0, 6*kHeightEighth, x, border));
				graphics.fill(new Rectangle2D.Float(0, 7*kHeightEighth, x, border));
	
			});

		}
	}
	
	private void renderMenuText(ApplicationContext context) {
		context.renderFrame((graphics, contentLost) -> {
		
			graphics.setColor(Color.BLACK);
			Font font = new Font("Helvetica Neue", Font.BOLD, 50);
			graphics.setFont(font);
			FontMetrics fm = graphics.getFontMetrics(font);

			float areaHeight = 4*kHeightEighth;
			float areaWidth = 3*kWidthFifth;
			renderString(graphics, "SAVE THE KITTENS", fm, kWidthFifth, 0, areaWidth, areaHeight);
			
			graphics.setColor(Color.WHITE);
			areaHeight = kHeightEighth;
			areaWidth = 3*kWidthFifth;
			
			renderString(graphics, "PLAY", fm, kWidthFifth, 4*kHeightEighth, areaWidth, areaHeight);
			renderString(graphics, "QUIT", fm, kWidthFifth, 6*kHeightEighth, areaWidth, areaHeight);
	
		});
	}
	
	private void renderString(Graphics2D graphics, String string, FontMetrics fm, float x, float y, float areaWidth, float areaHeight) {
		Rectangle2D rect = fm.getStringBounds(string, graphics);
		float textHeight = (float) rect.getHeight(); 
		float textWidth = (float) rect.getWidth();
		x += (areaWidth  - textWidth)  / 2;
		y += (areaHeight - textHeight) / 2 + fm.getAscent();
		graphics.drawString(string, x, y);
	}
	
	private void whiteScreenAnimation(ApplicationContext context) {
		
		float windowSize = Math.max(width, height);
		int speed = 25;
			
		for(float i=0; i<(3.0f/2.0f)*windowSize; i+=speed) {
				
			float ellipseWidth = i;
				
			context.renderFrame((graphics, contentLost) -> {
				if (contentLost) {
					graphics.setColor(Color.WHITE);
					graphics.fill(new Rectangle2D.Float(0, 0, width, height));
				}
					
				graphics.setColor(Color.WHITE);
				graphics.fill(new Ellipse2D.Float(
						kWidthHalf - ellipseWidth / 2,
						kHeightHalf - ellipseWidth / 2,
						ellipseWidth,
						ellipseWidth
				));
			});
		}
	}
	
	private void renderLevelSelection(ApplicationContext context, int levelNumber) {
		context.renderFrame((graphics, contentLost) -> {
			if (contentLost) {
				graphics.setColor(Color.BLACK);
				graphics.fill(new Rectangle2D.Float(0, 0, width, height));
			}
			
			Color color = new Color(levelNumber*20, 50, 75);
			graphics.setColor(color);
			graphics.fill(new Rectangle2D.Float(0, 0, width, height));
			
			float x = 3*kWidthSixth - (kWidthSixth) / 2;
			float y = 2*kHeightEighth - (kWidthSixth) / 2;
			
			int ellipseBorder = 3;
			
			
			
			graphics.setColor(Color.BLACK);
			graphics.fill(new Ellipse2D.Float(x, y, kWidthSixth, kWidthSixth));
			
			graphics.setColor(Color.WHITE);
			graphics.fill(new Ellipse2D.Float(
					x + ellipseBorder,
					y + ellipseBorder,
					kWidthSixth - 2 * ellipseBorder,
					kWidthSixth - 2 * ellipseBorder
			));
			renderLevelSelectionText(graphics, Color.BLACK, String.valueOf(levelNumber), 80, x, y, kWidthSixth, kWidthSixth);
			
			
			renderLevelSelectionButtons(graphics, "PLAY", Color.DARK_GRAY, 2*kWidthSixth, 4*kHeightEighth, 2*kWidthSixth, kHeightEighth);
			renderLevelSelectionButtons(graphics, "PREVIOUS",  Color.LIGHT_GRAY, 2*kWidthSixth, 5*kHeightEighth, kWidthSixth, kHeightEighth);
			renderLevelSelectionButtons(graphics, "NEXT", Color.LIGHT_GRAY, 3*kWidthSixth, 5*kHeightEighth, kWidthSixth, kHeightEighth);
			renderLevelSelectionButtons(graphics, "BACK", Color.DARK_GRAY, 2*kWidthSixth, 6*kHeightEighth, 2*kWidthSixth, kHeightEighth);
		});
	}
	
	private void renderLevelSelectionButtons(Graphics2D graphics, String text, Color color, float x, float y, float width, float height) {
		int border = 1;
		
		graphics.setColor(Color.BLACK);
		graphics.fill(new Rectangle2D.Float(x, y, width, height));
		graphics.setColor(color);
		graphics.fill(new Rectangle2D.Float(x + border, y + border, width - 2 * border, height - 2 * border));
		
		renderLevelSelectionText(graphics, Color.WHITE, text, 30, x, y, width, height);
	}
	
	private void renderLevelSelectionText(Graphics2D graphics, Color color, String text, int size, float x, float y, float areaWidth, float areaHeight) {
		graphics.setColor(color);
		Font font = new Font("Helvetica Neue", Font.BOLD, size);
		graphics.setFont(font);
		FontMetrics fm = graphics.getFontMetrics(font);
		renderString(graphics, text, fm, x, y, areaWidth, areaHeight);
	}

}
