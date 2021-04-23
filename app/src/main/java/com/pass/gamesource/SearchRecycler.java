package com.pass.gamesource;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class SearchRecycler extends AppCompatActivity {


    private ListView listView;
    private AdaptadorSerach adaptadorSerach;

    public static final String TEXTO = "texto";
    public static final String AUDIO = "audio";
    public static final String IMAGEN = "imagen";
    public static final String VIDEO = "video";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_recycler);

        init();

       this.listView.setNestedScrollingEnabled(false);
       this.listView.setAdapter(adaptadorSerach);

    }

    private void init() {
        this.listView = findViewById(R.id.listaHistorico);
        this.adaptadorSerach = new AdaptadorSerach(this, getArrayList());


    }

    private String getEmoji(int unicode) {
        return new String(Character.toChars(unicode));
    }


    private ArrayList<Videojuego> getArrayList() {

        ArrayList<Videojuego> arrayList = new ArrayList<>();

        arrayList.add(new Videojuego("Fantasya", "Fina Fantasy: ", TEXTO, false, 1, "", R.drawable.g));
        arrayList.add(new Videojuego("Fantasya", "Fina Fantasy: ", TEXTO, false, 2, "", R.drawable.s));
        arrayList.add(new Videojuego("Fantasya", "Fina Fantasy: ", TEXTO, false, 3, "", R.drawable.gamesinfondo));
        arrayList.add(new Videojuego("Fantasya", "Fina Fantasy: ", TEXTO, false, 4, "", R.drawable.gssinfondo));
        arrayList.add(new Videojuego("Fantasya", "Fina Fantasy: ", TEXTO, false, 5, "", R.drawable.soucersuelto));
//        arrayList.add(new Videojuego("Juan", "", "AYER", IMAGEN, true, 1, "Foto",R.drawable.img_3));
//        arrayList.add(new Videojuego("Amaya", "", "JUEVES", VIDEO, true, 1, "Video",R.drawable.img_4));
//        arrayList.add(new Videojuego("Diana", "Nos vemos viernes "+ getEmoji(0x1F60A), "2/8/18", TEXTO, true, 1, "",R.drawable.img_5));
//        arrayList.add(new Videojuego("Comida", "tú: ¡Me parece genial!", "2/8/18", TEXTO, true, 1, "",R.drawable.img_5));
//        arrayList.add(new Videojuego("Juan", "", "AYER", IMAGEN, true, 1, "Foto",R.drawable.img_3));
//        arrayList.add(new Videojuego("Amaya", "", "JUEVES", VIDEO, true, 1, "Video",R.drawable.img_4));
//        arrayList.add(new Videojuego("Diana", "Nos vemos viernes "+ getEmoji(0x1F60A), "2/8/18", TEXTO, true, 1, "",R.drawable.img_5));
//        arrayList.add(new Videojuego("Comida", "tú: ¡Me parece genial!", "2/8/18", TEXTO, true, 1, "",R.drawable.img_5));
//        arrayList.add(new Videojuego("Juan", "", "AYER", IMAGEN, true, 1, "Foto",R.drawable.img_3));
//        arrayList.add(new Videojuego("Amaya", "", "JUEVES", VIDEO, true, 1, "Video",R.drawable.img_4));
//        arrayList.add(new Videojuego("Diana", "Nos vemos viernes "+ getEmoji(0x1F60A), "2/8/18", TEXTO, true, 1, "",R.drawable.img_5));
//        arrayList.add(new Videojuego("Comida", "tú: ¡Me parece genial!", "2/8/18", TEXTO, true, 1, "",R.drawable.img_5));
//        arrayList.add(new Videojuego("Juan", "", "AYER", IMAGEN, true, 1, "Foto",R.drawable.img_3));
//        arrayList.add(new Videojuego("Amaya", "", "JUEVES", VIDEO, true, 1, "Video",R.drawable.img_4));
//        arrayList.add(new Videojuego("Diana", "Nos vemos viernes "+ getEmoji(0x1F60A), "2/8/18", TEXTO, true, 1, "",R.drawable.img_5));
//        arrayList.add(new Videojuego("Comida", "tú: ¡Me parece genial!", "2/8/18", TEXTO, true, 1, "",R.drawable.img_5));
//        arrayList.add(new Videojuego("Juan", "", "AYER", IMAGEN, true, 1, "Foto",R.drawable.img_3));
//        arrayList.add(new Videojuego("Amaya", "", "JUEVES", VIDEO, true, 1, "Video",R.drawable.img_4));
//        arrayList.add(new Videojuego("Diana", "Nos vemos viernes "+ getEmoji(0x1F60A), "2/8/18", TEXTO, true, 1, "",R.drawable.img_5));
//        arrayList.add(new Videojuego("Comida", "tú: ¡Me parece genial!", "2/8/18", TEXTO, true, 1, "",R.drawable.img_5));

        return arrayList;
    }
}