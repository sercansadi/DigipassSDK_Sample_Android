package com.vasco.labs.digipasssdk_sample_android_2016;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.FrameLayout;

import com.keylemon.oasiscs.face.OasisCSFaceClient;
import com.keylemon.oasiscs.face.camera.CameraPreview;


public class SecurityActivity extends Activity implements OasisCSFaceClient.OasisCSFaceClientListener,
        OasisCSFaceClient.EyeBlinkChallengeListener, OasisCSFaceClient.HeadMovementChallengeListener
{

    OasisCSFaceClient oasisCSFaceClient;
    CameraPreview cameraPreviewView;
    private OasisCSFaceClient.SpoofingDetectionMode antiSpoofingMode = OasisCSFaceClient.SpoofingDetectionMode.NONE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_security);

    }

    @Override
    public void onCameraStarted() {
        oasisCSFaceClient.startAcquisitionForVerification(antiSpoofingMode);
    }

    @Override
    public void onCameraStopped() {

    }

    @Override
    public void onCameraError(Exception e) {

    }

    @Override
    public void onQualityControlStarted() {

    }

    @Override
    public void onQualityControlFeedback(OasisCSFaceClient.QualityControlIssues qualityControlIssues) {

    }

    @Override
    public void onQualityControlDone() {

    }

    @Override
    public void onSuccessfulAcquisitionForEnrollment(byte[] bytes) {

    }

    @Override
    public void onSuccessfulAcquisitionForVerification(byte[] bytes, boolean b) {

    }

    @Override
    public void onFailedAcquisitionForEnrollment(OasisCSFaceClient.AcquisitionFailureReason acquisitionFailureReason) {

    }

    @Override
    public void onFailedAcquisitionForVerification(OasisCSFaceClient.AcquisitionFailureReason acquisitionFailureReason) {

    }

    @Override
    public void onRecordingProgress(float v) {

    }

    @Override
    public void onEyeBlinkChallengeStarted() {

    }

    @Override
    public void onEyeBlinkChallengeStopped() {

    }

    @Override
    public void onHeadMovementChallengeStarted(OasisCSFaceClient.HeadMovementChallengeType headMovementChallengeType) {

    }

    @Override
    public void onHeadMovementChallengeStopped() {

    }
}
