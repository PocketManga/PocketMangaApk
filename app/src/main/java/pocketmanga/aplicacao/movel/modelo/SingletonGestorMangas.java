package pocketmanga.aplicacao.movel.modelo;

import java.util.ArrayList;

import pocketmanga.aplicacao.movel.R;

public class SingletonGestorMangas {

    private static SingletonGestorMangas instance = null;
    private ArrayList<Manga> mangas;

    public SingletonGestorMangas() {
        gerarMangas();
    }

    public static synchronized SingletonGestorMangas getInstance() {
        if (instance == null)
            instance = new SingletonGestorMangas();
        return instance;
    }

    private void gerarMangas() {
        mangas = new ArrayList<>();

        mangas.add(new Manga(1, R.drawable.no_manga, "Manga 1", "Alternative Manga 1", "Original Manga 1", "SrcImage", "1/11/2020", "Description of this manga, well the thing is, I don't realy give a shit!!", false, false, false));
        mangas.add(new Manga(2, R.drawable.no_manga, "Manga 2", "Alternative Manga 2", "Original Manga 2", "SrcImage", "2/11/2020", "Description of this manga, well the thing is, I don't realy give a shit!!", true, false, false));
        mangas.add(new Manga(3, R.drawable.no_manga, "Manga 3", "Alternative Manga 3", "Original Manga 3", "SrcImage", "3/11/2020", "Description of this manga, well the thing is, I don't realy give a shit!!", false, false, true));
        mangas.add(new Manga(4, R.drawable.no_manga, "Manga 4", "Alternative Manga 4", "Original Manga 4", "SrcImage", "4/11/2020", "Description of this manga, well the thing is, I don't realy give a shit!!", true, true, false));
        mangas.add(new Manga(5, R.drawable.no_manga, "Manga 5", "Alternative Manga 5", "Original Manga 5", "SrcImage", "5/11/2020", "Description of this manga, well the thing is, I don't realy give a shit!!", true, false, false));
        mangas.add(new Manga(6, R.drawable.no_manga, "Manga 6", "Alternative Manga 6", "Original Manga 6", "SrcImage", "6/11/2020", "Description of this manga, well the thing is, I don't realy give a shit!!", true, false, false));
        mangas.add(new Manga(7, R.drawable.no_manga, "Manga 7", "Alternative Manga 7", "Original Manga 7", "SrcImage", "7/11/2020", "Description of this manga, well the thing is, I don't realy give a shit!!", false, false, false));
        mangas.add(new Manga(8, R.drawable.no_manga, "Manga 8", "Alternative Manga 8", "Original Manga 8", "SrcImage", "8/11/2020", "Description of this manga, well the thing is, I don't realy give a shit!!", true, false, true));
        mangas.add(new Manga(9, R.drawable.no_manga, "Manga 9", "Alternative Manga 9", "Original Manga 9", "SrcImage", "9/11/2020", "Description of this manga, well the thing is, I don't realy give a shit!!", false, false, false));
        mangas.add(new Manga(10, R.drawable.no_manga,"Manga 10", "Alternative Manga 10", "Original Manga 10", "SrcImage", "10/11/2020", "Description of this manga, well the thing is, I don't realy give a shit!!", true, false, false));
    }

    public ArrayList<Manga> getMangas() {
        return new ArrayList<>(mangas);
    }

    public Manga getManga(int id){
        for(Manga manga : mangas) {
            if (manga.getIdManga() == id)
                return manga;
        }
        return null;
    }
}
