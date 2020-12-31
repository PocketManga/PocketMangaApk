package pocketmanga.aplicacao.movel.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import pocketmanga.aplicacao.movel.modelo.Chapter;
import pocketmanga.aplicacao.movel.modelo.Manga;

public class ChapterJsonParser {

    public static ArrayList<Chapter> parserJsonChapters(JSONArray response) {
        ArrayList<Chapter> chapters = new ArrayList<>();
        try {
            for (int i = 0; i < response.length(); i++) {
                JSONObject chapter = (JSONObject) response.get(i);

                int idChapter = chapter.getInt("IdChapter");
                int mangaId = chapter.getInt("MangaId");
                int pagesNumber = chapter.getInt("PagesNumber");
                int season = chapter.getInt("Season");
                double number = chapter.getDouble("Number");
                String name = chapter.getString("Name");
                String releaseDate = chapter.getString("ReleaseDate");
                String srcFolder = chapter.getString("SrcFolder");
                String urlImage = chapter.getString("UrlImage");
                boolean oneShot = chapter.getBoolean("OneShot");
                boolean readed = chapter.getBoolean("Readed");

                Chapter auxChapter = new Chapter(idChapter, pagesNumber, season, mangaId, number, name, releaseDate, srcFolder, urlImage, oneShot, readed);
                chapters.add(auxChapter);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return chapters;
    }
}
