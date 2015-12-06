package com.dxtre.www.colas;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.dxtre.www.colas.api.ApiHelper;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by DXtre on 12/09/15.
 */
public class MapActivity extends FragmentActivity implements LocationListener {

    private GoogleMap map;

    private double latitud;
    private double longitud;
    private LocationManager locationManager;
    private String latDes;
    private String lngDes;
    private float currentZoom = 17.0f;
    private boolean init = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_map);

        Bundle b = getIntent().getExtras();

        latDes = b.getString("lat");
        lngDes = b.getString("lng");

        // Getting reference to SupportMapFragment of the activity_main
        SupportMapFragment fm = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

        // Getting Map for the SupportMapFragment
        map = fm.getMap();

        if (map != null) {

            locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

            Criteria criteria = new Criteria();

            String bestProvider = locationManager.getBestProvider(criteria, true);

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }

            locationManager.requestLocationUpdates(bestProvider,
                    1000 * 10,

                    1,
                    this);

            // Enable MyLocation Button in the Map
            map.setMyLocationEnabled(true);

            Location location = locationManager.getLastKnownLocation(bestProvider);

            if (location != null) {

                latitud = location.getLatitude();
                longitud = location.getLongitude();

                this.onLocationChanged(location);
            }

            map.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {

                @Override
                public void onCameraChange(CameraPosition position) {
                    if (init) {
                        currentZoom = position.zoom;  // here you get zoom level
                        Log.d(ApiHelper.TAG, "Zoom: " + currentZoom);
                    }
                }
            });

            //drawPisfil();
        }
    }

    @Override
    public void onLocationChanged(Location location) {

        if (location != null)
        {
            this.latitud = location.getLatitude();
            this.longitud = location.getLongitude();

            drawRoute();

        }
    }

    private void drawRoute()
    {
        this.map.clear();

        LatLng origin = new LatLng(this.latitud, this.longitud);

        LatLng dest = new LatLng(Double.parseDouble(latDes), Double.parseDouble(lngDes));

        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(origin, this.currentZoom);

        this.map.animateCamera(cameraUpdate);

        // Creating MarkerOptions
        MarkerOptions options = new MarkerOptions();

        options.position(dest);

        //options.icon(BitmapDescriptorFactory.fromResource(R.drawable.location_pisfil));

        // Add new marker to the Google Map Android API V2
        this.map.addMarker(options);

        String url = getDirectionsUrl(origin, dest);

        new DownloadTask().execute(url);
    }

    private void drawPisfil()
    {

        this.map.clear();

        LatLng dest = new LatLng(Double.parseDouble(latDes), Double.parseDouble(lngDes));

        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(dest, this.currentZoom);

        this.map.animateCamera(cameraUpdate);

        // Creating MarkerOptions
        MarkerOptions options = new MarkerOptions();

        options.position(dest);

        //options.icon(BitmapDescriptorFactory.fromResource(R.drawable.location_pisfil));

        this.map.addMarker(options);

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {
        Log.d(ApiHelper.TAG, s);
    }

    @Override
    public void onProviderEnabled(String s) {
        Log.d(ApiHelper.TAG, s);
    }

    @Override
    public void onProviderDisabled(String s) {
        Log.d(ApiHelper.TAG, s);
    }

    private String getDirectionsUrl(LatLng origin, LatLng dest){

        // Origin of route
        String str_origin = "origin="+origin.latitude+","+origin.longitude;

        // Destination of route
        String str_dest = "destination="+dest.latitude+","+dest.longitude;

        // Sensor enabled
        String sensor = "sensor=false";

        // Building the parameters to the web service
        String parameters = str_origin+"&"+str_dest+"&"+sensor;

        // Output format
        String output = "json";

        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/"+output+"?"+parameters;

        return url;
    }

    /** A method to download json data from url */
    private String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try{
            URL url = new URL(strUrl);

            // Creating an http connection to communicate with url
            urlConnection = (HttpURLConnection) url.openConnection();

            // Connecting to url
            urlConnection.connect();

            // Reading data from url
            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            StringBuffer sb = new StringBuffer();

            String line = "";
            while( ( line = br.readLine()) != null){
                sb.append(line);
            }

            data = sb.toString();

            br.close();

        }catch(Exception e){
            Log.d(ApiHelper.TAG, e.toString());
        }finally{
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }

    // Fetches data from url passed
    private class DownloadTask extends AsyncTask<String, Void, String> {

        // Downloading data in non-ui thread
        @Override
        protected String doInBackground(String... url) {

            // For storing data from web service
            String data = "";

            try{
                // Fetching the data from web service
                data = downloadUrl(url[0]);
            }catch(Exception e){
                Log.d(ApiHelper.TAG, e.toString());
            }
            return data;
        }

        // Executes in UI thread, after the execution of
        // doInBackground()
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            ParserTask parserTask = new ParserTask();

            // Invokes the thread for parsing the JSON data
            parserTask.execute(result);
        }
    }

    /** A class to parse the Google Places in JSON format */
    private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String,String>>> >{

        // Parsing the data in non-ui thread
        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {

            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;

            try{
                jObject = new JSONObject(jsonData[0]);
                // Starts parsing data
                routes = ApiHelper.parse(jObject);
            }catch(Exception e){
                e.printStackTrace();
            }
            return routes;
        }

        // Executes in UI thread, after the parsing process
        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> result) {
            ArrayList<LatLng> points = null;
            PolylineOptions lineOptions = null;
            MarkerOptions markerOptions = new MarkerOptions();

            // Traversing through all the routes
            for(int i=0;i<result.size();i++){
                points = new ArrayList<LatLng>();
                lineOptions = new PolylineOptions();

                // Fetching i-th route
                List<HashMap<String, String>> path = result.get(i);

                // Fetching all the points in i-th route
                for(int j=0;j<path.size();j++){
                    HashMap<String,String> point = path.get(j);

                    double lat = Double.parseDouble(point.get("lat"));
                    double lng = Double.parseDouble(point.get("lng"));
                    LatLng position = new LatLng(lat, lng);

                    points.add(position);
                }

                // Adding all the points in the route to LineOptions
                lineOptions.addAll(points);
                lineOptions.width(3);
                lineOptions.color(Color.RED);
            }

            // Drawing polyline in the Google Map for the i-th route
            map.addPolyline(lineOptions);
        }
    }

}
