package com.dxtre.www.colas;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.content.IntentCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.dxtre.www.colas.adapter.LugaresAdapter;
import com.dxtre.www.colas.api.ApiHelper;
import com.dxtre.www.colas.cls.Lugar;
import com.dxtre.www.colas.cls.Parametro;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DXtre on 5/12/15.
 */
public class LugaresActivity extends AppCompatActivity {

    private RecyclerView recycler;
    private RecyclerView.Adapter adapter;
    List<Lugar> list_lugares = new ArrayList<>();
    private String dni = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lugares);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        this.setTitle(getResources().getString(R.string.str_categoria));

        Bundle b = getIntent().getExtras();

        dni = b.getString("dni");

        Button btnAgregar = (Button) findViewById(R.id.btnAgregar);

        // Obtener el Recycler
        recycler = (RecyclerView) findViewById(R.id.reciclador);
        recycler.setHasFixedSize(true);

        // Usar un administrador para LinearLayout
        GridLayoutManager lManager = new GridLayoutManager(this, 2);
        recycler.setLayoutManager(lManager);

        // Crear un nuevo adaptador
        adapter = new LugaresAdapter(LugaresActivity.this, list_lugares);
        recycler.setAdapter(adapter);

        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();

                intent.setClass(LugaresActivity.this, SugerirLugarActivity.class);

                intent.putExtra("dni", dni);

                startActivity(intent);

            }
        });

        new TaskLugares().execute();
    }

    public void llenarLugares(){

        List<Parametro> list = new ArrayList<>();
        list.add(new Parametro("id_lugar", ""));
        String result = ApiHelper.queryPost("listar_lugares.php", list);

        if (result != null) {
            try {
                JSONObject jsonObject = new JSONObject(result);
                if (jsonObject.getInt("estado") == 200) {
                    JSONArray jsonData = jsonObject.getJSONArray("datos");


                    for(int i=0; i<jsonData.length(); i++){
                        JSONObject jsonItem = jsonData.getJSONObject(i);
                        list_lugares.add(new Lugar(jsonItem.getInt("idlugar"),
                                jsonItem.getString("latitud"),
                                jsonItem.getString("longitud"),
                                jsonItem.getString("nombre"),
                                jsonItem.getString("imagen"),
                                jsonItem.getString("idcategoria")));
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