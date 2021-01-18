package pocketmanga.aplicacao.movel.activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import pocketmanga.aplicacao.movel.R;
import pocketmanga.aplicacao.movel.adaptadores.ListaDownloadChapterAdapter;
import pocketmanga.aplicacao.movel.listeners.ChaptersListener;
import pocketmanga.aplicacao.movel.modelo.Chapter;
import pocketmanga.aplicacao.movel.modelo.Manga;
import pocketmanga.aplicacao.movel.modelo.SingletonGestorPocketManga;

public class DownloadActivity extends AppCompatActivity implements ChaptersListener {

    public static final String IDMANGA = "IDMANGA";
    private static final int PERMISSION_REQUEST_CODE = 1000;
    public static final int PERMISSION_WRITE = 0;
    private Manga manga;

    private ListView lvListaChapters;
    private ArrayList<Chapter> selectedChapters;
    private Button btnDownload;

    private long downloadId;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);

        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);

        lvListaChapters = findViewById(R.id.LVCheckBoxChapter);

        registerReceiver(onDownloadComplete, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));

        int id = getIntent().getIntExtra(IDMANGA, -1);
        manga = SingletonGestorPocketManga.getInstance(getApplicationContext()).getManga(id);

        SingletonGestorPocketManga.getInstance(getApplicationContext()).setChaptersListener(this);
        SingletonGestorPocketManga.getInstance(getApplicationContext()).getAllChaptersAPI(getApplicationContext(), manga.getIdManga());

        btnDownload = findViewById(R.id.BTNDownloadChapters);

        checkPermission();
        btnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /** Code while download is working with cache. PS: it will download the images to the storage, but it will not use them **/
                if(selectedChapters!=null) {
                    if(selectedChapters.size() > 0) {
                        if(checkPermission()) {
                            BeginDownload();
                            SingletonGestorPocketManga.getInstance(getApplicationContext()).addDownloadMangaBD(manga, selectedChapters);
                            finish();
                        }
                    }else{
                        //Toast.makeText(getApplicationContext(), R.string.chose_at_least_one_chapter, Toast.LENGTH_SHORT).show();
                        SingletonGestorPocketManga.getInstance(getApplicationContext()).removeDownloadMangaBD(manga);
                    }
                }else{
                    //Toast.makeText(getApplicationContext(), R.string.chose_at_least_one_chapter, Toast.LENGTH_SHORT).show();
                    SingletonGestorPocketManga.getInstance(getApplicationContext()).removeDownloadMangaBD(manga);
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                Toast.makeText(this, R.string.permission_granted, Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(this, R.string.permission_denied, Toast.LENGTH_SHORT).show();
        }
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
        if(ChaptersList!=null)
            lvListaChapters.setAdapter(new ListaDownloadChapterAdapter(getApplicationContext(),ChaptersList));
    }

    @Override
    public void onRefreshSelectedChaptersList(ArrayList<Chapter> ChaptersList) {
        selectedChapters = ChaptersList;
    }

    @Override
    public void onRefreshChapterImages(Chapter chapter) {
        // Don't do nothing
    }

    /******************************************************************************************************************************************************/
    public void SaveImages() {
        // Função criada para guardar as imagens na cache
        // Criada devido a não ter conseguido aceder ás imagens do storage
        for (Chapter chapter: selectedChapters) {
            for (int i = 0; i < chapter.getPagesNumber(); i++) {
                String imageName = String.format("%04d", i);
                String url = SingletonGestorPocketManga.getInstance(getApplicationContext()).getBaseUrl() + chapter.getUrlImage()+"/"+imageName+".jpg";

                Glide.with(this).asBitmap().load(url).into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap bitmap, Transition<? super Bitmap> transition) {
                        try {

                            File mydir = new File(Environment.getExternalStorageDirectory() + "/PocketManga");
                            if (!mydir.exists()) {
                                mydir.mkdirs();
                            }

                            String fileUri = mydir.getAbsolutePath() + File.separator + System.currentTimeMillis() + ".jpg";
                            FileOutputStream outputStream = new FileOutputStream(fileUri);

                            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                            outputStream.flush();
                            outputStream.close();
                        } catch(IOException e){
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onLoadCleared(Drawable placeholder) {
                    }
                });
                System.out.println(url);
            }
        }
    }
    /******************************************************************************************************************************************************/

    public boolean checkPermission() {
        int READ_EXTERNAL_PERMISSION = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if((READ_EXTERNAL_PERMISSION != PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_WRITE);
            return false;
        }
        return true;
    }

    private void BeginDownload () {
        // Função criada para guardar as imagens na storage
        for (Chapter chapter: selectedChapters) {
            for(int i = 0; i < chapter.getPagesNumber(); i++) {
                String imageName = String.format("%04d", i);
                String url = SingletonGestorPocketManga.getInstance(getApplicationContext()).getBaseUrl() + chapter.getUrlImage()+"/"+imageName+".jpg";
                File file = new File(getExternalFilesDir(null), imageName);
                DownloadManager.Request request = null;


                String path = "/PocketManga/"+manga.getTitle()+"/Chapter "+(chapter.getNumber()+"").replace(".0", "");

                File mydir = new File(Environment.getExternalStorageDirectory() + path);
                if (!mydir.exists()) {
                    mydir.mkdirs();
                }

                String fullPath = mydir.getAbsolutePath()+"/";

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                    request = new DownloadManager.Request(Uri.parse(url))
                            .setTitle(imageName)
                            .setDescription(getString(R.string.downloading))
                            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                            .setDestinationUri(Uri.fromFile(file))
                            .setRequiresCharging(false)
                            .setAllowedOverMetered(true)
                            .setAllowedOverRoaming(true);
                    request.setDestinationInExternalPublicDir(fullPath, imageName+".jpg");
                    request.allowScanningByMediaScanner();
                } else {
                    request = new DownloadManager.Request(Uri.parse(url))
                            .setTitle(imageName)
                            .setDescription(getString(R.string.downloading))
                            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)
                            .setDestinationUri(Uri.fromFile(file))
                            .setAllowedOverMetered(true)
                            .setAllowedOverRoaming(true);
                    request.setDestinationInExternalPublicDir(fullPath, imageName+".jpg");
                    request.allowScanningByMediaScanner();
                }

                DownloadManager downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
                downloadId = downloadManager.enqueue(request);
            }
        }
    }
    private BroadcastReceiver onDownloadComplete = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
            if(downloadId == id)
                Toast.makeText(context, R.string.download_completed, Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(onDownloadComplete);
    }
}