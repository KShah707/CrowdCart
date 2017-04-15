package com.google.android.gms.samples.vision.ocrreader;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.javatuples.*;

public class BestFromList {

	public HashMap<String, HashMap<String, Double>> data;
	public ArrayList<String> shoppinglist;
	int tradeoff;
	int samestore;
	
	public BestFromList(HashMap<String, HashMap<String, Double>> prices, ArrayList<String> list, int tradeoff, int samestore) {
		this.data = prices;
		this.shoppinglist = list;
		this.tradeoff = tradeoff;
		this.samestore = samestore;
	}
	
	public BestPrice individualPrices() {
		BestPrice best = new BestPrice();
		
		for (String food : this.shoppinglist) {
			HashMap<String, Double> foodlist = this.data.get(food);

			Pair<String, Double> min = new Pair<String, Double>(null, Double.MAX_VALUE);
			for(String location : foodlist.keySet()) {
				Double price = data.get(food).get(location);
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

        Log.w("printout", String.valueOf(this.nearbyStores()));

        ArrayList<String> nearbystores = new ArrayList<String>();
        for (String location : this.nearbyStores().keySet()) {
            nearbystores.add(location);
        }
		Log.w("added stores", "true");
		BestRoute best = new BestRoute();
		Log.w("init bestroute", "true");
		for (String location : nearbystores) {
			best.addStore(location);
			Log.w("best added location:", location);
		}


		int n = this.shoppinglist.size();
		Log.w("shoppinglist size", String.valueOf(n));
		int m = nearbystores.size();
		Log.w("shoppinglist size", String.valueOf(m));
		Set<String> stores = new Set<String>() {
			@Override
			public int size() {
				return this.size();
			}

			@Override
			public boolean isEmpty() {
				return this.isEmpty();
			}

			@Override
			public boolean contains(Object o) {
				return this.contains(o);
			}

			@NonNull
			@Override
			public Iterator<String> iterator() {
				return this.iterator();
			}

			@NonNull
			@Override
			public Object[] toArray() {
				return this.toArray();
			}

			@NonNull
			@Override
			public <T> T[] toArray(T[] ts) {
				return this.toArray(ts);
			}

			@Override
			public boolean add(String s) {
				return this.add(s);
			}

			@Override
			public boolean remove(Object o) {
				return this.remove(o);
			}

			@Override
			public boolean containsAll(Collection<?> collection) {
				return this.containsAll(collection);
			}

			@Override
			public boolean addAll(Collection<? extends String> collection) {
				return this.addAll(collection);
			}

			@Override
			public boolean retainAll(Collection<?> collection) {
				return this.retainAll(collection);
			}

			@Override
			public boolean removeAll(Collection<?> collection) {
				return this.removeAll(collection);
			}

			@Override
			public void clear() {
				this.clear();
			}
		};
		
		Double[][] totalWeight = new Double[n][m];
		
		// Initialize Dynamic Programming Table
		for (int j = 0; j < m; j++) {
			String location = nearbystores.get(j);
			totalWeight[0][j] = this.getScore(location, this.data.get(this.shoppinglist.get(0)).get(j), stores);
			Log.w("DP Table 1", String.valueOf(j));
		}
		
		// Recurrence
		for (int i = 1; i < n; i++) {
			for (int j = 0; j < m; j++) {
				String location = nearbystores.get(j);
				totalWeight[i][j] = this.getScore(location, this.data.get(this.shoppinglist.get(i)).get(location), stores) + this.getDPMin(totalWeight, i-1, m, stores, nearbystores);
				Log.w("DP Table 2", String.valueOf(j));
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
	
	public int getTracebackMin(Double[][] table, int i, int m){
		Double minVal = Double.MAX_VALUE;
		int minJ = Integer.MAX_VALUE;
		
		for (int j = 0; j < m; j++) {
			if (table[i][j] < minVal) {
				minVal = table[i][j];
				minJ = j;
			}
		}
		return minJ;
	}
	
	public Double getDPMin(Double[][] table, int i, int m, Set<String> stores, ArrayList<String> nearbystores){
		Double minVal = Double.MAX_VALUE;
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
	
	public Double getScore(String location, Double price, Set<String> stores){
		Double prescore = this.tradeoff * price * this.nearbyStores().get(location);
		if (stores.contains(location)) {
			return prescore * this.samestore;
		}
		else {
			return prescore;
		}
		
	}
	
	public HashMap<String, Integer> nearbyStores(){
            HashMap<String, Integer> stores = new HashMap<String, Integer>();
            stores.put("stopshop674",5);
            stores.put("mcdonalds1", 10);
            stores.put("bigy465", 12);
            return stores;
//        StoreFinder finder;
//        finder = new StoreFinder();
//        int x = finder.findStores();
//        Log.w("x = "+x,"true");
//       /* new Thread(new Runnable() {
//            @Override
//            public void run() {
//                Log.w("init StoreFinder", "true");
//                finder[0] = new StoreFinder();
//                Log.w("init StoreFinder", "true");
//                finder[0].findStores();
//            }
//        }).start();*/
//
//        Log.w("found stores", ""+finder.getIDMap().size());
//		return finder.getIDMap();
//		// Need an ArrayList of all Strings
	}
}

