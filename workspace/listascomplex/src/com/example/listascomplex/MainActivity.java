package com.example.listascomplex;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity {

    private Titular[] datos = new Titular[25];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        datos = new Titular[] { new Titular("Título 1", "Subtítulo largo 1"),
                new Titular("Título 2", "Subtítulo largo 2"),
                new Titular("Título 3", "Subtítulo largo 3"),
                new Titular("Título 4", "Subtítulo largo 4"),
                new Titular("Título 5", "Subtítulo largo 5") };

        // ...
        // ...

        AdaptadorTitulares adaptador = new AdaptadorTitulares(this);

        ListView lstOpciones = (ListView) findViewById(R.id.LstOpciones);

        lstOpciones.setAdapter(adaptador);

        lstOpciones.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> a, View v, int position,
                    long id) {

                // Alternativa 1:
                String opcionSeleccionada = ((Titular) a.getAdapter().getItem(
                        position)).getTitulo();

                TextView lblEtiqueta = (TextView) findViewById(R.id.LblEtiqueta);
                lblEtiqueta.setText("Opción seleccionada: "
                        + opcionSeleccionada);
            }
        });

    }

    class AdaptadorTitulares extends ArrayAdapter {

        Activity context;

        AdaptadorTitulares(Activity context) {
            super(context, R.layout.listitem_titular, datos);
            this.context = context;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = context.getLayoutInflater();
            View item = inflater.inflate(R.layout.listitem_titular, null);

            TextView lblTitulo = (TextView) item.findViewById(R.id.LblTitulo);
            lblTitulo.setText(datos[position].getTitulo());

            TextView lblSubtitulo = (TextView) item
                    .findViewById(R.id.LblSubTitulo);
            lblSubtitulo.setText(datos[position].getSubtitulo());

            return (item);
        }
    }

}
