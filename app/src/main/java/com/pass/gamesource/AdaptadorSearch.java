package com.pass.gamesource;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdaptadorSearch extends RecyclerView.Adapter<AdaptadorSearch.ViewHolderJuegos> {
    private final Activity activity;
    public ArrayList<Videojuego> listaJuegos;


    public AdaptadorSearch(SearchRecycler activity, ArrayList<Videojuego> listaJuegos) {
        this.activity = activity;
        this.listaJuegos = listaJuegos;
    }

    /**
     * Creamos la vista
     *
     * @param parent
     * @param viewType
     * @return
     */
    @NonNull
    @Override
    public ViewHolderJuegos onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewJuego = LayoutInflater.from(parent.getContext()).inflate(R.layout.vista_recycler_search, null, false);
        return new ViewHolderJuegos(viewJuego);
    }

    /**
     * Mete la informacion en los campos definidos
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolderJuegos holder, int position) {
        holder.tv_Titulo.setText(listaJuegos.get(position).getNombre());
        holder.tv_Fecha.setText(listaJuegos.get(position).getFechas().get(1));
        holder.tv_Descripcion.setText(listaJuegos.get(position).getDescripcion());
        holder.img_Caratula.setImageResource(listaJuegos.get(position).getImg());
    }

    /**
     * Longitud del array que carga el Recycler
     *
     * @return
     */
    @Override
    public int getItemCount() {
        return listaJuegos.size();
    }

    public class ViewHolderJuegos extends RecyclerView.ViewHolder {
        TextView tv_Titulo, tv_Descripcion, tv_Fecha, tv_temporizador;
        ImageView imgIcon1;
        ImageView imgIcon2;
        ImageView imgIcon3;
        ImageView img_Caratula;

        /**
         * Carga de datos en las vistas del Recycler
         *
         * @param itemView
         */
        public ViewHolderJuegos(@NonNull View itemView) {
            super(itemView);
            tv_Titulo = itemView.findViewById(R.id.tv_Titulo);
            tv_Descripcion = itemView.findViewById(R.id.tv_descripcion);
            tv_Fecha = itemView.findViewById(R.id.tv_Fecha);
            tv_temporizador = itemView.findViewById(R.id.tv_temporizador);

            imgIcon1 = itemView.findViewById(R.id.imgIcon1);
            imgIcon2 = itemView.findViewById(R.id.imgIcon2);
            imgIcon3 = itemView.findViewById(R.id.imgIcon3);
            img_Caratula = itemView.findViewById(R.id.img_Caratula);


            /**
             * Animacion Recycler
             */
            Animation animation = AnimationUtils.loadAnimation(activity, android.R.anim.slide_in_left);
            itemView.startAnimation(animation);
        }
    }
}


