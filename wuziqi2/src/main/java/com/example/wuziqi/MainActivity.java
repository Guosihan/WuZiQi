package com.example.wuziqi;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private WuZiQiPanel wuziq;
    private Button btnRestart;
    private Button btnExit;
             @Override
        protected void onCreate(Bundle savedInstanceState) {
                 super.onCreate(savedInstanceState);
                 setContentView(R.layout.activity_main);
                 wuziq= (WuZiQiPanel) findViewById(R.id.wuziqi);
                 btnExit= (Button) findViewById(R.id.exit);
                 btnRestart= (Button) findViewById(R.id.restart);
                 btnExit.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View v) {
                         finish();
                     }
                 });
                 btnRestart.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View v) {
                         wuziq.reStart();
                     }
                 });
             }




}
