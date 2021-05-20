package com.pass.gamesource;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashSet;

public class AccesoFirebase {

    static FirebaseDatabase database = FirebaseDatabase.getInstance();
    static DatabaseReference myRef = database.getReference().child("gratis");

    public static void obtenerVideojuegosGratis(ActualizarVideojuegosGratis a) {
        Log.d("MENSAJE", "Obteniendo datos de Firebase...");
        ArrayList<Videojuego> videojuegosGratis = new ArrayList<Videojuego>();
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for (DataSnapshot esnapshot : snapshot.getChildren()) {
                    for (DataSnapshot eesnapshot : esnapshot.getChildren()) {

                        videojuegosGratis.add(eesnapshot.getValue(Videojuego.class));
                    }

                }
                //Log.d("MENSAJE", snapshot.getValue(Videojuego.class).toString());
                a.recuperarVideojuegos(videojuegosGratis);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

    }

    public static void obtenerVideojuegosPS(ActualizarVideojuegosEpic a) {
        ArrayList<Videojuego> videojuegosEpic = new ArrayList<Videojuego>();
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for (DataSnapshot esnapshot : snapshot.child("ps_store_free").getChildren()) {
                    videojuegosEpic.add(esnapshot.getValue(Videojuego.class));
                }

                //Log.d("MENSAJE", snapshot.getValue(Videojuego.class).toString());
                a.recuperarVideojuegosEpic(videojuegosEpic);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }

    public static void obtenerVideojuegosSteam(ActualizarVideojuegosSteam a) {
        ArrayList<Videojuego> videojuegosSteam = new ArrayList<Videojuego>();
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for (DataSnapshot esnapshot : snapshot.child("steam_free").getChildren()) {
                    videojuegosSteam.add(esnapshot.getValue(Videojuego.class));
                }

                //Log.d("MENSAJE", snapshot.getValue(Videojuego.class).toString());
                a.recuperarVideojuegosSteam(videojuegosSteam);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }

    public static void obtenerVideojuegosFiltrado(ActualizarVideojuegosGratis a, String nombre) {
        HashSet<String> nombres = new HashSet<>();
        FirebaseDatabase databaseF = FirebaseDatabase.getInstance();
        DatabaseReference myRefFiltrar = databaseF.getReference().child("gratis").child("ps_store_free");

        Log.d("MENSAJE", "Obteniendo datos de Firebase...");
        ArrayList<Videojuego> videojuegosGratis = new ArrayList<Videojuego>();
        myRefFiltrar.orderByChild("nombre").startAt(nombre).endAt(nombre + "\uf8ff").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for (DataSnapshot esnapshot : snapshot.getChildren()) {
                    if (nombres.add(esnapshot.getValue(Videojuego.class).getNombre()))
                        videojuegosGratis.add(esnapshot.getValue(Videojuego.class));

                }

                DatabaseReference myRefFiltrarSteam = databaseF.getReference().child("gratis").child("steam_free");
                myRefFiltrarSteam.orderByChild("nombre").startAt(nombre).endAt(nombre + "\uf8ff").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                        for (DataSnapshot esnapshot : snapshot.getChildren()) {
                            if (nombres.add(esnapshot.getValue(Videojuego.class).getNombre()))
                                videojuegosGratis.add(esnapshot.getValue(Videojuego.class));

                        }

                        Log.d("MENSAJE", videojuegosGratis.toString());
                        a.recuperarVideojuegos(videojuegosGratis);
                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {

                    }

                });
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

    }
}
