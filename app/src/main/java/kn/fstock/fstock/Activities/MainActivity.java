package kn.fstock.fstock.Activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import kn.fstock.fstock.ApiFstock;
import kn.fstock.fstock.R;
import kn.fstock.fstock.Services.AlarmService;
import kn.fstock.fstock.models.Pessoa;
import kn.fstock.fstock.utils.SharedPreferencesUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {

    @BindView(R.id.imageLogo)
    ImageView imageLogo;
    @BindView(R.id.textLogo)
    TextView textLogo;
    @BindView(R.id.editEmail)
    EditText editEmail;
    @BindView(R.id.view2)
    View view2;
    @BindView(R.id.editPass)
    EditText editPass;
    @BindView(R.id.view3)
    View view3;
    @BindView(R.id.btn_l)
    Button btnL;
    @BindView(R.id.btn_r)
    Button btnR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        if(!AlarmService.isRunning()) {
            AlarmService.createAlarm(this);
        }

        if(SharedPreferencesUtils.getUserId(this) != -1) {
            Intent intent = new Intent(getBaseContext(), ListaEstoqueActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }

    public void singIn(View v) {
        startActivity(new Intent(getBaseContext(), SingInActivity.class));
    }

    public void mainMenu(View v) {

        Pessoa pessoa = new Pessoa();
        pessoa.setEmail(editEmail.getText().toString());
        pessoa.setSenha(editPass.getText().toString());

        final ProgressDialog dialog = ProgressDialog.show(this, "",
                "Carregando...", true, false);

        ApiFstock.getInstance().descricaoPessoaService().autenticarPessoa(pessoa).enqueue(new Callback<Pessoa>() {
            @Override
            public void onResponse(Call<Pessoa> call, Response<Pessoa> response) {
                if (response.code() == 200) {
                    dialog.dismiss();
                    SharedPreferencesUtils.putUserEmail(MainActivity.this, response.body().getEmail());
                    SharedPreferencesUtils.putUserName(MainActivity.this, response.body().getNome());
                    SharedPreferencesUtils.putUserId(MainActivity.this, response.body().getId());
                    Intent intent = new Intent(getBaseContext(), ListaEstoqueActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                } else {
                    dialog.dismiss();
                    new AlertDialog.Builder(MainActivity.this)
                            .setMessage("Usuário ou senha inválidos")
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
                dialog.dismiss();
                new AlertDialog.Builder(MainActivity.this)
                        .setMessage("Verifique sua conexão")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).show();
            }
        });

    }
}
