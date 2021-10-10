package com.example.filedownloader;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private Button startButton;
    private volatile boolean stopThread = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startButton = findViewById(R.id.startBtn);
    }


    public void mockFileDownloader(){
        runOnUiThread(
                new Runnable() {
                    @Override
                    public void run() {
                        startButton.setText("Downloading...");
                    }
                }
        );
        TextView progressText = (TextView) findViewById(R.id.downloadText);
        for(int downloadProgress = 0; downloadProgress <= 100; downloadProgress=downloadProgress+10){
            if(stopThread){
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        startButton.setText("Start");
                    }
                });
            }

            Log.d(TAG, "Download Progress: "+downloadProgress+"%");
            //TextView progressText = (TextView) findViewById(R.id.downloadText);
            int finalDownloadProgress = downloadProgress;
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    progressText.setText("Downloading Progress: " + finalDownloadProgress + "%");
                }
            });


            try{
                Thread.sleep(1000);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }

        runOnUiThread(
                new Runnable() {
                    @Override
                    public void run() {
                        startButton.setText("Start");
                    }
                }
        );
    }


    public void startDownload(View view){
        stopThread = false;
        ExampleRunnable runnable = new ExampleRunnable();
        new Thread(runnable).start();
    }

    class ExampleRunnable implements Runnable{
        public void run(){
            mockFileDownloader();
        }
    }

    public void stopDownload(View view){
        stopThread = true;
    }

}

