package kn.fstock.fstock.Adapters;

import android.app.DatePickerDialog;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import kn.fstock.fstock.ApiFstock;
import kn.fstock.fstock.R;
import kn.fstock.fstock.models.Item;
import kn.fstock.fstock.models.Produto;
import kn.fstock.fstock.models.ProdutoVencer;
import kn.fstock.fstock.utils.SharedPreferencesUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AVencerAdapter extends RecyclerView.Adapter<AVencerAdapter.ViewHolder> {

    private final List<ProdutoVencer> mValues;

    public AVencerAdapter(List<ProdutoVencer> produtos) {
        this.mValues = produtos;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_produto_a_vencer, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.vencimento.setText(holder.mItem.getDateFormatada());
        holder.nome.setText(mValues.get(position).getNome());

        holder.remover.setOnClickListener(v -> {
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public void addItem(ProdutoVencer item){
        mValues.add(item);
        notifyDataSetChanged();
    }

    public void addItem(List<ProdutoVencer> item){
        mValues.addAll(item);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView vencimento;
        public final TextView nome;
        public final ImageButton remover;
        public ProdutoVencer mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            vencimento = view.findViewById(R.id.vencimento);
            nome = view.findViewById(R.id.nome);
            remover = view.findViewById(R.id.btn_remover);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + nome.getText() + "'";
        }
    }
}
