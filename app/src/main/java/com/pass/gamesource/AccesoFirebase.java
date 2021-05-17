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

}
