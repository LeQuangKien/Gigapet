package com.example.gigapet_prj;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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

public class Register extends AppCompatActivity {

    private TextView alreadyHaveAccount;
    private EditText inputName, inputEmail, inputPhone, inputPass, inputRepass;
    private Button btnRegister;
    private Users users;
    DatabaseReference reff;
    long maxid = 0;



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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        alreadyHaveAccount = findViewById(R.id.textLogin);

        inputName = findViewById(R.id.user_name);
        inputEmail = findViewById(R.id.user_email);
        inputPhone = findViewById(R.id.phone_num);
        inputPass = findViewById(R.id.pass);
        inputRepass = findViewById(R.id.re_password);
        btnRegister = findViewById(R.id.loginBtn);





        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uName = inputName.getText().toString().trim();
                String uEmail = inputEmail.getText().toString().trim();
                String uPhoneno = inputPhone.getText().toString().trim();
                String uPass = inputPass.getText().toString().trim();
                String confirm = inputRepass.getText().toString().trim();
                register(uName,uEmail,uPhoneno,uPass,confirm);


                //    public User(String userName, String email,
                //    int phoneNo, String password, Boolean adminStatus) {
                if(validateInput(uName,uEmail,uPhoneno,uPass,confirm) == false) {
                    Toast.makeText(Register.this,"Wrong Validation",Toast.LENGTH_LONG).show();
                }else{


//                  reff.child(users.getEmail()).setValue(users);

                    Users user = new Users();
                    reff.push().setValue(user);
                    Toast.makeText(Register.this, "Data Inserted Succesfully", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getApplicationContext(),Login.class));
                }
            }
        });

        alreadyHaveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Register.this, Login.class));
            }
        });
    }



    private boolean validateInput(String name, String email, String phone, String password, String confirmpass) {//
        int t = 0;
        if (name.isEmpty()) {
            inputName.setError("Required");
            inputName.requestFocus();
            t++;
        }
        if (email.isEmpty()) {
            inputEmail.setError("Required");
            inputEmail.requestFocus();
            t++;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            inputEmail.setError("Invalid email form !");
            t++;
        }
        if (phone.isEmpty()) {
            inputPhone.setError("Required");
            inputPhone.requestFocus();
            t++;
        }
        if(phone.length()>10) {
            inputPhone.setError("Please enter correct phone number !");
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
            return false;
        }else {
            return true;
        }
    }


    public void register(String Name,String Email,String PhoneNo,String Pass,String confirm){
        reff = FirebaseDatabase.getInstance().getReference().child("User");
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    maxid = (snapshot.getChildrenCount());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}

