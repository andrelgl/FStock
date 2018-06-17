package kn.fstock.fstock.Activities;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kn.fstock.fstock.ApiFstock;
import kn.fstock.fstock.R;
import kn.fstock.fstock.models.Pessoa;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SingInActivity extends AppCompatActivity {

    @BindView(R.id.edit_text_nome)
    EditText editTextNome;
    @BindView(R.id.view)
    View view;
    @BindView(R.id.edit_text_email)
    EditText editTextEmail;
    @BindView(R.id.view2)
    View view2;
    @BindView(R.id.edit_text_password)
    EditText editTextPassword;
    @BindView(R.id.view3)
    View view3;
    @BindView(R.id.edit_text_confirm_password)
    EditText editTextConfirmPassword;
    @BindView(R.id.view4)
    View view4;
    @BindView(R.id.button)
    Button button;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_singin);
        ButterKnife.bind(this);
    }


    @OnClick(R.id.button)
    public void onViewClicked() {
        Pessoa pessoa = new Pessoa();
        pessoa.setEmail(editTextEmail.getText().toString());
        pessoa.setNome(editTextNome.getText().toString());
        if(editTextConfirmPassword.getText().toString().equals(editTextPassword.getText().toString())) {
            pessoa.setSenha(editTextPassword.getText().toString());
            final ProgressDialog dialog = ProgressDialog.show(this, "",
                    "Carregando...", true, false);

            ApiFstock.getInstance().descricaoPessoaService().criarPessoa(pessoa).enqueue(new Callback<Pessoa>() {
                @Override
                public void onResponse(Call<Pessoa> call, Response<Pessoa> response) {
                    dialog.dismiss();
                    if (response.code() == 200) {
                        SingInActivity.this.finish();
                    }else {
                        new AlertDialog.Builder(SingInActivity.this)
                                .setTitle("Erro ao cadastrar")
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                }).show();
                    }
                }
                @Override
                public void onFailure(Call<Pessoa> call, Throwable t) {
                    t.printStackTrace();
                    dialog.dismiss();
                    new AlertDialog.Builder(SingInActivity.this)
                            .setTitle("Erro ao cadastrar")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).show();
                }
            });
        }
        else {
            new AlertDialog.Builder(this)
                    .setTitle("Senhas não conferem")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();
        }
    }
}
