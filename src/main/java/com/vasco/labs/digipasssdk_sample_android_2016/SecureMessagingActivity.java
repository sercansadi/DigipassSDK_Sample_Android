package com.vasco.labs.digipasssdk_sample_android_2016;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.vasco.labs.digipasssdk_sample_android_2016.controller.OneTimePassword;
import com.vasco.labs.digipasssdk_sample_android_2016.controller.QRCodeScanner;
import com.vasco.labs.digipasssdk_sample_android_2016.controller.SecureChannelMsg;

public class SecureMessagingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secure_messaging);

        findViewById(R.id.btnScanImage).setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                startActivityForResult(QRCodeScanner.StartScanner(SecureMessagingActivity.this), 0);

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        TextView txtPassword = (TextView)findViewById(R.id.txtPasswordSc);
        TextView txtOtp = (TextView)findViewById(R.id.txtSignatureSc);
        TextView txtMessage = (TextView)findViewById(R.id.txtMessageSc);
        TextView txtHostCode = (TextView)findViewById(R.id.txtHostCodeSc);

        if(QRCodeScanner.parseResult(resultCode, data)){
            String s = QRCodeScanner.message;
            if(SecureChannelMsg.signMessage(s,txtPassword.getText().toString(),this)){
                if(SecureChannelMsg.getMessage(s,this)){
                    txtMessage.setText(SecureChannelMsg.messageContent);
                    txtOtp.setText(SecureChannelMsg.signature);
                    txtHostCode.setText(SecureChannelMsg.hostcode);
                }else{
                    Toast.makeText(this, SecureChannelMsg.vascoError.toString(), Toast.LENGTH_LONG).show();
                }

            }else{
                Toast.makeText(this, SecureChannelMsg.vascoError.toString(), Toast.LENGTH_LONG).show();
            }

        }else{
            Toast.makeText(this, QRCodeScanner.vascoError.toString(), Toast.LENGTH_LONG).show();
        }

    }
}
