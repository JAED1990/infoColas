package com.dxtre.www.colas;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.dxtre.www.colas.api.ApiHelper;
import com.dxtre.www.colas.cls.Parametro;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {


    private EditText txtDNI;
    private EditText txtClave;
    private TextView lblRegistrar;
    private Button lblRecuperar;
    private Button btnIngresar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        this.setTitle(getResources().getString(R.string.str_sesion));

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        txtDNI = (EditText) findViewById(R.id.txtDni);
        txtClave = (EditText) findViewById(R.id.txtContrasena);
        lblRegistrar = (TextView) findViewById(R.id.lblRegistrar);
        btnIngresar = (Button) findViewById(R.id.btnIngresar);

        btnIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                btnIngresar.setEnabled(false);

                List<Parametro> list = new ArrayList<>();

                list.add(new Parametro("user_dni", txtDNI.getText().toString()));
                list.add(new Parametro("user_clave", txtClave.getText().toString()));

                String result = ApiHelper.queryPost("login.php", list);

                btnIngresar.setEnabled(true);

                if (result != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(result);

                        if (jsonObject.getInt("estado") == 200) {
                            Toast.makeText(LoginActivity.this, "Bienvenido", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent();
                            intent.setClass(LoginActivity.this, LugaresActivity.class);
                            intent.putExtra("dni", jsonObject.getString("datos"));

                            SharedPreferences prefs = getSharedPreferences(ApiHelper.TAG_Pref, Context.MODE_PRIVATE);

                            SharedPreferences.Editor editor = prefs.edit();
                            editor.putString("dni", jsonObject.getString("datos"));
                            editor.commit();

                            startActivity(intent);
                            finish();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        });

        lblRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Dialog dialog = new Dialog(LoginActivity.this);
                dialog.setContentView(R.layout.activity_registrar);

                final EditText txtDni = (EditText) dialog.findViewById(R.id.txtDni);
                final EditText txtCorreo = (EditText) dialog.findViewById(R.id.txtCorreo);
                final EditText txtClave = (EditText) dialog.findViewById(R.id.txtClave);
                final Button btnGuardar = (Button) dialog.findViewById(R.id.btnGuardar);

                btnGuardar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        List<Parametro> list = new ArrayList<>();
                        list.add(new Parametro("user_dni", txtDni.getText().toString()));
                        list.add(new Parametro("user_correo", txtCorreo.getText().toString()));
                        list.add(new Parametro("user_clave", txtClave.getText().toString()));

                        String result = ApiHelper.queryPost("agregar_usuario.php", list);

                        if (result != null) {
                            try {
                                JSONObject jsonObject = new JSONObject(result);
                                if (jsonObject.getInt("estado") == 200) {
                                    Toast.makeText(LoginActivity.this, "Registro Correcto", Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });

                dialog.show();
            }
        });

    }
}
