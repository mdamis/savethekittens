import java.awt.Color;
import java.util.ArrayList;

import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.World;

import elements.Wall;
import elements.cat.Cat;
import elements.game.Level;
import fr.umlv.zen4.Application;
import fr.umlv.zen4.ScreenInfo;
import gui.GameUI;
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
			
			Level level = new Level();
			World world = level.getWorld();
			level.createLevelBorders();
			level.createLevelBasicCat(25.0f, 25.0f);
			
			for(Body body = world.getBodyList(); body != null; body = body.m_next) {
				System.out.println(body.getPosition());
			}
			System.out.println(world.getBodyCount());
			
			GameUI gameUI = GameUI.createGameUI(width, height);

			ArrayList<Wall> walls = level.getWalls();
			ArrayList<Cat> cats = level.getCats();
			gameUI.render(context, cats, walls);
		});
	}

}
