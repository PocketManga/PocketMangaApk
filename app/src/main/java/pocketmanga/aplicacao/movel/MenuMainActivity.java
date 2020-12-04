package pocketmanga.aplicacao.movel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

public class MenuMainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    public static final String USERNAME = "username";
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
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer,
                toolbar, R.string.ndOpen, R.string.ndClose);
        toggle.syncState();
        drawer.addDrawerListener(toggle);
        navigationView.setNavigationItemSelectedListener(this);
        fragmentManager = getSupportFragmentManager();


        carregarCabecalho();
        carregarFragmentoInicial();
    }

    private void carregarCabecalho() {
        username = getIntent().getStringExtra(USERNAME);
        View hView = navigationView.getHeaderView(0);
        TextView tvEmail = hView.findViewById(R.id.TvEmail);
        tvEmail.setText(username);
    }

    private void carregarFragmentoInicial() {
        navigationView.setCheckedItem(R.id.NavLatest);
        Fragment fragment = new MangaGrelhaFragment("latest");
        fragmentManager.beginTransaction().replace(R.id.contentFragment, fragment).commit();
        setTitle("Latest");
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;
        switch (item .getItemId()) {
            case R.id.NavLatest:
                System.out.println("-->Nav Latest");
                //fragment = new ListaLivrosFragment();
                //setTitle(item.getTitle());
                break;
            case R.id.NavCategory:
                System.out.println("-->Nav Category");
                //fragment = new ListaLivrosFragment();
                //setTitle(item.getTitle());
                break;
            case R.id.NavToRead:
                System.out.println("-->Nav To Read");
                //fragment = new DinamicoFragment();
                //setTitle(item.getTitle());
                break;
            case R.id.NavReading:
                System.out.println("-->Nav Reading");
                //TODO: intent implicto (Action.Send_To)
                break;
            case R.id.NavCompleted:
                System.out.println("-->Nav Completed");
                //TODO: intent implicto (Action.Send_To)
                break;
            case R.id.NavFavorite:
                System.out.println("-->Nav Favorite");
                //TODO: intent implicto (Action.Send_To)
                break;
            case R.id.NavDownload:
                System.out.println("-->Nav Download");
                //TODO: intent implicto (Action.Send_To)
                break;
            case R.id.NavDefinitions:
                System.out.println("-->Nav Definitions");
                //TODO: intent implicto (Action.Send_To)
                break;
        }
        if (fragment != null)
            fragmentManager.beginTransaction().replace(R.id.contentFragment, fragment).commit();

        drawer.closeDrawer(GravityCompat.START);
        return true;

    }
}