import java.util.Scanner;


public class TSClassificator3D extends AbstractTSClassificator<Move3D>{

	@Override
	protected Move3D extractMove(Scanner scMove) {
		Move3D move = new Move3D();
		move.setX(scMove.nextFloat());
		move.setY(scMove.nextFloat());
		move.setZ(scMove.nextFloat());
		return move;
	}

}
