package com.haodong.asynctaskprac;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.net.URI;

public class DownLoadFilesTask extends AsyncTask<String, Integer, String> {
    private static final String TAG = "DownLoadFilesTask";
   private MainActivity mainActivity;


    public DownLoadFilesTask(MainActivity mainActivity) {
      this.mainActivity=mainActivity;
    }


    @Override
    protected String doInBackground(String... strings) {
        int count = 0;
        int length = 1;
        try {

            while (count < 99) {
                count += length;
                publishProgress(count);
                Thread.sleep(50);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
    //          mainActivity.progressBar.setProgress(values[0]);
              mainActivity.tv1.setText("loading..." + values[0] + "%");
    }

    @Override
    protected void onPostExecute(String s) {
       mainActivity.tv1.setText("加载完成");

    }

    @Override
    protected void onCancelled() {
        mainActivity.tv1.setText("已取消");
        mainActivity.progressBar.setProgress(0);
    }
}
