package com.example.blockdenotas;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;
// import android.graphics.Color;
// import android.graphics.PorterDuff;

public class NuevaTarea extends Activity {

    EditText txtTitulo;
    EditText txtSubtitulo;
    RadioGroup rdbPrioridad;

    SQLiteDatabase db;

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
        TareasSQLiteHelper tdbh = new TareasSQLiteHelper(this, "DBTareas.sqlite3", null, 1); // nombre de archivo, y el numero es la VERSION.
        db = tdbh.getWritableDatabase(); // establecer base de datos en modo escritura

        txtTitulo = (EditText) findViewById(R.id.et_titulo);
        txtSubtitulo = (EditText) findViewById(R.id.et_nota);
        rdbPrioridad = (RadioGroup) findViewById(R.id.rg_priority);


        int id_rb_prioridad = rdbPrioridad.getCheckedRadioButtonId();
        int prioridad;
        switch (id_rb_prioridad) {
            case R.id.rb_high:
                prioridad = 3;
                break;
            case R.id.rb_medium:
                prioridad = 2;
                break;
            case R.id.rb_low:
                prioridad = 1;
                break;
            default:
                prioridad = 0;
                break;
        }

        ContentValues nuevoRegistro = new ContentValues();
        nuevoRegistro.put("prioridad", prioridad);
        nuevoRegistro.put("titulo", txtTitulo.getText().toString());
        nuevoRegistro.put("subtitulo", txtSubtitulo.getText().toString());

        // Insertamos el registro en la base de datos
        try {
            db.insert("Tarea", null, nuevoRegistro);
        }
        catch (Exception ex) {
            Log.e("SQLite", "Error!", ex);
        }

        Toast.makeText(NuevaTarea.this, "Nota guardada", Toast.LENGTH_LONG).show();
        finish();
    }
}
