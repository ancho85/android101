package py.com.unionsrl.stocksurvey.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import py.com.unionsrl.stocksurvey.models.Stock;

/**
 * Created by ancho on 09/03/15.
 */
public class StockDBManager {
    public static final String TABLE_NAME = "Stock";
    public static final String CN_CODE = "code";
    public static final String CN_NAME = "name";
    public static final String CN_QTY = "qty";
    public static final String CN_LOT = "lot";
    public static final String CN_DATETIME = "datetime";
    public static final String CN_PHONENUMBER = "phoneNumber";

    private SQLiteDatabase db;


    public StockDBManager(Context ctx){
        StockSQLiteHelper helper = StockSQLiteHelper.getInstance(ctx);
        db = helper.getWritableDatabase();
    }

    private ContentValues setContentValues(Integer code, String name, String lot, Integer qty, String dateTime, String phoneNumber) {
        ContentValues values = new ContentValues();
        values.put(CN_CODE, code);
        values.put(CN_NAME, name);
        values.put(CN_QTY, qty);
        values.put(CN_LOT, lot);
        values.put(CN_DATETIME, dateTime);
        values.put(CN_PHONENUMBER, phoneNumber);
        return values;
    }

    public void insert(Integer code, String name, String lot, Integer qty, String dateTime, String phoneNumber) {
        db.insert(TABLE_NAME, null, setContentValues(code, name, lot, qty, dateTime, phoneNumber));
    }

    public void delete(Integer code) {
        db.delete(TABLE_NAME, CN_CODE + "=?", new String[]{code.toString()});
    }

    public void update(Integer code, String name, String lot, Integer qty, String dateTime, String phoneNumber) {
        db.update(TABLE_NAME, setContentValues(code, name, lot, qty, dateTime, phoneNumber), CN_CODE + "=?", new String[]{code.toString()});
    }

    public ArrayList<Stock> getStocks() {
        String[] columns = new String[]{CN_CODE, CN_NAME, CN_LOT, CN_QTY, CN_DATETIME, CN_PHONENUMBER};
        Cursor c = db.query(TABLE_NAME, columns, null, null, null, null, null);
        ArrayList<Stock> alstock = new ArrayList<>();
        if (c.moveToFirst()) {
            do {
                Integer position = c.getPosition();
                Stock stock = new Stock(Integer.valueOf(c.getString(0)), c.getString(1), c.getString(2), Integer.valueOf(c.getString(3)), c.getString(4), c.getString(5));
                alstock.add(position, stock);
            } while (c.moveToNext());
        }
        c.close();
        return alstock;
    }

    public Integer countByCode(Integer code) {
        Integer count = 0;
        Cursor c = db.rawQuery("SELECT COUNT(*) AS count" +
                " FROM " + TABLE_NAME +
                " WHERE " + CN_CODE + "='" + code.toString(), null);
        if (c.moveToFirst()) {
            count = c.getInt(0);
        }
        c.close();
        return count;
    }

    public Integer count() {
        Integer count = 0;
        Cursor c = db.rawQuery("SELECT COUNT(*) AS count FROM " + TABLE_NAME, null);
        if (c.moveToFirst()) {
            count = c.getInt(0);
        }
        c.close();
        return count;
    }
}
