package com.heyfood.heyfoodapp.prato.ui;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.heyfood.heyfoodapp.R;
import com.heyfood.heyfoodapp.prato.dominio.Prato;

import java.util.List;

/**
 * Created by andre.csassis on 27/06/2019.
 */

public class AdapterPratos extends RecyclerView.Adapter<AdapterPratos.MyViewHolder> {
    private List<Prato> listaPrato;

    public AdapterPratos(List<Prato> listaPrato) {
        this.listaPrato = listaPrato;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_lista_prato, parent, false);
        return new MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Prato prato = listaPrato.get(position);
        holder.nomePrato.setText(prato.getNome());
        holder.preco.setText("R$ " + prato.getPreco().toString().replace(".",","));
    }

    @Override
    public int getItemCount() {
        return listaPrato.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView nomePrato;
        TextView preco;

        public MyViewHolder(View itemView){
            super(itemView);
            nomePrato = itemView.findViewById(R.id.textNomePratoId);
            preco = itemView.findViewById(R.id.textPrecoId);
        }
    }
}
