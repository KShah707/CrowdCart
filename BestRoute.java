import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import org.javatuples.*;

public class BestRoute {
	HashMap<Place, ArrayList<String>> best = new HashMap<Place, ArrayList<String>>(null);
	
	public void addStore(Place location) {
		best.put(location, new ArrayList<String>(null));
	}
	
	public void addFood(String food, Place location) {
		best.get(location).add(food);
	}

	
}
