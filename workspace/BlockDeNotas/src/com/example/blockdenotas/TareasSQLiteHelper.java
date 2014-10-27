package com.example.blockdenotas;
import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class TareasSQLiteHelper extends SQLiteOpenHelper {

    String sqlCreate = "CREATE TABLE Tarea (titulo TEXT, subtitulo TEXT, prioridad INTEGER)";

    public TareasSQLiteHelper(Context context, String name, CursorFactory factory, int version) {
        super(context, name, factory, version);
        // TODO Auto-generated constructor stub
    }

    public TareasSQLiteHelper(Context context, String name, CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Se ejecuta la sentencia SQL de creación de la tabla
        try {
            db.execSQL(sqlCreate);
        }
        catch (Exception ex) {
            Log.e("SQLite", "Error!", ex);
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Se elimina la versión anterior de la tabla
        db.execSQL("DROP TABLE IF EXISTS Tarea");

        // Se crea la nueva versión de la tabla
        db.execSQL(sqlCreate);

    }

}
