package py.com.unionsrl.stocksurvey;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    public void message(String toastString) {
        Toast.makeText(getApplicationContext(), toastString, Toast.LENGTH_LONG).show();
    }

    public void onClickSyncMasters(View view) {
        syncMasters();
    }

    public void onClickNewStockSurvey(View view) {
        Intent newSurvey = new Intent(this, StockSurveyActivity.class);
        startActivity(newSurvey);
    }

    public void onClickShowStockReport(View view) {
        Intent newReport = new Intent(this, StockReportActivity.class);
        startActivity(newReport);
    }

    public void syncMasters() {
        final ProgressDialog progressDialog = ProgressDialog.show(this, getString(R.string.please_wait), getString(R.string.requesting_masters_data));

        String URL = "http://www.geotechpy.com/stocksurvey/RESTful/";
        JSONObject obj = new JSONObject();
        try {
            obj.put("all", "yes");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(Request.Method.POST, URL, obj, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                message(getString(R.string.sync_success));
                progressDialog.cancel();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.cancel();
                message(webServiceErrorParser(getApplicationContext(), error));
            }
        });

        queue.add(jsonArrayRequest);
    }

    public static String webServiceErrorParser(Context ctx, VolleyError error) {
        String messageError = "";
        NetworkResponse response = error.networkResponse;
        if (response != null && response.data != null) {
            String json = new String(response.data);
            try {
                JSONObject responseWS = new JSONObject(json);
                JSONArray messagesObject = new JSONArray(responseWS.getString("messages"));
                for (int i = 0; i < messagesObject.length(); i++) {
                    JSONObject message = (JSONObject) messagesObject.get(i);
                    messageError += message.getString("dsc") + "\n";
                }
                if (messageError.length() <= 0) {
                    messageError = ctx.getString(R.string.unknown_error);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (messageError.length() <= 0) {
            messageError = ctx.getString(R.string.ws_comm_error);
        }

        return messageError;
    }
}
