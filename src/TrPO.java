import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class TrPO {

	static int TamanhoDaPopulacao = 80;
	static int CriterioDeParada = 40;

	static double TaxaDeCrossover = 0.75;
	static double TaxaDeMutacao = 0.10;

	static Random NumeroRandomico = new Random();
	static ArrayList<Cromossomo> populacao = new ArrayList<Cromossomo>();

	static double[][] Tabela = null;

	public static void main(String[] args) {
		// LerArquivo Arq = new LerArquivo();
		Tabela = new LerArquivo().lerArquivoTxT();

		criarPoP(Tabela);
		Collections.sort(populacao, new CustomComparator());
		rotina();
	}

	private static void rotina() {
		double BetterFitness = Double.MAX_VALUE;// melhor resultado
		int Parada = 0;
		while (Parada < CriterioDeParada) {
			double Fitness = 0;
			// Roleta
			ArrayList<Cromossomo> escolhidos = new ArrayList<Cromossomo>();

			for (int i = 0; i < TamanhoDaPopulacao; i++) {

				double sum = 0;
				double sumInvert = 0;
				double NumeroRoleta = 0;
				for (Cromossomo cromossomo : populacao) {
					sum += cromossomo.getFitness();
				}
				for (Cromossomo cromossomo : populacao) {
					sumInvert += sum / cromossomo.getFitness();
				}

				NumeroRoleta = NumeroRandomico.nextDouble() * (sumInvert - 0) + 0;

				for (Cromossomo cromossomo : populacao) {
					if (NumeroRoleta < sum / cromossomo.getFitness()) {
						escolhidos.add(cromossomo);
						populacao.remove(cromossomo);
						break;
					} else {
						NumeroRoleta -= sum / cromossomo.getFitness();
					}
				}

			}
			// Fim Roleta

			// Crossover
			ArrayList<Cromossomo> filhos = new ArrayList<Cromossomo>();

			for (int casal = 0; casal < escolhidos.size(); casal += 2) {
				if (NumeroRandomico.nextDouble() < TaxaDeCrossover) {
					for (int filho = 0; filho < 2; filho++) {
						int[] mask = new int[Tabela.length];
						int[] newCromo = new int[Tabela.length];
						ArrayList<Integer> sobrando = new ArrayList<Integer>();

						for (int i = 0; i < newCromo.length; i++) {
							newCromo[i] = -2;
						}
						for (int i = 0; i < mask.length; i++) {
							mask[i] = NumeroRandomico.nextInt(2);
						}

						for (int i = 0; i < newCromo.length; i++) {
							if (mask[i] == 1) {
								newCromo[i] = escolhidos.get(casal).getCromossomo().get(i);
							} else {
								newCromo[i] = -1;
								sobrando.add(escolhidos.get(casal).getCromossomo().get(i));
							}
						}

						for (int i = 0; i < newCromo.length; i++) {
							if (newCromo[i] == -1) {
								boolean aux = true;
								for (int j = 0; j < newCromo.length; j++) {
									if (escolhidos.get(casal + 1).getCromossomo().get(i) == newCromo[j]) {
										aux = false;
										break;
									}
								}
								if (aux) {
									newCromo[i] = escolhidos.get(casal + 1).getCromossomo().get(i);
									for (int j = 0; j < sobrando.size(); j++) {
										if (escolhidos.get(casal + 1).getCromossomo().get(i) == sobrando.get(j)) {
											sobrando.remove(j);
											break;
										}
									}

								}
							}

						}
						Collections.shuffle(sobrando);
						for (int i = 0; i < newCromo.length; i++) {
							if (newCromo[i] == -1) {
								newCromo[i] = sobrando.remove(0);
							}
						}
						filhos.add(new Cromossomo(newCromo, Tabela));
					}
				}
			}
			// Fim Crossover

			// Mutacao
			for (Cromossomo cromossomo : filhos) {
				if (NumeroRandomico.nextDouble() < TaxaDeMutacao) {
					cromossomo.Randomize(Tabela);// inversao
				}
			}
			// Fim Mutacao

			for (Cromossomo cromossomo : escolhidos) {
				populacao.add(cromossomo);
			}
			for (Cromossomo cromossomo : filhos) {
				populacao.add(cromossomo);
			}

			Collections.sort(populacao, new CustomComparator());
			for (int i = 0; i < populacao.size(); i++) {
				if (i >= TamanhoDaPopulacao) {
					populacao.remove(i);
				}
			}

			Fitness = populacao.get(0).getFitness();

			if (Fitness < BetterFitness) {
				BetterFitness = Fitness;
				Parada = 0;
				System.out.println(BetterFitness);
			} else {
				Parada++;
			}
		}
		for (int i = 0; i < Tabela.length; i++) {
			System.out.print(populacao.get(0).getCromossomo().get(i) + " ");
		}
	}

	public static void criarPoP(double[][] Arq) {
		for (int i = 0; i < TamanhoDaPopulacao; i++) {
			populacao.add(new Cromossomo(Arq));
		}
	}

}
