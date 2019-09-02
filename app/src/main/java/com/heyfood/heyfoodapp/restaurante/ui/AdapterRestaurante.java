package com.heyfood.heyfoodapp.restaurante.ui;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.heyfood.heyfoodapp.R;
import com.heyfood.heyfoodapp.restaurante.dominio.Restaurante;
import java.util.List;


public class AdapterRestaurante extends RecyclerView.Adapter<AdapterRestaurante.MyViewHolder> {
    private List<Restaurante> listaRestaurante;

    public AdapterRestaurante(List<Restaurante> lista){
        this.listaRestaurante = lista;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_lista_restaurante, parent, false);

        return new MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Restaurante restaurante = listaRestaurante.get(position);
        holder.nomeRestaurante.setText(restaurante.getNome());
        holder.telefone.setText(restaurante.getContato().getTelefone());
        holder.bairro.setText(restaurante.getEndereco().getBairro());
    }

    @Override
    public int getItemCount() {
        return listaRestaurante.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView nomeRestaurante;
        TextView bairro;
        TextView telefone;

        public MyViewHolder(View itemView){
            super(itemView);
            nomeRestaurante = itemView.findViewById(R.id.textNomeRestauranteId);
            bairro = itemView.findViewById(R.id.textBairroRestauranteId);
            telefone = itemView.findViewById(R.id.textTelefoneRestauranteId);
        }
    }
}
