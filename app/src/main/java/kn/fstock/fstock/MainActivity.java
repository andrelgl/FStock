package kn.fstock.fstock;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import kn.fstock.fstock.models.Pessoa;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    TextView t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        t = findViewById(R.id.textao);

        RetrofitInitilizer retro = new RetrofitInitilizer();

        retro.descricaoGrupoService().listarPessoas().enqueue(
                new Callback<List<Pessoa>>() {
                    @Override
                    public void onResponse(Call<List<Pessoa>> call, Response<List<Pessoa>> response) {
                        StringBuilder b = new StringBuilder();
                        for (Pessoa p : response.body()) {
                            b.append(p.getNome() + "\n");
                        }
                        t.setText(b.toString());
                    }

                    @Override
                    public void onFailure(Call<List<Pessoa>> call, Throwable t) {

                    }
                }
        );

    }


    public void singIn(View v){
        startActivity(new Intent(getBaseContext(), SingInActivity.class));
    }
}
