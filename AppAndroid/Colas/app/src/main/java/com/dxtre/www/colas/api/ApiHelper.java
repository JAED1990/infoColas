package com.dxtre.www.colas.api;

import android.net.Uri;
import android.util.Log;

import com.dxtre.www.colas.cls.Parametro;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by JAED on 9/07/13.
 */
public class ApiHelper {

	// give your server registration url here
	public static final String SERVER_URL = "http://localhost/colas/controlador/";

	public static final long PERIOD = 900000;//60000;//900000; //1800000;
	public static int distance = 650;//650;

	public static final String TAG = "DXtre_Colas";
	public static final String TAG_Pref = "DXtrePreferences";

	public static String queryPost(String p_nombreServicio, List<Parametro> p_parametros){
		String result = null;

		try{
			URL url = new URL(SERVER_URL+p_nombreServicio);

			HttpURLConnection connection = (HttpURLConnection) url.openConnection();

			connection.setRequestMethod("POST");

			Uri.Builder builder = new Uri.Builder();

			for(int i=0; i<p_parametros.size(); i++){
				builder.appendQueryParameter(p_parametros.get(i).getParametro(), p_parametros.get(i).getValor());
			}

			String parametros = builder.build().getEncodedQuery();

			OutputStream os = connection.getOutputStream();

			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));

			writer.write(parametros);
			writer.flush();
			writer.close();

			BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));

			String inputLine;

			StringBuffer response = new StringBuffer();

			while ((inputLine = br.readLine()) != null){
				response.append(inputLine);
			}

			result = response.toString();

			Log.d("Respuesta HTTP", result);

			os.close();

			connection.connect();

		}catch(MalformedURLException e){
			e.printStackTrace();
		}catch (IOException e){
			e.printStackTrace();
		}

		return result;
	}

	public static String queryGet(String nameService) {

		String result = null;

		try {

			URL obj = new URL(SERVER_URL + nameService);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();

			// optional default is GET
			con.setRequestMethod("GET");

			//add request header
			con.setRequestProperty("User-Agent", "Mozilla/5.0");
			con.setRequestProperty("Accept-Charset", "UTF-8");

			int responseCode = con.getResponseCode();
			System.out.println("\nSending 'GET' request to URL : " + SERVER_URL + nameService);
			System.out.println("Response Code : " + responseCode);

			BufferedReader in = new BufferedReader(
					new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();

			result = response.toString();

			result = HTMLEntities.unhtmlentities(result);

			System.out.println(response.toString());

		} catch (Exception e) {
			Log.e("log_tag", "Error in http connection " + e.toString());
		}

		return result;

	}

	/* Google Maps */
		/** Receives a JSONObject and returns a list of lists containing latitude and longitude */
	public static List<List<HashMap<String,String>>> parse(JSONObject jObject){

			List<List<HashMap<String, String>>> routes = new ArrayList<List<HashMap<String,String>>>() ;
			JSONArray jRoutes = null;
			JSONArray jLegs = null;
			JSONArray jSteps = null;

			try {

				jRoutes = jObject.getJSONArray("routes");

				/** Traversing all routes */
				for(int i=0;i<jRoutes.length();i++){
					jLegs = ( (JSONObject)jRoutes.get(i)).getJSONArray("legs");
					List path = new ArrayList<HashMap<String, String>>();

					/** Traversing all legs */
					for(int j=0;j<jLegs.length();j++){
						jSteps = ( (JSONObject)jLegs.get(j)).getJSONArray("steps");

						/** Traversing all steps */
						for(int k=0;k<jSteps.length();k++){
							String polyline = "";
							polyline = (String)((JSONObject)((JSONObject)jSteps.get(k)).get("polyline")).get("points");
							List<LatLng> list = decodePoly(polyline);

							/** Traversing all points */
							for(int l=0;l<list.size();l++){
								HashMap<String, String> hm = new HashMap<String, String>();
								hm.put("lat", Double.toString(((LatLng)list.get(l)).latitude) );
								hm.put("lng", Double.toString(((LatLng)list.get(l)).longitude) );
								path.add(hm);
							}
						}
						routes.add(path);
					}
				}

			} catch (JSONException e) {
				e.printStackTrace();
			}catch (Exception e){
			}

			return routes;
		}
		/**
		 * Method to decode polyline points
		 * Courtesy : http://jeffreysambells.com/2010/05/27/decoding-polylines-from-google-maps-direction-api-with-java
		 * */
	private static List<LatLng> decodePoly(String encoded) {

			List<LatLng> poly = new ArrayList<LatLng>();
			int index = 0, len = encoded.length();
			int lat = 0, lng = 0;

			while (index < len) {
				int b, shift = 0, result = 0;
				do {
					b = encoded.charAt(index++) - 63;
					result |= (b & 0x1f) << shift;
					shift += 5;
				} while (b >= 0x20);
				int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
				lat += dlat;

				shift = 0;
				result = 0;
				do {
					b = encoded.charAt(index++) - 63;
					result |= (b & 0x1f) << shift;
					shift += 5;
				} while (b >= 0x20);
				int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
				lng += dlng;

				LatLng p = new LatLng((((double) lat / 1E5)),
						(((double) lng / 1E5)));
				poly.add(p);
			}

			return poly;
		}

}