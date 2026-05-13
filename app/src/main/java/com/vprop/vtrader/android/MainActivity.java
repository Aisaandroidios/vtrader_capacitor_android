package com.vprop.vtrader.android;

import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.IntentSenderRequest;
import androidx.activity.result.contract.ActivityResultContracts;

import com.getcapacitor.BridgeActivity;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.appupdate.AppUpdateOptions;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.UpdateAvailability;

public class MainActivity extends BridgeActivity {
    private AppUpdateManager appUpdateManager;

    private final ActivityResultLauncher<IntentSenderRequest> updateFlowLauncher =
            registerForActivityResult(
                    new ActivityResultContracts.StartIntentSenderForResult(),
                    result -> {
                        // Google Play owns the native update UI and result handling.
                    }
            );

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        appUpdateManager = AppUpdateManagerFactory.create(this);
        checkForImmediateUpdate();
    }

    @Override
    public void onResume() {
        super.onResume();

        if (appUpdateManager == null) {
            return;
        }

        appUpdateManager
                .getAppUpdateInfo()
                .addOnSuccessListener(appUpdateInfo -> {
                    if (appUpdateInfo.updateAvailability()
                            == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS) {
                        startImmediateUpdate(appUpdateInfo);
                    }
                });
    }

    private void checkForImmediateUpdate() {
        appUpdateManager
                .getAppUpdateInfo()
                .addOnSuccessListener(this::startImmediateUpdateIfAvailable);
    }

    private void startImmediateUpdateIfAvailable(AppUpdateInfo appUpdateInfo) {
        boolean updateAvailable =
                appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE;
        boolean immediateAllowed = appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE);

        if (!updateAvailable || !immediateAllowed) {
            return;
        }

        startImmediateUpdate(appUpdateInfo);
    }

    private void startImmediateUpdate(AppUpdateInfo appUpdateInfo) {
        appUpdateManager.startUpdateFlowForResult(
                appUpdateInfo,
                updateFlowLauncher,
                AppUpdateOptions
                        .newBuilder(AppUpdateType.IMMEDIATE)
                        .build()
        );
    }
}
