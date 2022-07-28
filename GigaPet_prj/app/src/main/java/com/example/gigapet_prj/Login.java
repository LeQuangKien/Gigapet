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

import java.util.regex.Pattern;

public class Login extends AppCompatActivity {
    EditText uEmail, uPassword;
    Button btnLogin;
    ProgressBar progressBar;
    private TextView register;
    private DatabaseReference reff;



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

        reff = FirebaseDatabase.getInstance().getReference().child("User");

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String email = uEmail.getText().toString();
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

                reff.child(email).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                            Users users = snapshot.getValue(Users.class);
                            if (snapshot.hasChild(email) && snapshot.child(email).child("password").getValue(String.class).equals(password)){
                                Toast.makeText(Login.this, "Login Succesfull", Toast.LENGTH_LONG).show();
                                startActivity(new Intent(Login.this,MainActivity.class));
                            } else {
                                Toast.makeText(Login.this, "Invalid Username or Password", Toast.LENGTH_LONG).show();
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
                startActivity(new Intent(Login.this, Register.class));
            }
        });
    }
}