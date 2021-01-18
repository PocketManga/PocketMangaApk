package pocketmanga.aplicacao.movel.activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import pocketmanga.aplicacao.movel.R;
import pocketmanga.aplicacao.movel.fragments.BasicMangaGrelhaFragment;
import pocketmanga.aplicacao.movel.modelo.Category;
import pocketmanga.aplicacao.movel.modelo.SingletonGestorPocketManga;

public class CategoryActivity extends AppCompatActivity {
    public static final String IDCATEGORY = "IDCATEGORY";
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_category);

        fragmentManager = getSupportFragmentManager();

        int id = getIntent().getIntExtra(IDCATEGORY, 0);
        Fragment fragment = new BasicMangaGrelhaFragment("CATEGORY", id, 0);

        Category category = SingletonGestorPocketManga.getInstance(getApplicationContext()).getCategory(id);
        setTitle(category.getName());
        fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();
    }
}