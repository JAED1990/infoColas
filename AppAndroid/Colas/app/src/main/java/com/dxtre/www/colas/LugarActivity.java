package com.dxtre.www.colas;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dxtre.www.colas.adapter.ColasAdapter;
import com.dxtre.www.colas.adapter.LugaresAdapter;
import com.dxtre.www.colas.api.ApiHelper;
import com.dxtre.www.colas.cls.Cola;
import com.dxtre.www.colas.cls.Lugar;
import com.dxtre.www.colas.cls.Parametro;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DXtre on 5/12/15.
 */
public class LugarActivity extends AppCompatActivity {

    private RecyclerView rcvWait;
    private RecyclerView.Adapter adapter;
    List<Cola> list_wait = new ArrayList<>();
    private String idLugar;
    private String dni = "";
    private String id_categoria = "";
    private String lat = "";
    private String lng = "";
    private String nombre = "";
    private String url = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lugar);

        SharedPreferences prefs = getSharedPreferences(ApiHelper.TAG_Pref, Context.MODE_PRIVATE);

        dni = prefs.getString("dni", "");

        Bundle b = getIntent().getExtras();

        idLugar = b.getString("idLugar");
        nombre = b.getString("nombre");
        url = b.getString("imagen");
        lat = b.getString("lat");
        lng = b.getString("lng");
        id_categoria = b.getString("id_categoria");

        TextView txtLugar = (TextView) findViewById(R.id.txtLugar);
        final ImageView imgLugar = (ImageView) findViewById(R.id.imgLugar);
        ImageView btnLocation = (ImageView) findViewById(R.id.btnLocation);
        Button btnB = (Button) findViewById(R.id.btnB);
        Button btnM = (Button) findViewById(R.id.btnM);
        Button btnA = (Button) findViewById(R.id.btnA);
        Button btnSimiliar = (Button) findViewById(R.id.btnSimilar);

        btnSimiliar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Parametro> list = new ArrayList<>();
                list.add(new Parametro("id_lugar", idLugar));
                list.add(new Parametro("id_categoria", id_categoria));
                String result = ApiHelper.queryPost("recomendar_lugares_cercanos.php", list);

                if (result != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        if (jsonObject.getInt("estado") == 200) {

                            JSONArray arrayDatos = jsonObject.getJSONArray("datos");

                            List<Lugar> list_lugares = new ArrayList<Lugar>();

                            for (int i=0; i< arrayDatos.length(); i++) {
                                JSONObject itemLugar = arrayDatos.getJSONObject(i);

                                Lugar objLugar = new Lugar(Integer.valueOf(itemLugar.getString("idlugar")),
                                        itemLugar.getString("latitud"),
                                        itemLugar.getString("longitud"),
                                        itemLugar.getString("nombre"),
                                        itemLugar.getString("imagen"),
                                        itemLugar.getString("idcategoria")
                                        );

                                list_lugares.add(objLugar);

                            }

                            final Dialog dialog = new Dialog(LugarActivity.this);
                            dialog.setContentView(R.layout.activity_lugares_simple);

                            dialog.setTitle(getString(R.string.str_similares));

                            RecyclerView recycler = (RecyclerView) dialog.findViewById(R.id.reciclador);

                            // Crear un nuevo adaptador

                            RecyclerView.Adapter adapter = new LugaresAdapter(LugarActivity.this, list_lugares);
                            recycler.setAdapter(adapter);

                            recycler.setHasFixedSize(true);

                            // Usar un administrador para LinearLayout
                            GridLayoutManager lManager = new GridLayoutManager(LugarActivity.this, 1);
                            recycler.setLayoutManager(lManager);

                            dialog.show();

                        } else {
                            Toast.makeText(LugarActivity.this, "Error", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(LugarActivity.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(LugarActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addState("B");
            }
        });

        btnM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addState("M");
            }
        });

        btnA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addState("A");
            }
        });

        // Obtener el Recycler
        rcvWait = (RecyclerView) findViewById(R.id.rcvWait);
        rcvWait.setHasFixedSize(true);

        // Usar un administrador para LinearLayout
        GridLayoutManager lManager = new GridLayoutManager(this, 1);
        rcvWait.setLayoutManager(lManager);

        // Crear un nuevo adaptador
        adapter = new ColasAdapter(list_wait);
        rcvWait.setAdapter(adapter);

        btnLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();

                intent.setClass(LugarActivity.this, MapActivity.class);

                intent.putExtra("lat", lat);
                intent.putExtra("lng", lng);

                startActivity(intent);

            }
        });

        txtLugar.setText(nombre);
        displayImagen(url, imgLugar);

        new TaskLugares().execute();

    }

    public void displayImagen(String url, ImageView v){
        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(LugarActivity.this));
        DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true)
                .cacheOnDisc(true).resetViewBeforeLoading(true)
                .showImageForEmptyUri(R.drawable.img_placeholder_loading)
                .showImageOnFail(R.drawable.img_placeholder_loading)
                .showImageOnLoading(R.drawable.img_placeholder_loading).build();

        imageLoader.displayImage("http://dxtre.com/colas" + url, v, options);
    }

    private void addState(String state){
        List<Parametro> list = new ArrayList<>();
        list.add(new Parametro("id_lugar", idLugar));
        list.add(new Parametro("c_estado", state));
        list.add(new Parametro("user_dni", dni));
        String result = ApiHelper.queryPost("agregar_estado_cola.php", list);

        if (result != null) {
            try {
                JSONObject jsonObject = new JSONObject(result);
                if (jsonObject.getInt("estado") == 200) {
                    new TaskLugares().execute();
                    Toast.makeText(LugarActivity.this, "Registro Correcto", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(LugarActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(LugarActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(LugarActivity.this, "Error", Toast.LENGTH_SHORT).show();
        }
    }

    public void llenarLugares(){

        List<Parametro> list = new ArrayList<>();
        list.add(new Parametro("id_lugar", idLugar));
        String result = ApiHelper.queryPost("listado_estado_colas.php", list);

        if (result != null) {
            try {
                JSONObject jsonObject = new JSONObject(result);
                if (jsonObject.getInt("estado") == 200) {
                    JSONArray jsonData = jsonObject.getJSONArray("datos");

                    list_wait.clear();

                    for(int i=0; i<jsonData.length(); i++){
                        JSONObject jsonItem = jsonData.getJSONObject(i);
                        list_wait.add(new Cola(jsonItem.getString("idcola"),
                                jsonItem.getString("nombres"),
                                jsonItem.getString("fecha"),
                                jsonItem.getString("estado")
                                ));
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private class TaskLugares extends AsyncTask<String, String, String>
    {

        @Override
        protected String doInBackground(String... strings) {

            llenarLugares();

            return "";
        }

        protected void onPostExecute(String result)
        {
            adapter.notifyDataSetChanged();
        }
    }

}