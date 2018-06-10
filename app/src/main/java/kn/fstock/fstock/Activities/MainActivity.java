package kn.fstock.fstock.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import kn.fstock.fstock.R;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void singIn(View v){
        startActivity(new Intent(getBaseContext(), SingInActivity.class));
    }
    public void mainMenu(View v){
        startActivity(new Intent(getBaseContext(), PrincipalActivity.class));
    }
}
