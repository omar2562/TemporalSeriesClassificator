public class Move1D implements MoveInterface {
	protected float x;

	public Move1D() {
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	@Override
	public double difference(MoveInterface move) {
		Move1D moveDiff = (Move1D) move;
		return Math.pow(this.x - moveDiff.x,2);
	}

	@Override
	public String toString() {
		return x + "";
	}

	@Override
	public MoveInterface getZero() {
		Move1D zero = new Move1D();
		zero.x = 0.0f;
		return zero;
	}
}
