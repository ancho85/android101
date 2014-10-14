package com.example.blockdenotas;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class NuevaTarea extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);
        Intent intent = getIntent();
        // TextView tw = (TextView) findViewById(R.id.tv_header);
        // tw.setText(intent.getStringExtra("valor"));
    }

    public void guardar(View view) {
        Toast.makeText(NuevaTarea.this, "Nota guardada", Toast.LENGTH_LONG).show();
        finish();
    }

    public void changePriorityColor(View view){
        EditText et_nota = (EditText) findViewById(R.id.et_nota);
    	final RadioGroup rg = (RadioGroup) findViewById(R.id.rg_priority);        
        int id_rb_checket = rg.getCheckedRadioButtonId();
        
        switch (id_rb_checket){
	         case R.id.rb_high:
	        	 break;
	         case R.id.rb_medium:
	        	 break;
	         case R.id.rb_low:
	        	 break;
    }

}
