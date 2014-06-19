public class Move3D extends Move1D implements MoveInterface {
	private float y;
	private float z;

	public Move3D() {
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public float getZ() {
		return z;
	}

	public void setZ(float z) {
		this.z = z;
	}
	
	@Override
	public double difference(MoveInterface move) {
		Move3D moveDiff = (Move3D) move;
		return Math.sqrt(Math.pow(super.x - moveDiff.x, 2)+Math.pow(this.y - moveDiff.y, 2)+Math.pow(this.z - moveDiff.z, 2));
	}
	@Override
	public MoveInterface getZero() {
		Move3D zero = new Move3D();
		zero.x = zero.y = zero.z = 0.0f;
		return zero;
	}

}
