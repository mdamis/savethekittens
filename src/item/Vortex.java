package item;

public class Vortex implements Item {
	private final int posX;
	private final int posY;

	public Vortex(int posX, int posY) {
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
