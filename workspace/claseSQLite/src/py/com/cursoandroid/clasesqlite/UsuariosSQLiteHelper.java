package py.com.cursoandroid.clasesqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class UsuariosSQLiteHelper extends SQLiteOpenHelper {

    // Sentencia SQL para crear la tabla de Usuarios
    String sqlCreate = "CREATE TABLE Usuarios (nombre TEXT, email TEXT)";

    public UsuariosSQLiteHelper(Context contexto, String nombre, CursorFactory factory, int version) {
        super(contexto, nombre, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Se ejecuta la sentencia SQL de creaci�n de la tabla
        try {
            db.execSQL(sqlCreate);
        }
        catch (Exception ex) {
            Log.e("SQLite", "Error!", ex);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int versionAnterior, int versionNueva) {
        // NOTA: Por simplicidad del ejemplo aqu� utilizamos directamente la opci�n de
        // eliminar la tabla anterior y crearla de nuevo vac�a con el nuevo formato.
        // Sin embargo lo normal ser� que haya que migrar datos de la tabla antigua
        // a la nueva, por lo que este m�todo deber�a ser m�s elaborado.

        // OBS: En caso de pasar de version muy antigua a una muy nueva, deber�an haber
        // funcionalidades para cada caso, paso por paso entre version y version

        // Se elimina la versi�n anterior de la tabla
        db.execSQL("DROP TABLE IF EXISTS Usuarios");

        // Se crea la nueva versi�n de la tabla
        db.execSQL(sqlCreate);
    }
}
