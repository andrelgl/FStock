package kn.fstock.fstock.Activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kn.fstock.fstock.Adapters.AdapterListaEstoque;
import kn.fstock.fstock.ApiFstock;
import kn.fstock.fstock.R;
import kn.fstock.fstock.models.Estoque;
import kn.fstock.fstock.models.PessoaEstoque;
import kn.fstock.fstock.utils.SharedPreferencesUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListaEstoqueActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AdapterListaEstoque adapterListaEstoque;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_estoque);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> showNewEstoquDialog());

        adapterListaEstoque = new AdapterListaEstoque(new ArrayList<Estoque>(), ListaEstoqueActivity.this);

        recyclerView = findViewById(R.id.recycler_estoque);
        recyclerView.setAdapter(adapterListaEstoque);

        ApiFstock.getInstance().descricaoEstoqueService().listarEstoque(SharedPreferencesUtils.getUserId(this)).enqueue(new Callback<List<Estoque>>() {
            @Override
            public void onResponse(Call<List<Estoque>> call, Response<List<Estoque>> response) {
                Map<Integer, Estoque> estoquesMap = new HashMap<>();
                for (Estoque estoque : response.body()) {
                    estoquesMap.put(estoque.getId(), estoque);
                }
                for (Map.Entry<Integer, Estoque> entry : estoquesMap.entrySet()) {
                    adapterListaEstoque.addItem(entry.getValue());
                }
            }
            @Override
            public void onFailure(Call<List<Estoque>> call, Throwable t) {

            }
        });

        setTitle("Lista de estoque");
    }

    public void showNewEstoquDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Adicionar Estoque");

        View dialogView = getLayoutInflater().inflate(R.layout.dialog_text_input, null);
        final EditText input = dialogView.findViewById(R.id.input);
        builder.setView(dialogView);

        builder.setPositiveButton("OK", (dialog, which) -> {
            final Estoque estoque = new Estoque();
            estoque.setNome(input.getText().toString());
            estoque.setPessoa_admin(SharedPreferencesUtils.getUserId(ListaEstoqueActivity.this));
            ApiFstock.getInstance().descricaoEstoqueService().criarEstoque(SharedPreferencesUtils.getUserId(ListaEstoqueActivity.this), estoque).enqueue(new Callback<Estoque>() {
                @Override
                public void onResponse(Call<Estoque> call, Response<Estoque> response) {
                    adapterListaEstoque.addItem(response.body());
                    Estoque estoque = response.body();
                    PessoaEstoque pe = new PessoaEstoque(SharedPreferencesUtils.getUserId(ListaEstoqueActivity.this), estoque.getId());
                    ApiFstock.getInstance().descricaoEstoqueService()
                            .adcionarPessoaEstoque(SharedPreferencesUtils.getUserId(ListaEstoqueActivity.this), estoque.getId(), pe )
                            .enqueue(new Callback<Void>() {
                                @Override
                                public void onResponse(Call<Void> call, Response<Void> response) {
                                    dialog.dismiss();
                                }
                                @Override
                                public void onFailure(Call<Void> call, Throwable t) {
                                    dialog.dismiss();
                                }
                            });
                }
                @Override
                public void onFailure(Call<Estoque> call, Throwable t) {
                }
            });

        });
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

        builder.show();
    }

}
