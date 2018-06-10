package kn.fstock.fstock.Adapters;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ActionProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.io.IOException;
import java.io.PrintStream;
import java.util.List;

import kn.fstock.fstock.Activities.PrincipalActivity;
import kn.fstock.fstock.ApiFstock;
import kn.fstock.fstock.R;
import kn.fstock.fstock.models.Estoque;
import kn.fstock.fstock.utils.SharedPreferencesUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterListaEstoque extends RecyclerView.Adapter<AdapterListaEstoque.ViewHolder>{

    List<Estoque> estoques;
    Context context;

    public AdapterListaEstoque(List<Estoque> estoques, Context context) {
        this.estoques = estoques;
        this.context = context;
    }

    public void addItem(Estoque estoque){
        estoques.add(estoque);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_estoque, parent, false);

        ViewHolder holder = new ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final Estoque item = estoques.get(position);
        holder.textView.setText(item.getNome());

        holder.buton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final ProgressDialog dialog = ProgressDialog.show(context, "",
                        "Carregando...", true, false);
                    ApiFstock.getInstance().descricaoEstoqueService().deletarEstoque(SharedPreferencesUtils.getUserId(context),item.getId()).enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            dialog.dismiss();
                            estoques.remove(position);
                            notifyDataSetChanged();
                        }
                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                        }
                    });

            }
        });
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PrincipalActivity.class);
                intent.putExtra("estoque_id",item.getId());
                context.startActivity(intent);
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
