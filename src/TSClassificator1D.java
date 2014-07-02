import java.util.Scanner;

public class TSClassificator1D extends AbstractTSClassificator<Point1D> {

	@Override
	protected Point1D extractMove(Scanner scMove) {
		Point1D move = new Point1D();
		move.setX(scMove.nextFloat());
		return move;
	}

	@Override
	protected double pointComparation(Point1D a, Point1D b) {
		return Math.pow(a.getX() - b.getX(), 2);
	}

}
