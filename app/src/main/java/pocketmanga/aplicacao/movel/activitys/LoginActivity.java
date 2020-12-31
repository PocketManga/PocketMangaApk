package pocketmanga.aplicacao.movel.activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import pocketmanga.aplicacao.movel.R;
import pocketmanga.aplicacao.movel.listeners.LoginListener;
import pocketmanga.aplicacao.movel.modelo.SingletonGestorPocketManga;
import pocketmanga.aplicacao.movel.utils.ConnectionJsonParser;

public class LoginActivity extends AppCompatActivity implements LoginListener {
    private EditText etUsername, etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsername = findViewById(R.id.ETUsername);
        etPassword = findViewById(R.id.ETPassword);

        etUsername.setText("Nildgar");
        etPassword.setText("admin");

        SingletonGestorPocketManga.getInstance(getApplicationContext()).setLoginListener(this);
    }

    public void onClickLogin(View view) {
        if (ConnectionJsonParser.isConnectionInternet(getApplicationContext())) {

            String username = etUsername.getText().toString();
            String password = etPassword.getText().toString();

            if (!isUsernameValid(username)) {
                etUsername.setError("Invalid Username");
                return;
            }

            if (!isPasswordValid(password)) {
                etPassword.setError("Invalid Password");
                return;
            }

            SingletonGestorPocketManga.getInstance(getApplicationContext()).loginAPI(username, password, getApplicationContext());
        }
    }

    private boolean isUsernameValid(String username) {
        if(username==null)
            return false;

        return true;
    }

    private boolean isPasswordValid(String password) {
        if(password==null)
            return false;

        return true;
    }

    public void onValidateLogin(String token, String username, int idUser) {

        if(token!=null){
            saveInfoSharedPref(token,username,idUser);

            SingletonGestorPocketManga.getInstance(getApplicationContext()).getUserAPI(getApplicationContext(),idUser);

            Intent intent= new Intent(this, MenuMainActivity.class);
            intent.putExtra(MenuMainActivity.USERNAME, username);
            startActivity(intent);

            finish();
        }
        else {
            Toast.makeText(getApplicationContext(),"The username or password is incorrect",Toast.LENGTH_SHORT).show();
        }
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