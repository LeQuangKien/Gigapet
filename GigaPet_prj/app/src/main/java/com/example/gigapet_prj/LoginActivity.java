package com.example.gigapet_prj;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {
    EditText uEmail, uPassword;
    Button btnLogin;
    ProgressBar progressBar;
    private TextView register;
    private DatabaseReference reff;
    private TextView forgotpass;

    public static final Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile(
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                    "\\@" +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(" +
                    "\\." +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                    ")+"
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        uEmail = findViewById(R.id.user_email);
        uPassword = findViewById(R.id.pass);
        btnLogin = findViewById(R.id.loginBtn);
        register = findViewById(R.id.textRegister);
        Users users = new Users();

        reff = FirebaseDatabase.getInstance().getReference();
//        uEmail.setText("tuan@gmail.com");
//        uPassword.setText("1234");
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String email = uEmail.getText().toString().trim();
                String password = uPassword.getText().toString();

                if (TextUtils.isEmpty(email)) {
                    uEmail.setError("Required");
                    uEmail.requestFocus();
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    uEmail.setError("Invalid email form !");
                }
                if (TextUtils.isEmpty(password)) {
                    uPassword.setError("Required");
                    uPassword.requestFocus();
                }

                reff.child("User").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        ArrayList<String> names= new ArrayList<>();
                        for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                            Users user = userSnapshot.getValue(Users.class);
                            Boolean isEqualEmail = user.getEmail().trim().toLowerCase().equals(email.toLowerCase());
                            Boolean isEqualPassword = user.getPassword().equals(password);
                            if (isEqualEmail && isEqualPassword){
                                Toast.makeText(LoginActivity.this, "Login Succesfull", Toast.LENGTH_LONG).show();
                                startActivity(new Intent(LoginActivity.this,MainActivity.class));
                                finish();
                            } else {
                                Toast.makeText(LoginActivity.this, "Invalid Username or Password", Toast.LENGTH_LONG).show();
                            }

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });



            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

        forgotpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, SendCodeActivity.class));
            }
        });
    }
}