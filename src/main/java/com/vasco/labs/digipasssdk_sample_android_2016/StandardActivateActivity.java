package com.vasco.labs.digipasssdk_sample_android_2016;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.vasco.labs.digipasssdk_sample_android_2016.controller.Activate;
import com.vasco.labs.digipasssdk_sample_android_2016.controller.QRCodeScanner;
import com.vasco.labs.digipasssdk_sample_android_2016.controller.asyncTask.DsappActivation;

public class StandardActivateActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_standard_activate);
        final TextView lblProgress = (TextView)findViewById(R.id.lblProgress_2);

        findViewById(R.id.btnActivateDIGIPASS_s).setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                TextView lblProgress =(TextView)findViewById(R.id.lblProgress_2);
                if(Activate.activateStandard(StandardActivateActivity.this)) {
                    lblProgress.setText("DIGIPASS activated");
                    //Toast.makeText(this,"DIGIPASS activated",Toast.LENGTH_SHORT).show();
                }else{
                    //Toast.makeText(this,Activate.vascoError.toString(),Toast.LENGTH_LONG).show();
                    lblProgress.setText("Fail!! : " + Activate.vascoError.toString());
                }
            }
        });

    }
}


