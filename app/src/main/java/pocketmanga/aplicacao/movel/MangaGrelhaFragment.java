package pocketmanga.aplicacao.movel;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;

import pocketmanga.aplicacao.movel.adaptadores.GrelhaMangaAdapter;
import pocketmanga.aplicacao.movel.modelo.Manga;
import pocketmanga.aplicacao.movel.modelo.SingletonGestorPocketManga;

public class MangaGrelhaFragment extends Fragment {
    private GridView gvGrelhaMangas;
    private ArrayList<Manga> mangas;
    private String Type;

    public MangaGrelhaFragment(String type) {
        // Required empty public constructor
        this.Type = type;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_manga_grelha, container, false);

        gvGrelhaMangas = view.findViewById(R.id.GVGrelhaMangas);
        mangas = SingletonGestorPocketManga.getInstance(getContext()).getMangas();
        gvGrelhaMangas.setAdapter(new GrelhaMangaAdapter(getContext(),mangas));

        gvGrelhaMangas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), MangaActivity.class);
                intent.putExtra(MangaActivity.IDMANGA,(int) id);
                startActivity(intent);
            }
        });

        return view;
    }
}