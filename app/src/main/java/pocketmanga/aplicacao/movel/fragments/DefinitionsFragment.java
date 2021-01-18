package pocketmanga.aplicacao.movel.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import pocketmanga.aplicacao.movel.R;
import pocketmanga.aplicacao.movel.activitys.MenuMainActivity;
import pocketmanga.aplicacao.movel.listeners.ServersListener;
import pocketmanga.aplicacao.movel.modelo.Server;
import pocketmanga.aplicacao.movel.modelo.SingletonGestorPocketManga;
import pocketmanga.aplicacao.movel.modelo.User;
import pocketmanga.aplicacao.movel.utils.ConnectionJsonParser;

public class DefinitionsFragment extends Fragment implements ServersListener {
    private User user;

    private TextView tvUsername, tvUserEmail, tvTotalFavorite, tvTotalToRead, tvTotalReading, tvTotalCompleted, tvTotalMangaDownloaded, tvTotalChapterDownloaded;
    private ImageView ivUserImage;
    private RadioButton rbModeScroll, rbModePaginated, rbThemeDark, rbThemeLight;
    private RadioGroup rgReadingMode, rgTheme;
    private Spinner spServer;
    private Button btnLogout;

    public DefinitionsFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_definitions, container, false);

        user = SingletonGestorPocketManga.getInstance(getContext()).getUser();

        tvUsername = view.findViewById(R.id.TVUsername);
        tvUserEmail = view.findViewById(R.id.TVUserEmail);
        tvTotalFavorite = view.findViewById(R.id.TVTotalFavorite);
        tvTotalToRead = view.findViewById(R.id.TVTotalToRead);
        tvTotalReading = view.findViewById(R.id.TVTotalReading);
        tvTotalCompleted = view.findViewById(R.id.TVTotalCompleted);
        tvTotalMangaDownloaded = view.findViewById(R.id.TVTotalMangaDownloaded);
        tvTotalChapterDownloaded = view.findViewById(R.id.TVTotalChapterDownloaded);
        btnLogout = view.findViewById(R.id.BTNLogout);

        ivUserImage = view.findViewById(R.id.IVUserImage);

        rbModeScroll = view.findViewById(R.id.RBModeScroll);
        rbModePaginated = view.findViewById(R.id.RBModePaginated);
        rbThemeDark = view.findViewById(R.id.RBThemeDark);
        rbThemeLight = view.findViewById(R.id.RBThemeLight);

        rgReadingMode = view.findViewById(R.id.RGReadingMode);
        rgTheme = view.findViewById(R.id.RGTheme);

        spServer = view.findViewById(R.id.SPServer);

        SetInfo();
        SingletonGestorPocketManga.getInstance(getContext()).setServersListener(this);

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Activity activity = getActivity();
                if(activity!=null)
                    activity.finish();

                SharedPreferences sharedPrefUser = getContext().getSharedPreferences(MenuMainActivity.PREF_INFO_USER, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPrefUser.edit();
                editor.putString(MenuMainActivity.TOKEN,null);
                editor.apply();
            }
        });

        rgReadingMode.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId) {
                    case R.id.RBModeScroll:
                        rbModeScroll.setChecked(true);
                        rbModeScroll.setButtonDrawable(R.drawable.ic_action_radio_button_checked);
                        rbModePaginated.setChecked(false);
                        rbModePaginated.setButtonDrawable(R.drawable.ic_action_radio_button_unchecked);
                        break;
                    case R.id.RBModePaginated:
                        rbModePaginated.setChecked(true);
                        rbModePaginated.setButtonDrawable(R.drawable.ic_action_radio_button_checked);
                        rbModeScroll.setChecked(false);
                        rbModeScroll.setButtonDrawable(R.drawable.ic_action_radio_button_unchecked);
                        break;
                }
            }
        });

        rgTheme.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId) {
                    case R.id.RBThemeDark:
                        rbThemeDark.setChecked(true);
                        rbThemeDark.setButtonDrawable(R.drawable.ic_action_radio_button_checked);
                        rbThemeLight.setChecked(false);
                        rbThemeLight.setButtonDrawable(R.drawable.ic_action_radio_button_unchecked);
                        break;
                    case R.id.RBThemeLight:
                        rbThemeLight.setChecked(true);
                        rbThemeLight.setButtonDrawable(R.drawable.ic_action_radio_button_checked);
                        rbThemeDark.setChecked(false);
                        rbThemeDark.setButtonDrawable(R.drawable.ic_action_radio_button_unchecked);
                        break;
                }
            }
        });


        FloatingActionButton fab=view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ConnectionJsonParser.isConnectionInternet(getContext())) {
                    ArrayList<Server> servers = SingletonGestorPocketManga.getInstance(getContext()).getServers();
                    String server = spServer.getSelectedItem().toString();
                    for (Server sr: servers) {
                        if(server.equals(sr.getName()))
                            user.setServer_Id(sr.getIdServer());
                    }

                    user.setTheme(rbThemeDark.isChecked());
                    user.setChapterShow(rbModeScroll.isChecked());

                    SingletonGestorPocketManga.getInstance(getContext()).updateUserAPI(getContext(),user);
                }
                else{
                    Toast.makeText(getContext(), R.string.no_connection_to_the_internet,Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    private void SetInfo(){
        SingletonGestorPocketManga.getInstance(getContext()).getAllServersAPI(getContext());
        if(user!=null) {
            if(user.isChapterShow()) {
                rbModeScroll.setChecked(true);
                rbModeScroll.setButtonDrawable(R.drawable.ic_action_radio_button_checked);
            }else{
                rbModePaginated.setChecked(true);
                rbModePaginated.setButtonDrawable(R.drawable.ic_action_radio_button_checked);
            }
            if(user.isTheme()) {
                rbThemeDark.setChecked(true);
                rbThemeDark.setButtonDrawable(R.drawable.ic_action_radio_button_checked);
            }else{
                rbThemeLight.setChecked(true);
                rbThemeLight.setButtonDrawable(R.drawable.ic_action_radio_button_checked);
            }
            tvUsername.setText(user.getUsername());
            tvUserEmail.setText(user.getEmail());
            String favoriteText = "Have "+SingletonGestorPocketManga.getInstance(getContext()).getCountFavoriteMangasBD()+" in total";
            tvTotalFavorite.setText(favoriteText);
            String toReadText = "Have "+SingletonGestorPocketManga.getInstance(getContext()).getCountLibraryMangasBD(1)+" in total";
            tvTotalToRead.setText(toReadText);
            String readingText = "Have "+SingletonGestorPocketManga.getInstance(getContext()).getCountLibraryMangasBD(2)+" in total";
            tvTotalReading.setText(readingText);
            String completedText = "Have "+SingletonGestorPocketManga.getInstance(getContext()).getCountLibraryMangasBD(3)+" in total";
            tvTotalCompleted.setText(completedText);
            String mangaDownloadedText = "Have "+SingletonGestorPocketManga.getInstance(getContext()).getCountDownloadMangasBD()+" in total";
            tvTotalMangaDownloaded.setText(mangaDownloadedText);
            String chapterDownloadedText = "Have "+SingletonGestorPocketManga.getInstance(getContext()).getCountDownloadChaptersBD()+" in total";
            tvTotalChapterDownloaded.setText(chapterDownloadedText);

            String url = SingletonGestorPocketManga.getInstance(getContext()).getBaseUrl()+user.getUrlPhoto();
            Glide.with(getContext())
                    .load(url)
                    .placeholder(R.drawable.manga_alternative)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(ivUserImage);
        }
    }

    @Override
    public void onRefreshServersList(ArrayList<Server> ServersList) {
        ArrayList<String> servers = new ArrayList<>();
        for (Server server: ServersList) {
            servers.add(server.getName());
        }

        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, servers);
        //adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), R.layout.item_spinner_server, servers);
        adapter.setDropDownViewResource(R.layout.item_spinner_server2);

        spServer.setAdapter(adapter);

        if(user!=null) {
            Server server = SingletonGestorPocketManga.getInstance(getContext()).getServer(user.getServer_Id());
            spServer.setSelection(adapter.getPosition(server.getName()));
        }
    }
}