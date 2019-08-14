package com.optionringringtone.newringtonefree.mysetting.controlApp;

import android.content.Context;


import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

public class ControlFirebase {

    public static void initSetup(Context mContext, CallbackFirebaseControl mCallbackFirebaseControl) {
        FirebaseApp.initializeApp(mContext);
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
//                            Log.w(TAG, "getInstanceId failed", task.getException());
                            return;
                        }

                        // Get new Instance ID token
                        String mFbclid = task.getResult().getToken();
                        if (mCallbackFirebaseControl != null)
                            mCallbackFirebaseControl.callBackmFbclid(mFbclid);
                    }
                });
    }


    public interface CallbackFirebaseControl {
        void callBackmFbclid(String mFbclid);
    }
}
