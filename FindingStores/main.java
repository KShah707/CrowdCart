package NeededFiles;

public class main {

	public static void main(String[] args) {
		StoreFinder sf = new StoreFinder();
		sf.findStores();
		
		System.out.println(sf.getNearbyStoreNames().size());
		for(String s : sf.getNearbyStoreNames()) {
			for(Place p : sf.getNamesToPlacesMap().get(s)) {
				System.out.println(p.getName() + " " + p.getDistance());
			}
		}
	}
}