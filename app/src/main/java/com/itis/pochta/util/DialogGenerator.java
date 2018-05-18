package com.itis.pochta.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ProgressBar;

import com.itis.pochta.R;

import java.util.Timer;
import java.util.TimerTask;

public class DialogGenerator {

    private Activity activity;
    private AlertDialog progressDialog;

    public DialogGenerator(Activity activity) {
        this.activity = activity;
    }

    public void showProgressDialog(){
        AlertDialog.Builder dialog = new AlertDialog.Builder(activity);
        dialog.setTitle("Пожалуйста, подождите...");
        dialog.setMessage("Идет загрузка...");
        ProgressBar progressBar = new ProgressBar(activity);
        progressBar.setVisibility(View.VISIBLE);
        dialog.setView(progressBar);
        dialog.setCancelable(false);
        this.progressDialog = dialog.create();
        progressDialog.show();
    }

    public void hideProgressDialog(){
        if (progressDialog != null) {
            progressDialog.cancel();
        }
    }

    public boolean isProgressShowing() {
        return progressDialog != null && progressDialog.isShowing();
    }

    public void showExitDialog(){
        AlertDialog.Builder dialog = new AlertDialog.Builder(activity);
        dialog.setTitle("Выход");
        dialog.setMessage("Выйти из приложения?");
        dialog.setPositiveButton("Выйти", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int arg1) {
                activity.finish();
            }
        });
        dialog.setNegativeButton(R.string.action_cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int arg1) {
                dialog.cancel();
            }
        });
        dialog.setCancelable(true);
        dialog.show();
    }
}
