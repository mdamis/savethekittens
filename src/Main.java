import java.awt.Color;
import java.io.IOException;

import parser.Parser;
import elements.game.Level;
import fr.umlv.zen4.Application;
import fr.umlv.zen4.ScreenInfo;
import gui.Menu;

public class Main {

	public static void main(String[] args) {
		Application.run(Color.BLACK, context -> {

			ScreenInfo screenInfo = context.getScreenInfo();
			float width = screenInfo.getWidth();
			float height = screenInfo.getHeight();
			System.out.println("size of the screen (" + width + " x " + height + ")");

			Menu menu = Menu.createMenu(width, height);
			
			for(;;) {
				int levelNumber = menu.menuSelection(context);
				Level level = null;;
				
				try {
					level = Parser.parseLevel(levelNumber, context, width, height);
				} catch (IOException e) {
					e.printStackTrace();
				}
				level.update();	
			}
			
		});
	}

}
