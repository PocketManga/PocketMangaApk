package pocketmanga.aplicacao.movel.modelo;

import android.content.Context;

import java.util.ArrayList;

import pocketmanga.aplicacao.movel.R;

public class SingletonGestorPocketManga {

    private static SingletonGestorPocketManga instance = null;
    private ArrayList<Manga> mangas;
    private ArrayList<Chapter> chapters;
    private ArrayList<Author> authors;
    private ArrayList<Category> categories;

    public SingletonGestorPocketManga(Context context) {
        gerarMangas();
        gerarChapters();
    }

    public static synchronized SingletonGestorPocketManga getInstance(Context context) {
        if (instance == null)
            instance = new SingletonGestorPocketManga(context);
        return instance;
    }

    private void gerarMangas() {
        mangas = new ArrayList<>();

        Manga manga1 = new Manga(R.drawable.no_manga, "Manga 1 ahahahhf wsfewhfiojewrnfui oerhtgfr", "Alternative Manga 1", "Original Manga 1", "SrcImage", "1/11/2020", "English", "Description of this manga, well the thing is, I don't realy give a shit!!", false, false, false);
        Manga manga2 = new Manga(R.drawable.no_manga, "Manga 2", null, "Original Manga 2", "SrcImage", "2/11/2020", "English", "Description of this manga, well the thing is, I don't realy give a shit!!", true, false, false);
        Manga manga3 = new Manga(R.drawable.no_manga, "Manga 3", "Alternative Manga 3", "Original Manga 3", "SrcImage", "3/11/2020", "English", "Description of this manga, well the thing is, I don't realy give a shit!!", false, false, true);
        Manga manga4 = new Manga(R.drawable.no_manga, "Manga 4", "Alternative Manga 4", "Original Manga 4", "SrcImage", "4/11/2020", "English", "Description of this manga, well the thing is, I don't realy give a shit!!", true, true, false);
        Manga manga5 = new Manga(R.drawable.no_manga, "Manga 5", "Alternative Manga 5", "Original Manga 5", "SrcImage", "5/11/2020", "English", "Description of this manga, well the thing is, I don't realy give a shit!!", true, false, false);
        Manga manga6 = new Manga(R.drawable.no_manga, "Manga 6", "Alternative Manga 6", "Original Manga 6", "SrcImage", "6/11/2020", "English", "Description of this manga, well the thing is, I don't realy give a shit!!", true, false, false);
        Manga manga7 = new Manga(R.drawable.no_manga, "Manga 7", "Alternative Manga 7", "Original Manga 7", "SrcImage", "7/11/2020", "English", "Description of this manga, well the thing is, I don't realy give a shit!!", false, false, false);
        Manga manga8 = new Manga(R.drawable.no_manga, "Manga 8", "Alternative Manga 8", "Original Manga 8", "SrcImage", "8/11/2020", "English", "Description of this manga, well the thing is, I don't realy give a shit!!", true, false, true);
        Manga manga9 = new Manga(R.drawable.no_manga, "Manga 9", "Alternative Manga 9", "Original Manga 9", "SrcImage", "9/11/2020", "English", "Description of this manga, well the thing is, I don't realy give a shit!!", false, false, false);
        Manga manga10 = new Manga(R.drawable.no_manga,"Manga 10", "Alternative Manga 10", "Original Manga 10", "SrcImage", "10/11/2020", "English", "Description of this manga, well the thing is, I don't realy give a shit!!", true, false, false);

        manga1.setIdManga(1);
        manga2.setIdManga(2);
        manga3.setIdManga(3);
        manga4.setIdManga(4);
        manga5.setIdManga(5);
        manga6.setIdManga(6);
        manga7.setIdManga(7);
        manga8.setIdManga(8);
        manga9.setIdManga(9);
        manga10.setIdManga(10);

        mangas.add(manga1);
        mangas.add(manga2);
        mangas.add(manga3);
        mangas.add(manga4);
        mangas.add(manga5);
        mangas.add(manga6);
        mangas.add(manga7);
        mangas.add(manga8);
        mangas.add(manga9);
        mangas.add(manga10);
    }

    private void gerarChapters() {
        chapters = new ArrayList<>();
        for (Manga manga : mangas) {

            Chapter chapter1 = new Chapter(20, 1, manga.getIdManga(), 1, "there is no fun", "1/11/2020", "English", false);
            Chapter chapter2 = new Chapter(20, 1, manga.getIdManga(), 2, "there is no fun", "1/11/2020", "English", false);
            Chapter chapter3 = new Chapter(20, 1, manga.getIdManga(), 3, "there is no fun", "1/11/2020", "English", false);
            Chapter chapter4 = new Chapter(20, 1, manga.getIdManga(), 4, "there is no fun", "1/11/2020", "English", false);
            Chapter chapter5 = new Chapter(20, 1, manga.getIdManga(), 5, "there is no fun", "1/11/2020", "English", false);
            Chapter chapter6 = new Chapter(20, 1, manga.getIdManga(), 6, "there is no fun", "1/11/2020", "English", false);
            Chapter chapter7 = new Chapter(20, 2, manga.getIdManga(), 6.5, "there is no fun", "1/11/2020", "English", false);
            Chapter chapter8 = new Chapter(20, 2, manga.getIdManga(), 7, "there is no fun", "1/11/2020", "English", false);
            Chapter chapter9 = new Chapter(20, 2, manga.getIdManga(), 8, "there is no fun", "1/11/2020", "English", false);
            Chapter chapter10 = new Chapter(20, 2, manga.getIdManga(), 9, "there is no fun", "1/11/2020", "English", false);
            Chapter chapter11 = new Chapter(20, 2, manga.getIdManga(), 10, "there is no fun", "1/11/2020", "English", false);
            Chapter chapter12 = new Chapter(20, 2, manga.getIdManga(), 11, "there is no fun", "1/11/2020", "English", false);
            Chapter chapter13 = new Chapter(20, 2, manga.getIdManga(), 11.5, "there is no fun", "1/11/2020", "English", false);
            Chapter chapter14 = new Chapter(20, 2, manga.getIdManga(), 12, "there is no fun", "1/11/2020", "English", false);

            chapter1.setIdChapter(1);
            chapter2.setIdChapter(2);
            chapter3.setIdChapter(3);
            chapter4.setIdChapter(4);
            chapter5.setIdChapter(5);
            chapter6.setIdChapter(6);
            chapter7.setIdChapter(7);
            chapter8.setIdChapter(8);
            chapter9.setIdChapter(9);
            chapter10.setIdChapter(10);
            chapter11.setIdChapter(11);
            chapter12.setIdChapter(12);
            chapter13.setIdChapter(13);
            chapter14.setIdChapter(14);

            chapters.add(chapter1);
            chapters.add(chapter2);
            chapters.add(chapter3);
            chapters.add(chapter4);
            chapters.add(chapter5);
            chapters.add(chapter6);
            chapters.add(chapter7);
            chapters.add(chapter8);
            chapters.add(chapter9);
            chapters.add(chapter10);
            chapters.add(chapter11);
            chapters.add(chapter12);
            chapters.add(chapter13);
            chapters.add(chapter14);
        }
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

    public ArrayList<Chapter> getChapters(int mangaId) {
        ArrayList<Chapter> chaps = new ArrayList<>();
        for (Chapter chapter : chapters) {
            if(chapter.getMangaId() == mangaId){
                chaps.add(chapter);
            }
        }
        return chaps;
    }

    public Chapter getChapter(int id){
        for(Chapter chapter : chapters) {
            if (chapter.getIdChapter() == id)
                return chapter;
        }
        return null;
    }

    public ArrayList<Author> getAuthors() {
        return new ArrayList<>(authors);
    }

    public Author getAuthor(int id){
        for(Author author : authors) {
            if (author.getIdAuthor() == id)
                return author;
        }
        return null;
    }

    public ArrayList<Category> getCategories() {
        return new ArrayList<>(categories);
    }

    public Category getCategory(int id){
        for(Category category : categories) {
            if (category.getIdCategory() == id)
                return category;
        }
        return null;
    }
}
