package com.example.blockdenotas;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.TextView;
import android.widget.ToggleButton;

public class MainActivity extends Activity implements OnQueryTextListener{

    private Titular[] datos = new Titular[25];
    SQLiteDatabase db;
    Boolean sortByPriority = false;
    private Context mainContext; // para usarlo en el setOnItemClickListener de mi ListView
	
	String mCurFilter; // If non-null, this is the current filter the user has provided.

    static class ViewHolder {
        TextView lblTitulo;
        TextView lblSubtitulo;
        LinearLayout lilayTareas;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainContext = this;
        // showTitulares();
        // No hace falta, se usa el OnResume nomas que
        // se llama si o si luego del OnCreate y luego del Intent

        final ToggleButton tglPriority = (ToggleButton) findViewById(R.id.BtnPriority);
        tglPriority.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                sortByPriority = false;
                if (isChecked) {
                    sortByPriority = true;
                }
                showTitulares();
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        showTitulares();
    }

    private void showTitulares() {
        datos = getDataFromDatabase();

        class AdaptadorTitulares extends ArrayAdapter<Object> {
            Activity context;

            AdaptadorTitulares(Activity context) {
                super(context, R.layout.listitem_titular, datos); // se hace referencia al nuevo layout xml para el ListView
                this.context = context;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {

                // A ViewHolder keeps references to children views to avoid unneccessary calls
                // to findViewById() on each row.
                ViewHolder holder;
                // When convertView is not null, we can reuse it directly, there is no need
                // to reinflate it. We only inflate a new View when the convertView supplied
                // by ListView is null.
                View item = convertView;

                if (item == null) {
                    LayoutInflater inflater = context.getLayoutInflater();
                    item = inflater.inflate(R.layout.listitem_titular, parent, false);

                    holder = new ViewHolder();
                    holder.lblTitulo = (TextView) item.findViewById(R.id.LblTitulo);
                    holder.lblSubtitulo = (TextView) item.findViewById(R.id.LblSubTitulo);
                    holder.lilayTareas = (LinearLayout) item.findViewById(R.id.LilayTareas);
                    item.setTag(holder);
                }
                else {
                    holder = (ViewHolder) item.getTag();
                }

                holder.lblTitulo.setText(datos[position].getTitulo());
                holder.lblSubtitulo.setText(datos[position].getSubtitulo());
                holder.lblTitulo.setTextColor(getResources().getColor(datos[position].getPrioridadColor()));

                if (position % 2 == 0) {
                    holder.lilayTareas.setBackgroundResource(R.drawable.shapeblue);
                }
                else {
                    holder.lilayTareas.setBackgroundResource(R.drawable.shapedarkblue);
                }
                return (item);
            }
        }

        AdaptadorTitulares adaptador = new AdaptadorTitulares(this);
        ListView lstOpciones = (ListView) findViewById(R.id.LstOpciones);
        lstOpciones.setAdapter(adaptador);

        lstOpciones.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent editMode = new Intent(mainContext, NuevaTarea.class);
                editMode.putExtra("titulo", datos[position].getTitulo());
                editMode.putExtra("subtitulo", datos[position].getSubtitulo());
                editMode.putExtra("prioridad", datos[position].getPrioridad());
                editMode.putExtra("editMode", true);
                startActivity(editMode);
            }

        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
		// Place an action bar item for searching.
		MenuItem item = menu.add("Search");
		item.setIcon(android.R.drawable.ic_menu_search);
		item.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		SearchView sv = new SearchView(this);
		sv.setOnQueryTextListener(this);
		sv.setQueryHint("titulo/subtitulo");
		item.setActionView(sv);
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

    private Titular[] getDataFromDatabase() {
        TareasSQLiteHelper tdbh = TareasSQLiteHelper.getInstance(this);
        db = tdbh.getReadableDatabase(); // establecer base de datos en modo lectura

        String[] campos = new String[] { "titulo", "subtitulo", "prioridad" };
        Titular[] Tareas;

        String orderBy = null; // new String("titulo");
        if (sortByPriority){
            orderBy = new String("prioridad DESC");
        }
        
        String filterByFields = null; 
        String[] filterByValues = null;
        
        if (!TextUtils.isEmpty(mCurFilter)){
        	filterByFields = "titulo LIKE ? OR subtitulo LIKE ?";
        	filterByValues = new String[] {"%"+ mCurFilter+ "%", "%"+ mCurFilter+ "%"};
        }
        Cursor c = db.query("Tarea", campos, filterByFields, filterByValues, null, null, orderBy);

        Tareas = new Titular[c.getCount()];

        // Nos aseguramos de que existe al menos un registro
        if (c.moveToFirst()) {
            // Recorremos el cursor hasta que no haya mas registros
            int i = 0;
            do {
                String titulo = c.getString(0);
                String subtitulo = c.getString(1);
                String prioridad = c.getString(2);

                Tareas[i] = new Titular(titulo, subtitulo, Integer.valueOf(prioridad));
                i++;

            }
            while (c.moveToNext());
        }

        return Tareas;
    }

	@Override
	public boolean onQueryTextChange(String newText) {
		mCurFilter = !TextUtils.isEmpty(newText) ? newText : null;
		showTitulares();
		return false;
	}

	@Override
	public boolean onQueryTextSubmit(String arg0) {
		// Don't care about this.
		return false;
	}
}
