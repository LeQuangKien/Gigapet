package com.example.gigapet_prj;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

    private TextView alreadyHaveAccount;
    private EditText  inputEmail, inputPass, inputRepass;
    private Button btnRegister;
    private Users user;
    DatabaseReference reff;
    long userId = 0;
    private Boolean isUpdating = false;
    private FirebaseAuth mAuth;
    private  FirebaseUser mUser;

    public static final Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile(
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                    "\\@" +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(" +
                    "\\." +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                    ")+"
    );
    private ProgressBar progressBar;

    @Override
    public void onStart(){
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        alreadyHaveAccount = findViewById(R.id.textLogin);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        inputEmail = findViewById(R.id.user_email);
        inputPass = findViewById(R.id.pass);
        inputRepass = findViewById(R.id.re_password);
        btnRegister = findViewById(R.id.loginBtn);
        progressBar = findViewById(R.id.progressBar);



        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isUpdating = true;
                String uEmail = inputEmail.getText().toString().trim();
                String uPass = inputPass.getText().toString();
                String confirm = inputRepass.getText().toString();



                //    public User(String userName, String email,
                //    int phoneNo, String password, Boolean adminStatus) {
                if (validateInput( uEmail, uPass, confirm) == false) {
                    Toast.makeText(RegisterActivity.this, "Wrong Validation", Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);
                }else {
                    register(uEmail, uPass, confirm);
                    mAuth.signInWithEmailAndPassword(uEmail, uPass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {

                                FirebaseUser user = mAuth.getCurrentUser();
                                updateUI(user);
                            } else {
                                Toast.makeText(RegisterActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                                updateUI(null);
                            }

                        }
                    });
                }
            }
        });



        alreadyHaveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });
    }



    private boolean validateInput( String email, String password, String confirmpass) {//
        int t = 0;

        if (email.isEmpty()) {
            inputEmail.setError("Required");
            inputEmail.requestFocus();
            t++;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            inputEmail.setError("Invalid email form !");
            t++;
        }
        if (password.isEmpty()) {
            inputPass.setError("Required");
            inputPass.requestFocus();
            t++;
        }
        if (confirmpass.isEmpty()) {
            inputRepass.setError("Required");
            inputRepass.requestFocus();
            t++;
        }
        if (!password.matches(confirmpass)){
            inputRepass.setError("The password confirmation does not match.");
            t++;
        }
        if (t>0){
            isUpdating = false;
            return false;
        }else {
            return true;
        }
    }
    public void register(String Email,String Pass,String confirm){
        reff = FirebaseDatabase.getInstance().getReference().child("Users");
        user = new Users();
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(!isUpdating) {
                    return;
                }
                userId = snapshot.exists() ? snapshot.getChildrenCount() + 1 : 1;
                user.setUserId(userId);
                user.setEmail(Email);
                user.setPassword(Pass);

                reff.child(String.valueOf(userId)).setValue(user);
                isUpdating = false;
                Toast.makeText(RegisterActivity.this, "Data Inserted Succesfully", Toast.LENGTH_LONG).show();
                finish();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                isUpdating = false;
            }
        });
    }
    public void updateUI(FirebaseUser account){

        if(account != null){
            Toast.makeText(this,"You Signed In successfully",Toast.LENGTH_LONG).show();
            startActivity(new Intent(RegisterActivity.this,MainActivity.class));

        }else {
            Toast.makeText(this,"You Didnt signed in",Toast.LENGTH_LONG).show();
        }

    }
}


