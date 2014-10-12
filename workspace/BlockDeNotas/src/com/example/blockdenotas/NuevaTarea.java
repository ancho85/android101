package com.example.blockdenotas;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class NuevaTarea extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);
        Intent intent = getIntent();
        TextView tw = (TextView) findViewById(R.id.tw_otra);
        tw.setText(intent.getStringExtra("valor"));
    }

    public void volverMain(View view) {
        finish();
    }

}
