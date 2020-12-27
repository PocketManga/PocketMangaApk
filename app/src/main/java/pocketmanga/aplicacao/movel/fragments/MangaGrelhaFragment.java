package pocketmanga.aplicacao.movel.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;

import pocketmanga.aplicacao.movel.R;
import pocketmanga.aplicacao.movel.activitys.MangaActivity;
import pocketmanga.aplicacao.movel.adaptadores.GrelhaMangaAdapter;
import pocketmanga.aplicacao.movel.listeners.MangasListener;
import pocketmanga.aplicacao.movel.modelo.Manga;
import pocketmanga.aplicacao.movel.modelo.SingletonGestorPocketManga;

public class MangaGrelhaFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, MangasListener {
    private GridView gvGrelhaMangas;
    //private String Type;

    private SearchView searchView;
    private SwipeRefreshLayout swipeRefreshLayout;

    public MangaGrelhaFragment() {
        // Required empty public constructor
        //this.Type = type; // serve para dizer que é os lastests
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        View view = inflater.inflate(R.layout.fragment_manga_grelha, container, false);
        gvGrelhaMangas = view.findViewById(R.id.GVGrelhaMangas);

        swipeRefreshLayout=view.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(this);

        SingletonGestorPocketManga.getInstance(getContext()).setMangasListener(this);
        SingletonGestorPocketManga.getInstance(getContext()).getAllMangasAPI(getContext());

        gvGrelhaMangas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), MangaActivity.class);
                intent.putExtra(MangaActivity.IDMANGA, (int) id);
                startActivity(intent);
                //startActivity(intent,EDITAR);
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        //Fechar a searchView
        super.onResume();
        if (searchView!=null)
            searchView.onActionViewCollapsed();

        SingletonGestorPocketManga.getInstance(getContext()).setMangasListener(this);
    }

    @Override
    public void onRefresh() {
        SingletonGestorPocketManga.getInstance(getContext()).getAllMangasAPI(getContext());
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onRefreshMangasList(ArrayList<Manga> mangasList) {
        if(mangasList!=null)
            gvGrelhaMangas.setAdapter(new GrelhaMangaAdapter(getContext(),mangasList));
    }

    @Override
    public void onRefreshInfo() {

    }
}
