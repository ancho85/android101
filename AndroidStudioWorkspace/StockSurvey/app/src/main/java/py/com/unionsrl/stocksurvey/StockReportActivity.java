package py.com.unionsrl.stocksurvey;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_report);

        datos = new Stock[] {
                new Stock(1, "test", "a", 1),
                new Stock(2, "test2", "ab", 2)
        };

        class AdaptadorStock extends ArrayAdapter {
            ActionBarActivity context;

            AdaptadorStock(ActionBarActivity context) {
                super(context, R.layout.listitem_stock, datos);
                this.context = context;
            }

            public View getView(int position, View convertView, ViewGroup parent) {
                LayoutInflater inflater = context.getLayoutInflater();
                View item = inflater.inflate(R.layout.listitem_stock, null);
                TextView tvCode = (TextView) item.findViewById(R.id.txtv_Code);
                TextView tvName = (TextView) item.findViewById(R.id.txtv_Name);
                TextView tvLot = (TextView) item.findViewById(R.id.txtv_Lot);
                TextView tvQty = (TextView) item.findViewById(R.id.txtv_Qty);
                TextView tvDatetime = (TextView) item.findViewById(R.id.txtv_Datetime);
                tvCode.setText(datos[position].getCode().toString());
                tvName.setText(datos[position].getName());
                tvLot.setText(datos[position].getLot());
                tvQty.setText(datos[position].getQty().toString());
                tvDatetime.setText(datos[position].getDateTime());
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
}
