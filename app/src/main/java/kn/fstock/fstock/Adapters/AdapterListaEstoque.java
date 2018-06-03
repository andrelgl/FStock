package kn.fstock.fstock.Adapters;


import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

import kn.fstock.fstock.R;

public class AdapterListaEstoque extends RecyclerView.Adapter<AdapterListaEstoque.ViewHolder>{

    List<String> estoques;
    Context context;

    public AdapterListaEstoque(List<String> estoques, Context context) {
        this.estoques = estoques;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.ifragment_istsingles, parent, false);

        ViewHolder holder = new ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        String item = estoques.get(position);
        holder.textView.setText(item);
        holder.buton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                estoques.remove(position);
                notifyDataSetChanged();
            }
        });
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Cria a activity sua anta
                //Intent intent = new Intent(context, nomedaactivitinova.class);
                //context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return estoques.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public View view;
        public TextView textView;
        public ImageButton buton;

        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            this.textView = itemView.findViewById(R.id.nome_estoque);
            this.buton = itemView.findViewById(R.id.close_button);
        }


    }

}
