import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
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
	
	
	public ArrayList<Triplet<String, ArrayList<String>, Integer>> bestRoute(){
		ArrayList<Triplet<String, ArrayList<String>, Integer>> best = new ArrayList<Triplet<String, ArrayList<String>, Integer>>(null);
		ArrayList<String> nearbystores = this.nearbyStores();
		int n = this.shoppinglist.size();
		int m = nearbystores.size();
		
		int[][] totalWeight = new int[n][m];
		
		// Initialize Dynamic Programming Table
		for (int j = 0; j < m; j++) {
			totalWeight[0][j] = this.getScore(this.knownprices.get(shoppinglist).get(j));
		}
	
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				
			}
		}
		
		return best;
	}
	
	public int getScore(Pair<String, Integer> entry){
		return this.tradeoff * this.samestore * entry.getValue1() * this.getDistance(entry.getValue0());
	}
	
	public int getDistance(String location){
		return 10;
	}
	
	public ArrayList<String> nearbyStores(){
		return new ArrayList<String>(null);
	}
	
	public static void main(String[] args){
	}
}

