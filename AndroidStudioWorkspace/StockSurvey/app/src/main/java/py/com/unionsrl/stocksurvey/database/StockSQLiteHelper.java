package py.com.unionsrl.stocksurvey.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by ancho on 07/03/15.
 */
public class StockSQLiteHelper extends SQLiteOpenHelper {

    private static StockSQLiteHelper mInstance = null;

    private static final String DATABASE_NAME = "DBStockSurvey.sqlite";
    private static final int DATABASE_VERSION = 2;

    String sqlItemCreate = "CREATE TABLE Item (code INTEGER PRIMARY KEY NOT NULL, " +
            "name TEXT NOT NULL, barcode TEXT)";
    String sqlStockCreate = "CREATE TABLE Stock (code INTEGER, name TEXT NOT NULL, " +
            "lot TEXT NOT NULL, qty INTEGER NOT NULL, datetime TEXT NOT NULL, "+
            "phonenumber TEXT NOT NULL)";


    public static StockSQLiteHelper getInstance(Context ctx) {

        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (mInstance == null) {
            mInstance = new StockSQLiteHelper(ctx.getApplicationContext());
        }
        return mInstance;
    }

    /**
     * Constructor should be private to prevent direct instantiation. make call to static factory method "getInstance()" instead.
     */
    private StockSQLiteHelper(Context ctx) {
        super(ctx, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(sqlItemCreate);
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
