package pocketmanga.aplicacao.movel.activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

import pocketmanga.aplicacao.movel.R;
import pocketmanga.aplicacao.movel.adaptadores.MangaTabAdapter;
import pocketmanga.aplicacao.movel.listeners.MangasListener;
import pocketmanga.aplicacao.movel.modelo.Manga;
import pocketmanga.aplicacao.movel.modelo.SingletonGestorPocketManga;

public class MangaActivity extends AppCompatActivity implements MangasListener {

    public static final String IDMANGA = "IDMANGA";
    private Manga manga;
    private TextView tvDescriptionTop;
    private ImageView ivManga;

    private TabLayout tlBar;
    private ViewPager viewPagerp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manga);

        tlBar = findViewById(R.id.TLBar);
        viewPagerp = findViewById(R.id.VP);
        tvDescriptionTop = findViewById(R.id.TVDescriptionTop);
        ivManga = findViewById(R.id.IVManga);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        int id = getIntent().getIntExtra(IDMANGA, -1);
        manga = SingletonGestorPocketManga.getInstance(getApplicationContext()).getManga(id);

        MangaTabAdapter mangaTabAdapter = new
                MangaTabAdapter(getSupportFragmentManager(),
                tlBar.getTabCount(), manga);

        viewPagerp.setAdapter(mangaTabAdapter);

        if(manga != null){
            setTitle(manga.getTitle());
            carregarMangaInformation();
        }
    }

    private void carregarMangaInformation() {
        tvDescriptionTop.setText(manga.getDescription());
        //imgCapa.setImageResource(manga.getCapa());
    }

    @Override
    public void onRefreshMangasList(ArrayList<Manga> MangasList) {

    }

    @Override
    public void onRefreshInfo() {
        setResult(RESULT_OK);
        finish();
    }
}