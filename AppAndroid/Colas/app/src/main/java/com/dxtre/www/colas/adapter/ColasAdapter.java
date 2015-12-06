package com.dxtre.www.colas.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dxtre.www.colas.R;
import com.dxtre.www.colas.cls.Cola;

import java.util.List;

public class ColasAdapter extends RecyclerView.Adapter<ColasAdapter.AnimeViewHolder> {
    private List<Cola> items;

    public static class AnimeViewHolder extends RecyclerView.ViewHolder {
        // Campos respectivos de un item
        public ImageView imgState;
        public TextView txtUser;
        public TextView txtTime;
        public TextView txtDescription;

        public AnimeViewHolder(View v) {
            super(v);
            imgState = (ImageView) v.findViewById(R.id.imgState);
            txtUser = (TextView) v.findViewById(R.id.txtUser);
            txtTime = (TextView) v.findViewById(R.id.txtTime);
            txtDescription = (TextView) v.findViewById(R.id.txtDescription);
        }
    }

    public ColasAdapter(List<Cola> items) {
        this.items = items;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public AnimeViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_state_wait, viewGroup, false);
        return new AnimeViewHolder(v);
    }

    @Override
    public void onBindViewHolder(AnimeViewHolder viewHolder, int i) {
        //viewHolder.imagen.setImageResource(items.get(i).getImagen());

        viewHolder.txtUser.setText(items.get(i).getUserName());
        if (items.get(i).getTime().equals("")){
            viewHolder.txtTime.setText("Hace un momento");
        }else {
            viewHolder.txtTime.setText(items.get(i).getTime());
        }

        if (items.get(i).getState().equals("A")) {
            viewHolder.txtDescription.setText("Más 30 personas");
            viewHolder.imgState.setBackgroundResource(R.drawable.circle_a);
        } else if (items.get(i).getState().equals("B")) {
            viewHolder.txtDescription.setText("Menos de 10 personas");
            viewHolder.imgState.setBackgroundResource(R.drawable.circle_b);
        } else if (items.get(i).getState().equals("M")) {
            viewHolder.txtDescription.setText("De 10 a 30 personas");
            viewHolder.imgState.setBackgroundResource(R.drawable.circle_m);
        }
    }

}