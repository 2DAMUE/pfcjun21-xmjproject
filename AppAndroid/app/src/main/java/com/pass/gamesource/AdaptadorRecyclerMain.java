package com.pass.gamesource;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdaptadorRecyclerMain extends RecyclerView.Adapter<AdaptadorRecyclerMain.MiContenedorDeVistas> {

    private ArrayList<Videojuego> listaVideojuegos = new ArrayList<>();

    public AdaptadorRecyclerMain(ArrayList<Videojuego> listaVideojuegos) {
        this.listaVideojuegos = listaVideojuegos;
    }

    @NonNull
    @Override
    public MiContenedorDeVistas onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.vistajuegorecycler, parent, false);
        MiContenedorDeVistas contenedor = new MiContenedorDeVistas(vista);
        ImageView ivJuego = vista.findViewById(R.id.imagenJuegoRecyclerMain);
        Log.d("Contenedor", "Creando contenedor de vistas...");
        return contenedor;
    }

    @Override
    public void onBindViewHolder(@NonNull MiContenedorDeVistas holder, int position) {
        Videojuego v = listaVideojuegos.get(position);
        holder.ivJuego.setImageURI(v.getUri());
        Log.d("Contenedor", "Vinculando position " + position);
    }

    @Override
    public int getItemCount() {
        return listaVideojuegos.size();
    }

    public static class MiContenedorDeVistas extends RecyclerView.ViewHolder {
        public ImageView ivJuego;

        public MiContenedorDeVistas(View vista) {
            super(vista);
            this.ivJuego = vista.findViewById(R.id.imagenJuegoRecyclerMain);
        }

    }


}
