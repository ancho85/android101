package com.example.blockdenotas;

import android.app.Activity;
import android.content.Intent;
// import android.graphics.Color;
// import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class NuevaTarea extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);
        Intent intent = getIntent();
        TextView tw = (TextView) findViewById(R.id.tv_header);
        tw.setText(intent.getStringExtra("valor"));

        final EditText et_nota = (EditText) findViewById(R.id.et_nota);
        et_nota.setBackgroundColor(getResources().getColor(android.R.color.holo_green_light));

        RadioGroup rg_priority = (RadioGroup) findViewById(R.id.rg_priority);
        rg_priority.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                case R.id.rb_high:
                    // et_nota.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);
                    et_nota.setBackgroundColor(getResources().getColor(android.R.color.holo_red_light));
                    break;
                case R.id.rb_medium:
                    // et_nota.getBackground().setColorFilter(Color.YELLOW, PorterDuff.Mode.SRC_ATOP);
                    et_nota.setBackgroundColor(getResources().getColor(android.R.color.holo_orange_light));
                    break;
                case R.id.rb_low:
                    // et_nota.getBackground().setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_ATOP);
                    et_nota.setBackgroundColor(getResources().getColor(android.R.color.holo_green_light));
                    break;
                }
            }
        });
    }

    public void guardar(View view) {
        Toast.makeText(NuevaTarea.this, "Nota guardada", Toast.LENGTH_LONG).show();
        finish();
    }
}
