package kn.fstock.fstock.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import kn.fstock.fstock.Adapters.ProdutoEstoqueRecyclerViewAdapter;
import kn.fstock.fstock.ApiFstock;
import kn.fstock.fstock.R;
import kn.fstock.fstock.models.Estoque;
import kn.fstock.fstock.models.Item;
import kn.fstock.fstock.models.Produto;
import kn.fstock.fstock.utils.SharedPreferencesUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class ProdutoEstoqueFragment extends Fragment {

    @BindView(R.id.list)
    RecyclerView list;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    Unbinder unbinder;
    private Estoque estoque;
    private TipoItem tipoItem;
    private ProdutoEstoqueRecyclerViewAdapter adapter;

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    enum TipoItem {
        NORMAL,
        AVENCER
    }

    private OnListFragmentInteractionListener mListener;
    private Context context;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ProdutoEstoqueFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static ProdutoEstoqueFragment newInstance(Estoque estoque, TipoItem tipoItem) {
        ProdutoEstoqueFragment fragment = new ProdutoEstoqueFragment();
        fragment.estoque = estoque;
        fragment.tipoItem = tipoItem;
        Bundle args = new Bundle();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lista_estoque, container, false);
        unbinder = ButterKnife.bind(this, view);
        context = view.getContext();

        list.addOnScrollListener(new RecyclerView.OnScrollListener()
        {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx,int dy){
                super.onScrolled(recyclerView, dx, dy);

                if (dy >0) {
                    // Scroll Down
                    if (fab.isShown()) {
                        fab.hide();
                    }
                }
                else if (dy <0) {
                    // Scroll Up
                    if (!fab.isShown()) {
                        fab.show();
                    }
                }
            }
        });

        List<Produto> estoqueList = new ArrayList<>();
        adapter = new ProdutoEstoqueRecyclerViewAdapter(estoqueList, mListener);
        list.setAdapter(adapter);
        Call<List<Produto>> call = null;
        switch (tipoItem) {
            case NORMAL:
                call = ApiFstock.getInstance().descricaoProdutoService().listarProdutos(SharedPreferencesUtils.getUserId(getContext()), estoque.getId());
                break;
            case AVENCER:
                break;
        }

        if (call != null) {
            call.enqueue(new Callback<List<Produto>>() {
                @Override
                public void onResponse(Call<List<Produto>> call, Response<List<Produto>> response) {
                    if (response.body() != null) {

                        for (Produto produto : response.body()) {
                            ApiFstock.getInstance().descricaoProdutoService().itemListar(
                                    SharedPreferencesUtils.getUserId(getContext()),
                                    produto.getEstoque_id(),
                                    produto.getId()
                            ).enqueue(new Callback<List<Item>>() {
                                @Override
                                public void onResponse(Call<List<Item>> call, Response<List<Item>> response) {
                                    if(response.body() != null) {
                                        produto.setItems(response.body());
                                    }
                                    adapter.addItem(produto);
                                }

                                @Override
                                public void onFailure(Call<List<Item>> call, Throwable t) {
                                    t.printStackTrace();
                                }
                            });
                        }
                    }
                }

                @Override
                public void onFailure(Call<List<Produto>> call, Throwable t) {
                    t.printStackTrace();
                }
            });
        }

        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(Produto produto);
    }

    @OnClick(R.id.fab)
    public void onViewClicked() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Adicionar produto");

        View dialogView = getLayoutInflater().inflate(R.layout.dialog_text_input_produto, null);
        final EditText nome = dialogView.findViewById(R.id.nome);
        final EditText min = dialogView.findViewById(R.id.min);
        final EditText max = dialogView.findViewById(R.id.max);
        builder.setView(dialogView);

        builder.setPositiveButton("OK", (dialog, which) -> {

            Produto produto = new Produto(
                    nome.getText().toString(),
                    Double.parseDouble(min.getText().toString()),
                    Double.parseDouble(max.getText().toString())
            );

            ApiFstock.getInstance().descricaoProdutoService().criarProduto(SharedPreferencesUtils.getUserId(context), estoque.getId(),produto).enqueue(new Callback<Produto>() {
                @Override
                public void onResponse(Call<Produto> call, Response<Produto> response) {
                    Produto add = response.body();
                    add.setItems(new ArrayList<>());
                    adapter.addItem(response.body());
                }

                @Override
                public void onFailure(Call<Produto> call, Throwable t) {

                }
            });
            dialog.dismiss();
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
        builder.show();
    }


}
