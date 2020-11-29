package pocketmanga.aplicacao.movel;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import pocketmanga.aplicacao.movel.modelo.Manga;

public class InfoMangaFragment extends Fragment {

    private Manga manga;
    private TextView tvDescriptionTop, tvTitle, tvAlternativeTitle, tvOriginalTitle, tvAuthor, tvReleaseDate, tvServer, tvStatus, tvOneshot, tvChapters, tvCategories, tvDescription;

    public InfoMangaFragment(Manga manga) {
        this.manga = manga;
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_info_manga, container, false);
        // Inflate the layout for this fragment
        tvTitle = view.findViewById(R.id.TVTitle);
        tvAlternativeTitle = view.findViewById(R.id.TVAlternativeTitle);
        tvOriginalTitle = view.findViewById(R.id.TVOriginalTitle);
        tvAuthor = view.findViewById(R.id.TVAuthor);
        tvReleaseDate = view.findViewById(R.id.TVReleaseDate);
        tvServer = view.findViewById(R.id.TVServer);
        tvStatus = view.findViewById(R.id.TVStatus);
        tvOneshot = view.findViewById(R.id.TVOneshot);
        tvChapters = view.findViewById(R.id.TVChapters);
        tvCategories = view.findViewById(R.id.TVCategories);
        tvDescription = view.findViewById(R.id.TVDescription);

        if(manga != null){
            carregarMangaInformation();
        }

        return view;
    }


    private void carregarMangaInformation() {
        tvTitle.setText(manga.getTitle());
        tvAlternativeTitle.setText(manga.getAlternativeTitle());
        tvOriginalTitle.setText(manga.getOriginalTitle());
        //tvAuthor.setText(manga.get());
        tvReleaseDate.setText(manga.getReleaseDate()+"");
        tvServer.setText(manga.getServer());
        if(manga.isStatus()) {
            tvStatus.setText("Completed");
        }else{
            tvStatus.setText("Ongoing");
        }
        if(manga.isOneshot()) {
            tvOneshot.setText("Yes");
        }else{
            tvOneshot.setText("No");
        }
        //tvChapters.setText(manga.getAutor());
        //tvCategories.setText(manga.get());
        tvDescription.setText(manga.getDescription());
    }
}