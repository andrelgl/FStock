package kn.fstock.fstock.Services;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import kn.fstock.fstock.Activities.MainActivity;
import kn.fstock.fstock.ApiFstock;
import kn.fstock.fstock.R;
import kn.fstock.fstock.models.Estoque;
import kn.fstock.fstock.models.Produto;
import kn.fstock.fstock.models.ProdutoVencer;
import kn.fstock.fstock.utils.SharedPreferencesUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        getEstoques();
        // If we get killed, after returning from here, restart
        return START_STICKY;
    }
    void showNotification(String title, String content) {
        Uri uri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("default",
                    "YOUR_CHANNEL_NAME",
                    NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("YOUR_NOTIFICATION_CHANNEL_DISCRIPTION");
            mNotificationManager.createNotificationChannel(channel);
        }
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext(), "default")
                .setSmallIcon(R.mipmap.ic_launcher) // notification icon
                .setContentTitle(title) // title for notification
                .setContentText(content)// message for notification
                .setSound(uri) // set alarm sound for notification
                .setAutoCancel(true); // clear notification after click
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        PendingIntent pi = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(pi);
        mNotificationManager.notify((int)Math.random(), mBuilder.build());
    }

    public void getEstoques(){
        ApiFstock.getInstance().descricaoEstoqueService().listarEstoque(SharedPreferencesUtils.getUserId(this)).enqueue(new Callback<List<Estoque>>() {
            @Override
            public void onResponse(Call<List<Estoque>> call, Response<List<Estoque>> response) {
                Map<Integer, Estoque> estoquesMap = new HashMap<>();
                for (Estoque estoque : response.body()) {
                    getProdutosAVencer(estoque);
                    getProdutosQauntidade(estoque);
                }
            }
            @Override
            public void onFailure(Call<List<Estoque>> call, Throwable t) {

            }
        });
    }

    public void getProdutosAVencer(Estoque estoque) {
        ApiFstock.getInstance().descricaoEstoqueService().alertVencimento(SharedPreferencesUtils.getUserId(this),estoque.getId())
                .enqueue(new Callback<List<ProdutoVencer>>() {
                    @Override
                    public void onResponse(Call<List<ProdutoVencer>> call, Response<List<ProdutoVencer>> response) {
                        if(response.body() != null){
                            showNotification("Produtos à vencer", String.format("O estoque %s possui %d à vencer",estoque.getNome(),response.body().size()));
                        }
                    }

                    @Override
                    public void onFailure(Call<List<ProdutoVencer>> call, Throwable t) {

                    }
                });
    }

    public void getProdutosQauntidade(Estoque estoque) {
        ApiFstock.getInstance().descricaoEstoqueService().alertQuantidade(SharedPreferencesUtils.getUserId(this),estoque.getId())
                .enqueue(new Callback<List<Produto>>() {
                    @Override
                    public void onResponse(Call<List<Produto>> call, Response<List<Produto>> response) {
                        if(response.body() != null){
                            showNotification("Produtos à vencer", String.format("O estoque %s possui %d com alerta de quantidade",estoque.getNome(),response.body().size()));
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Produto>> call, Throwable t) {

                    }
                });
    }
}
