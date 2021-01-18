package pocketmanga.aplicacao.movel.activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

import pocketmanga.aplicacao.movel.R;
import pocketmanga.aplicacao.movel.adaptadores.MangaTabAdapter;
import pocketmanga.aplicacao.movel.modelo.Manga;
import pocketmanga.aplicacao.movel.modelo.SingletonGestorPocketManga;

public class MangaActivity extends AppCompatActivity {

    public static final String IDMANGA = "IDMANGA";
    public static final String DOWNLOADED = "DOWNLOADED";

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

        carregarInformation();
        if(manga != null){
            carregarMangaInformation();
            setTitle(manga.getTitle());
        }
        spLibrary.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Object item = parent.getItemAtPosition(position);
                if (item != null) {
                    if(!item.toString().equals("None")) {
                        manga.setList(item.toString().replace(" ", ""));
                    }else{
                        manga.setList(null);
                    }
                    SingletonGestorPocketManga.getInstance(getApplicationContext()).updateLibraryMangaBD(manga);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();

        if(getIntent().getBooleanExtra(DOWNLOADED, false))
            inflater.inflate(R.menu.menu_delete,menu);
        else
            inflater.inflate(R.menu.menu_download,menu);

        return super.onCreateOptionsMenu(menu);
    }

    private void carregarInformation() {

        tlBar.setupWithViewPager(viewPager);
        tlBar.getTabAt(0).setText(getResources().getText(R.string.more_info));
        tlBar.getTabAt(1).setText(getResources().getText(R.string.chapters));

        ArrayList<String> lists = new ArrayList<>();
        lists.add(getString(R.string.none));
        lists.add(getString(R.string.to_read));
        lists.add(getString(R.string.reading));
        lists.add(getString(R.string.completed));

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.item_spinner_server, lists);
        adapter.setDropDownViewResource(R.layout.item_spinner_server2);
        spLibrary.setAdapter(adapter);

    }

    private void carregarMangaInformation() {
        if(manga.getList()!=null) {
            switch (manga.getList()) {
                case "ToRead":
                    spLibrary.setSelection(1);
                    break;
                case "Reading":
                    spLibrary.setSelection(2);
                    break;
                case "Completed":
                    spLibrary.setSelection(3);
                    break;
                default:
                    break;
            }
        }
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
        switch (item.getItemId()) {
            case R.id.home:
                finish();
                return true;
            case R.id.downloadItem:
                Intent intent = new Intent(getApplicationContext(), DownloadActivity.class);
                intent.putExtra(DownloadActivity.IDMANGA, manga.getIdManga());
                startActivity(intent);
                return true;
            case R.id.deleteItem:
                SingletonGestorPocketManga.getInstance(getApplicationContext()).removeDownloadMangaBD(manga);
                Toast.makeText(getApplicationContext(), R.string.removed_from_download_with_success, Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }
}