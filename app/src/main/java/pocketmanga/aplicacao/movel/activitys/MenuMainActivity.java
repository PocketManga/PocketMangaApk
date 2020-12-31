package pocketmanga.aplicacao.movel.activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

import pocketmanga.aplicacao.movel.fragments.BasicMangaGrelhaFragment;
import pocketmanga.aplicacao.movel.fragments.CategoryGrelhaFragment;
import pocketmanga.aplicacao.movel.fragments.DefinitionsFragment;
import pocketmanga.aplicacao.movel.fragments.MangaGrelhaFragment;
import pocketmanga.aplicacao.movel.R;

public class MenuMainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public static final String USERNAME = "USERNAME";
    public static final String TOKEN="TOKEN";
    public static final String ID_USER="ID_USER";
    public static final String PREF_INFO_USER ="PREF_INFO_USER";

    private NavigationView navigationView;
    private DrawerLayout drawer;
    private String username = "";
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        navigationView = findViewById(R.id.nav_view);
        drawer = findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.ndOpen, R.string.ndClose);
        toggle.syncState();

        drawer.addDrawerListener(toggle);
        navigationView.setNavigationItemSelectedListener(this);
        fragmentManager = getSupportFragmentManager();

        carregarCabecalho();
        carregarFragmentoInicial();
    }

    private void carregarCabecalho() {
        SharedPreferences sharedPrefInfoUser = getSharedPreferences(PREF_INFO_USER, Context.MODE_PRIVATE);

        username = sharedPrefInfoUser.getString(USERNAME,"Reader");

        View hView = navigationView.getHeaderView(0);
        TextView tvUsername = hView.findViewById(R.id.TvNavUsername);
        tvUsername.setText(username);

        setTitle("Manga List");
    }

    private void carregarFragmentoInicial() {
        navigationView.setCheckedItem(R.id.NavLatest);
        Fragment fragment = new MangaGrelhaFragment();
        fragmentManager.beginTransaction().replace(R.id.contentFragment, fragment).commit();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment= null;
        switch (item .getItemId()) {
            case R.id.NavLatest:
                fragment = new MangaGrelhaFragment();
                setTitle(item.getTitle());
                break;
            case R.id.NavCategory:
                fragment = new CategoryGrelhaFragment();
                setTitle(item.getTitle());
                break;
            case R.id.NavToRead:
                fragment = new BasicMangaGrelhaFragment("LIBRARY", 0, 1);
                setTitle(item.getTitle());
                break;
            case R.id.NavReading:
                fragment = new BasicMangaGrelhaFragment("LIBRARY", 0, 2);
                setTitle(item.getTitle());
                break;
            case R.id.NavCompleted:
                fragment = new BasicMangaGrelhaFragment("LIBRARY", 0, 3);
                setTitle(item.getTitle());
                break;
            case R.id.NavFavorite:
                fragment = new BasicMangaGrelhaFragment("FAVORITE", 0, 0);
                setTitle(item.getTitle());
                break;
            case R.id.NavDownload:
                fragment = new BasicMangaGrelhaFragment("DOWNLOAD", 0, 0);
                setTitle(item.getTitle());
                break;
            case R.id.NavDefinitions:
                fragment = new DefinitionsFragment();
                setTitle(item.getTitle());
                break;
        }
        if (fragment != null)
            fragmentManager.beginTransaction().replace(R.id.contentFragment, fragment).commit();

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}