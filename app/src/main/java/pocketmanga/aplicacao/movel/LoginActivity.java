package pocketmanga.aplicacao.movel;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {
    private EditText etUsername;
    private EditText etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        etUsername = findViewById(R.id.ETUsername);
        etPassword = findViewById(R.id.ETPassword);
    }

    public void onClickLogin(View view) {
        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();
        if(!isUsernameValid(username)){
            etUsername.setError("Invalid Username");
            return;
        }
        if(!isPasswordValid(password)){
            etPassword.setError("Invalid Password");
            return;
        }

        Intent intent = new Intent(this, MenuMainActivity.class);
        intent.putExtra(MenuMainActivity.USERNAME,username);
        startActivity(intent);
        finish();
    }

    private boolean isPasswordValid(String password) {
        return true;
    }

    private boolean isUsernameValid(String username) {
        return true;
    }
}