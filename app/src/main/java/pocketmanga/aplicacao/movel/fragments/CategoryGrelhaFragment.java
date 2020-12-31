package pocketmanga.aplicacao.movel.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;

import pocketmanga.aplicacao.movel.R;
import pocketmanga.aplicacao.movel.activitys.CategoryActivity;
import pocketmanga.aplicacao.movel.activitys.MangaActivity;
import pocketmanga.aplicacao.movel.adaptadores.GrelhaCategoryAdapter;
import pocketmanga.aplicacao.movel.adaptadores.GrelhaMangaAdapter;
import pocketmanga.aplicacao.movel.listeners.CategoriesListener;
import pocketmanga.aplicacao.movel.modelo.Category;
import pocketmanga.aplicacao.movel.modelo.Chapter;
import pocketmanga.aplicacao.movel.modelo.Manga;
import pocketmanga.aplicacao.movel.modelo.SingletonGestorPocketManga;

public class CategoryGrelhaFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, CategoriesListener {
    private GridView gvGrelhaCategorias;

    private SwipeRefreshLayout swipeRefreshLayout;

    public CategoryGrelhaFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        View view = inflater.inflate(R.layout.fragment_category_grelha, container, false);
        gvGrelhaCategorias = view.findViewById(R.id.GVGrelhaCategory);

        swipeRefreshLayout=view.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(this);

        SingletonGestorPocketManga.getInstance(getContext()).setCategoriesListener(this);
        SingletonGestorPocketManga.getInstance(getContext()).getAllCategoriesAPI(getContext());

        gvGrelhaCategorias.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(getContext(), CategoryActivity.class);
                intent.putExtra(CategoryActivity.IDCATEGORY, (int) id);
                startActivity(intent);
            }
        });
        return view;
    }

    @Override
    public void onRefresh() {
        SingletonGestorPocketManga.getInstance(getContext()).getAllCategoriesAPI(getContext());
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onRefreshCategoriesList(ArrayList<Category> CategoriesList) {
        if(CategoriesList!=null)
            gvGrelhaCategorias.setAdapter(new GrelhaCategoryAdapter(getContext(),CategoriesList));
    }
}