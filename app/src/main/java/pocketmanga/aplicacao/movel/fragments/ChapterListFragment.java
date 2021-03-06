package pocketmanga.aplicacao.movel.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import pocketmanga.aplicacao.movel.R;
import pocketmanga.aplicacao.movel.activitys.ChapterActivity;
import pocketmanga.aplicacao.movel.adaptadores.ListaChapterAdapter;
import pocketmanga.aplicacao.movel.listeners.ChaptersListener;
import pocketmanga.aplicacao.movel.modelo.Chapter;
import pocketmanga.aplicacao.movel.modelo.Manga;
import pocketmanga.aplicacao.movel.modelo.SingletonGestorPocketManga;
import pocketmanga.aplicacao.movel.utils.ConnectionJsonParser;

public class ChapterListFragment extends Fragment implements ChaptersListener {

    public static final String IDMANGA = "IDMANGA";
    private ListView lvListaChapters;

    private Manga manga;

    public ChapterListFragment() {
        // Required empty public constructor
    }

    public ChapterListFragment(Manga manga) {
        this.manga = manga;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        View view = inflater.inflate(R.layout.fragment_chapter_list, container, false);
        lvListaChapters = view.findViewById(R.id.LVListaChapters);

        SingletonGestorPocketManga.getInstance(getContext()).setChaptersListener(this);
        if(manga != null) {
            if (ConnectionJsonParser.isConnectionInternet(getContext())) {
                SingletonGestorPocketManga.getInstance(getContext()).getAllChaptersAPI(getContext(), manga.getIdManga());
            }else{
                SingletonGestorPocketManga.getInstance(getContext()).getChaptersBD(manga.getIdManga());
            }
        }

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
        super.onResume();
        SingletonGestorPocketManga.getInstance(getContext()).setChaptersListener(this);
    }

    @Override
    public void onRefreshChaptersList(ArrayList<Chapter> ChaptersList) {
        if(ChaptersList!=null)
            lvListaChapters.setAdapter(new ListaChapterAdapter(getContext(),ChaptersList));
    }

    @Override
    public void onRefreshSelectedChaptersList(ArrayList<Chapter> ChaptersList) {
        // Don't do nothing
    }

    @Override
    public void onRefreshChapterImages(Chapter chapter) {
        // Don't do nothing
    }
}