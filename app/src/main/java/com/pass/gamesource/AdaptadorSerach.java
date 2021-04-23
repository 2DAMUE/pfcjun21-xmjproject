package com.pass.gamesource;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class AdaptadorSerach extends ArrayAdapter<Videojuego> {


    private final Activity activity;
    private ArrayList<Videojuego> arrayList;

    public AdaptadorSerach(Activity activity, ArrayList<Videojuego> arrayList) {
        super(activity, R.layout.vista_recycler_search, arrayList);
        this.activity = activity;
        this.arrayList = arrayList;

    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {
            convertView =
                    LayoutInflater.from(getContext())
                            .inflate(R.layout.vista_recycler_search,
                                    parent, false);
        }

        TextView txtNombre = convertView.findViewById(R.id.txtNickHistorico);
        TextView txtMensaje = convertView.findViewById(R.id.txtMensajeHistorico);
        TextView txtFecha = convertView.findViewById(R.id.txtFechaHistorico);
        TextView txtSmallIcon = convertView.findViewById(R.id.txtSmallIcon);

        ImageView imgAudio = convertView.findViewById(R.id.imgAudio);
        ImageView imgPhoto = convertView.findViewById(R.id.imgPhoto);
        ImageView imgVideo = convertView.findViewById(R.id.imgVideo);
        ImageView imgP = convertView.findViewById(R.id.profile_image);


        //imgP.setImageDrawable(activity.getResources().getDrawable(arrayList.get(position).getImg()));
        txtNombre.setText(arrayList.get(position).getTitulo());
        txtMensaje.setText(arrayList.get(position).getDescripcion());
        txtFecha.setText(arrayList.get(position).getPrecio());


        String tipoJuego = arrayList.get(position).getTitulo();

        switch (tipoJuego) {
            case SearchRecycler.TEXTO:
                txtMensaje.setVisibility(View.VISIBLE);
                imgAudio.setVisibility(View.GONE);
                imgPhoto.setVisibility(View.GONE);
                imgVideo.setVisibility(View.GONE);

                break;
            case SearchRecycler.AUDIO:
                txtMensaje.setVisibility(View.GONE);
                imgAudio.setVisibility(View.VISIBLE);
                txtSmallIcon.setVisibility(View.VISIBLE);

                break;

            case SearchRecycler.IMAGEN:
                txtMensaje.setVisibility(View.GONE);
                imgPhoto.setVisibility(View.VISIBLE);
                txtSmallIcon.setVisibility(View.VISIBLE);

                break;

            case SearchRecycler.VIDEO:
                txtMensaje.setVisibility(View.GONE);
                imgVideo.setVisibility(View.VISIBLE);
                txtSmallIcon.setVisibility(View.VISIBLE);

                break;

        }




        //ANIMACION

        Animation animation = AnimationUtils.loadAnimation(activity, android.R.anim.slide_in_left);
        convertView.startAnimation(animation);


        return convertView;
    }


    private int lastPosition = -1;


}


