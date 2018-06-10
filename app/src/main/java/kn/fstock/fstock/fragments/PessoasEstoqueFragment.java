package kn.fstock.fstock.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import kn.fstock.fstock.R;
import kn.fstock.fstock.models.Estoque;
import kn.fstock.fstock.models.Pessoa;

public class PessoasEstoqueFragment extends Fragment {

    private Estoque estoque;
    private Pessoa pessoa;

    public PessoasEstoqueFragment() {
        // Required empty public constructor
    }

    public static PessoasEstoqueFragment newInstance(Pessoa pessoa, Estoque estoque) {
        PessoasEstoqueFragment fragment = new PessoasEstoqueFragment();
        fragment.pessoa = pessoa;
        fragment.estoque = estoque;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pessoas_estoque, container, false);
    }

}
