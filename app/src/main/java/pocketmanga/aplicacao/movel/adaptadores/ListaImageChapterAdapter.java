package pocketmanga.aplicacao.movel.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

import pocketmanga.aplicacao.movel.R;
import pocketmanga.aplicacao.movel.modelo.SingletonGestorPocketManga;

public class ListaImageChapterAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private ArrayList<String> imagesUrl;
    private boolean downloaded;

    public ListaImageChapterAdapter(Context context, ArrayList<String> imagesUrl, boolean downloaded) {
        this.context = context;
        this.imagesUrl = imagesUrl;
        this.downloaded = downloaded;
    }

    @Override
    public int getCount() {
        return imagesUrl.size();
    }

    @Override
    public Object getItem(int position) {
        return imagesUrl.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(inflater==null)
            inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if(convertView==null)
            convertView=inflater.inflate(R.layout.item_image_chapter, null);

        ListaImageChapterAdapter.ViewHolderLista viewHolder = (ListaImageChapterAdapter.ViewHolderLista)convertView.getTag();
        if(viewHolder == null) {
            viewHolder = new ListaImageChapterAdapter.ViewHolderLista(convertView);
            convertView.setTag(viewHolder);
        }

        viewHolder.update(imagesUrl.get(position));

        return convertView;
    }

    private class ViewHolderLista{
        private ImageView ivImage;

        public ViewHolderLista(View view) {
            ivImage = view.findViewById(R.id.IVImage);
        }

        public void update(String imageUrl){
            Glide.with(context)
                    .load(imageUrl)
                    .placeholder(R.drawable.error_no_image)
                    .diskCacheStrategy((downloaded)?DiskCacheStrategy.ALL:DiskCacheStrategy.NONE)
                    .into(ivImage);
        }
    }
}
