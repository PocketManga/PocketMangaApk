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

import pocketmanga.aplicacao.movel.adaptadores.GrelhaMangaAdaptador;
import pocketmanga.aplicacao.movel.modelo.Manga;
import pocketmanga.aplicacao.movel.modelo.SingletonGestorMangas;

public class LatestGrelhaFragment extends Fragment {
    private GridView gvGrelhaMangas;
    private ArrayList<Manga> mangas;

    public LatestGrelhaFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_latest_grelha, container, false);

        gvGrelhaMangas = view.findViewById(R.id.GVGrelhaMangas);
        mangas = SingletonGestorMangas.getInstance().getMangas();
        gvGrelhaMangas.setAdapter(new GrelhaMangaAdaptador(getContext(),mangas));

        gvGrelhaMangas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(),InfoMangaActivity.class);
                intent.putExtra(InfoMangaActivity.IDMANGA,(int) id);
                startActivity(intent);
            }
        });

        return view;
    }
}