package game;

import java.util.List;

import cat.Cat;
import item.Item;

public class Level {
	private final int width;
	private final int height;
	private final List<Cat> cats;
	private final List<Item> items;
	
	public Level(int width, int height, List<Cat> cats, List<Item> items) {
		this.width = width;
		this.height = height;
		this.cats = cats;
		this.items = items;
	}
	
}
