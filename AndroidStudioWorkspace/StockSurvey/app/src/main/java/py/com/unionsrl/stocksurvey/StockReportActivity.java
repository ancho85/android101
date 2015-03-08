package py.com.unionsrl.stocksurvey;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;


public class StockReportActivity extends ActionBarActivity {

    private Stock[] datos = new Stock[25];
    SQLiteDatabase db;

    static class ViewHolder {
        TextView tvCode;
        TextView tvName;
        TextView tvLot;
        TextView tvQty;
        TextView tvDatetime;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_report);

        datos = getDataFromDatabase();

        class AdaptadorStock extends ArrayAdapter {
            ActionBarActivity context;

            AdaptadorStock(ActionBarActivity context) {
                super(context, R.layout.listitem_stock, datos);
                this.context = context;
            }

            public View getView(int position, View convertView, ViewGroup parent) {
                ViewHolder holder;
                View item = convertView;
                if (item == null) {
                    LayoutInflater inflater = context.getLayoutInflater();
                    item = inflater.inflate(R.layout.listitem_stock, parent, false);
                    holder = new ViewHolder();
                    holder.tvCode = (TextView) item.findViewById(R.id.txtv_Code);
                    holder.tvName = (TextView) item.findViewById(R.id.txtv_Name);
                    holder.tvLot = (TextView) item.findViewById(R.id.txtv_Lot);
                    holder.tvQty = (TextView) item.findViewById(R.id.txtv_Qty);
                    holder.tvDatetime = (TextView) item.findViewById(R.id.txtv_Datetime);
                    item.setTag(holder);
                }
                else {
                    holder = (ViewHolder) item.getTag();
                }
                holder.tvCode.setText(datos[position].getCode().toString());
                holder.tvName.setText(datos[position].getName());
                holder.tvLot.setText(datos[position].getLot());
                holder.tvQty.setText(datos[position].getQty().toString());
                holder.tvDatetime.setText(datos[position].getDateTime());
                return (item);
            }
        }

        AdaptadorStock adaptador = new AdaptadorStock(this);
        ListView lstStock = (ListView) findViewById(R.id.lst_Stock);
        lstStock.setAdapter(adaptador);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_stock_report, menu);
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

    public void backMain(View view) {
        finish();
    }

    private Stock[] getDataFromDatabase() {
        StockSQLiteHelper sdb = new StockSQLiteHelper(this, "DBStockSurvey.sqlite", null, 1);
        db = sdb.getReadableDatabase();

        String[] campos = new String[] { "code", "name", "lot", "qty", "datetime" };
        Stock[] Survey;

        Cursor c = db.query("Stock", campos, null, null, null, null, null);

        Survey = new Stock[c.getCount()];

        if (c.moveToFirst()) {
            int i = 0;
            do {
                String code = c.getString(0);
                String name = c.getString(1);
                String lot = c.getString(2);
                String qty = c.getString(3);
                String datetime = c.getString(4);

                Survey[i] = new Stock(Integer.valueOf(code), name, lot, Integer.valueOf(qty), datetime);
                i++;

            }
            while (c.moveToNext());
        }
        db.close();
        return Survey;
    }
}
