
package com.example.xisko.testme;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Clase para almacenar el adaptador con los datos
 * de los acontecimientos que va a mostrar
 * el RecyclerView
 *
 * Hay que aÃ±adir al proyecto la siguiente
 * dependencia en el archivo /app/build.gradle
 * 'com.android.support:recyclerview-v7:+
 */

public class PreguntaAdapter
        extends RecyclerView.Adapter<PreguntaAdapter.PreguntaViewHolder>
        implements View.OnClickListener {

    private ArrayList<Pregunta> items;
    private View.OnClickListener listener;

    // Clase interna:
    // Se implementa el ViewHolder que se encargará¡
    // de almacenar la vista del elemento y sus datos
    public static class PreguntaViewHolder
            extends RecyclerView.ViewHolder {

        private TextView TextView_categoria;
        private TextView TextView_nombre;

        public PreguntaViewHolder(View itemView) {
            super(itemView);
            TextView_categoria = (TextView) itemView.findViewById(R.id.TextView_categoria);
            TextView_nombre = (TextView) itemView.findViewById(R.id.TextView_nombre);
        }

        public void PreguntaBind(Pregunta item) {
            TextView_categoria.setText(item.getEnunciado());
            TextView_nombre.setText(item.getCategoria());
        }
    }

    // Contruye el objeto adaptador recibiendo la lista de datos
    public PreguntaAdapter(@NonNull ArrayList<Pregunta> items) {
        this.items = items;
    }

    // Se encarga de crear los nuevos objetos ViewHolder necesarios
    // para los elementos de la colecciÃ³n.
    // Infla la vista del layout, crea y devuelve el objeto ViewHolder
    @Override
    public PreguntaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row, parent, false);
        row.setOnClickListener(this);

        PreguntaViewHolder avh = new PreguntaViewHolder(row);
        return avh;
    }

    // Se encarga de actualizar los datos de un ViewHolder ya existente.
    @Override
    public void onBindViewHolder(PreguntaViewHolder viewHolder, int position) {
        Pregunta item = items.get(position);
        viewHolder.PreguntaBind(item);
    }

    // Indica el nÃºmero de elementos de la colecciÃ³n de datos.
    @Override
    public int getItemCount() {
        return items.size();
    }

    // Asigna un listener al elemento
    public void setOnClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View view) {
        if(listener != null)
            listener.onClick(view);
    }
}
