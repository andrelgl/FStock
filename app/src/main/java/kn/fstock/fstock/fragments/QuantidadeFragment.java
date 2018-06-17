package kn.fstock.fstock.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import kn.fstock.fstock.Adapters.AVencerAdapter;
import kn.fstock.fstock.Adapters.QuantidadeAdapter;
import kn.fstock.fstock.ApiFstock;
import kn.fstock.fstock.R;
import kn.fstock.fstock.models.Estoque;
import kn.fstock.fstock.models.Produto;
import kn.fstock.fstock.utils.SharedPreferencesUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class QuantidadeFragment extends Fragment {


    @BindView(R.id.rv)
    RecyclerView rv;
    Unbinder unbinder;
    private Estoque estoque;

    public QuantidadeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_quantidade, container, false);
        unbinder = ButterKnife.bind(this, view);
        gerarLista();
        return view;
    }

    public static Fragment newInstance(Estoque estoque) {
        QuantidadeFragment fragment = new QuantidadeFragment();
        fragment.estoque = estoque;
        return fragment;
    }

    private void gerarLista() {

        QuantidadeAdapter adapter = new QuantidadeAdapter(new ArrayList<>());
        rv.setAdapter(adapter);

        ApiFstock.getInstance().descricaoEstoqueService().alertQuantidade(
                SharedPreferencesUtils.getUserId(getContext()),
                estoque.getId()
        ).enqueue(new Callback<List<Produto>>() {
            @Override
            public void onResponse(Call<List<Produto>> call, Response<List<Produto>> response) {
                if (response.body() != null) {
                    adapter.addItem(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Produto>> call, Throwable t) {

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
