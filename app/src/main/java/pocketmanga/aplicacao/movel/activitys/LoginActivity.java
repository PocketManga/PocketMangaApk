package pocketmanga.aplicacao.movel.activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import pocketmanga.aplicacao.movel.R;
import pocketmanga.aplicacao.movel.listeners.LoginListener;
import pocketmanga.aplicacao.movel.modelo.SingletonGestorPocketManga;
import pocketmanga.aplicacao.movel.modelo.User;
import pocketmanga.aplicacao.movel.utils.ConnectionJsonParser;

public class LoginActivity extends AppCompatActivity implements LoginListener {
    private EditText etUsername, etPassword, etDeviceIP1, etDeviceIP2, etDeviceIP3, etDeviceIP4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsername = findViewById(R.id.ETUsername);
        etPassword = findViewById(R.id.ETPassword);
        etDeviceIP1 = findViewById(R.id.ETDeviceIP1);
        etDeviceIP2 = findViewById(R.id.ETDeviceIP2);
        etDeviceIP3 = findViewById(R.id.ETDeviceIP3);
        etDeviceIP4 = findViewById(R.id.ETDeviceIP4);

        SingletonGestorPocketManga.getInstance(getApplicationContext()).setLoginListener(this);

        checkSharedPreferences();
    }

    public void onClickLogin(View view) {
        if (ConnectionJsonParser.isConnectionInternet(getApplicationContext())) {

            String username = etUsername.getText().toString();
            String password = etPassword.getText().toString();
            String ip1 = etDeviceIP1.getText().toString();
            String ip2 = etDeviceIP2.getText().toString();
            String ip3 = etDeviceIP3.getText().toString();
            String ip4 = etDeviceIP4.getText().toString();

            if (!isIPValid(ip1)) {
                etDeviceIP1.setError(getString(R.string.invalid_ip_number));
                return;
            }
            if (!isIPValid(ip2)) {
                etDeviceIP2.setError(getString(R.string.invalid_ip_number));
                return;
            }
            if (!isIPValid(ip3)) {
                etDeviceIP3.setError(getString(R.string.invalid_ip_number));
                return;
            }
            if (!isIPValid(ip4)) {
                etDeviceIP4.setError(getString(R.string.invalid_ip_number));
                return;
            }

            if (!isUsernameValid(username)) {
                etUsername.setError(getString(R.string.invalid_username));
                return;
            }

            if (!isPasswordValid(password)) {
                etPassword.setError(getString(R.string.invalid_password));
                return;
            }

            SingletonGestorPocketManga.getInstance(getApplicationContext()).setDeviceIP(ip1+"."+ip2+"."+ip3+"."+ip4);

            SingletonGestorPocketManga.getInstance(getApplicationContext()).loginAPI(username, password, getApplicationContext());
        }else{
            Toast.makeText(getApplicationContext(), "No connection to the internet", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isIPValid(String ipNumber) {
        if(ipNumber.isEmpty())
            return false;
        int Ip = Integer.parseInt(ipNumber);
        return Ip >= 0 && Ip <= 255;
    }

    private boolean isUsernameValid(String username) {
        return username != null && !username.isEmpty();
    }

    private boolean isPasswordValid(String password) {
        return password != null && !password.isEmpty();
    }

    private void checkSharedPreferences() {
        SharedPreferences sharedPrefUser = getSharedPreferences(MenuMainActivity.PREF_INFO_USER, Context.MODE_PRIVATE);
        String username = sharedPrefUser.getString(MenuMainActivity.USERNAME, null);
        String token = sharedPrefUser.getString(MenuMainActivity.TOKEN, null);
        String st_idUser = sharedPrefUser.getString(MenuMainActivity.ID_USER, null);
        String urlPhoto = sharedPrefUser.getString(MenuMainActivity.URL_PHOTO, null);
        String st_mangaShow = sharedPrefUser.getString(MenuMainActivity.MANGA_SHOW, null);
        String st_chapterShow = sharedPrefUser.getString(MenuMainActivity.CHAPTER_SHOW, null);
        String st_theme = sharedPrefUser.getString(MenuMainActivity.THEME, null);
        String email = sharedPrefUser.getString(MenuMainActivity.EMAIL, null);
        String device_ip = sharedPrefUser.getString(MenuMainActivity.DEVICE_IP, null);
        String st_server_Id = sharedPrefUser.getString(MenuMainActivity.SERVER_ID, null);

        if(device_ip != null) {
            SingletonGestorPocketManga.getInstance(getApplicationContext()).setDeviceIP(device_ip);
            setDeviceIP(device_ip);
        }
        else {
            SingletonGestorPocketManga.getInstance(getApplicationContext()).setDeviceIP("127.0.0.1");
        }

        if(username!=null)
            etUsername.setText(username);

        if(token != null && username != null && st_idUser != null &&  urlPhoto != null &&  st_mangaShow != null &&  st_chapterShow != null &&  st_theme != null &&  email != null &&  st_server_Id != null) {
            int idUser = Integer.parseInt(st_idUser);
            int server_Id = Integer.parseInt(st_server_Id);
            boolean mangaShow = Boolean.parseBoolean(st_server_Id);
            boolean chapterShow = Boolean.parseBoolean(st_server_Id);
            boolean theme = Boolean.parseBoolean(st_server_Id);

            User newUser = new User(idUser, server_Id, username, email, urlPhoto, chapterShow, mangaShow, theme);
            SingletonGestorPocketManga.getInstance(getApplicationContext()).autoLogin(newUser);
        }
    }

    public void onValidateLogin(String token, String username, int idUser) {

        if(token!=null){
            saveInfoSharedPref(token,username,idUser);

            SingletonGestorPocketManga.getInstance(getApplicationContext()).getUserAPI(getApplicationContext(),idUser);

            startMainMenu(username, idUser);
        }
        else {
            Toast.makeText(getApplicationContext(), R.string.the_username_or_password_is_incorrect,Toast.LENGTH_SHORT).show();
        }
    }

    public void setDeviceIP(String deviceIP) {
        String[] Ip = deviceIP.split("\\.");
        etDeviceIP1.setText(Ip[0]);
        etDeviceIP2.setText(Ip[1]);
        etDeviceIP3.setText(Ip[2]);
        etDeviceIP4.setText(Ip[3]);
    }

    @Override
    public void onAutoLogin(User user) {
        if (ConnectionJsonParser.isConnectionInternet(getApplicationContext())) {
            SingletonGestorPocketManga.getInstance(getApplicationContext()).getUserAPI(getApplicationContext(),user.getIdUser());
            user = SingletonGestorPocketManga.getInstance(getApplicationContext()).getUser();
        }
        startMainMenu(user.getUsername(), user.getIdUser());
    }

    public void startMainMenu(String username, int idUser){
        Intent intent = new Intent(this, MenuMainActivity.class);
        intent.putExtra(MenuMainActivity.USERNAME, username);
        intent.putExtra(MenuMainActivity.ID_USER, idUser);
        startActivity(intent);
        etUsername.setText(username);
    }

    private void saveInfoSharedPref(String token, String username, int idUser) {
        SharedPreferences sharedPrefUser = getSharedPreferences(MenuMainActivity.PREF_INFO_USER, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPrefUser.edit();

        editor.putString(MenuMainActivity.USERNAME,username);
        editor.putString(MenuMainActivity.TOKEN,token);
        editor.putString(MenuMainActivity.ID_USER,idUser+"");

        editor.apply();
    }
}