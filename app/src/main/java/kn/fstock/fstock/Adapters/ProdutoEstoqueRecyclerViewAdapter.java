package kn.fstock.fstock.Adapters;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import kn.fstock.fstock.ApiFstock;
import kn.fstock.fstock.R;
import kn.fstock.fstock.fragments.ProdutoEstoqueFragment.OnListFragmentInteractionListener;
import kn.fstock.fstock.models.Item;
import kn.fstock.fstock.models.Produto;
import kn.fstock.fstock.utils.SharedPreferencesUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProdutoEstoqueRecyclerViewAdapter extends RecyclerView.Adapter<ProdutoEstoqueRecyclerViewAdapter.ViewHolder> {

    private final List<Produto> mValues;
    private final OnListFragmentInteractionListener mListener;

    public ProdutoEstoqueRecyclerViewAdapter(List<Produto> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_produto, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.atual.setText(holder.mItem.getItems().size()+"");
        holder.min.setText(holder.mItem.getQtd_min()+"");
        holder.max.setText(holder.mItem.getQtd_max()+"");
        holder.mContentView.setText(mValues.get(position).getNome());

        holder.add.setOnClickListener((View v) -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(holder.mView.getContext());
            View dialogView = LayoutInflater.from(holder.mView.getContext()).inflate(R.layout.dialog_text_input_item, null);
            AlertDialog mAlertDialog =  builder.setTitle("Adicionar item")
                    .setView(dialogView)
                    .setPositiveButton("Ok",null)
                    .create();
            EditText editText = dialogView.findViewById(R.id.editText);

            Calendar myCalendar = Calendar.getInstance();

            mAlertDialog.setOnShowListener(dialog -> {
                Button b = mAlertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                b.setOnClickListener(view -> {
                    if(editText.getText().length()>0){

                        Item item = new Item();
                        item.setQtd(1);
                        item.setProduto_id(holder.mItem.getId());
                        item.setDt_validade(myCalendar.getTime());
                        ApiFstock.getInstance().descricaoProdutoService().criarItem(
                                SharedPreferencesUtils.getUserId(holder.mView.getContext()),
                                holder.mItem.getEstoque_id(),
                                holder.mItem.getId(),
                                item
                        ).enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                ApiFstock.getInstance().descricaoProdutoService().itemListar(SharedPreferencesUtils.getUserId(holder.mView.getContext()),
                                        holder.mItem.getEstoque_id(),
                                        holder.mItem.getId()).enqueue(new Callback<List<Item>>() {
                                    @Override
                                    public void onResponse(Call<List<Item>> call, Response<List<Item>> response) {
                                        holder.mItem.setItems(response.body());
                                        holder.atual.setText(Integer.toString(holder.mItem.getItems().size()));
                                        mAlertDialog.dismiss();
                                    }
                                    @Override
                                    public void onFailure(Call<List<Item>> call, Throwable t) {
                                        mAlertDialog.dismiss();
                                    }
                                });
                            }
                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {
                                mAlertDialog.dismiss();
                            }
                        });
                    }
                });
            });

            DatePickerDialog.OnDateSetListener date = (view, year, monthOfYear, dayOfMonth) -> {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                String myFormat = "dd/MM/yyyy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, new Locale("pt","BR"));

                editText.setText(sdf.format(myCalendar.getTime()));
            };

            editText.setOnClickListener(v1 -> new DatePickerDialog(holder.mView.getContext(), date, myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH)).show());
            mAlertDialog.show();
        });

        holder.remover.setOnClickListener(v -> {

            CharSequence[] items = new CharSequence[ holder.mItem.getItems().size()];
            for (int i = 0; i < holder.mItem.getItems().size(); i++) {
                items[i] =  holder.mItem.getItems().get(i).toString();
            }

            new AlertDialog.Builder(holder.mView.getContext())
                    .setTitle("Selecione o item a ser excluido")
                    .setItems(items, (dialog, which) ->
                            ApiFstock.getInstance().descricaoProdutoService().deletarItem(SharedPreferencesUtils.getUserId(holder.mView.getContext()),
                                    holder.mItem.getEstoque_id(),
                                    holder.mItem.getId(),
                                    holder.mItem.getItems().get(which).getId()).enqueue(new Callback<Void>() {
                                @Override
                                public void onResponse(Call<Void> call, Response<Void> response) {
                                    holder.mItem.getItems().remove(which);
                                    notifyDataSetChanged();
                                }
                                @Override
                                public void onFailure(Call<Void> call, Throwable t) {

                                }
                            })).show();
        });
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
        public final ImageButton add;
        public final ImageButton remover;
        public Produto mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            atual = view.findViewById(R.id.item_atual);
            min = view.findViewById(R.id.item_min);
            max = view.findViewById(R.id.item_max);
            mContentView = view.findViewById(R.id.content);
            add = view.findViewById(R.id.btn_add);
            remover = view.findViewById(R.id.btn_remover);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
