package py.com.unionsrl.stocksurvey.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

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
    public static final String CN_DATETIME = "dateTime";

    private StockSQLiteHelper helper;
    private SQLiteDatabase db;


    public StockDBManager(Context ctx){
        StockSQLiteHelper sdb = StockSQLiteHelper.getInstance(ctx);
        db = sdb.getWritableDatabase();
    }

    private ContentValues setContentValues(Integer code, String name, String lot, Integer qty, String dateTime) {
        ContentValues values = new ContentValues();
        values.put(CN_CODE, code);
        values.put(CN_NAME, name);
        values.put(CN_QTY, qty);
        values.put(CN_LOT, lot);
        values.put(CN_DATETIME, dateTime);
        return values;
    }

    public void insert(Integer code, String name, String lot, Integer qty, String dateTime) {
        db.insert(TABLE_NAME, null, setContentValues(code, name, lot, qty, dateTime));
    }

    public void delete(Integer code) {
        db.delete(TABLE_NAME, CN_CODE + "=?", new String[]{code.toString()});
    }

    public void update(Integer code, String name, String lot, Integer qty, String dateTime) {
        db.update(TABLE_NAME, setContentValues(code, name, lot, qty, dateTime), CN_CODE + "=?", new String[]{code.toString()});
    }

    public Stock[] getStocks() {
        String[] columns = new String[]{CN_CODE, CN_NAME, CN_LOT, CN_QTY, CN_DATETIME};
        Cursor c = db.query(TABLE_NAME, columns, null, null, null, null, null);
        Stock[] customer = new Stock[c.getCount()];
        if (c.moveToFirst()) {
            do {
                Integer position = c.getPosition();
                customer[position] = new Stock();
                customer[position].setCode(Integer.valueOf(c.getString(0)));
                customer[position].setName(c.getString(1));
                customer[position].setLot(c.getString(2));
                customer[position].setQty(Integer.valueOf(c.getString(3)));
                customer[position].setDateTime(c.getString(4));
            } while (c.moveToNext());
        }
        c.close();
        return customer;
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
