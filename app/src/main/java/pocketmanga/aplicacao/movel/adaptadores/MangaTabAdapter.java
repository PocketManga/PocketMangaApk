package pocketmanga.aplicacao.movel.adaptadores;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import pocketmanga.aplicacao.movel.ChapterListFragment;
import pocketmanga.aplicacao.movel.InfoMangaFragment;
import pocketmanga.aplicacao.movel.modelo.Manga;

public class MangaTabAdapter extends FragmentPagerAdapter {
    private int numOfTabs;
    private Manga manga;

    public MangaTabAdapter(FragmentManager fm, int numOfTabs, Manga manga) {
        super(fm);
        this.numOfTabs = numOfTabs;
        this.manga = manga;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0:
                return new InfoMangaFragment(manga);
            case 1:
                return new ChapterListFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numOfTabs;
    }
}
