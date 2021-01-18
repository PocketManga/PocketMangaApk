package pocketmanga.aplicacao.movel.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;

import pocketmanga.aplicacao.movel.R;
import pocketmanga.aplicacao.movel.activitys.MangaActivity;
import pocketmanga.aplicacao.movel.adaptadores.GrelhaMangaAdapter;
import pocketmanga.aplicacao.movel.listeners.MangasListener;
import pocketmanga.aplicacao.movel.modelo.Manga;
import pocketmanga.aplicacao.movel.modelo.SingletonGestorPocketManga;

public class BasicMangaGrelhaFragment extends Fragment implements MangasListener {
    private static String TYPE = "CATEGORY";
    private static int IDCATEGORY = 0;
    private static int LIST = 0;

    private GridView gvGrelhaMangas;

    public BasicMangaGrelhaFragment() {
        // Required empty public constructor
    }

    public BasicMangaGrelhaFragment(String type, int idCategory, int list) {
        TYPE = type;
        IDCATEGORY = idCategory;
        LIST = list;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_basic_manga_grelha, container, false);
        gvGrelhaMangas = view.findViewById(R.id.GVBasicGrelhaMangas);

        SingletonGestorPocketManga.getInstance(getContext()).setMangasListener(this);

        if(TYPE.equals("CATEGORY")) {
            SingletonGestorPocketManga.getInstance(getContext()).getCategoryMangasAPI(getContext(), IDCATEGORY);
        }
        if(TYPE.equals("LIBRARY")) {
            SingletonGestorPocketManga.getInstance(getContext()).getLibraryMangasBD(LIST);
        }
        if(TYPE.equals("FAVORITE")) {
            SingletonGestorPocketManga.getInstance(getContext()).getFavoriteMangasBD();
        }
        if(TYPE.equals("DOWNLOAD")) {
            SingletonGestorPocketManga.getInstance(getContext()).getDownloadMangasBD();
        }

        gvGrelhaMangas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), MangaActivity.class);
                intent.putExtra(MangaActivity.IDMANGA, (int) id);
                if(TYPE.equals("DOWNLOAD"))
                    intent.putExtra(MangaActivity.DOWNLOADED, true);
                startActivity(intent);
            }
        });
        return view;
    }

    @Override
    public void onRefreshMangasList(ArrayList<Manga> MangasList) {
        if(MangasList!=null)
            gvGrelhaMangas.setAdapter(new GrelhaMangaAdapter(getContext(),MangasList));

    }
}