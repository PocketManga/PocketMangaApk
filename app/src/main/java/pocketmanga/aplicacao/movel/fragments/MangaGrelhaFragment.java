package pocketmanga.aplicacao.movel.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
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

import java.util.ArrayList;

import pocketmanga.aplicacao.movel.R;
import pocketmanga.aplicacao.movel.activitys.MangaActivity;
import pocketmanga.aplicacao.movel.adaptadores.GrelhaMangaAdapter;
import pocketmanga.aplicacao.movel.listeners.MangasListener;
import pocketmanga.aplicacao.movel.modelo.Manga;
import pocketmanga.aplicacao.movel.modelo.SingletonGestorPocketManga;

public class MangaGrelhaFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, MangasListener {

    private GridView gvGrelhaMangas;

    private SearchView searchView;
    private SwipeRefreshLayout swipeRefreshLayout;

    public MangaGrelhaFragment() {
        // Required empty public constructor
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
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_search,menu);

        MenuItem searchItem=menu.findItem(R.id.searchItem);

        searchView = (SearchView)searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                ArrayList<Manga> tempMangas = new ArrayList<>();

                for (Manga mg : SingletonGestorPocketManga.getInstance(getContext()).getMangas())
                    if(mg.getTitle().toLowerCase().contains(newText.toLowerCase()) || mg.getAlternativeTitle().toLowerCase().contains(newText.toLowerCase()) ||
                            mg.getOriginalTitle().toLowerCase().contains(newText.toLowerCase()) || mg.getAuthors().toLowerCase().contains(newText.toLowerCase()))
                        tempMangas.add(mg);

                gvGrelhaMangas.setAdapter(new GrelhaMangaAdapter(getContext(),tempMangas));

                return true;
            }
        });

        super.onCreateOptionsMenu(menu, inflater);
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
    public void onRefreshMangasList(ArrayList<Manga> MangasList) {
        if(MangasList!=null)
            gvGrelhaMangas.setAdapter(new GrelhaMangaAdapter(getContext(),MangasList));
    }
}
