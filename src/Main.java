import java.io.FileNotFoundException;

public class Main {

	public final static int MOVE1D = 0;
	public final static int MOVE3D = 1;

	public static void main(String[] args) throws FileNotFoundException {
		TSClassificator1D tsClassificator1D = new TSClassificator1D();
		tsClassificator1D.fillTrainingMoves("treino.txt");
		tsClassificator1D.fillTestMoves("teste.txt");
		tsClassificator1D.solve();
		tsClassificator1D.printResults();
		/*
		TSClassificator3D tsClassificator3D = new TSClassificator3D();
		tsClassificator3D.fillTrainingMoves("treino3D.txt");
		tsClassificator3D.fillTestMoves("teste3D.txt");
		tsClassificator3D.solve();
		tsClassificator3D.printResults();*/
	}
}
