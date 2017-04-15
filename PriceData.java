import java.util.Collections;
import java.util.HashMap;

import org.javatuples.*;

public class PriceData {
	HashMap<String, HashMap<Place, Integer>> database;

	public void addEntry(String food, Place location, Integer price) {
		if (!database.containsKey(food)) {
			database.put(food, new HashMap<Place, Integer>(null));
		}
		database.get(food).put(location, price);
	}
	
	public void removeEntry(String food, Place location, Integer price) {
		Pair<Place, Integer> toRemove = new Pair<Place, Integer>(location, price);
		database.get(food).remove(toRemove);
	}
	
	public void displayEntries(){
		for (String food : database.keySet()) {
			for (Place entry : database.get(food).keySet()){
				Place location = entry;
				Integer price = database.get(food).get(entry);
				System.out.println(food+" costs $"+price+" at "+location+".");
			}
				
		}
	}
	
	public int getPrice(String food, Place location){
		return database.get(food).get(location);
	}
	
	public HashMap<String, HashMap<Place, Integer>> getDatabase() {
		return database;
	}
	

}
