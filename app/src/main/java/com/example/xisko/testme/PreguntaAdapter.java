
package com.example.xisko.testme;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.xisko.testme.Pregunta;

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
        extends RecyclerView.Adapter<PreguntaAdapter.AcontecimientoViewHolder>
        implements View.OnClickListener {

    private static ArrayList<Pregunta> items;
    private View.OnClickListener listener;



    // Clase interna:
    // Se implementa el ViewHolder que se encargarÃ¡
    // de almacenar la vista del elemento y sus datos
    public static class AcontecimientoViewHolder
            extends RecyclerView.ViewHolder {

        private CardView cv;

        private TextView TextView_categoria;
        private TextView TextView_nombre;

        public AcontecimientoViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv) ;
            TextView_categoria = (TextView) itemView.findViewById(R.id.TextView_categoria);
            TextView_nombre = (TextView) itemView.findViewById(R.id.TextView_nombre);
        }

        public void AcontecimientoBind(AcontecimientoViewHolder item, int i) {
            item.TextView_categoria.setText(items.get(i).getCategoria());
            //TextView_nombre.setText(item.getEnunciado());
            //TextView_categoria.setText(item.getCategoria());
        }
    }

    // Contruye el objeto adaptador recibiendo la lista de datos
    public PreguntaAdapter(ArrayList<Pregunta> items){
        this.items= items;
    }

    // Se encarga de crear los nuevos objetos ViewHolder necesarios
    // para los elementos de la colecciÃ³n.
    // Infla la vista del layout, crea y devuelve el objeto ViewHolder
    @Override
    public AcontecimientoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card, parent, false);
        row.setOnClickListener(this);

        AcontecimientoViewHolder avh = new AcontecimientoViewHolder(row);
        return avh;
    }

    // Se encarga de actualizar los datos de un ViewHolder ya existente.
    @Override
    public void onBindViewHolder(AcontecimientoViewHolder viewHolder, int position) {


        viewHolder.TextView_categoria.setText(items.get(position).getEnunciado());
        viewHolder.TextView_nombre.setText(items.get(position).getCategoria());

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
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public void onClick(View view) {
        if(listener != null)
            listener.onClick(view);
    }
}
