package com.vasco.labs.digipasssdk_sample_android_2016;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.vasco.labs.digipasssdk_sample_android_2016.controller.ChallengeResponse;
import com.vasco.labs.digipasssdk_sample_android_2016.controller.OneTimePassword;

public class ChallengeResponseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge_response);

        findViewById(R.id.btnGenrateResponse).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView txtPassword = (TextView)findViewById(R.id.txtPasswordCr);
                TextView txtChallenge = (TextView)findViewById(R.id.txtChallengeCr);
                TextView txtResponse = (TextView)findViewById(R.id.txtResponseCr);
                TextView txtHostcode = (TextView)findViewById(R.id.txtHostCodeCr);

                if(ChallengeResponse.generate(txtChallenge.getText().toString(),txtPassword.getText().toString(),ChallengeResponseActivity.this)){
                    txtResponse.setText(ChallengeResponse.response);
                    txtHostcode.setText(ChallengeResponse.hostcode);
                }else{
                    Toast.makeText(ChallengeResponseActivity.this, ChallengeResponse.vascoError.toString(), Toast.LENGTH_LONG).show();
                }

            }
        });
    }
}
