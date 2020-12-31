package pocketmanga.aplicacao.movel.activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

import pocketmanga.aplicacao.movel.R;
import pocketmanga.aplicacao.movel.adaptadores.MangaTabAdapter;
import pocketmanga.aplicacao.movel.listeners.ChaptersListener;
import pocketmanga.aplicacao.movel.listeners.MangasListener;
import pocketmanga.aplicacao.movel.modelo.Chapter;
import pocketmanga.aplicacao.movel.modelo.Manga;
import pocketmanga.aplicacao.movel.modelo.SingletonGestorPocketManga;

public class MangaActivity extends AppCompatActivity {

    public static final String IDMANGA = "IDMANGA";
    private Manga manga;
    private TextView tvDescriptionTop;
    private ImageView ivManga;
    private Spinner spLibrary;

    private TabLayout tlBar;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manga);

        tlBar = findViewById(R.id.TLBar);
        viewPager = findViewById(R.id.VP);
        tvDescriptionTop = findViewById(R.id.TVDescriptionTop);
        ivManga = findViewById(R.id.IVManga);
        spLibrary = findViewById(R.id.SPLibrary);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        int id = getIntent().getIntExtra(IDMANGA, -1);
        manga = SingletonGestorPocketManga.getInstance(getApplicationContext()).getManga(id);

        MangaTabAdapter mangaTabAdapter = new MangaTabAdapter(getSupportFragmentManager(), tlBar.getTabCount(), manga);

        viewPager.setAdapter(mangaTabAdapter);

        tlBar.setupWithViewPager(viewPager);
        tlBar.getTabAt(0).setText(getResources().getText(R.string.more_info));
        tlBar.getTabAt(1).setText(getResources().getText(R.string.chapters));

        ArrayList<String> lists = new ArrayList<>();
        lists.add("None");
        lists.add("To Read");
        lists.add("Reading");
        lists.add("Completed");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.item_spinner_server, lists);
        adapter.setDropDownViewResource(R.layout.item_spinner_server2);
        spLibrary.setAdapter(adapter);

        if(manga != null){
            setTitle(manga.getTitle());
            carregarMangaInformation();
        }
        spLibrary.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Object item = parent.getItemAtPosition(position);
                if (item != null && !item.toString().equals("None")) {
                    manga.setList(item.toString().replace(" ", ""));
                    SingletonGestorPocketManga.getInstance(getApplicationContext()).updateLibraryMangaBD(manga);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });


    }

    private void carregarMangaInformation() {
        tvDescriptionTop.setText(manga.getDescription());
        String url = SingletonGestorPocketManga.getInstance(getApplicationContext()).getBaseUrl()+manga.getImage();
        Glide.with(getApplicationContext())
                .load(url)
                .placeholder(R.drawable.manga_alternative)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(ivManga);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}