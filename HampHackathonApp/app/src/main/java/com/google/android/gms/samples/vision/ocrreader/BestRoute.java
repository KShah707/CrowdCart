package com.google.android.gms.samples.vision.ocrreader;
import java.util.ArrayList;
import java.util.HashMap;

public class BestRoute {
	HashMap<String, ArrayList<String>> best = new HashMap<String, ArrayList<String>>();
	
	public void addStore(String location) {
		best.put(location, new ArrayList<String>());
	}
	
	public void addFood(String food, String location) {
		best.get(location).add(food);
	}

	public HashMap<String, ArrayList<String>> getBest() {
		return best;
	}
	
}
