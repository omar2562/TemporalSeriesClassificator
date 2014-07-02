import java.util.Vector;

public class WiiMove<T> {
	private int wiiMoveType = -1;
	private int wiiMoveTypeAprox = -1;
	private Vector<T> moveVector = new Vector<T>();

	public WiiMove() {
	}

	public int getWiiMoveType() {
		return wiiMoveType;
	}

	public void setWiiMoveType(int wiiMoveType) {
		this.wiiMoveType = wiiMoveType;
	}

	public Vector<T> getMoveVector() {
		return moveVector;
	}

	public void setMoveVector(Vector<T> moveVector) {
		this.moveVector = moveVector;
	}

	public void addMove(T move) {
		this.moveVector.add(move);
	}

	public int getWiiMoveTypeAprox() {
		return wiiMoveTypeAprox;
	}

	public void setWiiMoveTypeAprox(int wiiMoveTypeAprox) {
		this.wiiMoveTypeAprox = wiiMoveTypeAprox;
	}

	@Override
	public String toString() {
		return "type:" + wiiMoveType + " ,size:" + moveVector.size()
				+ " ,values:" + moveVector;
	}
}
