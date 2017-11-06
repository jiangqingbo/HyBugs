package com.huyunit.bugsdemo;

import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView resultText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        resultText = (TextView) findViewById(R.id.tv_result);

        findViewById(R.id.btn_crashhandler).setOnClickListener(this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_crashhandler:
                System.out.println("model = " + Build.MODEL);
                System.out.println("sdk = " + Build.VERSION.SDK_INT);
                System.out.println("CODENAME = " + Build.VERSION.CODENAME);
                System.out.println("BASE_OS = " + Build.VERSION.BASE_OS);
                System.out.println("CPU_ABI = " + Build.CPU_ABI);
                System.out.println("CPU_ABI = " + Build.SUPPORTED_ABIS);
                System.out.println("Build.VERSION.RELEASE = " + Build.VERSION.RELEASE);
                String s = "abc";
                int i  = Integer.parseInt(s);
                System.out.println("i = " + i);
                break;
            default:
                break;
        }
    }
}
