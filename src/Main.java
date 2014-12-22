import java.awt.Color;

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
			menu.render(context);

		});
	}

}
