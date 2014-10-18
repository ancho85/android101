package com.example.blockdenotas;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

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

        class AdaptadorTitulares extends ArrayAdapter {
            Activity context;

            AdaptadorTitulares(Activity context) {
                super(context, R.layout.listitem_titular, datos); // se hace referencia al nuevo layout xml para el ListView
                this.context = context;
            }

            public View getView(int position, View convertView, ViewGroup parent) {
                LayoutInflater inflater = context.getLayoutInflater();
                View item = inflater.inflate(R.layout.listitem_titular, null);
                TextView lblTitulo = (TextView) item.findViewById(R.id.LblTitulo);
                lblTitulo.setText(datos[position].getTitulo());
                TextView lblSubtitulo = (TextView) item.findViewById(R.id.LblSubTitulo);
                lblSubtitulo.setText(datos[position].getSubtitulo());
                return (item);
            }
        }
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
