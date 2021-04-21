package com.pass.gamesource;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class SearchRecycler extends AppCompatActivity {


    private ListView listView;
    private AdaptadorSerach AdaptadorSerach;

    public static final String TEXTO = "texto";
    public static final String AUDIO = "audio";
    public static final String IMAGEN = "imagen";
    public static final String VIDEO = "video";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        this.listView.setNestedScrollingEnabled(true);
        this.listView.setAdapter(AdaptadorSerach);

    }

    private void init() {
        this.listView = findViewById(R.id.listaHistorico);
        this.AdaptadorSerach = new CustomAdapter(this, getArrayList());


    }

    private String getEmoji(int unicode) {
        return new String(Character.toChars(unicode));
    }


    private ArrayList<ObjetoListView> getArrayList() {

        ArrayList<ObjetoListView> arrayList = new ArrayList<>();

        arrayList.add(new ObjetoListView("Familia" + getEmoji(0x1F601), "Juan: " + getEmoji(0x1F64C),
                "10/8/18", TEXTO, false, 3, "", R.drawable.img_1));
        arrayList.add(new ObjetoListView("Alicia", "", "4/8/18", AUDIO, false, 1, "0:34",R.drawable.img_2));
        arrayList.add(new ObjetoListView("Juan", "", "AYER", IMAGEN, true, 1, "Foto",R.drawable.img_3));
        arrayList.add(new ObjetoListView("Amaya", "", "JUEVES", VIDEO, true, 1, "Video",R.drawable.img_4));
        arrayList.add(new ObjetoListView("Diana", "Nos vemos viernes "+ getEmoji(0x1F60A), "2/8/18", TEXTO, true, 1, "",R.drawable.img_5));
        arrayList.add(new ObjetoListView("Comida", "tú: ¡Me parece genial!", "2/8/18", TEXTO, true, 1, "",R.drawable.img_5));
        arrayList.add(new ObjetoListView("Juan", "", "AYER", IMAGEN, true, 1, "Foto",R.drawable.img_3));
        arrayList.add(new ObjetoListView("Amaya", "", "JUEVES", VIDEO, true, 1, "Video",R.drawable.img_4));
        arrayList.add(new ObjetoListView("Diana", "Nos vemos viernes "+ getEmoji(0x1F60A), "2/8/18", TEXTO, true, 1, "",R.drawable.img_5));
        arrayList.add(new ObjetoListView("Comida", "tú: ¡Me parece genial!", "2/8/18", TEXTO, true, 1, "",R.drawable.img_5));
        arrayList.add(new ObjetoListView("Juan", "", "AYER", IMAGEN, true, 1, "Foto",R.drawable.img_3));
        arrayList.add(new ObjetoListView("Amaya", "", "JUEVES", VIDEO, true, 1, "Video",R.drawable.img_4));
        arrayList.add(new ObjetoListView("Diana", "Nos vemos viernes "+ getEmoji(0x1F60A), "2/8/18", TEXTO, true, 1, "",R.drawable.img_5));
        arrayList.add(new ObjetoListView("Comida", "tú: ¡Me parece genial!", "2/8/18", TEXTO, true, 1, "",R.drawable.img_5));
        arrayList.add(new ObjetoListView("Juan", "", "AYER", IMAGEN, true, 1, "Foto",R.drawable.img_3));
        arrayList.add(new ObjetoListView("Amaya", "", "JUEVES", VIDEO, true, 1, "Video",R.drawable.img_4));
        arrayList.add(new ObjetoListView("Diana", "Nos vemos viernes "+ getEmoji(0x1F60A), "2/8/18", TEXTO, true, 1, "",R.drawable.img_5));
        arrayList.add(new ObjetoListView("Comida", "tú: ¡Me parece genial!", "2/8/18", TEXTO, true, 1, "",R.drawable.img_5));
        arrayList.add(new ObjetoListView("Juan", "", "AYER", IMAGEN, true, 1, "Foto",R.drawable.img_3));
        arrayList.add(new ObjetoListView("Amaya", "", "JUEVES", VIDEO, true, 1, "Video",R.drawable.img_4));
        arrayList.add(new ObjetoListView("Diana", "Nos vemos viernes "+ getEmoji(0x1F60A), "2/8/18", TEXTO, true, 1, "",R.drawable.img_5));
        arrayList.add(new ObjetoListView("Comida", "tú: ¡Me parece genial!", "2/8/18", TEXTO, true, 1, "",R.drawable.img_5));

        return arrayList;
    }
}