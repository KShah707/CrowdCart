package NeededFiles;
import java.util.HashMap;

import org.javatuples.*;

public class PriceData {
	HashMap<String, HashMap<String, Integer>> database;

	public void addEntry(String food, String location, Integer price) {
		if (!database.containsKey(food)) {
			database.put(food, new HashMap<String, Integer>(null));
		}
		database.get(food).put(location, price);
	}
	
	public void removeEntry(String food, String location, Integer price) {
		Pair<String, Integer> toRemove = new Pair<String, Integer>(location, price);
		database.get(food).remove(toRemove);
	}
	
	public void displayEntries(){
		for (String food : database.keySet()) {
			for (String entry : database.get(food).keySet()){
				String location = entry;
				Integer price = database.get(food).get(entry);
				System.out.println(food+" costs $"+price+" at "+location+".");
			}
				
		}
	}
	
	public int getPrice(String food, String location){
		return database.get(food).get(location);
	}
	
	public HashMap<String, HashMap<String, Integer>> getDatabase() {
		return database;
	}
	

}
