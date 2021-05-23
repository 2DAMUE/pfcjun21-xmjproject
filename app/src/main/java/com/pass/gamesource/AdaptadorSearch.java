package com.pass.gamesource;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class AdaptadorSearch extends RecyclerView.Adapter<AdaptadorSearch.MiContenedorDeVistas> {

    private ArrayList<Videojuego> listaVideojuegos = new ArrayList<>();
    private SearchRecycler main;

    public AdaptadorSearch(ArrayList<Videojuego> listaVideojuegos, SearchRecycler main) {
        this.listaVideojuegos = listaVideojuegos;
        this.main = main;
    }

    @NonNull
    @Override
    public MiContenedorDeVistas onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.vista_recycler_search, parent, false);
        MiContenedorDeVistas contenedor = new AdaptadorSearch.MiContenedorDeVistas(vista);
        Log.d("Contenedor", "Creando contenedor de vistas...");
        return contenedor;
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorSearch.MiContenedorDeVistas holder, int position) {
        Videojuego juego = listaVideojuegos.get(position);
        //Log.d("mensaje", juego.toString());
        Glide.with(holder.vista).load(juego.getImage_url()).centerCrop().into(holder.ivJuego);
        holder.vista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                main.mostrarAlertDialog(juego, main);
            }
        });
        /**
         * Animacion Recycler
         */
        Animation animation = AnimationUtils.loadAnimation(main, android.R.anim.slide_in_left);
        holder.vista.startAnimation(animation);
        holder.tvTitulo.setText(juego.getNombre());
        holder.tvDescripcion.setText(juego.getDescripcion());
        //Log.d("Contenedor", "Vinculando position " + position);

    }

    @Override
    public int getItemCount() {
        return listaVideojuegos.size();
    }

    public class MiContenedorDeVistas extends RecyclerView.ViewHolder {
        public ImageView ivJuego;
        public TextView tvTitulo;
        public TextView tvDescripcion;
        public View vista;

        public MiContenedorDeVistas(View vista) {
            super(vista);
            this.ivJuego = vista.findViewById(R.id.img_Caratula);
            this.tvTitulo = vista.findViewById(R.id.tv_Titulo);
            this.tvDescripcion = vista.findViewById(R.id.tv_descripcion);
            this.vista = vista;


        }
    }

}


