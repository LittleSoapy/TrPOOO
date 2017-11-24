import java.util.Comparator;

public class CustomComparator implements Comparator<Cromossomo> {

	public int compare(Cromossomo Crom1, Cromossomo Crom2) {
		return Crom1.getFitness().compareTo(Crom2.getFitness());
	}
}