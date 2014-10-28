package com.example.blockdenotas;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class NuevaTarea extends Activity {

    EditText txtTitulo;
    EditText txtSubtitulo;
    RadioGroup rdbPrioridad;

    final TareasSQLiteHelper tdbh = TareasSQLiteHelper.getInstance(this);
    SQLiteDatabase db;

    Boolean editMode = false;
    Button btnGuardar;
    Button btnActualizar;
    Button btnBorrar;
    Button btnCompartir;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);
        Intent intent = getIntent();

        txtTitulo = (EditText) findViewById(R.id.et_titulo);
        txtTitulo = (EditText) findViewById(R.id.et_titulo);

        txtSubtitulo = (EditText) findViewById(R.id.et_nota);
        txtTitulo.setTextColor(getResources().getColor(android.R.color.holo_green_light));

        RadioGroup rdbPrioridad = (RadioGroup) findViewById(R.id.rg_priority);

        editMode = intent.getBooleanExtra("editMode", false);
        txtTitulo.setText(intent.getStringExtra("titulo"));
        txtSubtitulo.setText(intent.getStringExtra("subtitulo"));
        int prioridad = intent.getIntExtra("prioridad", 4);
        switch (prioridad) {
            case 3:
                rdbPrioridad.check(R.id.rb_high);
                txtTitulo.setTextColor(getResources().getColor(android.R.color.holo_red_light));
                break;
            case 2:
                rdbPrioridad.check(R.id.rb_medium);
                txtTitulo.setTextColor(getResources().getColor(android.R.color.holo_orange_light));
                break;
            case 1:
                rdbPrioridad.check(R.id.rb_low);
                txtTitulo.setTextColor(getResources().getColor(android.R.color.holo_green_light));
                break;
        };

        if (editMode) {
            prepareEditMode();
        }

        rdbPrioridad.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_high:
                        txtTitulo.setTextColor(getResources().getColor(android.R.color.holo_red_light));
                        break;
                    case R.id.rb_medium:
                        txtTitulo.setTextColor(getResources().getColor(android.R.color.holo_orange_light));
                        break;
                    case R.id.rb_low:
                        txtTitulo.setTextColor(getResources().getColor(android.R.color.holo_green_light));
                        break;
                }
            }
        });
    }

    public void prepareEditMode() {
        setTitle("Editar nota");

        btnGuardar = (Button) findViewById(R.id.btn_save);
        btnGuardar.setVisibility(View.GONE); // oculto el boton guardar para no insertar un nuevo dato.

        txtTitulo = (EditText) findViewById(R.id.et_titulo);
        txtTitulo.setKeyListener(null); // evito que se pueda cambiar el titulo en modo edicion.
        txtTitulo.setBackgroundResource(R.drawable.shapeblue);
        txtTitulo.setFocusable(false);

        TextView tv_header = (TextView) findViewById(R.id.tv_header);
        tv_header.setText("Titulo (No editable)");

        btnActualizar = (Button) findViewById(R.id.btn_update);
        btnActualizar.setVisibility(View.VISIBLE);

        btnBorrar = (Button) findViewById(R.id.btn_delete);
        btnBorrar.setVisibility(View.VISIBLE);

        btnCompartir = (Button) findViewById(R.id.btn_share);
        btnCompartir.setVisibility(View.VISIBLE);
    };

    public ContentValues generateContentValues() {
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
        return nuevoRegistro;
    }

    public void guardar(View view) {
        db = tdbh.getWritableDatabase(); // establecer base de datos en modo escritura

        ContentValues nuevoRegistro = generateContentValues();
        // Insertamos el registro en la base de datos
        try {
            db.insert("Tarea", null, nuevoRegistro);
        }
        catch (Exception ex) {
            Log.e("SQLite", "Error!", ex);
        }
        finally {
            db.close();
        }

        Toast.makeText(NuevaTarea.this, "Nota guardada", Toast.LENGTH_LONG).show();
        finish();
    }

    public void actualizar(View view) {
        db = tdbh.getWritableDatabase();

        ContentValues actRegistro = generateContentValues();
        txtTitulo = (EditText) findViewById(R.id.et_titulo);
        String[] titulo = new String[] { txtTitulo.getText().toString() };
        try {
            db.update("Tarea", actRegistro, "titulo=?", titulo);
        }
        catch (Exception ex) {
            Log.e("SQLite", "Error!", ex);
        }
        finally {
            db.close();
        }

        Toast.makeText(NuevaTarea.this, "Nota actualizada", Toast.LENGTH_LONG).show();
        finish();

    }

    public void borrar(View view) {
        FragmentManager fragmentManager = getFragmentManager();
        DialogoConfirmacion dialogo = new DialogoConfirmacion();
        dialogo.show(fragmentManager, "confirmar primero");
    }

    public void borrarDeBase() {
        db = tdbh.getWritableDatabase();

        txtTitulo = (EditText) findViewById(R.id.et_titulo);
        String[] titulo = new String[] { txtTitulo.getText().toString() };

        try {
            db.delete("Tarea", "titulo=?", titulo);
        }
        catch (Exception ex) {
            Log.e("SQLite", "Error!", ex);
        }
        finally {
            db.close();
        }

        Toast.makeText(NuevaTarea.this, "Nota borrada", Toast.LENGTH_LONG).show();
        finish();
    }

    public void compartir(View view) {

        txtTitulo = (EditText) findViewById(R.id.et_titulo);
        txtSubtitulo = (EditText) findViewById(R.id.et_nota);
        Social.share(NuevaTarea.this, txtTitulo.getText().toString(), txtSubtitulo.getText().toString());
    }

    public class DialogoConfirmacion extends DialogFragment {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

            builder.setMessage("¿Confirma la acción seleccionada?");
            builder.setTitle("Confirmacion");
            builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id) {
                    borrarDeBase();
                }
            });
            builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id) {
                    dialog.cancel();
                }
            });

            return builder.create();
        }
    }
}
