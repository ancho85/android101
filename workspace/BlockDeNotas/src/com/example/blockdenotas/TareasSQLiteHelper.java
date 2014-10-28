package com.example.blockdenotas;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class TareasSQLiteHelper extends SQLiteOpenHelper {

    private static TareasSQLiteHelper mInstance = null;

    private static final String DATABASE_NAME = "DBTareas.sqlite3";
    private static final String DATABASE_TABLE = "Tarea";
    private static final int DATABASE_VERSION = 1;

    String sqlCreate = "CREATE TABLE " + DATABASE_TABLE + "(titulo TEXT, subtitulo TEXT, prioridad INTEGER)";


    public static TareasSQLiteHelper getInstance(Context ctx) {

        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (mInstance == null) {
            mInstance = new TareasSQLiteHelper(ctx.getApplicationContext());
        }
        return mInstance;
    }

    /**
     * Constructor should be private to prevent direct instantiation. make call to static factory method "getInstance()" instead.
     */
    private TareasSQLiteHelper(Context ctx) {
        super(ctx, DATABASE_NAME, null, DATABASE_VERSION);
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
        db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);

        // Se crea la nueva versión de la tabla
        db.execSQL(sqlCreate);

    }

}
