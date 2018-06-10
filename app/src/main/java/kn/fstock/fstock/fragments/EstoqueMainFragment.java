package kn.fstock.fstock.fragments;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import kn.fstock.fstock.Adapters.TabsAdapter;
import kn.fstock.fstock.R;
import kn.fstock.fstock.models.Estoque;
import kn.fstock.fstock.models.Pessoa;

public class    EstoqueMainFragment extends Fragment {

    private Pessoa pessoa;
    private Estoque estoque;

    public EstoqueMainFragment() {
        // Required empty public constructor
    }

    public static EstoqueMainFragment newInstance(Pessoa pessoa, Estoque estoque) {
        EstoqueMainFragment fragment = new EstoqueMainFragment();
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
        View v =  inflater.inflate(R.layout.fragment_estoque_main, container, false);

        TabsAdapter tabsAdapter= new TabsAdapter(getActivity().getSupportFragmentManager());
        tabsAdapter.adicionar(ProdutoEstoqueFragment.newInstance(9), "Atual");
        tabsAdapter.adicionar(ProdutoEstoqueFragment.newInstance(9), "À vencer");

        ViewPager pager = v.findViewById(R.id.viewpager);
        pager.setAdapter(tabsAdapter);

        TabLayout tabs = v.findViewById(R.id.tabs);
        tabs.setupWithViewPager(pager);

        return v;
    }

}
