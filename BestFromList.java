import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Set;

import org.javatuples.*;

public class BestFromList {

	public HashMap<String, ArrayList<Pair<String, Integer>>> knownprices;
	public ArrayList<String> shoppinglist;
	int tradeoff;
	int samestore;
	
	public BestFromList(HashMap<String, ArrayList<Pair<String, Integer>>> prices, ArrayList<String> list, int tradeoff, int samestore) {
		this.knownprices = prices;
		this.shoppinglist = list;
		this.tradeoff = tradeoff;
		this.samestore = samestore;
	}
	
	public ArrayList<Triplet<String, String, Integer>> individualPrices() {
		ArrayList<Triplet<String, String, Integer>> best = new ArrayList<Triplet<String, String, Integer>>();
		
		for (String food : this.shoppinglist) {
			ArrayList<Pair<String, Integer>> foodlist = this.knownprices.get(food);
			
			Pair<String, Integer> min = new Pair<String, Integer>(null, Integer.MAX_VALUE);
			for(Pair<String, Integer> foodpair : foodlist) {
				String location = foodpair.getValue0();
				Integer price = foodpair.getValue1();
				if (price < min.getValue1()) {
					min.setAt0(location);
					min.setAt1(price);
				}
			best.add(new Triplet<String, String, Integer>(food, min.getValue0(), min.getValue1()));			
			}
		}
		return best;
	}
	
	
	public HashMap<String, ArrayList<String>> bestRoute(){
		
		ArrayList<String> nearbystores = this.nearbyStores();
		
		HashMap<String, ArrayList<String>> best = new HashMap<String, ArrayList<String>>(null);
		for (String location : nearbystores) {
			best.put(location, new ArrayList<String>(null));
		}
		
		
		int n = this.shoppinglist.size();
		int m = nearbystores.size();
		Set<String> stores = null;
		
		int[][] totalWeight = new int[n][m];
		
		// Initialize Dynamic Programming Table
		for (int j = 0; j < m; j++) {
			totalWeight[0][j] = this.getScore(this.knownprices.get(this.shoppinglist.get(0)).get(j), stores);
		}
		
		// Recurrence
		for (int i = 1; i < n; i++) {
			for (int j = 0; j < m; j++) {
				totalWeight[i][j] = this.getScore(this.knownprices.get(this.shoppinglist.get(i)).get(j), stores) + this.getDPMin(totalWeight, i-1, m, stores);
			}
		}
		
		// Trace Back
		for (int i = n-1; i >= 0; i--) {
			String food = shoppinglist.get(i);
			int j = getTracebackMin(totalWeight, i, m);
			best.get(nearbystores.get(j)).add(food);
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
	
	public int getDPMin(int[][] table, int i, int m, Set<String> stores){
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
	
	public int getScore(Pair<String, Integer> entry, Set<String> stores){
		int prescore = this.tradeoff * entry.getValue1() * this.getDistance(entry.getValue0());
		if (stores.contains(entry.getValue0())) {
			return prescore * this.samestore;
		}
		else {
			return prescore;
		}
		
	}
	
	public int getDistance(String location){
		return 10;
	}
	
	public ArrayList<String> nearbyStores(){
		return new ArrayList<String>(null);
	}
}

