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
import kn.fstock.fstock.ApiFstock;
import kn.fstock.fstock.R;
import kn.fstock.fstock.models.Estoque;
import kn.fstock.fstock.models.ProdutoVencer;
import kn.fstock.fstock.utils.SharedPreferencesUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class AVencerFragment extends Fragment {


    @BindView(R.id.rv)
    RecyclerView rv;
    Unbinder unbinder;
    private Estoque estoque;

    public AVencerFragment() {
        // Required empty public constructor
    }

    public static AVencerFragment newInstance(Estoque estoque) {
        AVencerFragment fragment = new AVencerFragment();
        fragment.estoque = estoque;
        Bundle args = new Bundle();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_avencer, container, false);
        unbinder = ButterKnife.bind(this, view);
        gerarLista();
        return view;
    }

    private void gerarLista() {

        AVencerAdapter adapter = new AVencerAdapter(new ArrayList<>());
        rv.setAdapter(adapter);

        ApiFstock.getInstance().descricaoEstoqueService().alertVencimento(
                SharedPreferencesUtils.getUserId(getContext()),
                estoque.getId()
        ).enqueue(new Callback<List<ProdutoVencer>>() {
            @Override
            public void onResponse(Call<List<ProdutoVencer>> call, Response<List<ProdutoVencer>> response) {
                if(response.body() != null){
                    adapter.addItem(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<ProdutoVencer>> call, Throwable t) {

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
