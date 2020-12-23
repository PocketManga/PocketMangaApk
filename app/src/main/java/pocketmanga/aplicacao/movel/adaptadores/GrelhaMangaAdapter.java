package pocketmanga.aplicacao.movel.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

import pocketmanga.aplicacao.movel.R;
import pocketmanga.aplicacao.movel.modelo.Manga;

public class GrelhaMangaAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private ArrayList<Manga> mangas;

    public GrelhaMangaAdapter(Context context, ArrayList<Manga> mangas) {
        this.context = context;
        this.mangas = mangas;
    }

    @Override
    public int getCount() {
        return mangas.size();
    }

    @Override
    public Object getItem(int position) {
            return mangas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return mangas.get(position).getIdManga();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(inflater==null)
            inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if(convertView==null)
            convertView=inflater.inflate(R.layout.item_grelha_manga, null);

        ViewHolderGrelha viewHolder = (ViewHolderGrelha)convertView.getTag();
        if(viewHolder == null) {
            viewHolder = new ViewHolderGrelha(convertView);
            convertView.setTag(viewHolder);
        }

        viewHolder.update(mangas.get(position));

        return convertView;
    }
    private class ViewHolderGrelha{
        private TextView tvTitle;
        private ImageView ivImage;

        public ViewHolderGrelha(View view) {
            tvTitle = view.findViewById(R.id.TVTitle);
            ivImage = view.findViewById(R.id.IVImage);
        }

        public void update(Manga manga){
            tvTitle.setText(manga.getTitle());
            Glide.with(context)
                    .load(manga.getImage())
                    .placeholder(R.drawable.manga_alternative)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(ivImage);
        }
    }
}
