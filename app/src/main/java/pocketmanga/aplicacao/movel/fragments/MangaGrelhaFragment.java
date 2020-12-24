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
    private static final int EDITAR = 1 ;
    private static final int ADICIONAR =2 ;

    private GridView gvGrelhaMangas;
    private ArrayList<Manga> mangas;
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
                //startActivity(intent);
                startActivityForResult(intent,EDITAR);
            }
        });
        return view;
    }

    //quando retornar a esta atividade
    //requestCode: código enviado atividade
    //resultCode: o código recebido da ativvidade

    /**
     *
     * @param requestCode código enviado para a atividade
     * @param resultCode o código recebido pela atividade
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(resultCode == Activity.RESULT_OK){
            switch (requestCode){
                case ADICIONAR:
                    SingletonGestorPocketManga.getInstance(getContext()).getAllMangasAPI(getContext());

                    Toast.makeText(getContext(),"Livro adicionado com sucesso", Toast.LENGTH_LONG).show();
                    break;
                case EDITAR:
                    SingletonGestorPocketManga.getInstance(getContext()).getAllMangasAPI(getContext());

                    Toast.makeText(getContext(),"Livro editado/eleminado com sucesso",Toast.LENGTH_LONG).show();
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_pesquisa,menu);

        MenuItem itemSearch = menu.findItem(R.id.itemPesquisa);
        searchView=(SearchView)itemSearch.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                ArrayList<Manga> tempMangas = new ArrayList<>();

                for (Manga mg : SingletonGestorPocketManga.getInstance(getContext()).getAllMangasAPI(getContext()))
                    if(mg.getTitle().toLowerCase().contains(newText.toLowerCase()) || mg.getOriginalTitle().toLowerCase().contains(newText.toLowerCase()) ||
                            mg.getAlternativeTitle().toLowerCase().contains(newText.toLowerCase()))
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
    public void onRefreshMangasList(ArrayList<Manga> mangasList) {
        if(mangasList!=null)
            gvGrelhaMangas.setAdapter(new GrelhaMangaAdapter(getContext(),mangasList));
    }

    @Override
    public void onRefreshInfo() {
        //EMPTY
    }
}
