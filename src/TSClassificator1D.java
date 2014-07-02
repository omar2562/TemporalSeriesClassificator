import java.util.Scanner;

public class TSClassificator1D extends AbstractTSClassificator<Move1D> {

	@Override
	protected Move1D extractMove(Scanner scMove) {
		Move1D move = new Move1D();
		move.setX(scMove.nextFloat());
		return move;
	}

	@Override
	protected double pointComparation(Move1D a, Move1D b) {
		return Math.pow(a.getX() - b.getX(), 2);
	}

}
