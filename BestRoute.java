package NeededFiles;
import java.util.ArrayList;
import java.util.HashMap;

public class BestRoute {
	HashMap<String, ArrayList<String>> best = new HashMap<String, ArrayList<String>>(null);
	
	public void addStore(String location) {
		best.put(location, new ArrayList<String>(null));
	}
	
	public void addFood(String food, String location) {
		best.get(location).add(food);
	}

	public HashMap<String, ArrayList<String>> getBest() {
		return best;
	}
	
}
