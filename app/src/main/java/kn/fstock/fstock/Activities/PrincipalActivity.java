package kn.fstock.fstock.Activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import kn.fstock.fstock.R;
import kn.fstock.fstock.fragments.EstoqueMainFragment;
import kn.fstock.fstock.fragments.ItemEstoqueFragment;
import kn.fstock.fstock.fragments.PerfilPessoaFragment;
import kn.fstock.fstock.fragments.PessoasEstoqueFragment;
import kn.fstock.fstock.models.Estoque;
import kn.fstock.fstock.models.Pessoa;

public class PrincipalActivity extends AppCompatActivity implements ItemEstoqueFragment.OnListFragmentInteractionListener {

    private TextView mTextMessage;
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

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    @Override
    public void onListFragmentInteraction(Estoque item) {

    }
}
