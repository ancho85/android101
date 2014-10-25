package py.com.cursoandroid.clasesqlite;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class MainActivity extends Activity {

    private Button btnInsertar;
    private Button btnActualizar;
    private Button btnEliminar;
    private Button btnListar;

    private EditText txtNombre;
    private EditText txtEmail;

    private ListView lstUsuarios;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Abrimos la base de datos 'DBUsuarios' en modo escritura
        UsuariosSQLiteHelper usdbh = new UsuariosSQLiteHelper(this, "DBUsuarios2", null, 1); // nombre de archivo, y el numero es la VERSION.

        db = usdbh.getWritableDatabase(); // establecer base de datos en modo escritura
        CrearEventosOnClick();

        // Si hemos abierto correctamente la base de datos
        if (db != null) {
            // Insertamos 5 usuarios de ejemplo
            /*
             * for(int i=1; i<=5; i++) { //Generamos los datos int codigo = i; String nombre = "Usuario" + i; String email = "usuario" + i + "@gmail.com"; //Insertamos los datos en la tabla Usuarios
             * db.execSQL("INSERT INTO Usuarios (nombre, email) " + "VALUES ('" + nombre +"', '"+email+"')"); }
             */

            // Cerramos la base de datos
            // db.close();
        }

    }

    private void CrearEventosOnClick() {

        btnInsertar = (Button) findViewById(R.id.Insertar);
        btnActualizar = (Button) findViewById(R.id.Actualizar);
        btnEliminar = (Button) findViewById(R.id.Borrar);
        btnListar = (Button) findViewById(R.id.Listar);

        txtNombre = (EditText) findViewById(R.id.TxtNombre);
        txtEmail = (EditText) findViewById(R.id.TxtEmail);

        lstUsuarios = (ListView) findViewById(R.id.LstValores);

        btnInsertar.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                insertarDatos(txtNombre.getText().toString(), txtEmail.getText().toString());
            }
        });

        btnEliminar.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] nombre = new String[1];
                nombre[0] = txtNombre.getText().toString();
                eliminarDatos(nombre);

            }
        });
        btnListar.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                lstUsuarios.setAdapter(listarDatos());
            }
        });
        btnActualizar.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] nombre = new String[1];
                nombre[0] = txtNombre.getText().toString();
                actualizarDatos(nombre, txtEmail.getText().toString());

            }
        });

    }

    /*-----------------------------METODOS DE BASE DE DATOS-------------------------------------------------*/
    private void insertarDatos(String nombre, String email) {
        // Creamos el registro a insertar como objeto ContentValues
        ContentValues nuevoRegistro = new ContentValues();
        nuevoRegistro.put("nombre", nombre);
        nuevoRegistro.put("email", email);

        // Insertamos el registro en la base de datos
        try {
            db.insert("Usuarios", null, nuevoRegistro);
        }
        catch (Exception ex) {
            Log.e("SQLite", "Error!", ex);
        }
    }

    private void actualizarDatos(String[] ArgNombres, String email) {

        // OJO: la funcion esta preparada para recibir un solo nombre como argumento.
        // Actualizar dos registros con update(), utilizando argumentos
        ContentValues valores = new ContentValues();
        valores.put("email", email);

        String[] args = new String[1];
        args = ArgNombres;
        try {
            db.update("Usuarios", valores, "nombre=?", args);
        }
        catch (Exception ex) {
            Log.e("SQLite", "Error!", ex);
        }

    }

    private void eliminarDatos(String[] ArgNombres) {

        String[] args = new String[1];
        args = ArgNombres;
        try {
            db.delete("Usuarios", "nombre=?", args); // se le pasa un string de arrays
                                                     // que se matchea con todos los "?" que haya, posición por posición
        }
        catch (Exception ex) {
            Log.e("SQLite", "Error!", ex);
        }
    }

    private ArrayAdapter<String> listarDatos() {

        String[] campos = new String[] { "nombre", "email" };
        String[] Usuarios;
        // String[] args = new String[] {"usu1"}; --si necesitamos filtrar por algun valor aqui deberian ir los valores

        Cursor c = db.query("Usuarios", campos, null, null, null, null, null);

        Usuarios = new String[c.getCount()];
        // Nos aseguramos de que existe al menos un registro
        if (c.moveToFirst()) {
            // Recorremos el cursor hasta que no haya más registros
            int i = 0;
            do {
                String usuario = c.getString(0);
                String email = c.getString(1);
                Usuarios[i] = "" + usuario + " | " + email;
                i++;

            }
            while (c.moveToNext());
        }

        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, Usuarios);
        return adaptador;

    }

}
