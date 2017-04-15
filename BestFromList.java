import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Set;

import org.javatuples.*;

public class BestFromList {

	public HashMap<String, HashMap<Place, Integer>> data;
	public ArrayList<String> shoppinglist;
	int tradeoff;
	int samestore;
	
	public BestFromList(HashMap<String, HashMap<Place, Integer>> prices, ArrayList<String> list, int tradeoff, int samestore) {
		this.data = prices;
		this.shoppinglist = list;
		this.tradeoff = tradeoff;
		this.samestore = samestore;
	}
	
	public BestPrice individualPrices() {
		BestPrice best = new BestPrice();
		
		for (String food : this.shoppinglist) {
			HashMap<Place, Integer> foodlist = this.data.get(food);
			
			Pair<Place, Integer> min = new Pair<Place, Integer>(null, Integer.MAX_VALUE);
			for(Place location : foodlist.keySet()) {
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
		
		ArrayList<Place> nearbystores = this.nearbyStores();
		BestRoute best = new BestRoute();
		
		for (Place location : nearbystores) {
			best.addStore(location);
		}
		
		
		int n = this.shoppinglist.size();
		int m = nearbystores.size();
		Set<Place> stores = null;
		
		int[][] totalWeight = new int[n][m];
		
		// Initialize Dynamic Programming Table
		for (int j = 0; j < m; j++) {
			Place location = nearbystores.get(j);
			totalWeight[0][j] = this.getScore(location, this.data.get(this.shoppinglist.get(0)).get(j), stores);
		}
		
		// Recurrence
		for (int i = 1; i < n; i++) {
			for (int j = 0; j < m; j++) {
				Place location = nearbystores.get(j);
				totalWeight[i][j] = this.getScore(location, this.data.get(this.shoppinglist.get(i)).get(location), stores) + this.getDPMin(totalWeight, i-1, m, stores);
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
	
	public int getDPMin(int[][] table, int i, int m, Set<Place> stores){
		int minVal = Integer.MAX_VALUE;
		int minJ = -1;
		
		for (int j = 0; j < m; j++) {
			if (table[i][j] < minVal) {
				minVal = table[i][j];
				minJ = j;
			}
		}
		stores.add(this.nearbyStores().get(minJ));
		return minVal;
	}
	
	public int getScore(Place location, int price, Set<Place> stores){
		int prescore = this.tradeoff * price * location.getDistance();
		if (stores.contains(location)) {
			return prescore * this.samestore;
		}
		else {
			return prescore;
		}
		
	}
	
	public ArrayList<Place> nearbyStores(){
		return new ArrayList<Place>(null);
		// Need an ArrayList of all places
	}
}

