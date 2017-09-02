package com.example.bahaaibrahim.tasteria;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private boolean backPressedTwice;
    private Handler backHandler = new Handler();
    private final Runnable backRunnable = new Runnable() {
        @Override
        public void run() {
            backPressedTwice = false;
        }
    };
    private Toast backPressToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    @Override
    public void onBackPressed() {
        //Plugging a handler to wait for 1 second before receiving a confirmation to destroy Activity
        if (backPressedTwice) {
            Intent exitIntent = new Intent(Intent.ACTION_MAIN);
            exitIntent.addCategory(Intent.CATEGORY_HOME);
            exitIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(exitIntent);

        }
        backPressedTwice = true;
        //Prevent the toast from appearing twice
        if (backPressToast != null) {
            backPressToast.cancel();
            backPressToast = null;
        } else {
            backPressToast = Toast.makeText(getApplicationContext(), R.string.backPressExit, Toast.LENGTH_SHORT);
            backPressToast.show();
        }
        backHandler.postDelayed(backRunnable, 1000);


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //Release the Handler and its runnable from the memory on Activity destruction
        if (backHandler != null) {
            backHandler.removeCallbacks(backRunnable);
        }
    }
}