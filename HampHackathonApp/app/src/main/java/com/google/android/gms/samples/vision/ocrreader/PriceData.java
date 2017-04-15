package com.google.android.gms.samples.vision.ocrreader;
import java.util.HashMap;

import org.javatuples.*;

public class PriceData {
	HashMap<String, HashMap<String, Double>> database;

    PriceData() {
        database = new HashMap<String, HashMap<String, Double>>();
    }

	public void addEntry(String food, String location, Double price) {
		if (!database.containsKey(food)) {
			database.put(food, new HashMap<String, Double>());
		}
		database.get(food).put(location, price);
	}
	
	public void removeEntry(String food, String location, Double price) {
		Pair<String, Double> toRemove = new Pair<String, Double>(location, price);
		database.get(food).remove(toRemove);
	}
	
	public void displayEntries(){
		for (String food : database.keySet()) {
			for (String entry : database.get(food).keySet()){
				String location = entry;
				Double price = database.get(food).get(entry);
				System.out.println(food+" costs $"+price+" at "+location+".");
			}
				
		}
	}
	
	public Double getPrice(String food, String location){
		return database.get(food).get(location);
	}
	
	public HashMap<String, HashMap<String, Double>> getDatabase() {
		return database;
	}
	

}
