package kn.fstock.fstock.Activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import kn.fstock.fstock.ApiFstock;
import kn.fstock.fstock.R;
import kn.fstock.fstock.fragments.EstoqueMainFragment;
import kn.fstock.fstock.fragments.ProdutoEstoqueFragment;
import kn.fstock.fstock.fragments.PerfilPessoaFragment;
import kn.fstock.fstock.fragments.PessoasEstoqueFragment;
import kn.fstock.fstock.models.Estoque;
import kn.fstock.fstock.models.Item;
import kn.fstock.fstock.models.Pessoa;
import kn.fstock.fstock.utils.SharedPreferencesUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PrincipalActivity extends AppCompatActivity implements ProdutoEstoqueFragment.OnListFragmentInteractionListener {

    private Pessoa pessoa;
    private Estoque estoque;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment = null;
            switch (item.getItemId()) {
                case R.id.navigation_estoque:
                    selectedFragment = EstoqueMainFragment.newInstance(pessoa, estoque);
                    break;
                case R.id.navigation_pessoas:
                    selectedFragment = PessoasEstoqueFragment.newInstance(pessoa, estoque);
                    break;
                case R.id.navigation_perfil:
                    selectedFragment = PerfilPessoaFragment.newInstance(pessoa);
                    break;
            }
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.frame_layout, selectedFragment);
            transaction.commit();
            return true;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        pessoa = new Pessoa();
        pessoa.setEmail(SharedPreferencesUtils.getUserEmail(this));
        pessoa.setNome(SharedPreferencesUtils.getUserName(this));

        getEstoque(getIntent().getIntExtra("estoque_id",-1));

    }

    public void getEstoque(int id){
        ApiFstock.getInstance().descricaoEstoqueService().buscarEstoque(pessoa.getId(),id).enqueue(new Callback<Estoque>() {
            @Override
            public void onResponse(Call<Estoque> call, Response<Estoque> response) {
                if(response.code() == 200){
                    estoque = response.body();
                    setUpNavigationBar();
                }
            }

            @Override
            public void onFailure(Call<Estoque> call, Throwable t) {

            }
        });
    }

    public void setUpNavigationBar(){
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    @Override
    public void onListFragmentInteraction(Item item) {

    }
}
