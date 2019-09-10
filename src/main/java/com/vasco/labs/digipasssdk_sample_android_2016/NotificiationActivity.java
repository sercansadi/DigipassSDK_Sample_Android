package com.vasco.labs.digipasssdk_sample_android_2016;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.vasco.labs.digipasssdk_sample_android_2016.controller.Notification;
import com.vasco.labs.digipasssdk_sample_android_2016.controller.asyncTask.NotificationService;

public class NotificiationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        TextView txtRegistrationIdentifier = (TextView) findViewById(R.id.txtRegistrationIdentifier);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notificiation);

        findViewById(R.id.btnRegisterNotificationId).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView txtRegisterNotifId = (TextView)findViewById(R.id.txtRegisterNotifId);

                NotificationService n = new NotificationService();
                n.context = NotificiationActivity.this;
                n.execute(txtRegisterNotifId.getText().toString());
            }
        });
    }
}
