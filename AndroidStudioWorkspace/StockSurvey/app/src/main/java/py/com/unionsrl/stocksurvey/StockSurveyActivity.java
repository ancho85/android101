package py.com.unionsrl.stocksurvey;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

import py.com.unionsrl.stocksurvey.database.ItemDBManager;
import py.com.unionsrl.stocksurvey.database.StockSQLiteHelper;
import py.com.unionsrl.stocksurvey.models.Item;


public class StockSurveyActivity extends ActionBarActivity {

    EditText etxtCode;
    EditText etxtLot;
    EditText etxtQty;
    TextView txtvName;

    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_survey);
    }

    @Override
    public void onResume() {
        super.onResume();
        etxtCode = (EditText) findViewById(R.id.etxt_Code);
        etxtCode.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {
                    txtvName = (TextView) findViewById(R.id.txtv_Name);
                    etxtCode = (EditText) findViewById(R.id.etxt_Code);
                    String code = etxtCode.getText().toString();
                    if (code.length() > 0) {
                        Item item = ItemDBManager.load(getApplicationContext(), Integer.valueOf(code));
                        if (item.getCode() != 0) {
                            txtvName.setText(item.getName());
                        }
                        else {
                            txtvName.setText(R.string.Name);
                            message("Codigo no encontrado");
                        }
                    }
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_stock_survey, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void saveSurvey(View view) {
        StockSQLiteHelper sdb = StockSQLiteHelper.getInstance(this);
        db = sdb.getWritableDatabase();

        etxtCode = (EditText) findViewById(R.id.etxt_Code);
        etxtLot = (EditText) findViewById(R.id.etxt_Lot);
        etxtQty = (EditText) findViewById(R.id.etxt_Qty);
        txtvName = (TextView) findViewById(R.id.txtv_Name);

        ContentValues newStock = new ContentValues();
        newStock.put("code", etxtCode.getText().toString());
        newStock.put("lot", etxtLot.getText().toString());
        newStock.put("qty", etxtQty.getText().toString());
        newStock.put("name", txtvName.getText().toString());
        newStock.put("datetime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));

        // Insertamos el registro en la base de datos
        try {
            db.insert("Stock", null, newStock);
        }
        catch (Exception ex) {
            Log.e("SQLite", "Error!", ex);
        }

        db.close();
        Toast.makeText(StockSurveyActivity.this, R.string.stock_survey_saved, Toast.LENGTH_LONG).show();
        finish();
    }

    public void message(String toastString) {
        Toast.makeText(getApplicationContext(), toastString, Toast.LENGTH_LONG).show();
    }

    public void backMain(View view) {
        finish();
    }
}
