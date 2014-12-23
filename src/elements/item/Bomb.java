package elements.item;

public class Bomb implements Item {
	private final int posX;
	private final int posY;

	public Bomb(int posX, int posY) {
		this.posX = posX;
		this.posY = posY;
	}

	@Override
	public int posX() {
		return posX;
	}

	@Override
	public int posY() {
		return posY;
	}

}
