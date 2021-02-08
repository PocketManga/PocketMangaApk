package pocketmanga.aplicacao.movel.activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Environment;
import android.view.MenuItem;
import android.widget.ListView;

import java.io.File;
import java.util.ArrayList;

import pocketmanga.aplicacao.movel.R;
import pocketmanga.aplicacao.movel.adaptadores.ListaImageChapterAdapter;
import pocketmanga.aplicacao.movel.listeners.ChaptersListener;
import pocketmanga.aplicacao.movel.modelo.Chapter;
import pocketmanga.aplicacao.movel.modelo.Manga;
import pocketmanga.aplicacao.movel.modelo.SingletonGestorPocketManga;
import pocketmanga.aplicacao.movel.utils.ConnectionJsonParser;

public class ChapterActivity extends AppCompatActivity implements ChaptersListener {
    public static final String IDCHAPTER = "IDCHAPTER";
    private Chapter chapter;
    private Manga manga;

    private ListView lvChapterImages;
    private ListaImageChapterAdapter ImgAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter);

        lvChapterImages = findViewById(R.id.LVListaChapterImages);

        int id = getIntent().getIntExtra(IDCHAPTER, -1);
        chapter = SingletonGestorPocketManga.getInstance(getApplicationContext()).getChapter(id);
        manga = SingletonGestorPocketManga.getInstance(getApplicationContext()).getManga(chapter.getMangaId());

        if(chapter != null){
            setTitle("Chapter "+(chapter.getNumber()+"").replace(".0", ""));
        }

        SingletonGestorPocketManga.getInstance(getApplicationContext()).setChaptersListener(this);
        SingletonGestorPocketManga.getInstance(getApplicationContext()).getChapterImages(chapter);

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
    public void onRefreshSelectedChaptersList(ArrayList<Chapter> ChaptersList) {
        // Don't do nothing
    }

    @Override
    public void onRefreshChapterImages(Chapter chapter) {
        if(chapter != null) {
            ArrayList<String> imagesUrl = new ArrayList<>();
            for(int i = 0; i < chapter.getPagesNumber(); i++){
                String imageName = String.format("%04d", i);
                String imageUrl = null;
                //if (ConnectionJsonParser.isConnectionInternet(getApplicationContext())) {
                    imageUrl = SingletonGestorPocketManga.getInstance(getApplicationContext()).getBaseUrl()+chapter.getUrlImage() + "/" + imageName + ".jpg";
                /*}else {
                    String path = "/PocketManga/"+manga.getTitle()+"/Chapter "+(chapter.getNumber()+"").replace(".0", "")+"/"+ imageName + ".jpg";

                    File imgFile = new File(Environment.getExternalStorageDirectory() + path);

                    if (!imgFile.exists()) {
                        imageUrl = SingletonGestorPocketManga.getInstance(getApplicationContext()).getBaseUrl()+chapter.getUrlImage() + "/" + imageName + ".jpg";
                    }else{
                        imageUrl = imgFile.getAbsolutePath();
                    }

                    System.out.println(imgFile.getAbsolutePath());
                    if(imgFile.exists())
                    {
                        System.out.println("Image "+imageName+".jpg exists");
                    }else {
                        System.out.println("Image "+imageName+".jpg doesn't exists");
                    }
                }*/
                imagesUrl.add(imageUrl);
            }
            Chapter chapterBD = SingletonGestorPocketManga.getInstance(getApplicationContext()).getChapterBD(chapter.getIdChapter());

            ImgAdapter = new ListaImageChapterAdapter(getApplicationContext(), imagesUrl, chapterBD!=null);
            lvChapterImages.setAdapter(ImgAdapter);
        }
    }
}