package pocketmanga.aplicacao.movel.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import pocketmanga.aplicacao.movel.R;
import pocketmanga.aplicacao.movel.modelo.Category;

public class GrelhaCategoryAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private ArrayList<Category> categories;

    public GrelhaCategoryAdapter(Context context, ArrayList<Category> categories) {
        this.context = context;
        this.categories = categories;
    }

    @Override
    public int getCount() {
        return categories.size();
    }

    @Override
    public Object getItem(int position) {
        return categories.get(position);
    }

    @Override
    public long getItemId(int position) {
        return categories.get(position).getIdCategory();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(inflater==null)
            inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if(convertView==null)
            convertView=inflater.inflate(R.layout.item_grelha_category, null);

        ViewHolderGrelha viewHolder = (ViewHolderGrelha)convertView.getTag();
        if(viewHolder == null) {
            viewHolder = new ViewHolderGrelha(convertView);
            convertView.setTag(viewHolder);
        }

        viewHolder.update(categories.get(position));

        return convertView;
    }

    private class ViewHolderGrelha{
        private TextView tvName, tvNumber;

        public ViewHolderGrelha(View view) {
            tvName = view.findViewById(R.id.TVName);
            tvNumber = view.findViewById(R.id.TVNumber);
        }

        public void update(Category category){
            String text = "( "+category.getNumMangas()+" )";
            tvName.setText(category.getName());
            tvNumber.setText(text);
        }
    }
}
