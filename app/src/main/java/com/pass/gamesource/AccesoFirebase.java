package com.pass.gamesource;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class AccesoFirebase {

    static FirebaseDatabase database = FirebaseDatabase.getInstance();
    static DatabaseReference myRef = database.getReference("message");

    public static void obtenerVideojuegosGratis(ActualizarVideojuegosGratis a) {
        List<Videojuego> videojuegosGratis = new ArrayList<Videojuego>();
        myRef.child("gratis").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    for (DataSnapshot esnapshot : task.getResult().getChildren()) {
                        Log.d("MENSAJE", "Obteniendo datos de Firebase...");
                        for (DataSnapshot esnapshotDos : esnapshot.getChildren()) {
                            videojuegosGratis.add(esnapshotDos.getValue(Videojuego.class));

                        }
                    }
                    a.recuperarVideojuegos(videojuegosGratis);
                }
            }
        });

    }

}
