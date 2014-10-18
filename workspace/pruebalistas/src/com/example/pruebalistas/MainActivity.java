package com.example.pruebalistas;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final String[] datos = new String[]{"Elem1","Elem2","Elem3","Elem4","Elem5"}; 
        ArrayAdapter <String> adaptador = 
                new ArrayAdapter <String> (this, android.R.layout.simple_spinner_item, datos);

        final Spinner cmbOpciones = (Spinner)findViewById(R.id.CmbOpciones);

        adaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        cmbOpciones.setAdapter(adaptador);
        final TextView lblMensaje = (TextView)findViewById(R.id.TxtMessage);

        cmbOpciones.setOnItemSelectedListener( 
                new AdapterView.OnItemSelectedListener() { 
                    public void onItemSelected(AdapterView<?> parent, 
                           android.view.View v, int position, long id) { 
                            lblMensaje.setText("Seleccionado: " + datos[position]); 
                    } 
                    public void onNothingSelected(AdapterView<?> parent) {
                        lblMensaje.setText("");
                    } 
                }
        );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
