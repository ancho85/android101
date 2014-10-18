package com.example.blockdenotas;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends Activity {

    private Titular[] datos = new Titular[25];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        datos = new Titular[] {
                new Titular("Título 1", "Subtítulo largo 1", 3),
                new Titular("Título 2", "Subtítulo largo 2", 1),
                new Titular("Título 3", "Subtítulo largo 3", 2),
                new Titular("Título 4", "Subtítulo largo 4", 1),
                new Titular("Título 5", "Subtítulo largo 5", 3) 
                };
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onClickAdd(View view) {
        Intent otra = new Intent(this, NuevaTarea.class);
        otra.putExtra("valor", "Titulo");
        startActivity(otra);
    }
}
