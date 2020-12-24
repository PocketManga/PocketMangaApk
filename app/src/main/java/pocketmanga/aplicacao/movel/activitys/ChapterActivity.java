package pocketmanga.aplicacao.movel.activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import pocketmanga.aplicacao.movel.R;
import pocketmanga.aplicacao.movel.adaptadores.ListaImageChapterAdapter;
import pocketmanga.aplicacao.movel.modelo.Chapter;
import pocketmanga.aplicacao.movel.modelo.Manga;
import pocketmanga.aplicacao.movel.modelo.SingletonGestorPocketManga;

import static java.security.AccessController.getContext;

public class ChapterActivity extends AppCompatActivity {

    public static final String IDCHAPTER = "IDCHAPTER";
    private ListView lvListaChapterImages;
    private Manga manga;
    private ArrayList<String> images;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter);
/*
        lvListaChapterImages = findViewById(R.id.LVListaLivros);
        images = SingletonGestorPocketManga.getInstance(getContext()).getImages(chapter);
        lvListaChapterImages.setAdapter(new ListaImageChapterAdapter(getContext(),chapter));

        lvListaChapterImages.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //reload image
            }
        });*/
    }
}