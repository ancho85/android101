package com.example.calculadora;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	int result;
	int valor1;
	int valor2;
	EditText n1;
	EditText n2;
	Button calcularButton;
	TextView resultView;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	
	// se hace un cast de los controles que tenemos en el layout
	n1 = (EditText) findViewById(R.id.editTextValor1); // R.id contiene todas la referencias a los xml
	n2 = (EditText) findViewById(R.id.editTextValor2);
	calcularButton = (Button) findViewById(R.id.buttonCalcular1);
	resultView = (TextView) findViewById(R.id.textViewResultado1);
	
	calcularButton.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			Toast.makeText(MainActivity.this, Integer.toString(calcular()), Toast.LENGTH_LONG).show(); // esta es una notificacion
			resultView.setText(Integer.toString(calcular())); //reemplaza el texto en el edittext
			
		}
		
		private int calcular(){
			valor1 = Integer.parseInt(n1.getText().toString());
			valor2 = Integer.parseInt(n2.getText().toString());
			result = valor1 * valor2;
			return result;
		}
	});
	

	}
}
