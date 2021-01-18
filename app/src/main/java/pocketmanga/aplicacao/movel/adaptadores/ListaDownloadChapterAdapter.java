package pocketmanga.aplicacao.movel.adaptadores;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import pocketmanga.aplicacao.movel.R;
import pocketmanga.aplicacao.movel.modelo.Chapter;
import pocketmanga.aplicacao.movel.modelo.SingletonGestorPocketManga;

public class ListaDownloadChapterAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private ArrayList<Chapter> chapters;
    private ArrayList<Chapter> selectedChapters;

    public ListaDownloadChapterAdapter(Context context, ArrayList<Chapter> chapters) {
        this.context = context;
        this.chapters = chapters;
    }

    @Override
    public int getCount() {
        return chapters.size();
    }

    @Override
    public Object getItem(int position) {
        return chapters.get(position);
    }

    @Override
    public long getItemId(int position) {
        return chapters.get(position).getIdChapter();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if(inflater==null)
            inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if(convertView==null)
            convertView=inflater.inflate(R.layout.item_list_download_chapter, null);

        ViewHolderList viewHolder = (ViewHolderList)convertView.getTag();
        if(viewHolder == null) {
            viewHolder = new ViewHolderList(convertView);
            convertView.setTag(viewHolder);
        }

        selectedChapters = new ArrayList<>();

        viewHolder.update(chapters.get(position));

        /** Code because download is working with cache **/
        if(chapters.get(position).isDownload()){
            viewHolder.CBChapter.setChecked(true);
            viewHolder.CBChapter.setTextColor(context.getResources().getColor(R.color.colorGreen));
            viewHolder.CBChapter.setButtonDrawable(R.drawable.ic_action_checkbox_checked);
        }
        /*************************************************/

        viewHolder.CBChapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckBox cb = view.findViewById(R.id.CBChapter);

                Chapter chapter = chapters.get(position);
                if(cb.isChecked()){
                    cb.setTextColor(view.getResources().getColor(R.color.colorGreen));
                    cb.setButtonDrawable(R.drawable.ic_action_checkbox_checked);
                    selectedChapters.add(chapter);

                    /** Code because download is working with cache **/
                    SingletonGestorPocketManga.getInstance(context).addDownloadChapterBD(chapter);
                    SaveImages(chapter);
                    Toast.makeText(context,"Chapter Added", Toast.LENGTH_SHORT).show();
                    /*************************************************/
                }else{
                    cb.setTextColor(view.getResources().getColor(R.color.colorBlack));
                    cb.setButtonDrawable(R.drawable.ic_action_checkbox_unchecked);
                    selectedChapters.remove(chapter);

                    /** Code because download is working with cache **/
                    SingletonGestorPocketManga.getInstance(context).removeDownloadChapterBD(chapter);
                    Toast.makeText(context,"Chapter Removed", Toast.LENGTH_SHORT).show();
                    /*************************************************/
                }
                SingletonGestorPocketManga.getInstance(context).refreshSelectedChapters(selectedChapters);
            }
        });

        return convertView;
    }

    private static class ViewHolderList{
        private CheckBox CBChapter;

        public ViewHolderList(View view) {
            CBChapter = view.findViewById(R.id.CBChapter);
        }

        public void update(Chapter chapter){
            String Text = "S"+chapter.getSeason()+" - Chapter "+chapter.getNumber() + ((chapter.getName().equals("null") || chapter.getName() == null)?"":" - "+chapter.getName());
            CBChapter.setText(Text);
        }
    }

    public void SaveImages(Chapter chapter) {
        // Função criada para guardar as imagens na cache
        // Criada devido a não ter conseguido aceder ás imagens do storage
        for (int i = 0; i < chapter.getPagesNumber(); i++) {
            String imageName = String.format("%04d", i);
            String url = SingletonGestorPocketManga.getInstance(context).getBaseUrl() + chapter.getUrlImage()+"/"+imageName+".jpg";

            Glide.with(context).asBitmap().load(url).into(new CustomTarget<Bitmap>() {
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
            SystemClock.sleep(10);
        }
    }
}
