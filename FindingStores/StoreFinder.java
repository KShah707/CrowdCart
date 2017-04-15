package NeededFiles;

import java.util.List;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


public class StoreFinder {
	
	String urlBase = "https://maps.googleapis.com/maps/api/distancematrix/json?units=imperial&origins=";
	String dest = "&destinations=";
	String urlTail = "&key=AIzaSyCAHrxbFgEGc32RymgFH32IuXIer6VwSOs"; //distance API key
	
	static int maxMinutes = 60;
	static int radius = 40000;
	double lat = 42.324079;
	double lon = -72.530696;
	
	List<String> placeNames = new ArrayList<String>();
	Map<String,ArrayList<Place>> nameToPlace = new HashMap<String,ArrayList<Place>>();
	
	
	List<Place> places = new ArrayList<Place>();
	
	String[] mainStores = {"Stop & Shop", "Super Stop & Shop", "Walmart","Walmart Supercenter","Costco","The Kroger Company","The Home Depot","Walgreens","Walgreens Pharmacy","Target","CVS","Best Buy",
			"Safeway","Rite Aid","Kohl's","Dollar General","Family Dollar","Dollar Tree","Staples","Big Y","CVS Pharmacy"};
	
	GooglePlaces client;
	
public static void main(String[] args) {
		
		ArrayList<String> inputLines = readFile("testInput.txt");
		for(String s : inputLines)
			TESTDATADISTANCE(s);
			
		
		System.out.println("All finished!");
		
	}
	
	public StoreFinder() {
		client = new GooglePlaces("AIzaSyCAHrxbFgEGc32RymgFH32IuXIer6VwSOs");
		
	}
	
	public StoreFinder(double la, double lo) {
		client = new GooglePlaces("AIzaSyCAHrxbFgEGc32RymgFH32IuXIer6VwSOs");
		lat = la;
		lon = lo;
	}
	
	public void findStores() {
		//System.out.println("Finding Stores...");
		
		for(int in = 0 ; in < mainStores.length ; in++) {
			List<Place> temp = client.getNearbyPlaces(lat,lon,radius,2,Param.name("name").value(mainStores[in]));
			for(Place p : temp) {
				System.out.println(p.getName());
				if(Arrays.asList(mainStores).contains(p.getName()))
					places.add(p);
			}
		}
			
		
		for(Place p : places) {
			System.out.println("Found " + p.getName());
			if(Arrays.asList(mainStores).contains(p.getName())) {
				int dist = distanceTo(p);
				p.setDistance(dist);
				if(!placeNames.contains(p.getName()))
					placeNames.add(p.getName());
				if(!nameToPlace.containsKey(p.getName()))
						nameToPlace.put(p.getName(), new ArrayList<Place>());
				nameToPlace.get(p.getName()).add(p);
				
			}
		}
		
		//System.out.println(placeNames.size());
		//System.out.println("All finished!");
	}
	
	
	public void setLatitude(double la) {
		lat = la;
	}
	
	public void setLongitude(double lo) {
		lon = lo;
	}
	
	public void setLatLong(double la,double lo) {
		lat = la;
		lon = lo;
	}
	
	public List<String> getNearbyStoreNames(){
		return placeNames;
	}
	
	public Map<String,ArrayList<Place>> getNamesToPlacesMap(){
		return nameToPlace;
	}
	
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
	
	public static int TESTDATADISTANCE(String t) 
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
			} catch(Exception e) { return 60;}
			
			String[] json = s.split(" ");
			
			System.out.println(s);
			int num = 0;
			for(String a : json) {
				
				for(char c : a.toCharArray())
					if(c == '\"')
						num++;
				if(a.length() > 0 && a.substring(0,1).equals("\"") &&
						!a.contains("-") && a.length() < 4 && Integer.parseInt(a.substring(1)) < maxMinutes )
						//System.out.println("parsed dist = "+ a.substring(1));
					try {
						//System.out.println("found distance: " + Integer.parseInt(a.substring(1)));
						return Integer.parseInt(a.substring(1));
					} catch(Exception e) {
						//System.out.println("bad distance parsed, returned 60");
						return 60;
					}
				num = 0;
				
			}
			return 60;
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


