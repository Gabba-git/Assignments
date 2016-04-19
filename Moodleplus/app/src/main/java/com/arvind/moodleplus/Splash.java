package com.arvind.moodleplus;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by arvind on 23/1/16.
 */

// splash activity
public class Splash extends Activity{
    @Override
    protected void onCreate(Bundle lat) {
        super.onCreate(lat);
        setContentView(R.layout.splash);
        Thread timer = new Thread(){  /// thread to stop the process
            public void run(){
                try{
                    sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    Intent strt = new Intent("com.arvind.moodleplus.STARTINGPOINT");
                    startActivity(strt);
                }
            }
        };
        timer.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

}
