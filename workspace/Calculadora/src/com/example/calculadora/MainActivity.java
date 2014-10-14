package com.example.calculadora;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
    int result;
    int valor1;
    int valor2;
    EditText n1;
    EditText n2;
    Button calcularButton;
    Button otraActividad;
    TextView resultView;
    RadioGroup rgOperations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // se hace un cast de los controles que tenemos en el layout
        n1 = (EditText) findViewById(R.id.editTextValor1); // R.id contiene
                                                           // todas la
                                                           // referencias a los
                                                           // xml
        n2 = (EditText) findViewById(R.id.editTextValor2);
        calcularButton = (Button) findViewById(R.id.buttonCalcular1);
        resultView = (TextView) findViewById(R.id.textViewResultado1);
        rgOperations = (RadioGroup) findViewById(R.id.rg_operations);

        calcularButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, Integer.toString(calcular()),
                        Toast.LENGTH_LONG).show(); // esta es una notificacion
                resultView.setText(Integer.toString(calcular())); // reemplaza
                                                                  // el texto en
                                                                  // el edittext

            }

            private int calcular() {
                valor1 = Integer.parseInt(n1.getText().toString());
                valor2 = Integer.parseInt(n2.getText().toString());

                int id_rb_checket =rgOperations.getCheckedRadioButtonId();
                
                switch (id_rb_checket){
                   case R.id.radioSuma:
                      result = valor1 + valor2;
                      break;
                   case R.id.radioResta:
                      result = valor1 - valor2;
                      break;
                   case R.id.radioMulti:
                       result = valor1 * valor2;
                       break;
                   case R.id.radioDivi:
                       if (valor2 > 0){
                           result = valor1 / valor2;
                       }
                       break;
                }
                return result;
            }
        });

        /*
         * otraActividad = (Button) findViewById(R.id.buttonOtraActivity);
         * otraActividad.setOnClickListener(new OnClickListener() {
         * 
         * @Override public void onClick(View v) { startActivity(new
         * Intent(this, OtraActivity.class)); } });
         */

    }

    public void onClickAction(View view) {
        Intent otra = new Intent(this, OtraActivity.class);
        otra.putExtra("valor", "hola");
        startActivity(otra);
    };
}
