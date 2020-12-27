package pocketmanga.aplicacao.movel.activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

import pocketmanga.aplicacao.movel.R;
import pocketmanga.aplicacao.movel.adaptadores.GrelhaMangaAdapter;
import pocketmanga.aplicacao.movel.adaptadores.ListaImageChapterAdapter;
import pocketmanga.aplicacao.movel.fragments.ChapterListFragment;
import pocketmanga.aplicacao.movel.fragments.MangaGrelhaFragment;
import pocketmanga.aplicacao.movel.listeners.ChaptersListener;
import pocketmanga.aplicacao.movel.modelo.Chapter;
import pocketmanga.aplicacao.movel.modelo.Manga;
import pocketmanga.aplicacao.movel.modelo.SingletonGestorPocketManga;

import static java.security.AccessController.getContext;

public class ChapterActivity extends AppCompatActivity implements ChaptersListener {
    public static final String IDCHAPTER = "IDCHAPTER";
    private Chapter chapter;

    private FragmentManager fragmentManager;
    private ListView lvChapterImages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter);

        lvChapterImages = findViewById(R.id.LVListaChapterImages);

        int id = getIntent().getIntExtra(IDCHAPTER, -1);

        chapter = SingletonGestorPocketManga.getInstance(getApplicationContext()).getChapter(id);

        SingletonGestorPocketManga.getInstance(getApplicationContext()).setChaptersListener(this);

        SingletonGestorPocketManga.getInstance(getApplicationContext()).getChapterImages(chapter);
    }

    @Override
    public void onRefreshChaptersList(ArrayList<Chapter> ChaptersList) {
        // Don't do nothing
    }

    @Override
    public void onRefreshChapterImages(Chapter chapter) {
        if(chapter != null) {
            ArrayList<String> imagesUrl = new ArrayList<>();
            String url = null;
            for(int i = 0; i < chapter.getPagesNumber(); i++){
                String imageUrl = chapter.getUrlImage()+"/"+String.format("%04d", i)+".jpg";
                imagesUrl.add(imageUrl);
                url = imageUrl;
            }
            
            lvChapterImages.setAdapter(new ListaImageChapterAdapter(getApplicationContext(), imagesUrl));
        }
    }
}