package kn.fstock.fstock.Activities;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.List;

import kn.fstock.fstock.Adapters.AdapterListaEstoque;
import kn.fstock.fstock.ApiFstock;
import kn.fstock.fstock.R;
import kn.fstock.fstock.models.Estoque;
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
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showNewEstoquDialog();
            }
        });

        recyclerView = findViewById(R.id.recycler_estoque);

        ApiFstock.getInstance().descricaoEstoqueService().listarEstoque(SharedPreferencesUtils.getUserId(this)).enqueue(new Callback<List<Estoque>>() {
            @Override
            public void onResponse(Call<List<Estoque>> call, Response<List<Estoque>> response) {
                adapterListaEstoque = new AdapterListaEstoque(response.body(), ListaEstoqueActivity.this);
                recyclerView.setAdapter(adapterListaEstoque);
            }

            @Override
            public void onFailure(Call<List<Estoque>> call, Throwable t) {

            }
        });

    }

    public void showNewEstoquDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Adicionar Estoque");

        View dialogView = getLayoutInflater().inflate(R.layout.dialog_text_inpu_password, null);
        final EditText input = dialogView.findViewById(R.id.input);
        builder.setView(dialogView);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(final DialogInterface dialog, int which) {
                final Estoque estoque = new Estoque();
                estoque.setNome(input.getText().toString());
                estoque.setPessoa_admin(SharedPreferencesUtils.getUserId(ListaEstoqueActivity.this));
                ApiFstock.getInstance().descricaoEstoqueService().criarEstoque(SharedPreferencesUtils.getUserId(ListaEstoqueActivity.this), estoque).enqueue(new Callback<Estoque>() {
                    @Override
                    public void onResponse(Call<Estoque> call, Response<Estoque> response) {
                        adapterListaEstoque.addItem(response.body());
                        dialog.dismiss();
                    }
                    @Override
                    public void onFailure(Call<Estoque> call, Throwable t) {
                    }
                });

            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

}
