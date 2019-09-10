package com.vasco.labs.digipasssdk_sample_android_2016;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.vasco.labs.digipasssdk_sample_android_2016.controller.Activate;
import com.vasco.labs.digipasssdk_sample_android_2016.controller.QRCodeScanner;
import com.vasco.labs.digipasssdk_sample_android_2016.controller.asyncTask.DsappActivation;

public class TwoStepNoCroNoSCActivateActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activate);
        final TextView txtRegistrationIdentifier = (TextView)findViewById(R.id.txtRegistrationIdentifier);
        final TextView txtActivationPassword = (TextView)findViewById(R.id.txtActivationPassword);
        final TextView txtPassword = (TextView)findViewById(R.id.txtPassword);
        final TextView lblProgress = (TextView)findViewById(R.id.lblProgress);
        Button btnActivateInstance = (Button)findViewById(R.id.btnActivateInstance);
        btnActivateInstance.setEnabled(false);

        findViewById(R.id.btnActivateLicense).setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                getResult();

            }
        });

        findViewById(R.id.btnActivateInstance).setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                String password = txtPassword.getText().toString();
                if(password.equals("")){
                    Toast.makeText(TwoStepNoCroNoSCActivateActivity.this,"Please provide a password",Toast.LENGTH_LONG).show();
                }else {

                    getResult();
                }

            }
        });


        findViewById(R.id.btnAutomaticActivation)

            .

            setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String password = txtPassword.getText().toString();
                    String registrationId = txtRegistrationIdentifier.getText().toString();
                    String activationPwd = txtActivationPassword.getText().toString();
                    if (password.equals("") || registrationId.equals("")||activationPwd.equals("")) {
                        Toast.makeText(TwoStepNoCroNoSCActivateActivity.this, "Please provide a password, registration id and activation password", Toast.LENGTH_LONG).show();
                    } else {
                        DsappActivation dsappActivation = new DsappActivation();
                        dsappActivation.context = TwoStepNoCroNoSCActivateActivity.this;
                        dsappActivation.activity = TwoStepNoCroNoSCActivateActivity.this;
                        dsappActivation.execute(txtRegistrationIdentifier.getText().toString(), txtActivationPassword.getText().toString(), txtPassword.getText().toString());
                    }
                }

            });


    }

    protected void getResult() {
        TextView txtDeviceCode = (TextView)findViewById(R.id.txtDeviceCode);
        TextView lblProgress =(TextView)findViewById(R.id.lblProgress);
        final TextView txtPassword = (TextView)findViewById(R.id.txtPassword);
        Button btnActivateInstance = (Button)findViewById(R.id.btnActivateInstance);
        Button btnActivateLicense = (Button)findViewById(R.id.btnActivateLicense);
        if(true){
            String s = "0000C3E40F42CC000000000000000000D636A44B54755CFC4812D0FF7FE8ED181455BE043499C0D6305BEE0E240CB999A6621405DA64";
            if(!btnActivateInstance.isEnabled()) {
                if (s != "") {
                    if(Activate.activateLicenceNoSC(s, this)) {
                        txtDeviceCode.setText(Activate.deviceCode);
                        lblProgress.setText("License activated");
                        btnActivateLicense.setEnabled(false);
                        btnActivateInstance.setEnabled(true);
                    }else{

                        Toast.makeText(this,Activate.vascoError.toString(),Toast.LENGTH_LONG).show();
                    }
                }
            }else{
                if (s != "") {
                    s = "0000C3E40F42CC000000000000000000D636A44B54755CFC4812D0FF7FE8ED181455BE043499C0D6305BEE0E240CB999A6621405DA64";
                    if(Activate.activateInstanceNoSC(s,txtPassword.getText().toString() , this)) {
                        lblProgress.setText("DIGIPASS activated");
                        btnActivateInstance.setEnabled(false);
                        btnActivateLicense.setEnabled(true);
                        Toast.makeText(this,"DIGIPASS activated",Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(this,Activate.vascoError.toString(),Toast.LENGTH_LONG).show();

                    }
                }
            }
        }else{
            Toast.makeText(this,QRCodeScanner.vascoError.toString(),Toast.LENGTH_LONG).show();
        }

    }

}


