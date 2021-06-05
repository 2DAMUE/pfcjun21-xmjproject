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
        String url = "";
        switch (juego.getPlataforma()) {
            case "ps":
                url = "https://image.flaticon.com/icons/png/512/37/37812.png";
                break;
            case "pc":
                url = "https://w7.pngwing.com/pngs/829/293/png-transparent-computer-cases-housings-personal-computer-computer-icons-computer-monitors-logo-technology-computer-computer-network-rectangle-logo.png";
                break;
            case "switch":
                url = "https://w7.pngwing.com/pngs/868/467/png-transparent-nintendo-switch-gamecube-logo-nintendo-text-rectangle-nintendo.png";
                break;
        }
        Glide.with(holder.vista).load(url).centerCrop().into(holder.ivPlataforma);
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


