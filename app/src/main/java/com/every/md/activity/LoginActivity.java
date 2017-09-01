package com.every.md.activity;

import android.content.Context;
import android.content.Intent;
import android.icu.util.Output;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.every.md.R;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketAddress;

/**
 * Created by Yunga on 2017/5/5.
 */

public class LoginActivity extends AppCompatActivity {
    private EditText account;
    private EditText password;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        account = findViewById(R.id.account);
        password = findViewById(R.id.password);

        Button login_btn = findViewById(R.id.login_btn);
        login_btn.setOnClickListener(loginBtnListener);

        findViewById(R.id.login_layout).setOnClickListener(v -> exitEdit());

    }

    private void exitEdit() {
        account.clearFocus();
        password.clearFocus();
        View view = getWindow().peekDecorView();
        if (view == null) {
            return;
        }
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private View.OnClickListener loginBtnListener = v -> startActivity(new Intent(LoginActivity.this, MainActivity.class));
}
