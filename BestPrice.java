package NeededFiles;
import java.util.HashMap;
import org.javatuples.*;

public class BestPrice {
	HashMap<String, Pair<String, Double>> best = new HashMap<String, Pair<String, Double>>();
	
	public void addTriplet(String food, String location, Double price) {
		best.put(food, new Pair<String, Double>(location, price));
	}
	
	public Pair<String, Double> getPrice(String food) {
		return best.get(food);
	}

}
