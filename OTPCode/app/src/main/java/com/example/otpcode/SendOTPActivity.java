package com.example.otpcode;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SendOTPActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_o_t_p);

        final EditText inputPhone = findViewById(R.id.inputPhone);
        Button btnGetOtp = findViewById(R.id.btnGetOtp);

        btnGetOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(inputPhone.getText().toString().trim().isEmpty()){
                    Toast.makeText(SendOTPActivity.this, "Enter Your Phone Number",Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(getApplicationContext(), VerifyOTPActivity.class);
                intent.putExtra("mobile", inputPhone.getText().toString());
                startActivity(intent);
            }
        });
    }
}