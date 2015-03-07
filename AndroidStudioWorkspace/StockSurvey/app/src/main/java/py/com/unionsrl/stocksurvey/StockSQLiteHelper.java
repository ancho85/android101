package py.com.unionsrl.stocksurvey;

import android.annotation.TargetApi;
import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.util.Log;

/**
 * Created by ancho on 07/03/15.
 */
public class StockSQLiteHelper extends SQLiteOpenHelper {

    String sqlItemCreate = "CREATE TABLE Item (code INTEGER PRIMARY KEY NOT NULL, " +
            "name TEXT NOT NULL, barcode TEXT)";
    String sqlStockCreate = "CREATE TABLE Stock (code INTEGER, name TEXT NOT NULL, " +
            "lot TEXT NOT NULL, qty INTEGER NOT NULL, datetime TEXT NOT NULL)";

    public StockSQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public StockSQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(sqlItemCreate);
            db.execSQL("INSERT INTO Item(code, name, barcode) VALUES (1, 'Item1', 'AAAAAA')");
            db.execSQL(sqlStockCreate);
        }
        catch (Exception ex) {
            Log.e("SQLite", "Error!", ex);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS Item");
        db.execSQL("DROP TABLE IF EXISTS Stock");
        try {
            db.execSQL(sqlItemCreate);
            db.execSQL(sqlStockCreate);
        }
        catch (Exception ex) {
            Log.e("SQLite", "Error!", ex);
        }
    }
}
