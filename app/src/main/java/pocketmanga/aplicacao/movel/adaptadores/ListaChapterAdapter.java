package pocketmanga.aplicacao.movel.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import pocketmanga.aplicacao.movel.R;
import pocketmanga.aplicacao.movel.modelo.Chapter;

public class ListaChapterAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private ArrayList<Chapter> chapters;

    public ListaChapterAdapter(Context context, ArrayList<Chapter> chapters) {
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
    public View getView(int position, View convertView, ViewGroup parent) {
        if(inflater==null)
            inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if(convertView==null)
            convertView=inflater.inflate(R.layout.item_list_chapter, null);

        ViewHolderList viewHolder = (ViewHolderList)convertView.getTag();
        if(viewHolder == null) {
            viewHolder = new ViewHolderList(convertView);
            convertView.setTag(viewHolder);
        }

        viewHolder.update(chapters.get(position));

        return convertView;
    }

    private class ViewHolderList{
        private TextView tvTitle, tvReleaseDate;

        public ViewHolderList(View view) {
            tvTitle = view.findViewById(R.id.TVTitle);
            tvReleaseDate = view.findViewById(R.id.TVReleaseDate);
        }

        public void update(Chapter chapter){
            String Text = "S"+chapter.getSeason()+" - Chapter "+(chapter.getNumber()+"").replace(".0", "") + ((chapter.getName().equals("null") || chapter.getName() == null)?"":" - "+chapter.getName());
            tvTitle.setText(Text);

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                Date date = simpleDateFormat.parse(chapter.getReleaseDate());

                Date currentDate = new Date();

                long diff = currentDate.getTime() - date.getTime();
                long seconds = diff / 1000;
                long minutes = seconds / 60;
                long hours = minutes / 60;
                long days = hours / 24;

                String ReleaseDate = date+"";

                if(days == 0 && hours == 0 && minutes == 0){
                    ReleaseDate = seconds+((seconds==1)?" second":" seconds")+" ago";
                }

                if(days == 0 && hours == 0 && minutes > 0){
                    ReleaseDate = minutes+((minutes==1)?" minute":" minutes")+" ago";
                }

                if(days == 0 && hours > 0 ){
                    ReleaseDate = hours+((hours==1)?" hour":" hours")+" ago";
                }

                if(days <= 7 && days >= 1){
                    ReleaseDate = days+((days==1)?" day":" days")+" ago";
                }

                String textDate = ReleaseDate+"";
                tvReleaseDate.setText(textDate);

            } catch (ParseException e) {
                String error = "Error";
                tvReleaseDate.setText(error);
                e.printStackTrace();
            }
        }
    }
}
