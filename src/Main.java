import java.awt.Color;

import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.World;

import elements.Wall;
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
			menu.render(context);
			
			Level level = new Level(20, 20);
			World world = level.getWorld();
			Wall.createStandardWall(world, 1.0f, 1.0f);
			Wall.createStandardWall(world, 1.0f, 3.0f);
			for(Body body = world.getBodyList(); body != null; body = body.m_next) {
				System.out.println(body.getPosition());
			}
		});
	}

}
