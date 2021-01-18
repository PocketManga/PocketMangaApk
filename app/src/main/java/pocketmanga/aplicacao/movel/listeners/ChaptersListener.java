package pocketmanga.aplicacao.movel.listeners;

import java.util.ArrayList;

import pocketmanga.aplicacao.movel.modelo.Chapter;

public interface ChaptersListener {
    void onRefreshChaptersList(ArrayList<Chapter> ChaptersList);

    void onRefreshSelectedChaptersList(ArrayList<Chapter> ChaptersList);

    void onRefreshChapterImages(Chapter chapter);
}
