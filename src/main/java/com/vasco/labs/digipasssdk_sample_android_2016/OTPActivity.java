package com.vasco.labs.digipasssdk_sample_android_2016;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.vasco.labs.digipasssdk_sample_android_2016.controller.Activate;
import com.vasco.labs.digipasssdk_sample_android_2016.controller.OneTimePassword;

public class OTPActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);



        findViewById(R.id.btnGenerateOtp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView txtPassword = (TextView)findViewById(R.id.txtPasswordOtp);
                TextView txtOtp = (TextView)findViewById(R.id.txtOtp);
                TextView txtHostcode = (TextView)findViewById(R.id.txtHostCodeOtp);

                if(OneTimePassword.generate(txtPassword.getText().toString(), OTPActivity.this)) {
                    txtOtp.setText(OneTimePassword.otp);
                    txtHostcode.setText(OneTimePassword.hostcode);
                }else{
                    Toast.makeText(OTPActivity.this, OneTimePassword.vascoError.toString(), Toast.LENGTH_LONG).show();
                }
            }

        });

    }
}
