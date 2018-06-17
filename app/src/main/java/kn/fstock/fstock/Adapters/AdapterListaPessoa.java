package kn.fstock.fstock.Adapters;


import android.app.ProgressDialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import kn.fstock.fstock.ApiFstock;
import kn.fstock.fstock.R;
import kn.fstock.fstock.models.Estoque;
import kn.fstock.fstock.models.Pessoa;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterListaPessoa extends RecyclerView.Adapter<AdapterListaPessoa.ViewHolder>{

    public List<Pessoa> getPessoas() {
        return pessoas;
    }

    List<Pessoa> pessoas;
    private Estoque estoque;
    Context context;

    public AdapterListaPessoa(List<Pessoa> pessoas, Estoque estoque, Context context) {
        this.pessoas = pessoas;
        this.estoque = estoque;
        this.context = context;
    }

    public void addItem(Pessoa pessoa){
        pessoas.add(pessoa);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_pessoa_estoque, parent, false);

        ViewHolder holder = new ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final Pessoa item = pessoas.get(position);
        holder.textView.setText(item.getNome());
        holder.imageView.setVisibility(item.getId() == estoque.getPessoa_admin() ? View.VISIBLE : View.GONE);
        holder.buton.setVisibility(item.getId() == estoque.getPessoa_admin() ? View.GONE : View.VISIBLE);

        holder.buton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final ProgressDialog dialog = ProgressDialog.show(context, "",
                        "Carregando...", true, false);
                    ApiFstock.getInstance().descricaoEstoqueService().deletarPessoaEstoque(item.getId(), estoque.getId() ).enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            dialog.dismiss();
                            pessoas.remove(position);
                            notifyDataSetChanged();
                        }
                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                        }
                    });

            }
        });
    }

    @Override
    public int getItemCount() {
        return pessoas.size();
    }

    public void setList(List<Pessoa> body) {
        this.pessoas = body;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public View view;
        public TextView textView;
        public ImageView imageView;
        public ImageButton buton;

        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            this.textView = itemView.findViewById(R.id.nome_pessoa);
            this.buton = itemView.findViewById(R.id.close_button);
            this.imageView = itemView.findViewById(R.id.img_admin);
        }


    }

}
