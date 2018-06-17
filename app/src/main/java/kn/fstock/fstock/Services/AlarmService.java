package kn.fstock.fstock.Services;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import static android.content.Context.ALARM_SERVICE;

public class AlarmService {
    private static boolean isRunning;
    private static Intent alarmIntent = null;
    private static PendingIntent pendingIntent = null;
    private static AlarmManager alarmManager = null;
    private static final int uploadInterval = 30 * 60 * 1000;

    public static boolean isRunning() {
        return isRunning;
    }

    public static void createAlarm(Context context){
        alarmIntent = new Intent ( context, AlarmReceiver.class );
        pendingIntent = PendingIntent.getBroadcast( context.getApplicationContext(), 234324243, alarmIntent, 0 );
        alarmManager = ( AlarmManager ) context.getSystemService( ALARM_SERVICE );
        alarmManager.setRepeating( AlarmManager.RTC_WAKEUP, 100 , uploadInterval , pendingIntent );
        isRunning = true;
    }

}
