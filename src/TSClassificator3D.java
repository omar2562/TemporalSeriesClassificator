import java.util.Scanner;

public class TSClassificator3D extends AbstractTSClassificator<Point3D> {

	@Override
	protected Point3D extractMove(Scanner scMove) {
		Point3D move = new Point3D();
		move.setX(scMove.nextFloat());
		move.setY(scMove.nextFloat());
		move.setZ(scMove.nextFloat());
		return move;
	}

	@Override
	protected double pointComparation(Point3D a, Point3D b) {
		return Math.sqrt(Math.pow(a.getX() - b.getX(), 2)
				+ Math.pow(a.getY() - b.getY(), 2)
				+ Math.pow(a.getZ() - b.getZ(), 2));
	}

}
