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

public class AdaptadorFavoritos extends RecyclerView.Adapter<AdaptadorFavoritos.MiContenedorDeVistas> {
    private ArrayList<Videojuego> listaVideojuegos = new ArrayList<>();
    private FavoritosActivity main;

    public AdaptadorFavoritos(ArrayList<Videojuego> listaVideojuegos, FavoritosActivity main) {
        this.listaVideojuegos = listaVideojuegos;
        this.main = main;
    }

    @NonNull
    @Override
    public AdaptadorFavoritos.MiContenedorDeVistas onCreateViewHolder(@NonNull ViewGroup parent,
                                                                      int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.vista_recycler_search, parent, false);
        AdaptadorFavoritos.MiContenedorDeVistas contenedor = new AdaptadorFavoritos.MiContenedorDeVistas(vista);
        Log.d("Contenedor", "Creando contenedor de vistas...");
        return contenedor;
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorFavoritos.MiContenedorDeVistas holder,
                                 int position) {
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
        switch (juego.getPlataforma()) {
            case "ps":
                holder.ivPlataforma.setImageResource(R.mipmap.logo_ps_foreground);
                break;
            case "pc":
                holder.ivPlataforma.setImageResource(R.mipmap.logo_pc_foreground);
                break;
            case "switch":
                holder.ivPlataforma.setImageResource(R.mipmap.logo_switch_foreground);
                break;
        }
        //Log.d("Contenedor", "Vinculando position " + position);

    }

    @Override
    public int getItemCount() {
        return listaVideojuegos.size();
    }

    public class MiContenedorDeVistas extends RecyclerView.ViewHolder {
        public ImageView ivJuego;
        public ImageView ivPlataforma;
        public TextView tvTitulo;
        public TextView tvDescripcion;
        public View vista;

        public MiContenedorDeVistas(View vista) {
            super(vista);
            this.ivJuego = vista.findViewById(R.id.img_Caratula);
            this.tvTitulo = vista.findViewById(R.id.tv_Titulo);
            this.tvDescripcion = vista.findViewById(R.id.tv_descripcion);
            this.ivPlataforma = vista.findViewById(R.id.imgIcon2);
            this.vista = vista;


        }
    }

}

