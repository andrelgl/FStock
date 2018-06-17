package kn.fstock.fstock.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

import kn.fstock.fstock.R;
import kn.fstock.fstock.models.Produto;

public class QuantidadeAdapter extends RecyclerView.Adapter<QuantidadeAdapter.ViewHolder> {

    private final List<Produto> mValues;

    public QuantidadeAdapter(List<Produto> produtos) {
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
        holder.atual.setText(holder.mItem.getItems().size()+"");
        holder.min.setText(holder.mItem.getQtd_min()+"");
        holder.max.setText(holder.mItem.getQtd_max()+"");
        holder.mContentView.setText(mValues.get(position).getNome());
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public void addItem(Produto item){
        mValues.add(item);
        notifyDataSetChanged();
    }

    public void addItem(List<Produto> item){
        mValues.addAll(item);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView atual;
        public final TextView min;
        public final TextView max;
        public final TextView mContentView;
        public Produto mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            atual = view.findViewById(R.id.item_atual);
            min = view.findViewById(R.id.item_min);
            max = view.findViewById(R.id.item_max);
            mContentView = view.findViewById(R.id.content);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
