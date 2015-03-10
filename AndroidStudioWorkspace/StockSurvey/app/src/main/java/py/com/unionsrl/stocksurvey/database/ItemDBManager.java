package py.com.unionsrl.stocksurvey.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import py.com.unionsrl.stocksurvey.models.Item;

/**
 * Created by ancho on 09/03/15.
 */
public class ItemDBManager {
    public static final String TABLE_NAME = "Item";
    public static final String CN_CODE = "code";
    public static final String CN_NAME = "name";
    public static final String CN_BARCODE = "barcode";

    private StockSQLiteHelper helper;
    private SQLiteDatabase db;


    public ItemDBManager(Context ctx){
        StockSQLiteHelper sdb = StockSQLiteHelper.getInstance(ctx);
        db = sdb.getWritableDatabase();
    }

    private ContentValues setContentValues(Integer code, String name, String barcode) {
        ContentValues values = new ContentValues();
        values.put(CN_CODE, code);
        values.put(CN_NAME, name);
        values.put(CN_BARCODE, barcode);
        return values;
    }

    public void insert(Integer code, String name, String barcode) {
        db.insert(TABLE_NAME, null, setContentValues(code, name, barcode));
    }

    public void delete(Integer code) {
        db.delete(TABLE_NAME, CN_CODE + "=?", new String[]{code.toString()});
    }

    public void update(Integer code, String name, String barcode) {
        db.update(TABLE_NAME, setContentValues(code, name, barcode), CN_CODE + "=?", new String[]{code.toString()});
    }

    public ArrayList<Item> getItems() {
        String[] columns = new String[]{CN_CODE, CN_NAME, CN_BARCODE};
        Cursor c = db.query(TABLE_NAME, columns, null, null, null, null, null);
        ArrayList<Item> alitem = new ArrayList<>();
        if (c.moveToFirst()) {
            do {
                Integer position = c.getPosition();
                Item item = new Item(Integer.valueOf(c.getString(0)), c.getString(1), c.getString(2));
                alitem.add(position, item);
            } while (c.moveToNext());
        }
        c.close();
        return alitem;
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

    public static Item load(Context ctx, Integer code) {
        StockSQLiteHelper sdb = StockSQLiteHelper.getInstance(ctx);
        SQLiteDatabase db = sdb.getReadableDatabase();
        Item item = new Item();
        String[] columns = new String[]{CN_CODE, CN_NAME, CN_BARCODE};
        String where = "code = '" + code.toString() + "'";
        Cursor c = db.query(TABLE_NAME, columns, where, null, null, null, null);
        if (c.moveToFirst()) {
            item.setCode(Integer.valueOf(c.getString(0)));
            item.setName(c.getString(1));
            item.setBarCode(c.getString(2));
        }
        c.close();
        return item;
    }
}
