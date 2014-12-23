package elements.cat;

public class BasicCat implements Cat {
	private int posX;
	private int posY;
	private int speed;
	
	public BasicCat(int posX, int posY, int speed) {
		this.posX = posX;
		this.posY = posY;
		this.speed = speed;
	}

	@Override
	public int posX() {
		return posX;
	}

	@Override
	public int posY() {
		return posY;
	}

	@Override
	public int speed() {
		return speed;
	}
}
