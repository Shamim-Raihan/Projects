package com.rahat.pharmsafebd.view.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;
import com.rahat.pharmsafebd.R;

public class LoadingDialog {
    private Activity activity;
    private AlertDialog dialog;
    LoadingDialog(Activity myActivity){
        activity = myActivity;
    }
    void startLoadingDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        LayoutInflater inflater = activity.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.loading_dialog_box, null));
        dialog = builder.create();
        dialog.show();
    }
    void dismissDialog(){
        dialog.dismiss();
    }
}
