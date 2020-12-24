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
import pocketmanga.aplicacao.movel.modelo.Chapter;
import pocketmanga.aplicacao.movel.modelo.Manga;

public class ChapterListFragment extends Fragment {

    private ListView lvListaChapters;
    private ArrayList<Chapter> chapters;
    private Manga manga;

    public ChapterListFragment(Manga manga) {
        // Required empty public constructor
        this.manga = manga;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chapter_list, container, false);

        lvListaChapters = view.findViewById(R.id.LVListaChapters);
        //chapters = SingletonGestorPocketManga.getInstance(getContext()).getChapters(manga.getIdManga());
        lvListaChapters.setAdapter(new ListaChapterAdapter(getContext(),chapters));

        lvListaChapters.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), ChapterActivity.class);
                intent.putExtra(ChapterActivity.IDCHAPTER,(int) id);
                startActivity(intent);
            }
        });
/*
        FloatingActionButton fab = view.findViewById(R.id.fav);
        fab.setOnClickListener({
                Intent intent = new Intent()
        });*/

        return view;
    }
}