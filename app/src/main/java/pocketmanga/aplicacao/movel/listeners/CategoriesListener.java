package pocketmanga.aplicacao.movel.listeners;

import java.util.ArrayList;

import pocketmanga.aplicacao.movel.modelo.Category;

public interface CategoriesListener {
    void onRefreshCategoriesList(ArrayList<Category> CategoriesList);
}
