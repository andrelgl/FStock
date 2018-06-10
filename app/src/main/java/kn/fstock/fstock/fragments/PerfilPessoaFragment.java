package kn.fstock.fstock.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import kn.fstock.fstock.R;
import kn.fstock.fstock.models.Pessoa;

public class PerfilPessoaFragment extends Fragment {

    private Pessoa pessoa;

    public PerfilPessoaFragment() {
        // Required empty public constructor
    }

    public static PerfilPessoaFragment newInstance(Pessoa pessoa) {
        PerfilPessoaFragment fragment = new PerfilPessoaFragment();
        fragment.pessoa = pessoa;
        Bundle args = new Bundle();
        fragment.setArguments(args);
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
        return inflater.inflate(R.layout.fragment_perfil_pessoa, container, false);
    }

}
