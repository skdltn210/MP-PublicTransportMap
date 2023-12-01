package com.example.mp_publictransportmap;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private EditText usernameInput;
    private EditText passwordInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usernameInput = findViewById(R.id.editTextId);
        passwordInput = findViewById(R.id.editTextPassword);

        Button loginButton = findViewById(R.id.buttonLogin);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameInput.getText().toString();
                String password = passwordInput.getText().toString();

                if (!username.isEmpty() && !password.isEmpty()) {
                    Intent intent = new Intent(MainActivity.this, MenuActivity.class);

                    intent.putExtra("username", username);
                    intent.putExtra("password", password);

                    startActivity(intent);

                } else {
                    Toast.makeText(MainActivity.this, "아이디와 비밀번호를 입력하세요.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
