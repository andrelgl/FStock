package kn.fstock.fstock.fragments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.security.spec.PSSParameterSpec;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import kn.fstock.fstock.Activities.ListaEstoqueActivity;
import kn.fstock.fstock.Adapters.AdapterListaEstoque;
import kn.fstock.fstock.Adapters.AdapterListaPessoa;
import kn.fstock.fstock.ApiFstock;
import kn.fstock.fstock.R;
import kn.fstock.fstock.models.Estoque;
import kn.fstock.fstock.models.Pessoa;
import kn.fstock.fstock.models.PessoaEstoque;
import kn.fstock.fstock.utils.SharedPreferencesUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PessoasEstoqueFragment extends Fragment {

    @BindView(R.id.recycler_pessoa)
    RecyclerView recyclerPessoa;
    @BindView(R.id.fab)
    FloatingActionButton fab;

    Unbinder unbinder;
    private Estoque estoque;
    private Pessoa pessoa;
    private AdapterListaPessoa adapterListaPessoa;

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
        View view = inflater.inflate(R.layout.fragment_pessoas_estoque, container, false);
        unbinder = ButterKnife.bind(this, view);
        configureList();
        return view;
    }

    public void configureList(){
        adapterListaPessoa = new AdapterListaPessoa(new ArrayList<>(), estoque, getActivity());
        recyclerPessoa.setAdapter(adapterListaPessoa);

        ApiFstock.getInstance().descricaoEstoqueService().listarPessoaEstoque(SharedPreferencesUtils.getUserId(getActivity()), estoque.getId()).enqueue(new Callback<List<Pessoa>>() {
            @Override
            public void onResponse(Call<List<Pessoa>> call, Response<List<Pessoa>> response) {
                if(response.body()!=null)
                for (Pessoa pessoa1 : response.body()) {
                    adapterListaPessoa.addItem(pessoa1);
                }
            }
            @Override
            public void onFailure(Call<List<Pessoa>> call, Throwable t) {
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.fab)
    public void onViewClicked() {
        showNewPessoaDialog();
    }

    public void showNewPessoaDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Adicionar Pessoa");

        View dialogView = getLayoutInflater().inflate(R.layout.dialog_text_input, null);
        final EditText input = dialogView.findViewById(R.id.input);
        builder.setView(dialogView);

        builder.setPositiveButton("OK", (dialog, which) -> {
            dialog.dismiss();
            ApiFstock.getInstance().descricaoPessoaService().listarPessoasEstoque().enqueue(new Callback<List<Pessoa>>() {
                @Override
                public void onResponse(Call<List<Pessoa>> call, Response<List<Pessoa>> response) {

                    Pessoa add = null;

                    for (Pessoa pessoa1 : response.body()) {
                        if(pessoa1.getEmail().equals(input.getText().toString())){
                            boolean jaNaLista = false;
                            add = pessoa1;

                            for (Pessoa pessoa2 : adapterListaPessoa.getPessoas()) {
                                if (pessoa2.getId() == pessoa1.getId()){
                                    new AlertDialog.Builder(PessoasEstoqueFragment.this.getActivity())
                                            .setTitle("Pessoa já está no estoque")
                                            .setPositiveButton("Ok", (dialog1, which1) -> {
                                                dialog1.dismiss();
                                            }).show();
                                    jaNaLista = true;
                                    break;
                                }
                            }
                            if(!jaNaLista) {
                                addPessoaEstoque(add);
                            }
                            break;
                        }
                    }

                    if(add == null){
                        new AlertDialog.Builder(PessoasEstoqueFragment.this.getActivity())
                                .setTitle("Pessoa não encotrada")
                                .setPositiveButton("Ok", (dialog1, which1) -> {
                                    dialog1.dismiss();
                                }).show();
                    }
                }
                @Override
                public void onFailure(Call<List<Pessoa>> call, Throwable t) {
                }
            });
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
        builder.show();
    }

    private void addPessoaEstoque(Pessoa add) {

        PessoaEstoque ps = new PessoaEstoque(add.getId(), estoque.getId());

        ApiFstock.getInstance().descricaoEstoqueService().adcionarPessoaEstoque( SharedPreferencesUtils.getUserId(getActivity()), estoque.getId(), ps).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                adapterListaPessoa.addItem(add);
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {
            }
        });
    }

}
