package NeededFiles;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.javatuples.*;

public class BestFromList {

	public HashMap<String, HashMap<String, Integer>> data;
	public ArrayList<String> shoppinglist;
	int tradeoff;
	int samestore;
	
	public BestFromList(HashMap<String, HashMap<String, Integer>> prices, ArrayList<String> list, int tradeoff, int samestore) {
		this.data = prices;
		this.shoppinglist = list;
		this.tradeoff = tradeoff;
		this.samestore = samestore;
	}
	
	public BestPrice individualPrices() {
		BestPrice best = new BestPrice();
		
		for (String food : this.shoppinglist) {
			HashMap<String, Integer> foodlist = this.data.get(food);

			Pair<String, Integer> min = new Pair<String, Integer>(null, Integer.MAX_VALUE);
			for(String location : foodlist.keySet()) {
				Integer price = data.get(food).get(location);
				if (price < min.getValue1()) {
					min.setAt0(location);
					min.setAt1(price);
				}
			best.addTriplet(food, min.getValue0(), min.getValue1());			
			}
		}
		return best;
	}
	
	
	public BestRoute bestRoute(){
		
		ArrayList<String> nearbystores = new ArrayList<String>(null);
		
		for (String location : this.nearbyStores().keySet()) {
			nearbystores.add(location);
		}
		BestRoute best = new BestRoute();
		
		for (String location : nearbystores) {
			best.addStore(location);
		}
		
		
		int n = this.shoppinglist.size();
		int m = nearbystores.size();
		Set<String> stores = null;
		
		int[][] totalWeight = new int[n][m];
		
		// Initialize Dynamic Programming Table
		for (int j = 0; j < m; j++) {
			String location = nearbystores.get(j);
			totalWeight[0][j] = this.getScore(location, this.data.get(this.shoppinglist.get(0)).get(j), stores);
		}
		
		// Recurrence
		for (int i = 1; i < n; i++) {
			for (int j = 0; j < m; j++) {
				String location = nearbystores.get(j);
				totalWeight[i][j] = this.getScore(location, this.data.get(this.shoppinglist.get(i)).get(location), stores) + this.getDPMin(totalWeight, i-1, m, stores, nearbystores);
			}
		}
		
		// Trace Back
		for (int i = n-1; i >= 0; i--) {
			String food = shoppinglist.get(i);
			int j = getTracebackMin(totalWeight, i, m);
			best.addFood(food, nearbystores.get(j));
		}
		return best;
	}
	
	public int getTracebackMin(int[][] table, int i, int m){
		int minVal = Integer.MAX_VALUE;
		int minJ = -1;
		
		for (int j = 0; j < m; j++) {
			if (table[i][j] < minVal) {
				minVal = table[i][j];
				minJ = j;
			}
		}
		return minJ;
	}
	
	public int getDPMin(int[][] table, int i, int m, Set<String> stores, ArrayList<String> nearbystores){
		int minVal = Integer.MAX_VALUE;
		int minJ = -1;
		
		for (int j = 0; j < m; j++) {
			if (table[i][j] < minVal) {
				minVal = table[i][j];
				minJ = j;
			}
		}
		stores.add(nearbystores.get(minJ));
		return minVal;
	}
	
	public int getScore(String location, int price, Set<String> stores){
		int prescore = this.tradeoff * price * this.nearbyStores().get(location).getDistance();
		if (stores.contains(location)) {
			return prescore * this.samestore;
		}
		else {
			return prescore;
		}
		
	}
	
	public Map<String, Place> nearbyStores(){
		StoreFinder finder = new StoreFinder();
		finder.findStores();
		return finder.getIDMap();
		// Need an ArrayList of all Strings
	}
}

