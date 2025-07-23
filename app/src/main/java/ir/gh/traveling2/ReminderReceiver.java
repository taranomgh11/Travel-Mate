package ir.gh.traveling2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;


public class ReminderReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String destination = intent.getStringExtra("destination");

        // ✅ بررسی Permission قبل از notify
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (context.checkSelfPermission(android.Manifest.permission.POST_NOTIFICATIONS)
                    != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(context, "Notification permission denied", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "trip_channel")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("Trip Reminder")
                .setContentText("Don't forget! Trip to " + destination + " is tomorrow.")
                .setPriority(NotificationCompat.PRIORITY_HIGH);

        NotificationManagerCompat manager = NotificationManagerCompat.from(context);
        manager.notify(101, builder.build());

        Toast.makeText(context, "Trip reminder sent!", Toast.LENGTH_SHORT).show();
    }
}


