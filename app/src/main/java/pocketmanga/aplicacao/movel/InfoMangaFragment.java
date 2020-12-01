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
    private TextView tvDescriptionTop, tvTitle, tvAlternativeTitle, tvOriginalTitle, tvAuthor, tvR18,
            tvReleaseDate, tvServer, tvStatus, tvOneshot, tvChapters, tvCategories, tvDescription,
            tvLabelAlternativeTitle, tvLabelOriginalTitle, tvLabelOneshot, tvLabelR18;

    public InfoMangaFragment(Manga manga) {
        this.manga = manga;
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
        tvR18 = view.findViewById(R.id.TVR18);
        tvChapters = view.findViewById(R.id.TVChapters);
        tvCategories = view.findViewById(R.id.TVCategories);
        tvDescription = view.findViewById(R.id.TVDescription);

        tvLabelAlternativeTitle = view.findViewById(R.id.AlternativeTitle);
        tvLabelOriginalTitle = view.findViewById(R.id.OriginalTitle);
        tvLabelOneshot = view.findViewById(R.id.Oneshot);
        tvLabelR18 = view.findViewById(R.id.R18);

        if(manga != null){
            carregarMangaInformation();
        }

        return view;
    }


    private void carregarMangaInformation() {
        tvTitle.setText(manga.getTitle());
        if(manga.getAlternativeTitle() != null) {
            tvAlternativeTitle.setText(manga.getAlternativeTitle());
        }else{
            tvLabelAlternativeTitle.getLayoutParams().height = 0;
            tvAlternativeTitle.getLayoutParams().height = 0;
        }
        if(manga.getOriginalTitle() != null) {
            tvOriginalTitle.setText(manga.getOriginalTitle());
        }else{
            tvLabelOriginalTitle.getLayoutParams().height = 0;
            tvOriginalTitle.getLayoutParams().height = 0;
        }
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
            tvLabelOneshot.getLayoutParams().height = 0;
            tvOneshot.getLayoutParams().height = 0;
        }
        if(manga.isR18()) {
            tvR18.setText("Yes");
        }else{
            tvLabelR18.getLayoutParams().height = 0;
            tvR18.getLayoutParams().height = 0;
        }
        //tvChapters.setText(manga.getAutor());
        //tvCategories.setText(manga.get());
        if(manga.getDescription() != null) {
            tvDescription.setText(manga.getDescription());
        }else{
            tvDescription.setText("N/A");
        }
    }
}