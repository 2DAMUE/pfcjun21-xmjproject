package com.pass.gamesource;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class AdaptadorCalendario extends RecyclerView.Adapter<AdaptadorCalendario.MiContenedorDeVistas> {
    private ArrayList<Videojuego> listaVideojuegos = new ArrayList<>();

    public AdaptadorCalendario(ArrayList<Videojuego> listaVideojuegos) {
        this.listaVideojuegos = listaVideojuegos;
    }

    @NonNull
    @Override
    public AdaptadorCalendario.MiContenedorDeVistas onCreateViewHolder(@NonNull ViewGroup parent,
                                                                       int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.vista_recycler_search, parent, false);
        AdaptadorCalendario.MiContenedorDeVistas contenedor = new AdaptadorCalendario.MiContenedorDeVistas(vista);
        Log.d("Contenedor", "Creando contenedor de vistas...");
        return contenedor;
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorCalendario.MiContenedorDeVistas holder,
                                 int position) {
        Videojuego juego = listaVideojuegos.get(position);
        //Log.d("mensaje", juego.toString());
        Glide.with(holder.vista).load(juego.getImage_url()).centerCrop().into(holder.ivJuego);
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

