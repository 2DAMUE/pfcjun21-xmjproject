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
    static DatabaseReference myRefProximos = database.getReference().child("proximos");
    static DatabaseReference myRefDestacado = database.getReference().child("epic_semanal");
    static DatabaseReference myRefFavorite = database.getReference().child("user_favorite");

    /**
     * Método que devuelve todos los videojuegos gratuitos de la base de datos Firebase
     *
     * @param a Interfaz de actualización para poder recuperar los datos en el main o en la pantalla que se le requiera
     */
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

    /**
     * Método que recupera todos los juegos de PlayStation accediendo a la base de datos Firebase
     *
     * @param a Interfaz de actualización para poder recuperar los datos en el main o en la pantalla que se le requiera
     */
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

    /**
     * Obtención del videojuego destacado, que en este caso es el primer juego de la tienda de EpicGames semanal
     *
     * @param a Interfaz de actualización para poder recuperar los datos en el main o en la pantalla que se le requiera
     */
    public static void obtenerVideojuegoDestacado(ActualizarVideojuegoDestacado a) {

        myRefDestacado.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for (DataSnapshot esnapshot : snapshot.getChildren()) {

                    a.recuperarVideojuego(esnapshot.getValue(Videojuego.class));
                    break;
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }

    /**
     * Método que devuelve la lista de los videojuegos gratuitos de Steam accediendo a Firebase
     *
     * @param a Interfaz de actualización para poder recuperar los datos en el main o en la pantalla que se le requiera
     */
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

    /**
     * Método que devuelve todos los juegos favoritos de un usuario
     *
     * @param a interfaz de actualizar los datos para manejar la callback
     */
    public static void obtenerVideojuegosFavoritos(ActualizarVideojuegosFavoritos a) {
        ArrayList<Videojuego> videojuegosFavoritos = new ArrayList<>();
        myRefFavorite.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if (!snapshot.exists()) {
                    a.obtenerVideojuegosFavoritos(videojuegosFavoritos);
                    return;
                }
                Log.d("MENSAJE", LoginActivity.userEmail.split("\\.")[0]);
                for (DataSnapshot esnapshot : snapshot.child(LoginActivity.userEmail.split("\\.")[0] + LoginActivity.userEmail.split("\\.")[1]).getChildren()) {
                    videojuegosFavoritos.add(esnapshot.getValue(Videojuego.class));
                }

                Log.d("MENSAJE", videojuegosFavoritos.toString());
                a.obtenerVideojuegosFavoritos(videojuegosFavoritos);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }

    public static void aniadirJuegoFavorito(Videojuego v) {
        myRefFavorite.child(LoginActivity.userEmail.split("\\.")[0] + LoginActivity.userEmail.split("\\.")[1]).child(v.getNombre()).setValue(v);
    }

    public static void eliminarJuegoFavorito(Videojuego v) {
        myRefFavorite.child(LoginActivity.userEmail.split("\\.")[0] + LoginActivity.userEmail.split("\\.")[1]).child(v.getNombre()).removeValue();
    }

    /**
     * Es un método que devuelve la lista de videojuegos que coinciden con las letras que se
     * van escribiendo en el campo de búsqueda
     *
     * @param a      Interfaz de acutalización para la recuperación de datos
     * @param nombre El nombre del videojuego a buscar
     */
    public static void obtenerVideojuegosFiltrado(ActualizarVideojuegosGratis a, String nombre) {
        HashSet<String> nombres = new HashSet<>();
        FirebaseDatabase databaseF = FirebaseDatabase.getInstance();
        DatabaseReference myRefFiltrar = databaseF.getReference().child("gratis").child("ps_store_free");

        Log.d("MENSAJE", "Obteniendo datos de Firebase...");
        ArrayList<Videojuego> videojuegosGratis = new ArrayList<Videojuego>();
        myRefFiltrar.orderByChild("nombreMin").startAt(nombre.toLowerCase()).endAt(nombre.toLowerCase() + "\uf8ff").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for (DataSnapshot esnapshot : snapshot.getChildren()) {
                    if (nombres.add(esnapshot.getValue(Videojuego.class).getNombre()))
                        videojuegosGratis.add(esnapshot.getValue(Videojuego.class));

                }

                DatabaseReference myRefFiltrarSteam = databaseF.getReference().child("gratis").child("steam_free");
                myRefFiltrarSteam.orderByChild("nombreMin").startAt(nombre.toLowerCase()).endAt(nombre.toLowerCase() + "\uf8ff").addValueEventListener(new ValueEventListener() {
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

    public static void obtenerFavoritosFiltrado(ActualizarVideojuegosFavoritos a, String nombre) {
        DatabaseReference myRefFiltrar = database.getReference().child("user_favorite").child(LoginActivity.userEmail.split("\\.")[0] + LoginActivity.userEmail.split("\\.")[1]);
        ArrayList<Videojuego> videojuegosGratis = new ArrayList<Videojuego>();

        myRefFiltrar.orderByChild("nombreMin").startAt(nombre.toLowerCase()).endAt(nombre.toLowerCase() + "\uf8ff").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for (DataSnapshot esnapshot : snapshot.getChildren()) {
                    videojuegosGratis.add(esnapshot.getValue(Videojuego.class));

                }

                Log.d("MENSAJE", videojuegosGratis.toString());
                a.obtenerVideojuegosFavoritos(videojuegosGratis);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }

        });
    }

    /**
     * Método que devuelves los próximos lanzamientos para añadirlos en el calendario
     * @param a Interfaz de actualización para recuperar los datos en la clase llamante
     */
    public static void obtenerProximos(ActualizarVideojuegosGratis a) {
        ArrayList<Videojuego> videojuegosGratis = new ArrayList<Videojuego>();
        myRefProximos.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for (DataSnapshot esnapshot : snapshot.getChildren()) {
                    videojuegosGratis.add(esnapshot.getValue(Videojuego.class));

                }
                a.recuperarVideojuegos(videojuegosGratis);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }
}
