package kn.fstock.fstock.fragments;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import kn.fstock.fstock.Activities.MainActivity;
import kn.fstock.fstock.ApiFstock;
import kn.fstock.fstock.R;
import kn.fstock.fstock.models.Pessoa;
import kn.fstock.fstock.utils.SharedPreferencesUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PerfilPessoaFragment extends Fragment {

    @BindView(R.id.img_admin)
    ImageView imgAdmin;
    @BindView(R.id.tv_nome)
    TextView tvNome;
    @BindView(R.id.btn_alterar_email)
    Button btnAlterarEmail;
    @BindView(R.id.btn_alterar_senha)
    Button btnAlterarSenha;
    @BindView(R.id.btn_logout)
    Button btnLogout;
    Unbinder unbinder;
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
        View view = inflater.inflate(R.layout.fragment_perfil_pessoa, container, false);
        unbinder = ButterKnife.bind(this, view);

        tvNome.setText(pessoa.getNome());

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.btn_alterar_email, R.id.btn_alterar_senha, R.id.btn_logout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_alterar_email:
                alterarEmail();
                break;
            case R.id.btn_alterar_senha:
                alterarSenha();
                break;
            case R.id.btn_logout:
                logOut();
                break;
        }
    }

    private void alterarEmail() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Alterar Email");

        View dialogView = getLayoutInflater().inflate(R.layout.dialog_text_input, null);
        final EditText input = dialogView.findViewById(R.id.input);
        builder.setView(dialogView);

        builder.setPositiveButton("OK", (dialog, which) -> {
            pessoa.setEmail(input.getText().toString());
            final ProgressDialog progressDialog = ProgressDialog.show(PerfilPessoaFragment.this.getContext(), "",
                    "Carregando...", true, false);
            ApiFstock.getInstance().descricaoPessoaService().alteraPessoa(SharedPreferencesUtils.getUserId(PerfilPessoaFragment.this.getContext()), pessoa).enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    progressDialog.dismiss();
                    logOut();
                }
                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    progressDialog.dismiss();
                }
            });

        });
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
        builder.show();
    }

    private void alterarSenha() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Alterar Email");

        View dialogView = getLayoutInflater().inflate(R.layout.dialog_text_input_password, null);
        final TextInputEditText input = dialogView.findViewById(R.id.input_senha);
        final TextInputEditText input2 = dialogView.findViewById(R.id.input_senha_2);
        builder.setView(dialogView);
        builder.setPositiveButton("OK", null);
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

        final AlertDialog mAlertDialog = builder.create();
        mAlertDialog.setOnShowListener(dialog -> {
            Button b = mAlertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
            b.setOnClickListener(view -> {
                if(input.getText().toString().equals(input2.getText().toString())) {
                    pessoa.setSenha(input.getText().toString());
                    final ProgressDialog progressDialog = ProgressDialog.show(PerfilPessoaFragment.this.getContext(), "",
                            "Carregando...", true, false);
                    ApiFstock.getInstance().descricaoPessoaService().alteraPessoa(SharedPreferencesUtils.getUserId(PerfilPessoaFragment.this.getContext()), pessoa).enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            progressDialog.dismiss();
                            logOut();
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            progressDialog.dismiss();
                        }
                    });
                }
                else{
                    input2.setError("Senhas não conferem");
                }
            });
        });
        mAlertDialog.show();
    }

    private void logOut() {
        SharedPreferencesUtils.putUserId(getContext(),-1);
        SharedPreferencesUtils.putUserName(getContext(),null);
        SharedPreferencesUtils.putUserEmail(getContext(),null);

        Intent intent = new Intent(getContext(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}
