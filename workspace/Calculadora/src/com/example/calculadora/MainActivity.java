package com.example.calculadora;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {
	int result;
	int valor1;
	int valor2;
	EditText n1;
	EditText n2;
	Button calcular;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	
	// se hace un cast de los controles que tenemos en el layout
	n1 = (EditText) findViewById(R.id.editTextValor1); // R.id contiene todas la referencias a los xml
	n2 = (EditText) findViewById(R.id.editTextValor2);
	calcular = (Button) findViewById(R.id.buttonCalcular1);
	
	calcular.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			Toast.makeText(MainActivity.this, Integer.toString(calcular()), Toast.LENGTH_LONG).show();
			
		}
	});
	}
}
