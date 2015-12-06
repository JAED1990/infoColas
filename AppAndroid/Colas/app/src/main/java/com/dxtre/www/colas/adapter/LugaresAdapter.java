package com.dxtre.www.colas.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dxtre.www.colas.LugarActivity;
import com.dxtre.www.colas.R;
import com.dxtre.www.colas.api.ApiHelper;
import com.dxtre.www.colas.cls.Lugar;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.List;

public class LugaresAdapter extends RecyclerView.Adapter<LugaresAdapter.AnimeViewHolder> {
    private List<Lugar> items;
    private Context activity;

    public static class AnimeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // Campos respectivos de un item
        public String id;
        public ImageView imagen;
        public TextView nombre;
        private final Context context;
        public String lat;
        public String lng;
        public String id_categoria;

        public AnimeViewHolder(View v) {
            super(v);
            context = v.getContext();
            imagen = (ImageView) v.findViewById(R.id.imagen);
            nombre = (TextView) v.findViewById(R.id.nombre);
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            Intent intent = new Intent();

            intent.setClass(context, LugarActivity.class);

            intent.putExtra("idLugar", id);
            intent.putExtra("nombre", nombre.getText().toString());
            intent.putExtra("imagen", imagen.getContentDescription());
            intent.putExtra("lat", lat);
            intent.putExtra("lng", lng);
            intent.putExtra("id_categoria", id_categoria);

            context.startActivity(intent);

        }
    }

    public LugaresAdapter(Context a, List<Lugar> items) {
        this.activity = a;
        this.items = items;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public AnimeViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_lugar, viewGroup, false);
        return new AnimeViewHolder(v);
    }

    @Override
    public void onBindViewHolder(AnimeViewHolder viewHolder, int i) {
        //viewHolder.imagen.setImageResource(items.get(i).getImagen());
        viewHolder.id = String.valueOf(items.get(i).getIdlugar());
        viewHolder.nombre.setText(items.get(i).getNombre());
        viewHolder.imagen.setContentDescription(items.get(i).getImagen());
        viewHolder.lat = items.get(i).getLat();
        viewHolder.lng = items.get(i).getLng();
        viewHolder.id_categoria = items.get(i).getId_categoria();
        displayImagen(items.get(i).getImagen(), viewHolder.imagen);
    }

    public void displayImagen(String url, ImageView v){
        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(this.activity));
        DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true)
                .cacheOnDisc(true).resetViewBeforeLoading(true)
                .showImageForEmptyUri(R.drawable.img_placeholder_loading)
                .showImageOnFail(R.drawable.img_placeholder_loading)
                .showImageOnLoading(R.drawable.img_placeholder_loading).build();

        imageLoader.displayImage("http://dxtre.com/colas" + url, v, options);
    }

}