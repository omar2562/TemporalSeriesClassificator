public class Point1D {
	protected float x;

	public Point1D() {
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	@Override
	public String toString() {
		return x + "";
	}
}
