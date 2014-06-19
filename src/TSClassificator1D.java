import java.util.Scanner;

public class TSClassificator1D extends AbstractTSClassificator<Move1D> {

	@Override
	protected Move1D extractMove(Scanner scMove) {
		Move1D move = new Move1D();
		move.setX(scMove.nextFloat());
		return move;
	}

}
