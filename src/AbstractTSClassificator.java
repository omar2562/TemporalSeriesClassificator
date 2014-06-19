import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Locale;
import java.util.Scanner;
import java.util.Vector;

public abstract class AbstractTSClassificator<T extends MoveInterface> {
	private Vector<WiiMove<T>> trainingMoveVector = new Vector<WiiMove<T>>();
	private Vector<WiiMove<T>> testMoveVector = new Vector<WiiMove<T>>();

	private double[][] matrix;
	private int falsePositive = 0;

	public AbstractTSClassificator() {
	}

	public void fillTrainingMoves(String trainingFile)
			throws FileNotFoundException {
		fillVectorMoves(trainingMoveVector, trainingFile);
	}

	public void fillTestMoves(String testFile) throws FileNotFoundException {
		fillVectorMoves(testMoveVector, testFile);
	}

	public void fillVectorMoves(Vector<WiiMove<T>> vector, String fileName)
			throws FileNotFoundException {
		Scanner sc = new Scanner(new File(fileName));
		Scanner scMove;
		WiiMove<T> move;
		while (sc.hasNext()) {
			String line = sc.nextLine();
			scMove = new Scanner(line).useDelimiter(" ");
			scMove.useLocale(Locale.US);
			move = new WiiMove<T>();
			move.setWiiMoveType(scMove.nextInt());
			while (scMove.hasNext()) {
				move.addMove(extractMove(scMove));
			}
			vector.add(move);
			scMove.close();
		}
		sc.close();
	}

	protected abstract T extractMove(Scanner scMove);

	public void solve() {
		double distance, distanceMin;
		WiiMove<T> approximateMove;
		for (WiiMove<T> move : testMoveVector) {
			approximateMove = new WiiMove<T>();
			distanceMin = Float.MAX_VALUE;
			//System.err.print("====== "+move.getWiiMoveType()+" ======");
			for (WiiMove<T> trainTest : trainingMoveVector) {
				matrix = new double[move.getMoveVector().size() + 1][trainTest
						.getMoveVector().size() + 1];
				fillEmptyMatrix();
				fillBaseCase(move,trainTest);
				distance = dtw(move, move.getMoveVector().size(), trainTest,
						trainTest.getMoveVector().size());
				//printMatrix();
				//System.out.println("calculado...."+ distance);
				if (Math.abs(distance - distanceMin) < 0.000001) {
				    //System.err.println("equal " + trainTest.getWiiMoveType() + ","+approximateMove.getWiiMoveType());
				}
				if (distance < distanceMin) {
					//System.err.println("diff " + trainTest.getWiiMoveType() + ","+approximateMove.getWiiMoveType());
					approximateMove = trainTest;
					distanceMin = distance;
				}
			}
			move.setWiiMoveTypeAprox(approximateMove.getWiiMoveType());
			if (move.getWiiMoveType() != move.getWiiMoveTypeAprox()){
				falsePositive++;
				//System.err.println("KO");
			}else{
				//System.err.println("OK");
			}
			//System.out.println("falso positivo:" + falsePositive +"  ---- tipo O: "+move.getWiiMoveType()+" N:"+ move.getWiiMoveTypeAprox());
		}
	}

	private void fillBaseCase(WiiMove<T> a,WiiMove<T> b) {
		T zero = (T) a.getMoveVector().firstElement().getZero();
		matrix[0][0] = 0;
		for (int i = 1; i < matrix.length; i++) {
			matrix[i][0] = a.getMoveVector().get(i-1).difference(zero);
		}
		for (int i = 1; i < matrix[0].length; i++) {
			matrix[0][i] = b.getMoveVector().get(i-1).difference(zero);
		}
	}

	private double dtw(WiiMove<T> a, int i, WiiMove<T> b, int j) {
		if (matrix[i][j] != -1)
			return matrix[i][j];
		T ta = a.getMoveVector().get(i - 1);
		T tb = b.getMoveVector().get(j - 1);
		 
		matrix[i][j] = ta.difference(tb)
				+ Math.min(dtw(a, i - 1, b, j - 1),
						Math.min(dtw(a, i, b, j - 1), dtw(a, i - 1, b, j)));
		//printMatrix();
		//System.out.println("--------------------------------------------------------------------------");
		return matrix[i][j];
	}

	private void fillEmptyMatrix() {
		for (int i = 0; i < matrix.length; i++) {
			Arrays.fill(matrix[i], -1);
		}
	}

	private void printMatrix() {
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[0].length; j++) {
				System.out.format("%06.3f |",matrix[i][j]);
			}
			System.out.println();
		}
	}

	public Vector<WiiMove<T>> getTrainingMoveVector() {
		return trainingMoveVector;
	}

	public void setTrainingMoveVector(Vector<WiiMove<T>> trainingMoveVector) {
		this.trainingMoveVector = trainingMoveVector;
	}

	public Vector<WiiMove<T>> getTestMoveVector() {
		return testMoveVector;
	}

	public void setTestMoveVector(Vector<WiiMove<T>> testMoveVector) {
		this.testMoveVector = testMoveVector;
	}

	public int getFalsePositive() {
		return falsePositive;
	}

	public double getAccertPercentage() {
		return 100.0 - (100.0*falsePositive / testMoveVector.size());
	}

}
