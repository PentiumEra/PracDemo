package com.haodong.pracbroadcast;

import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.SharedMemory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    private EditText etvPnumber;
    private Button btnSub;
    private MyReceiver myReceiver;
    private SharedPreferences sp;
    @OnClick(R.id.btn_sub)
   void subCilck(){
        String pnumber=etvPnumber.getText().toString().trim();
        SharedPreferences.Editor editor=sp.edit();
        editor.putString("number",pnumber);
        editor.commit();

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initWidget();
        myReceiver = new MyReceiver();
        // 创建action
//        String action = "android.provider.Telephone.SMS_RECEIVED";
        // 创建Intent过滤器
//        IntentFilter intentFilter = new IntentFilter(action);
        // 注册广播
//        registerReceiver(myReceiver, intentFilter);
        /**
         * 第二种方式：
         */
//        intentFilter = new IntentFilter();
//        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
//        networkChangeReceiver = new NetWorkChangeReceiver();
//        registerReceiver(networkChangeReceiver, intentFilter);//注册广播接收器

    }

    private void initWidget() {
        etvPnumber=findViewById(R.id.etv_pnumber_main);
        btnSub=findViewById(R.id.btn_sub);
        sp=getSharedPreferences("config",MODE_PRIVATE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(myReceiver);
    }
}
