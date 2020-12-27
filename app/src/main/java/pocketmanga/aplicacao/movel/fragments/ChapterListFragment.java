package pocketmanga.aplicacao.movel.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;

import java.util.ArrayList;

import pocketmanga.aplicacao.movel.R;
import pocketmanga.aplicacao.movel.activitys.ChapterActivity;
import pocketmanga.aplicacao.movel.activitys.MangaActivity;
import pocketmanga.aplicacao.movel.adaptadores.GrelhaMangaAdapter;
import pocketmanga.aplicacao.movel.adaptadores.ListaChapterAdapter;
import pocketmanga.aplicacao.movel.listeners.ChaptersListener;
import pocketmanga.aplicacao.movel.listeners.MangasListener;
import pocketmanga.aplicacao.movel.modelo.Chapter;
import pocketmanga.aplicacao.movel.modelo.Manga;
import pocketmanga.aplicacao.movel.modelo.SingletonGestorPocketManga;

public class ChapterListFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, ChaptersListener {

    private ListView lvListaChapters;
    private ArrayList<Chapter> chapters;
    private Manga manga;

    private SearchView searchView;

    public ChapterListFragment(Manga manga) {
        // Required empty public constructor
        this.manga = manga;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        View view = inflater.inflate(R.layout.fragment_chapter_list, container, false);
        lvListaChapters = view.findViewById(R.id.LVListaChapters);

        SingletonGestorPocketManga.getInstance(getContext()).setChaptersListener(this);
        SingletonGestorPocketManga.getInstance(getContext()).getAllChaptersAPI(getContext(), manga.getIdManga());

        lvListaChapters.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), ChapterActivity.class);
                intent.putExtra(ChapterActivity.IDCHAPTER, (int) id);
                startActivity(intent);
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


        SingletonGestorPocketManga.getInstance(getContext()).setChaptersListener(this);
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onRefreshChaptersList(ArrayList<Chapter> ChaptersList) {
        if(ChaptersList!=null)
            lvListaChapters.setAdapter(new ListaChapterAdapter(getContext(),ChaptersList));
    }

    @Override
    public void onRefreshChapterImages(Chapter chapter) {
        // Don't do nothing
    }
}