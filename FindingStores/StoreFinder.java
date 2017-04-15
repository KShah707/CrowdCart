package NeededFiles;

import java.awt.List;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;


public class StoreFinder {
	
	String urlBase = "https://maps.googleapis.com/maps/api/distancematrix/json?units=imperial&origins=";
	String dest = "&destinations=";
	String urlTail = "&key=AIzaSyBlpXHhm4nBdqEUHRSTyDyxafrLZxf5Jck";
	
	static int maxMinutes = 60;
	static int radius = 61000;
	double lat = 42.324079;
	double lon = -72.530696;
	
	ArrayList<String> placeNames;
	Map<String,ArrayList<Place>> nameToPlace;
	Map<Place,Integer> storeByPlace;
	
	
	ArrayList<Place> places;
	
	String[] mainStores = {"Stop and Shop","Walmart","Walmart Supercenter","Costco","The Kroger Company","The Home Depot","Walgreens","Walgreens Pharmacy","Target","CVS","Best Buy",
			"Safeway","Rite Aid","Kohl's","Dollar General","Family Dollar","Dollar Tree","Staples","Big Y","CVS Pharmacy"};
	
	GooglePlaces client;
	
	public static void main(String[] args) {
		
		ArrayList<String> inputLines = readFile("testInput.txt");
		for(String s : inputLines)
			distance2(s);
			
		
		System.out.println("All finished!");
		
	}
	
	public StoreFinder() {
		client = new GooglePlaces("AIzaSyDxkr8XQc2mbW3z2seCcGaQDBuQHUQaG9s");
	}
	
	public StoreFinder(double la, double lo) {
		client = new GooglePlaces("AIzaSyDxkr8XQc2mbW3z2seCcGaQDBuQHUQaG9s");
		lat = la;
		lon = lo;
	}
	
	public void findStores() {
		places = client.getNearbyPlaces(la, lon, radius, GooglePlaces.MAXIMUM_RESULTS)
		for(Place p : places) {
			if(Arrays.asList(mainStores).contains(p.getName())) {
				int dist = distanceTo(p);
				p.setDistance(dist);
				nameToPlace.get(p.getName()).add(p);
				
				
				
				
				/*int dist = distanceTo(p);
				if(storeByPlace.containsKey(p)) {
					if(dist < storeByPlace.get(p))
						storeByPlace.put(p, dist);
				} else {
					storeByPlace.put(p, dist);
				}
				if(!placeNames.contains(p.getName()))
					placeNames.add(p.getName());
				nameToPlace.put(p.getName(), p);*/
			}
		}
	}
	
	
	public void setLatitude(double la) {
		lat = la;
	}
	
	public void setLongitude(double lo) {
		lon = lo;
	}
	
	public ArrayList<String> getNearbyStoreNames(){
		return placeNames;
	}
	
	public Map<String,Place> getNamesToPlacesMap(){
		return nameToPlace;
	}
	
	public Map<Place,Integer>
	
	private static ArrayList<String> readFile(String filename)
	{
	  ArrayList<String> records = new ArrayList<String>();
	  try
	  {
	    BufferedReader reader = new BufferedReader(new FileReader(filename));
	    String line;
	    while ((line = reader.readLine()) != null)
	    {
	      records.add(line);
	    }
	    reader.close();
	    return records;
	  }
	  catch (Exception e)
	  {
	    System.err.format("Exception occurred trying to read '%s'.", filename);
	    e.printStackTrace();
	    return null;
	  }
	}
	
	public static int distance2(String t) 
	{
			String s = t;
			
			String[] json = s.split(" ");
			//System.out.println(s);
			int num = 0;
			for(String a : json) {
				
				for(char c : a.toCharArray())
					if(c == '\"')
						num++;
				if(a.length() > 0 && a.substring(0,1).equals("\"") &&
						!a.contains("-") && a.length() < 4 && Integer.parseInt(a.substring(1)) < maxMinutes )
						System.out.println("parsed dist = "+ a.substring(1));
					//return Integer.parseInt(a.substring(1));
				num = 0;
				
			}
			return Integer.MAX_VALUE;
	}

	public int distanceTo(Place p) 
	{
			String s;
			try {
				s = getHTML(urlBase+lat+","+lon+dest+p.getLatitude()+","+p.getLongitude()+urlTail);
			} catch(Exception e) { return Integer.MAX_VALUE;}
			
			String[] json = s.split(" ");
			//System.out.println(s);
			int num = 0;
			for(String a : json) {
				
				for(char c : a.toCharArray())
					if(c == '\"')
						num++;
				if(a.length() > 0 && a.substring(0,1).equals("\"") &&
						!a.contains("-") && a.length() < 4 && Integer.parseInt(a.substring(1)) < maxMinutes )
						//System.out.println("parsed dist = "+ a.substring(1));
					return Integer.parseInt(a.substring(1));
				num = 0;
				
			}
			return Integer.MAX_VALUE;
	}
	
	 public String getHTML(String urlToRead) throws Exception {
	      StringBuilder result = new StringBuilder();
	      URL url = new URL(urlToRead);
	      HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	      conn.setRequestMethod("GET");
	      BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	      String line;
	      while ((line = rd.readLine()) != null) {
	         result.append(line);
	      }
	      rd.close();
	      return result.toString();
	   }
	

	
}


