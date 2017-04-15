package NeededFiles;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

/**
 * Main class of API. Used for all entry web-api operations.
 */
public class GooglePlaces implements GooglePlacesInterface {

    /*
     * Argument #1: API Base URL
     * Argument #2: API Method
     * Argument #3: API Method arguments
     */
    public static String API_URL_FORMAT_STRING = "%s%s/json?%s";

    private String apiKey;
    //private RequestHandler requestHandler;
    private boolean debugModeEnabled;

    /**
     * Creates a new GooglePlaces object using the specified API key and the specified {@link RequestHandler}.
     *
     * @param apiKey         that has been registered on the Google Developer Console
     * @param requestHandler to handle HTTP traffic
     */
    public GooglePlaces(String apiKey, RequestHandler requestHandler) {
        this.apiKey = apiKey;
        //this.requestHandler = requestHandler;
    }

    /**
     * Creates a new GooglePlaces object using the specified API key.
     *
     * @param apiKey that has been registered on the Google Developer Console
     */
    public GooglePlaces(String apiKey) {
        //this(apiKey, new DefaultRequestHandler());
    	this.apiKey = apiKey;
    }

    /**
     * Creates a new GooglePlaces object using the specified API key and character encoding. Using a character encoding
     * other than UTF-8 is not advised.
     *
     * @param apiKey            that has been registered on the Google Developer Console
     * @param characterEncoding to parse data with
     */
    public GooglePlaces(String apiKey, String characterEncoding) {
        //this(apiKey, new DefaultRequestHandler(characterEncoding));
    }

    private static String addExtraParams(String base, Param... extraParams) {
        for (Param param : extraParams)
            base += "&" + param.name + (param.value != null ? "=" + param.value : "");
        return base;
    }

    private static String buildUrl(String method, String params, Param... extraParams) {
        String url = String.format(Locale.ENGLISH, API_URL_FORMAT_STRING, API_URL, method, params);
        url = addExtraParams(url, extraParams);
        url = url.replace(' ', '+');
        return url;
    }

    protected static void checkStatus(String statusCode, String errorMessage) {
        GooglePlacesException e = GooglePlacesException.parse(statusCode, errorMessage);
        if (e != null)
            throw e;
    }

    /**
     * Parses the specified raw json String into a list of places.
     *
     * @param places to parse into
     * @param str    raw json
     * @param limit  the maximum amount of places to return
     * @return Next page token
     */
    
    
    public static String parse(GooglePlaces client, List<Place> places, String str, int limit) {
        // parse json
        JSONObject json = new JSONObject(str);

        // check root elements
        String statusCode = json.getString(STRING_STATUS);
        checkStatus(statusCode, json.optString(STRING_ERROR_MESSAGE));
        if (statusCode.equals(STATUS_ZERO_RESULTS))
            return null;

        JSONArray results = json.getJSONArray(ARRAY_RESULTS);
        parseResults(client, places, results, Math.min(limit, MAXIMUM_PAGE_RESULTS));

        return json.optString(STRING_NEXT_PAGE_TOKEN, null);
    }
    
    /**
     * Parses the specified Radar raw json String into a list of places.
     *
     * @param places to parse into
     * @param str    Radar raw json
     * @param limit  the maximum amount of places to return
     */
    public static void parseRadar(GooglePlaces client, List<Place> places, String str, int limit) {
      // parse json
      JSONObject json = new JSONObject(str);
      
      // check root elements
      String statusCode = json.getString(STRING_STATUS);
      checkStatus(statusCode, json.optString(STRING_ERROR_MESSAGE));
      if (statusCode.equals(STATUS_ZERO_RESULTS))
        return;
      
      JSONArray results = json.getJSONArray(ARRAY_RESULTS);
      parseResults(client, places, results, Math.min(limit, MAXIMUM_RADAR_RESULTS));
    }

    private static void parseResults(GooglePlaces client, List<Place> places, JSONArray results, int limit) {
        for (int i = 0; i < limit; i++) {

            // reached the end of the page
            if (i >= results.length())
                return;

            JSONObject result = results.getJSONObject(i);

            // location
            JSONObject location = result.getJSONObject(OBJECT_GEOMETRY).getJSONObject(OBJECT_LOCATION);
            double lat = location.getDouble(DOUBLE_LATITUDE);
            double lon = location.getDouble(DOUBLE_LONGITUDE);

            String placeId = result.getString(STRING_PLACE_ID);
            String iconUrl = result.optString(STRING_ICON, null);
            String name = result.optString(STRING_NAME);
            String addr = result.optString(STRING_ADDRESS, null);
            double rating = result.optDouble(DOUBLE_RATING, -1);
            String vicinity = result.optString(STRING_VICINITY, null);

            // see if the place is open, fail-safe if opening_hours is not present
            JSONObject hours = result.optJSONObject(OBJECT_HOURS);
            boolean hoursDefined = hours != null && hours.has(BOOLEAN_OPENED);
            Status status = Status.NONE;
            if (hoursDefined) {
                boolean opened = hours.getBoolean(BOOLEAN_OPENED);
                status = opened ? Status.OPENED : Status.CLOSED;
            }

            // get the price level for the place, fail-safe if not defined
            boolean priceDefined = result.has(INTEGER_PRICE_LEVEL);
            Price price = Price.NONE;
            if (priceDefined) {
                price = Price.values()[result.getInt(INTEGER_PRICE_LEVEL)];
            }

            // the place "types"
            List<String> types = new ArrayList<>();
            JSONArray jsonTypes = result.optJSONArray(ARRAY_TYPES);
            if (jsonTypes != null) {
                for (int a = 0; a < jsonTypes.length(); a++) {
                    types.add(jsonTypes.getString(a));
                }
            }

            Place place = new Place();

            // build a place object
            places.add(place.setClient(client).setPlaceId(placeId).setLatitude(lat).setLongitude(lon).setIconUrl(iconUrl).setName(name)
                    .setAddress(addr).setRating(rating).setStatus(status).setPrice(price)
                    .addTypes(types).setVicinity(vicinity).setJson(result));
        }
    }

    @Override
    public boolean isDebugModeEnabled() {
        return debugModeEnabled;
    }

    @Override
    public void setDebugModeEnabled(boolean debugModeEnabled) {
        this.debugModeEnabled = debugModeEnabled;
    }

    private void debug(String msg) {
        if (debugModeEnabled)
            System.out.println(msg);
    }

    @Override
    public String getApiKey() {
        return apiKey;
    }

    @Override
    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    @Override
    public RequestHandler getRequestHandler() {
        return null;//requestHandler;
    }

    @Override
    public void setRequestHandler(RequestHandler requestHandler) {
        //this.requestHandler = requestHandler;
    }

    @Override
    public List<Place> getNearbyPlaces(double lat, double lng, double radius, int limit, Param... extraParams) {
        try {
            String uri = buildUrl(METHOD_NEARBY_SEARCH, String.format(Locale.ENGLISH, "key=%s&location=%s,%s&radius=%s",
                    apiKey, String.valueOf(lat), String.valueOf(lng), String.valueOf(radius)), extraParams);
            System.out.println("uri ="+uri);
            return getPlaces(uri, METHOD_NEARBY_SEARCH, limit);
        } catch (Exception e) {
            //throw new GooglePlacesException(e);
        	System.out.println("uri not found");
        	return new ArrayList<Place>();
        }
    }
    
    public List<Place> getNearbyPlacesByList(double lat, double lng, double radius, int limit, String[] arr, Param... extraParams) {
        try {
        	List<Place> PLACES = new ArrayList<Place>();
        	for(String s : arr) {
            String uri = buildUrl(METHOD_NEARBY_SEARCH, String.format(Locale.ENGLISH, "key=%s&location=%s,%s&radius=%s",
                    apiKey, String.valueOf(lat), String.valueOf(lng), String.valueOf(radius)), Param.name("name").value(s));
            PLACES.addAll(getPlaces(uri, METHOD_NEARBY_SEARCH, limit));
        	}
        	return PLACES;
        } catch (Exception e) {
            throw new GooglePlacesException(e);
        }
    }
    
    
    @Override
    public List<Place> getNearbyPlaces(double lat, double lng, double radius, Param... extraParams) {
        return getNearbyPlaces(lat, lng, radius, DEFAULT_RESULTS, extraParams);
    }

    @Override
    public List<Place> getNearbyPlacesRankedByDistance(double lat, double lng, int limit, Param... params) {
        try {
            String uri = buildUrl(METHOD_NEARBY_SEARCH, String.format(Locale.ENGLISH, "key=%s&location=%s,%s&rankby=distance",
                    apiKey, String.valueOf(lat), String.valueOf(lng)), params);
            return getPlaces(uri, METHOD_NEARBY_SEARCH, limit);
        } catch (Exception e) {
            throw new GooglePlacesException(e);
        }
    }

    @Override
    public List<Place> getNearbyPlacesRankedByDistance(double lat, double lng, Param... params) {
        return getNearbyPlacesRankedByDistance(lat, lng, DEFAULT_RESULTS, params);
    }

    @Override
    public List<Place> getPlacesByQuery(String query, int limit, Param... extraParams) {
        try {
            String uri = buildUrl(METHOD_TEXT_SEARCH, String.format("query=%s&key=%s", query, apiKey),
                    extraParams);
            return getPlaces(uri, METHOD_TEXT_SEARCH, limit);
        } catch (Exception e) {
            throw new GooglePlacesException(e);
        }
    }

    @Override
    public List<Place> getPlacesByQuery(String query, Param... extraParams) {
        return getPlacesByQuery(query, DEFAULT_RESULTS, extraParams);
    }

    

    @Override
    public List<Place> getPlacesByRadar(double lat, double lng, double radius, Param... extraParams) {
        return getPlacesByRadar(lat, lng, radius, MAXIMUM_RESULTS, extraParams);
    }

  

   
   

    @Override
    public void deletePlace(Place place, Param... extraParams) {
        deletePlaceById(place.getPlaceId(), extraParams);
    }

    


  


    @Override
    public List<Prediction> getPlacePredictions(String input, int offset, Param... extraParams) {
        return getPlacePredictions(input, offset, -1, -1, -1, extraParams);
    }

    @Override
    public List<Prediction> getPlacePredictions(String input, Param... extraParams) {
        return getPlacePredictions(input, -1, extraParams);
    }

    

    @Override
    public List<Prediction> getQueryPredictions(String input, int offset, Param... extraParams) {
        return getQueryPredictions(input, offset, -1, -1, -1, extraParams);
    }

    @Override
    public List<Prediction> getQueryPredictions(String input, Param... extraParams) {
        return getQueryPredictions(input, -1, extraParams);
    }
    
    private String readString(HttpResponse response) throws IOException {
        String str = IOUtils.toString(response.getEntity().getContent(), "UTF-8");
        if (str == null || str.trim().length() == 0) {
            return null;
        }
        return str.trim();
    }
    
    public String getRaw(String uri) throws IOException {
        HttpGet get = new HttpGet(uri);
        try {
        	HttpClient client = HttpClientBuilder.create().build();
            return readString(client.execute(get));
        } catch (Exception e) {
            throw new IOException(e);
        } finally {
            get.releaseConnection();
        }
    }
    
    

    private List<Place> getPlaces(String uri, String method, int limit) throws IOException {
        limit = Math.min(limit, MAXIMUM_RESULTS); // max of 60 results possible
        int pages = (int) Math.ceil(limit / (double) MAXIMUM_PAGE_RESULTS);

        List<Place> places = new ArrayList<>();
        // new request for each page
        for (int i = 0; i < pages; i++) {
            debug("Page: " + (i + 1));
            //String raw = requestHandler.get(uri);
            //debug(raw);
            String raw = this.getRaw(uri);
            System.out.println("raw = "+raw);
            
            String nextPage = parse(this, places, raw, limit);
            System.out.println("nextPage = "+nextPage);
            // reduce the limit, update the uri and wait for token, but only if there are more pages to read
            if (nextPage != null && i < pages - 1) {
                limit -= MAXIMUM_PAGE_RESULTS;
                uri = String.format("%s%s/json?pagetoken=%s&key=%s",
                        API_URL, method, nextPage, apiKey);
                sleep(3000); // Page tokens have a delay before they are available
            } else {
                break;
            }
        }

        return places;
    }


    private void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

	@Override
	public List<Place> getPlacesByRadar(double lat, double lng, double radius, int limit, Param... extraParams) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Place getPlaceById(String placeId, Param... extraParams) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Place addPlace(PlaceBuilder builder, boolean returnPlace, Param... extraParams) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deletePlaceById(String placeId, Param... extraParams) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Prediction> getPlacePredictions(String input, int offset, int lat, int lng, int radius,
			Param... extraParams) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Prediction> getQueryPredictions(String input, int offset, int lat, int lng, int radius,
			Param... extraParams) {
		// TODO Auto-generated method stub
		return null;
	}

}
