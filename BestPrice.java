import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import org.javatuples.*;

public class BestPrice {
	HashMap<String, Pair<Place, Integer>> best = new HashMap<String, Pair<Place, Integer>>();
	
	public void addTriplet(String food, Place location, Integer price) {
		best.put(food, new Pair<Place, Integer>(location, price));
	}
	
	public Pair<Place, Integer> getPrice(String food) {
		return best.get(food);
	}
}
