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

	private int[][] results = new int[2][12];

	private int sakoeChibaBand;

	public AbstractTSClassificator() {
		Arrays.fill(results[0], 0);
		Arrays.fill(results[1], 0);
		sakoeChibaBand = 100;
	}

	public void fillTrainingMoves(String trainingFile)
			throws FileNotFoundException {
		fillVectorMoves(trainingMoveVector, trainingFile, false);
	}

	public void fillTestMoves(String testFile) throws FileNotFoundException {
		fillVectorMoves(testMoveVector, testFile, true);
	}

	public void fillVectorMoves(Vector<WiiMove<T>> vector, String fileName,
			boolean saveResults) throws FileNotFoundException {
		Scanner sc = new Scanner(new File(fileName));
		Scanner scMove;
		WiiMove<T> move;
		while (sc.hasNext()) {
			String line = sc.nextLine();
			scMove = new Scanner(line).useDelimiter(" ");
			scMove.useLocale(Locale.US);
			move = new WiiMove<T>();
			move.setWiiMoveType(scMove.nextInt());
			if (saveResults)
				results[0][move.getWiiMoveType() - 1]++;
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
			// System.err.println("====== "+move.getWiiMoveType()+" ======");
			for (WiiMove<T> trainTest : trainingMoveVector) {
				matrix = new double[move.getMoveVector().size() + 1][trainTest
						.getMoveVector().size() + 1];
				// fillEmptyMatrix();
				// fillBaseCase(move, trainTest);
				fillMatrixMatrix(move, trainTest);
				// printMatrix();
				distance = dtw(move, move.getMoveVector().size(), trainTest,
						trainTest.getMoveVector().size());
				// printMatrix();
				// System.out.println("calculado...."+ distance);
				if (Math.abs(distance - distanceMin) < 0.000001) {
					// System.err.println("equal " + trainTest.getWiiMoveType()
					// + ","+approximateMove.getWiiMoveType());
				}
				if (distance < distanceMin) {
					// System.err.println("diff " + trainTest.getWiiMoveType() +
					// ","+approximateMove.getWiiMoveType());
					approximateMove = trainTest;
					distanceMin = distance;
				}
			}
			move.setWiiMoveTypeAprox(approximateMove.getWiiMoveType());
			if (move.getWiiMoveType() != move.getWiiMoveTypeAprox()) {
				falsePositive++;
				results[1][move.getWiiMoveType() - 1]++;
				// System.err.println("KO");
			} else {
				// System.err.println("OK");
			}
			// System.out.println("falso positivo:" + falsePositive
			// +"  ---- tipo O: "+move.getWiiMoveType()+" N:"+
			// move.getWiiMoveTypeAprox());
		}
	}

	private void fillMatrixMatrix(WiiMove<T> move, WiiMove<T> trainTest) {
		int minPossibleValue, maxPossibleValue;
		int N = move.getMoveVector().size();
		int M = trainTest.getMoveVector().size();
		int T = (sakoeChibaBand * (N <= M ? N - 1 : M)) / 100;
		Arrays.fill(matrix[0], Double.MAX_VALUE);
		int negativeNumber;
		for (int i = 1; i < matrix.length; i++) {
			minPossibleValue = (int) Math.floor((1. * (M - T) / (N - T))
					* (i - T));
			minPossibleValue = minPossibleValue <= 0 ? 1 : minPossibleValue;
			maxPossibleValue = (int) Math
					.ceil((1. * (M - T) / (N - T)) * i + T);
			maxPossibleValue = maxPossibleValue > M ? M : maxPossibleValue;
			negativeNumber = 0;
			for (int j = 0; j < matrix[0].length; j++) {
				if (j < minPossibleValue || j > maxPossibleValue)
					matrix[i][j] = Double.MAX_VALUE;
				else {
					matrix[i][j] = -1;
					negativeNumber++;
				}
			}
			if (negativeNumber == 0)
				System.out.println("Matrix Incompleat:" + i + " ,type"
						+ move.getWiiMoveType());
		}
		matrix[0][0] = 0;
	}

	private void fillBaseCase(WiiMove<T> a, WiiMove<T> b) {
		T zero = (T) a.getMoveVector().firstElement().getZero();
		matrix[0][0] = 0;
		for (int i = 1; i < matrix.length; i++) {
			matrix[i][0] = Double.MAX_VALUE;// a.getMoveVector().get(i -
											// 1).difference(zero);
		}
		for (int i = 1; i < matrix[0].length; i++) {
			matrix[0][i] = Double.MAX_VALUE;// b.getMoveVector().get(i -
											// 1).difference(zero);
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
		// printMatrix();
		// System.out.println("--------------------------------------------------------------------------");
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
				if (matrix[i][j] != Double.MAX_VALUE)
					System.out.format("%06.3f |", matrix[i][j]);
				else
					System.out.print("  inf  |");
			}
			System.out.println();
		}
	}

	public void printResults() {
		String outputHeadFormat = "result training:%d test:%d BandPrec:%d%n";
		//outputHeadFormat = "training: %d, test: %d BandPrec: %d%n";
		System.out.format(outputHeadFormat,
				trainingMoveVector.size(), testMoveVector.size(),
				sakoeChibaBand);
		int falseP = 0, total = 0;
		// String outputFormat =
		// "type: %d ,false+: %d ,total: %d ,percentage: %.2f%n";
		String outputFormat = "%d %d %d %.2f%n";
		//String outputResultFormat = "general: ,false+: %d ,total: %d ,percentage: %.2f%n";
		String outputResultFormat = "general %d %d %.2f%n";
		for (int i = 0; i < results[0].length; i++) {
			falseP += results[1][i];
			total += results[0][i];
			System.out.format(outputFormat, i + 1, results[1][i],
					results[0][i], 100.0 * results[1][i] / results[0][i]);
		}
		System.out.format(
				outputResultFormat, falseP,
				total, 100.0 * falseP / total);
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
		return 100.0 * falsePositive / trainingMoveVector.size();
	}

	public int getSakoeChibaBand() {
		return sakoeChibaBand;
	}

	public void setSakoeChibaBand(int sakoeChibaBand) {
		this.sakoeChibaBand = sakoeChibaBand;
	}

}
