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
	public static float kHeightEighth;

	private Menu(float width, float height) {
		this.width = width;
		this.height = height;
	}

	private void initializeMenuConstants() {
		kWidthHalf = width / 2;
		kHeightHalf = height / 2;
		kWidthFifth = width / 5;
		kHeightEighth = height / 8;
	}
	
	public static Menu createMenu(float width, float height) {
		Menu menu = new Menu(width, height);
		menu.initializeMenuConstants();
		return menu;
	}
	
	public void menuSelection(ApplicationContext context) {
		render(context);
		
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
				return;
			}
			
			if(event.getAction() == Action.UP &&
					(event.getX() > kWidthFifth && event.getX() < width - kWidthFifth) &&
					(event.getY() > 6 * kHeightEighth && event.getY() < 7 * kHeightEighth)) {
				context.exit(0);
			}
		}
	}

	private void render(ApplicationContext context) {
		animate(context);
		renderText(context);
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
	
	private void renderText(ApplicationContext context) {
		context.renderFrame((graphics, contentLost) -> {
		
			graphics.setColor(Color.BLACK);
			Font font = new Font("Helvetica Neue", Font.BOLD, 50);
			graphics.setFont(font);
			FontMetrics fm   = graphics.getFontMetrics(font);

			float areaHeight = 4*kHeightEighth;
			float areaWidth = 3*kWidthFifth;
			renderString(graphics, "SAVE THE KITTENS", fm, areaHeight, areaWidth, (int)kWidthFifth, 0);
			
			graphics.setColor(Color.WHITE);
			areaHeight = kHeightEighth;
			areaWidth = 3*kWidthFifth;
			
			renderString(graphics, "PLAY", fm, areaHeight, areaWidth, (int)kWidthFifth, (int)(4*kHeightEighth));
			renderString(graphics, "QUIT", fm, areaHeight, areaWidth, (int)kWidthFifth, (int)(6*kHeightEighth));
	
		});
	}
	
	private void renderString(Graphics2D graphics, String string, FontMetrics fm, float areaHeight, float areaWidth, int x, int y) {
		Rectangle2D rect = fm.getStringBounds(string, graphics);
		int textHeight = (int) (rect.getHeight()); 
		int textWidth = (int) (rect.getWidth());
		x += (areaWidth  - textWidth)  / 2;
		y += (areaHeight - textHeight) / 2 + fm.getAscent();
		graphics.drawString(string, x, y);
	}
	
	private void whiteScreenAnimation(ApplicationContext context) {
		
			float windowSize = Math.max(width, height);
			int speed = 25;
			
			for(float i=0; i<2*windowSize; i+=speed) {
				
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

}
