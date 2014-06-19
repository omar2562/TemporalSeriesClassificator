import java.io.FileNotFoundException;

public class Main {

	public final static int MOVE1D = 0;
	public final static int MOVE3D = 1;

	public static void main(String[] args) throws FileNotFoundException {
		/*TSClassificator1D tsClassificator1D = new TSClassificator1D();
		tsClassificator1D.fillTrainingMoves("treino.txt");
		tsClassificator1D.fillTestMoves("teste.txt");
		tsClassificator1D.solve();
		System.out.println("test: "
				+ tsClassificator1D.getTestMoveVector().size());
		System.out.println("training: "
				+ tsClassificator1D.getTrainingMoveVector().size());		
		System.out.println("Falso positivo:" + tsClassificator1D.getFalsePositive());
		System.out.println("Porcentaje Coincidencia:" + tsClassificator1D.getAccertPercentage());
		*/
		TSClassificator3D tsClassificator3D = new TSClassificator3D();
		tsClassificator3D.fillTrainingMoves("treino3D.txt");
		tsClassificator3D.fillTestMoves("teste3D.txt");
		System.out.println("test3D: "
				+ tsClassificator3D.getTestMoveVector().size());
		System.out.println("training3D: "
				+ tsClassificator3D.getTrainingMoveVector().size());
		tsClassificator3D.solve();
		System.out.println("Falso positivo:" + tsClassificator3D.getFalsePositive());
		System.out.println("Porcentaje Coincidencia:" + tsClassificator3D.getAccertPercentage());
	}
}
