package com.example.calculadora;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class OtraActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.otra_activity_main);
        Intent intent = getIntent();
        TextView tw = (TextView) findViewById(R.id.tw_otra);
        tw.setText(intent.getStringExtra("valor"));
    }

    public void volverMain(View view) {
        finish();
    }

}
