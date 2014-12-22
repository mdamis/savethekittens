package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.geom.Rectangle2D;

import fr.umlv.zen4.ApplicationContext;


public class Menu {
	public final float width;
	public final float height;

	public static float kWidthFifth;
	public static float kHeightEighth;

	private Menu(float width, float height) {
		this.width = width;
		this.height = height;
	}

	private void initializeMenuConstants() {
		kWidthFifth = width / 5;
		kHeightEighth = height / 8;
	}
	
	public static Menu createMenu(float width, float height) {
		Menu menu = new Menu(width, height);
		menu.initializeMenuConstants();
		return menu;
	}

	public void render(ApplicationContext context) {
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
			
			String string = "Save the Kittens";
			Rectangle2D rect = fm.getStringBounds(string, graphics);
			int textHeight = (int) (rect.getHeight()); 
			int textWidth = (int) (rect.getWidth());
			int areaHeight = (int) (4*kHeightEighth);
			int areaWidth = (int) (3*kWidthFifth);
			// Center title
			int x = (int) (kWidthFifth + (areaWidth  - textWidth)  / 2);
			int y = (int) ((areaHeight - textHeight) / 2) + fm.getAscent();
			graphics.drawString(string, x, y);
			
			
			graphics.setColor(Color.WHITE);
			areaHeight = (int) kHeightEighth;
			areaWidth = (int) (3*kWidthFifth);
			
			
			string = "PLAY";
			rect = fm.getStringBounds(string, graphics);
			textHeight = (int) (rect.getHeight()); 
			textWidth = (int) (rect.getWidth());
			// Center PLAY
			x = (int) (kWidthFifth + (areaWidth  - textWidth)  / 2);
			y = (int) (4*kHeightEighth + ((areaHeight - textHeight) / 2) + fm.getAscent());
			graphics.drawString(string, x, y);
			
			
			string = "QUIT";
			rect = fm.getStringBounds(string, graphics);
			textHeight = (int) (rect.getHeight()); 
			textWidth = (int) (rect.getWidth());
			// Center QUIT
			x = (int) (kWidthFifth + (areaWidth  - textWidth)  / 2);
			y = (int) (6*kHeightEighth + ((areaHeight - textHeight) / 2) + fm.getAscent());
			graphics.drawString(string, x, y);
			
		});
	}
	

}
