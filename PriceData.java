import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import org.javatuples.*;

public class PriceData {
	HashMap<String, ArrayList<Pair<String, Integer>>> database;

	public void addEntry(String food, String location, Integer price) {
		if (!database.containsKey(food)) {
			database.put(food, new ArrayList<Pair<String, Integer>>(null));
		}
		database.get(food).add(new Pair<String, Integer>(location, price));
	}
	
	public void removeEntry(String food, String location, Integer price) {
		Pair<String, Integer> toRemove = new Pair<String, Integer>(location, price);
		database.get(food).remove(toRemove);
	}
	
	public void displayEntries(){
		for (String food : database.keySet()) {
			for (Pair<String, Integer> entry : database.get(food)){
				String location = entry.getValue0();
				String price = entry.getValue1().toString();
				System.out.println(food+" costs $"+price+" at "+location+".");
			}
				
		}
	}

}
