package pocketmanga.aplicacao.movel.listeners;

import java.util.ArrayList;

import pocketmanga.aplicacao.movel.modelo.Manga;

public interface MangasListener {
    void onRefreshMangasList(ArrayList<Manga> MangasList);
}
