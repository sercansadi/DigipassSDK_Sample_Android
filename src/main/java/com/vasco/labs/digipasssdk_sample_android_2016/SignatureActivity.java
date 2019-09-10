package com.vasco.labs.digipasssdk_sample_android_2016;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.vasco.labs.digipasssdk_sample_android_2016.controller.OneTimePassword;
import com.vasco.labs.digipasssdk_sample_android_2016.controller.Signature;

public class SignatureActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signature);

        findViewById(R.id.btnSign).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView txtPassword = (TextView) findViewById(R.id.txtPasswordSign);
                TextView txtSign = (TextView) findViewById(R.id.txtSign);
                TextView txtHostCodeSign = (TextView) findViewById(R.id.txtHostcodeSign);
                String[] dataFields = new String[8];
                TextView txtDTF1 = (TextView) findViewById(R.id.txtDTF1);
                TextView txtDTF2 = (TextView) findViewById(R.id.txtDTF2);
                TextView txtDTF3 = (TextView) findViewById(R.id.txtDTF3);
                TextView txtDTF4 = (TextView) findViewById(R.id.txtDTF4);
                TextView txtDTF5 = (TextView) findViewById(R.id.txtDTF5);
                TextView txtDTF6 = (TextView) findViewById(R.id.txtDTF6);
                TextView txtDTF7 = (TextView) findViewById(R.id.txtDTF7);
                TextView txtDTF8 = (TextView) findViewById(R.id.txtDTF8);
                dataFields[0] = txtDTF1.getText().toString();
                dataFields[1] = txtDTF2.getText().toString();
                dataFields[2] = txtDTF3.getText().toString();
                dataFields[3] = txtDTF4.getText().toString();
                dataFields[4] = txtDTF5.getText().toString();
                dataFields[5] = txtDTF6.getText().toString();
                dataFields[6] = txtDTF7.getText().toString();
                dataFields[7] = txtDTF8.getText().toString();


                if (Signature.generate(dataFields, txtPassword.getText().toString(), SignatureActivity.this)) {
                    txtSign.setText(Signature.signature);
                    txtHostCodeSign.setText(OneTimePassword.hostcode);
                } else {
                    Toast.makeText(SignatureActivity.this, Signature.vascoError.toString(), Toast.LENGTH_SHORT).show();
                }
            }

        });
    }
}
