package com.dxtre.www.colas;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.dxtre.www.colas.api.ApiHelper;
import com.dxtre.www.colas.cls.Parametro;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerDragListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.location.LocationManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SugerirLugarActivity extends FragmentActivity implements OnMapLongClickListener,OnMapClickListener,OnMarkerDragListener,AdapterView.OnItemSelectedListener, LocationListener {

    private static GoogleMap map;

    private double latitud;
    private double longitud;
    private LocationManager locationManager;
    private String latDes = "-6.774201";
    private String lngDes  = "-79.849368";
    private float currentZoom = 17.0f;
    private boolean init = false;
    private int idcategoria = 1;
    private String dni = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sugerir_lugar);

        Bundle b = getIntent().getExtras();

        dni = b.getString("dni");

        this.setTitle(getResources().getString(R.string.str_sugerir_lugar));

        Button btnRegistroSugerir = (Button) findViewById(R.id.btnRegistroSugerir);
        final EditText txtnombrelugar = (EditText) findViewById(R.id.txtnombrelugar);

        btnRegistroSugerir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Parametro> list = new ArrayList<>();
                list.add(new Parametro("l_nombre", txtnombrelugar.getText().toString()));
                list.add(new Parametro("l_latitud", latDes));
                list.add(new Parametro("l_longitud", lngDes));

                list.add(new Parametro("user_dni", dni));
                list.add(new Parametro("id_categoria", ""+idcategoria));

                String result = ApiHelper.queryPost("aregar_nuevo_local.php", list);

                if (result != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        if (jsonObject.getInt("estado") == 200) {
                            Toast.makeText(SugerirLugarActivity.this, "Registro Correcto. El administrador debe aprobar su recomendación.", Toast.LENGTH_LONG).show();
                            finish();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        //Llenar spinner
        Spinner sp = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter a = ArrayAdapter.createFromResource(this, R.array.categorias, android.R.layout.simple_spinner_item);
        sp.setAdapter(a);
        sp.setOnItemSelectedListener(this);

        map = ((SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map)).getMap();

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
                1000 * 60,
                1,
                this);

        // Enable MyLocation Button in the Map
        map.setMyLocationEnabled(true);

        Location location = locationManager.getLastKnownLocation(bestProvider);

        if (location != null) {

            latDes = ""+location.getLatitude();
            lngDes = ""+location.getLongitude();

            //this.onLocationChanged(location);
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

        map.setOnMarkerDragListener(this);
        map.setOnMapLongClickListener(this);
        map.setOnMapClickListener(this);
        CameraPosition INIT =
                new CameraPosition.Builder()
                        .target(new LatLng(Double.parseDouble(latDes), Double.parseDouble(lngDes)))
                        .zoom(17.5F)
                        .bearing(300F) // orientation
                        .tilt( 50F) // viewing angle
                        .build();

        // use map to move camera into position
        map.moveCamera(CameraUpdateFactory.newCameraPosition(INIT));

        //create initial marker
        map.addMarker( new MarkerOptions()
                .position(new LatLng(Double.parseDouble(latDes), Double.parseDouble(lngDes)) )
                .title("Ubicación inicial")
                .snippet("Busca la dirección del lugar...")
                .draggable(true)).showInfoWindow();
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        idcategoria = (i + 1);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


    public void onMarkerDrag(Marker arg0) {
        // TODO Auto-generated method stub

    }

    public void onMarkerDragEnd(Marker arg0) {
        // TODO Auto-generated method stub

        LatLng dragPosition = arg0.getPosition();
        double dragLat = dragPosition.latitude;
        double dragLong = dragPosition.longitude;
        Log.i("info", "on drag end :" + dragLat + " dragLong :" + dragLong);
        Toast.makeText(getApplicationContext(), "Marker Dragged..!", Toast.LENGTH_LONG).show();
    }

    public void onMarkerDragStart(Marker arg0) {
        // TODO Auto-generated method stub

    }

    public void onMapClick(LatLng arg0) {
        // TODO Auto-generated method stub
        //map.animateCamera(CameraUpdateFactory.newLatLng(arg0));
        map.clear();
        //create new marker when user long clicks
        map.addMarker(new MarkerOptions()
                .position(arg0)
                .draggable(true)
                .title("Ubicación")
                .snippet("Dirección del lugar...")).showInfoWindow();

        //Toast.makeText(SugerirLugarActivity.this,"LAT: "+arg0.latitude +" - " + "LON: "+arg0.longitude,Toast.LENGTH_LONG).show();
        latDes = ""+arg0.latitude;
        lngDes = ""+arg0.longitude;
    }

    public void onMapLongClick(LatLng arg0) {
        // TODO Auto-generated method stub
        /*map.clear();
        //create new marker when user long clicks
        map.addMarker(new MarkerOptions()
                .position(arg0)
                .draggable(true));*/
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
