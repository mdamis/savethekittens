package parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import elements.barrel.Barrel;
import elements.game.Level;
import fr.umlv.zen4.ApplicationContext;

public class Parser {
	public static final int SPLITS_WALL = 3;
	public static final int SPLITS_NET = 3;
	public static final int SPLITS_BARREL = 6;
	public static final int SPLITS_CAT = 2;
	
	/**
	 * Parses a file describing a level.
	 * @param levelNumber number of the file to load.
	 * @param context current context.
	 * @param width width of the current window.
	 * @param height height of the current window.
	 * @return the new Level.
	 * @throws IOException if the file contains an error.
	 */
	public static Level parseLevel(int levelNumber, ApplicationContext context, float width, float height) throws IOException {
		
		String path = "levels/level";
		path += String.valueOf(levelNumber) + ".txt";
		
		Level level;
		
		try(BufferedReader br = Files.newBufferedReader(Paths.get(path), StandardCharsets.UTF_8)) {
			
			String line = br.readLine();
			String[] splits = line.split(" ");
			
			int nbWalls = Integer.parseInt(splits[0]);
			int nbNets = Integer.parseInt(splits[1]);
			int nbBarrels = Integer.parseInt(splits[2]);
			int nbCats = Integer.parseInt(splits[3]);
			int nbBombs = Integer.parseInt(splits[4]);
			
			if(nbNets > nbCats) {
				throw new IOException("Wrong file format");
			}
			
			if(nbBombs != 1 && nbBombs != 0) {
				throw new IOException("Wrong file format");
			}
			
			level = Level.createLevel(context, width, height, nbBombs);
			level.createLevelBorders();
			
			for(int i=0; i<nbWalls; i++) {
				line = br.readLine();
				splits = line.split(" ");
				
				if(splits.length != SPLITS_WALL || splits[0].compareToIgnoreCase("wall") != 0) {
					throw new IOException("Wrong file format");
				}
				
				float x = Float.parseFloat(splits[1]);
				float y = Float.parseFloat(splits[2]);
				
				level.createLevelWall(x, y);
			}
			
			for(int i=0; i<nbNets; i++) {
				line = br.readLine();
				splits = line.split(" ");
				
				if(splits.length != SPLITS_NET || splits[0].compareToIgnoreCase("net") != 0) {
					throw new IOException("Wrong file format");
				}
				
				float x = Float.parseFloat(splits[1]);
				float y = Float.parseFloat(splits[2]);
				
				level.createLevelNet(x, y);
			}
			
			int nbCatsTotal = 0;
			
			for(int i=0; i<nbBarrels; i++) {
				line = br.readLine();
				splits = line.split(" ");
				
				if(splits.length != SPLITS_BARREL || splits[0].compareToIgnoreCase("barrel") != 0) {
					throw new IOException("Wrong file format");
				}
				
				String type = splits[1];
				int nbCatsBarrel = Integer.parseInt(splits[2]);
				float x = Float.parseFloat(splits[3]);
				float y = Float.parseFloat(splits[4]);
				String angleString = splits[5];
				
				Barrel barrel;
				
				switch(type) {
				case "Single":
					barrel = level.createLevelSingleBarrel(x, y, angleString);
					break;
				case "Automatic":
					barrel = level.createLevelAutomaticBarrel(x, y, angleString);
					break;
				default:
					throw new IOException("Wrong file format");
				}	
				
				nbCatsTotal += nbCatsBarrel;
				
				for(int j=0; j<nbCatsBarrel; j++) {
					line = br.readLine();
					splits = line.split(" ");
					
					if(splits.length != SPLITS_CAT || splits[0].compareToIgnoreCase("cat") != 0) {
						throw new IOException("Wrong file format");
					}
					
					String catType = splits[1];
					level.addCatBarrel(barrel, catType);
				}
				
			}
			
			if(nbCats != nbCatsTotal) {
				throw new IOException("Wrong file format");
			}
			
		} catch(IOException e) {
			throw e;
		}
		
		return level;
	}
	
}
