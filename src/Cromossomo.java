import java.util.ArrayList;
import java.util.Collections;

public class Cromossomo{
	private double Fitness = 0.0;
	private ArrayList<Integer> Cromossomo = new ArrayList<Integer>();

	public Cromossomo(double[][] Tabela) {
		
		//preencher o arraylist
		for (int i = 0; i < Tabela.length; i++) {
			Cromossomo.add(i);
		}
		
		Randomize(Tabela);
		
		
	}
	
	public Cromossomo(int[] Cromo, double[][] Tabela) {
		
		//preencher o arraylist
		for (int i = 0; i < Tabela.length; i++) {
			Cromossomo.add(Cromo[i]);
		}
		
		calcularFitness(Tabela);
		
		
	}
	
	
	public void Randomize(double[][] Tabela){
		//randomizar o arraylist
		Collections.shuffle(Cromossomo);
		
		calcularFitness(Tabela);
		
	}
	
	public void calcularFitness(double[][] Tabela) {
		//calcular o fitness do array
		for (int i = 0; i < Tabela.length; i++) {
			if (i == Tabela.length - 1) {
				Fitness += Tabela[Cromossomo.get(i)][Cromossomo.get(0)];
			} else {
				Fitness += Tabela[Cromossomo.get(i)][Cromossomo.get(i + 1)];
			}
		}
	}
	
	public Double getFitness(){
		return Fitness;
	}
	
	public ArrayList<Integer> getCromossomo(){
		return Cromossomo;
	}

}
