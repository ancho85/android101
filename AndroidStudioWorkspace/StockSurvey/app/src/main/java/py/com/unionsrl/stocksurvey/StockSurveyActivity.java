package py.com.unionsrl.stocksurvey;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

import py.com.unionsrl.stocksurvey.database.ItemDBManager;
import py.com.unionsrl.stocksurvey.database.StockDBManager;
import py.com.unionsrl.stocksurvey.models.Item;


public class StockSurveyActivity extends ActionBarActivity {

    EditText etxtCode;
    EditText etxtLot;
    EditText etxtQty;
    TextView txtvName;

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
                        Context ctx = getApplicationContext();
                        Item item = ItemDBManager.load(ctx, Integer.valueOf(code));
                        if (item.getCode() != 0) {
                            txtvName.setText(item.getBarCode() + "-" + item.getName());
                        }
                        else {
                            txtvName.setText(R.string.Name);
                            message(ctx.getString(R.string.code_not_found));
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
        etxtCode = (EditText) findViewById(R.id.etxt_Code);
        etxtLot = (EditText) findViewById(R.id.etxt_Lot);
        etxtQty = (EditText) findViewById(R.id.etxt_Qty);

        String code = etxtCode.getText().toString();
        String lot = etxtLot.getText().toString();
        String qty = etxtQty.getText().toString();

        if (!(lot.trim().length() > 0 && qty.trim().length() > 0 && code.trim().length() > 0)){
            message(getString(R.string.code_lot_qty_not_empty));
            return;
        }
        Integer intCode = Integer.valueOf(code);
        Integer intQty = Integer.valueOf(qty);

        Item item = ItemDBManager.load(this, intCode);
        if (item.getCode() != 0) {
            String name = item.getName();
            String dateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", java.util.Locale.getDefault()
            ).format(new Date());
            TelephonyManager phoneManager = (TelephonyManager)
                    getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
            String phoneNumber = phoneManager.getLine1Number();

            StockDBManager stock = new StockDBManager(this);
            stock.insert(intCode, name, lot, intQty, dateTime, phoneNumber);

            message(getString(R.string.stock_survey_saved));
            finish();
        }
        else{
            message(getString(R.string.code_not_found));
        }
    }

    public void message(String toastString) {
        Toast.makeText(getApplicationContext(), toastString, Toast.LENGTH_LONG).show();
    }

    public void backMain(View view) {
        finish();
    }
}
