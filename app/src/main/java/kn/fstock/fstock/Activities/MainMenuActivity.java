package kn.fstock.fstock.Activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import kn.fstock.fstock.Adapters.AdapterListaEstoque;
import kn.fstock.fstock.R;
import kn.fstock.fstock.RetrofitInitilizer;
import kn.fstock.fstock.models.Pessoa;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainMenuActivity extends AppCompatActivity {
    RecyclerView t;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        t = findViewById(R.id.list);


        RecyclerView.LayoutManager layout = new LinearLayoutManager(this,
        LinearLayoutManager.VERTICAL, false);
        t.setLayoutManager(layout);

        //retrofit fazendo seu trabalho
        RetrofitInitilizer retro = new RetrofitInitilizer();

    }

}
