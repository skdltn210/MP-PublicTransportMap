package com.example.mp_publictransportmap;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private EditText idInput;
    private EditText passwordInput;
    DBHelper dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        idInput = findViewById(R.id.editTextId);
        passwordInput = findViewById(R.id.editTextPassword);
        dbHelper = new DBHelper(this);

        Button loginButton = findViewById(R.id.buttonLogin);
        Button registerButton = findViewById(R.id.buttonRegister);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = idInput.getText().toString();
                String password = passwordInput.getText().toString();

                if (!id.isEmpty() && !password.isEmpty()) {
                    if (dbHelper.checkLogin(id, password)) {
                        String username = dbHelper.getUsername(id, password);
                        Intent intent = new Intent(MainActivity.this, MenuActivity.class);
                        intent.putExtra("username", username);
                        startActivity(intent);
                    }else {
                        Toast.makeText(MainActivity.this, "아이디 또는 비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "아이디와 비밀번호를 입력하세요.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
}
