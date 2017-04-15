package NeededFiles;
import java.util.HashMap;
import org.javatuples.*;

public class BestPrice {
	HashMap<String, Pair<String, Integer>> best = new HashMap<String, Pair<String, Integer>>();
	
	public void addTriplet(String food, String location, Integer price) {
		best.put(food, new Pair<String, Integer>(location, price));
	}
	
	public Pair<String, Integer> getPrice(String food) {
		return best.get(food);
	}

}
