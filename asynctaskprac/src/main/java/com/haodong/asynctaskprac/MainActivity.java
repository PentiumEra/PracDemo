package com.haodong.asynctaskprac;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    NewAsyncTask mTask;
    TextView tv1;
    ProgressBar progressBar;
    //
//    @BindView(R.id.btn_execute)
    Button btnExecute;
    //    @OnClick(R.id.btn_execute)
//    void onEcecuteClick() {
//        Log.i(TAG,"btn");
//        mTask.execute();
//    }
//    @BindView(R.id.btn_cancel)
    Button btnCancel;
//    @OnClick(R.id.btn_cancel)
//    void onCancelClick() {
//        mTask.cancel(true);
//        tv1.setText("已取消");
//    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv1 = findViewById(R.id.tv_progress_1);
        btnExecute = findViewById(R.id.btn_execute);
        btnCancel = findViewById(R.id.btn_cancel);
        progressBar = findViewById(R.id.progrossbar);
        mTask = new NewAsyncTask();
        btnExecute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "btn");
                mTask.execute();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTask.cancel(true);
                tv1.setText("已取消");
            }
        });

    }

    class NewAsyncTask extends AsyncTask<String, Integer, String> {

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
        protected void onCancelled() {
            tv1.setText("已取消");
            progressBar.setProgress(0);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            progressBar.setProgress(values[0]);
            tv1.setText("loading..." + values[0] + "%");
        }

        @Override
        protected void onPostExecute(String s) {
            progressBar.setProgress(0);
            tv1.setText("加载完成");
        }
    }

}
