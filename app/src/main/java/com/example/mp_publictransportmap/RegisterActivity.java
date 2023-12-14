package com.example.mp_publictransportmap;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    EditText username, id, pw;
    Button register;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        ImageView backImageView = findViewById(R.id.backImageView);

        backImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        username = findViewById(R.id.username);
        id = findViewById(R.id.ID);
        pw = findViewById(R.id.PassWord);

        register = findViewById(R.id.Register);
        dbHelper = new DBHelper(this);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = username.getText().toString();
                String userId = id.getText().toString();
                String userPassword = pw.getText().toString();

                if (!userName.isEmpty() && !userId.isEmpty() && !userPassword.isEmpty()) {
                    if (dbHelper.checkIfIdExists(userId)) {
                        Toast.makeText(RegisterActivity.this, "중복된 ID입니다.", Toast.LENGTH_SHORT).show();
                    } else {
                        dbHelper.addUser(userName, userId, userPassword);
                        Toast.makeText(RegisterActivity.this, "사용자 등록 완료", Toast.LENGTH_SHORT).show();
                        onBackPressed();
                    }
                } else {
                    Toast.makeText(RegisterActivity.this, "모든 필드를 입력하세요.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
