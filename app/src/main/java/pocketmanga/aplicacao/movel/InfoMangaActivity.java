package pocketmanga.aplicacao.movel;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;

import pocketmanga.aplicacao.movel.modelo.Manga;

public class InfoMangaActivity extends AppCompatActivity {

    public static final String IDMANGA = "IDMANGA";
    private Manga manga;
    private EditText etTitulo, etAutor, etSerie, etAno;
    private ImageView imgCapa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_manga);
    }
}