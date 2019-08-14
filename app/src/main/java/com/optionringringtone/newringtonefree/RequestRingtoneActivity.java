package com.optionringringtone.newringtonefree;


import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.gson.Gson;
import com.optionringringtone.newringtonefree.Untils.CommonUntil;
import com.optionringringtone.newringtonefree.Untils.RetrofitUtil;
import com.optionringringtone.newringtonefree.Untils.SlideAnimationUtil;
import com.optionringringtone.newringtonefree.Untils.ValidationUtil;
import com.optionringringtone.newringtonefree.object.RequestRingtoneResponse;

import freeringtones.newringtones.dowloadringtones.iphoneringtone2222.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RequestRingtoneActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView btnBack;
    private EditText edtSongTitle, edtSinger, edtYourEmail;
    private RadioButton rdoNewRingtone, rdoUpdateRingtone;
    private Button btnSubmitRequestRingtone;
    private ProgressBar prLoading;
    private String TAG = "RequestRingtoneActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_ringtone);
        getSupportActionBar().hide();

        initView();
    }

    private void initView() {
        btnBack = findViewById(R.id.btnBack);
        edtSongTitle = findViewById(R.id.edtSongTitle);
        edtSinger = findViewById(R.id.edtSinger);
        edtYourEmail = findViewById(R.id.edtYourEmail);
        rdoNewRingtone = findViewById(R.id.rdoNewRingtone);
        rdoUpdateRingtone = findViewById(R.id.rdoUpdateRingtone);
        btnSubmitRequestRingtone = findViewById(R.id.btnSubmitRequestRingtone);
        prLoading = findViewById(R.id.prLoading);

        btnBack.setOnClickListener(this);
        btnSubmitRequestRingtone.setOnClickListener(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        SlideAnimationUtil.overridePendingTransitionExit(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnBack:
                onBackPressed();
                break;
            case R.id.btnSubmitRequestRingtone:
                // call api submit new ringtone
                FirebaseInstanceId.getInstance().getInstanceId()
                        .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                            @Override
                            public void onComplete(@NonNull Task<InstanceIdResult> task) {
                                if (!task.isSuccessful()) {
                                    Log.w(TAG, "getInstanceId failed", task.getException());
                                    return;
                                }
                                // Get new Instance ID token
                                String token = task.getResult().getToken();
                                handleRequestNewRingtone(token);
                                Log.d(TAG, token);
                            }
                        });

                break;
        }
    }

    private void handleRequestNewRingtone(String token) {
        String titleValue = edtSongTitle.getText().toString().trim();
        String singerValue = edtSinger.getText().toString().trim();
        String emailValue = edtYourEmail.getText().toString().trim();
        if (ValidationUtil.isEmptyValue(titleValue)) {
            Toast.makeText(this, "Title not empty!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (ValidationUtil.isEmptyValue(singerValue)) {
            Toast.makeText(this, "Singer not empty!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (ValidationUtil.isEmptyValue(emailValue)) {
            Toast.makeText(this, "Your email not empty!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!ValidationUtil.isValidEmail(emailValue)) {
            Toast.makeText(this, "Your email is invalid!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Call api request new ringtone
        prLoading.setVisibility(View.VISIBLE);
        RetrofitUtil.getApi().requestRingtone(titleValue, singerValue, emailValue, token).enqueue(new Callback<RequestRingtoneResponse>() {
            @Override
            public void onResponse(Call<RequestRingtoneResponse> call, Response<RequestRingtoneResponse> response) {
                prLoading.setVisibility(View.GONE);
                resetValue();
                if (response.code() == 200) {
                    Log.i(TAG, "onResponse: link " + call.request().toString());
                    Log.i(TAG, "onResponse: " + response.body());
                    if (response.body().getStatus().equalsIgnoreCase("success")) {
                        Dialog dialog = CommonUntil.createDialog(RequestRingtoneActivity.this, "Create request success!", RequestRingtoneActivity.this.getString(R.string.app_name));
                        dialog.setCancelable(true);
                        dialog.show();
                    }
                }
            }

            @Override
            public void onFailure(Call<RequestRingtoneResponse> call, Throwable t) {
                resetValue();
                prLoading.setVisibility(View.GONE);
                Log.i(TAG, "onResponse: link " + call.request().toString());
                Gson gson = new Gson();
                String json = gson.toJson(call.request().body());
                Log.i(TAG, "onResponse: link2 " + json);
                Log.e(TAG, "onFailure: " + t.getMessage());
                Dialog dialog = CommonUntil.createDialog(RequestRingtoneActivity.this, "Create request failed!", RequestRingtoneActivity.this.getString(R.string.app_name));
                dialog.setCancelable(true);
                dialog.show();
            }
        });
    }

    private void resetValue() {
        edtSinger.setText("");
        edtSongTitle.setText("");
    }
}
