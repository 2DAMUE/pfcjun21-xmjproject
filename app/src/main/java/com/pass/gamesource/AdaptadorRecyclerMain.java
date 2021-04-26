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

public class AdaptadorRecyclerMain extends RecyclerView.Adapter<AdaptadorRecyclerMain.MiContenedorDeVistas> {

    private ArrayList<Videojuego> listaVideojuegos = new ArrayList<>();
    private MainActivity main = new MainActivity();

    public AdaptadorRecyclerMain(ArrayList<Videojuego> listaVideojuegos) {
        this.listaVideojuegos = listaVideojuegos;
    }

    @NonNull
    @Override
    public MiContenedorDeVistas onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.vistajuegorecycler, parent, false);
        MiContenedorDeVistas contenedor = new MiContenedorDeVistas(vista);
        ImageView ivJuego = vista.findViewById(R.id.imagenJuegoRecyclerMain);
        TextView tvTitulo = vista.findViewById(R.id.tvTituloJuegoRecycler);
        Log.d("Contenedor", "Creando contenedor de vistas...");
        return contenedor;
    }

    @Override
    public void onBindViewHolder(@NonNull MiContenedorDeVistas holder, int position) {
        Videojuego juego = listaVideojuegos.get(position);
        Log.d("mensaje", juego.toString());
        Glide.with(holder.vista).load(juego.getUri()).centerCrop().into(holder.ivJuego);
        holder.ivJuego.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                main.mostrarAlertDialog(juego);
            }
        });
        holder.tvTitulo.setText(juego.getTitulo());
        Log.d("Contenedor", "Vinculando position " + position);
    }

    @Override
    public int getItemCount() {
        return listaVideojuegos.size();
    }

    public class MiContenedorDeVistas extends RecyclerView.ViewHolder {
        public ImageView ivJuego;
        public TextView tvTitulo;
        public View vista;

        public MiContenedorDeVistas(View vista) {
            super(vista);
            this.ivJuego = vista.findViewById(R.id.imagenJuegoRecyclerMain);
            this.tvTitulo = vista.findViewById(R.id.tvTituloJuegoRecycler);
            this.vista = vista;
        }

    }


}