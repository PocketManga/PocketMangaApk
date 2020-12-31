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
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
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

    private ListView lvChapterImages;
    private ListaImageChapterAdapter ImgAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter);

        lvChapterImages = findViewById(R.id.LVListaChapterImages);

        int id = getIntent().getIntExtra(IDCHAPTER, -1);
        chapter = SingletonGestorPocketManga.getInstance(getApplicationContext()).getChapter(id);

        SingletonGestorPocketManga.getInstance(getApplicationContext()).setChaptersListener(this);
        SingletonGestorPocketManga.getInstance(getApplicationContext()).getChapterImages(chapter);

        /*lvChapterImages.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE
                        && (lvChapterImages.getLastVisiblePosition() - lvChapterImages.getHeaderViewsCount() -
                        lvChapterImages.getFooterViewsCount()) >= (ImgAdapter.getCount() - 1)) {

                    chapter = SingletonGestorPocketManga.getInstance(getApplicationContext()).getChapter(chapter.getIdChapter()+1);
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });*/
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRefreshChaptersList(ArrayList<Chapter> ChaptersList) {
        // Don't do nothing
    }

    @Override
    public void onRefreshChapterImages(Chapter chapter) {
        if(chapter != null) {
            ArrayList<String> imagesUrl = new ArrayList<>();
            for(int i = 0; i < chapter.getPagesNumber(); i++){
                String imageUrl = chapter.getUrlImage()+"/"+String.format("%04d", i)+".jpg";
                imagesUrl.add(imageUrl);
            }

            ImgAdapter = new ListaImageChapterAdapter(getApplicationContext(), imagesUrl);
            lvChapterImages.setAdapter(ImgAdapter);
        }
    }
}