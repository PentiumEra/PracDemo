package com.haodong.prachandler;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.lang.ref.WeakReference;

import butterknife.BindView;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.tv_1_handler)
    TextView tv1;
    private static Handler newHandler;

    static {
        newHandler = new Handler();
    }


    private final NewHandler myHandler = new NewHandler(this, tv1);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new Thread() {
            @Override
            public void run() {
                // 实例化Message
                Message message = Message.obtain();
                // 消息标识
                message.what = 1;
                // 消息内容存放(handler)
                message.obj = "aa";
                myHandler.sendMessage(message);
            }
        };

    }

    public static class NewHandler extends Handler {
        private final WeakReference<AppCompatActivity> mActivity;
        private TextView mView;

        public NewHandler(AppCompatActivity mActivity, TextView view) {
            this.mActivity = new WeakReference<AppCompatActivity>(mActivity);
            this.mView = view;
        }

        @Override
        public void handleMessage(Message msg) {
            AppCompatActivity appCompatActivity = mActivity.get();
            if (appCompatActivity != null) {
                switch (msg.what) {
                    case 1:
                        mView.setText("执行了线程1中的线程");
                        break;
                    case 2:
                        mView.setText("执行了线程2中的线程");
                        break;
                    default:
                        break;
                }

            }
        }
    }

}
