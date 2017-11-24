import java.awt.Point;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Scanner;

public class LerArquivo {
	private Scanner ler;
	private BufferedReader lerArq;

	public double[][] lerArquivoTxT() {
		ler = new Scanner(System.in);

		System.out.printf("Informe o nome de arquivo texto:\n");
		String nome = "30CIT.txt";
		//String nome = ler.nextLine();

		try {

			FileReader arq = new FileReader(nome);
			lerArq = new BufferedReader(arq);

			String linha = lerArq.readLine();
			int numCid = Integer.parseInt(linha);
			Point[] Cidades = new Point[numCid];
			double[][] CidadesTab = new double[numCid][numCid];

			linha = lerArq.readLine();
			int count = 0;
			while (linha != null) {
				String[] parts = linha.split(" ");
				Cidades[count] = new Point(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]));
				count++;
				linha = lerArq.readLine();
			}
			
			for (int i = 0; i < CidadesTab.length; i++) {
				for (int j = 0; j <= i; j++) {
					if (i != j) {
						double result = Math.sqrt(
								Math.pow(Cidades[i].x - Cidades[j].x, 2) + Math.pow(Cidades[i].y - Cidades[j].y, 2));
						CidadesTab[i][j] = result;
						CidadesTab[j][i] = result;
					} else {
						CidadesTab[i][j] = 0;
					}

				}

			}
			
			return CidadesTab;
			
		} catch (Exception e) {
			System.err.printf("Erro na abertura do arquivo: %s.\n", e.getMessage());
		}
		return null;
	}

}
