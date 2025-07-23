package ir.gh.traveling2;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AddTripActivity extends AppCompatActivity {

    EditText etDestination, etStartDate, etEndDate, etBudget, etNotes;
    CheckBox cbReminder;
    Button btnSave;

    private FirebaseFirestore db;
    private FirebaseAuth mAuth;

    private static final int NOTIFICATION_PERMISSION_REQUEST = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_trip);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(
                        this,
                        new String[]{android.Manifest.permission.POST_NOTIFICATIONS},
                        NOTIFICATION_PERMISSION_REQUEST
                );
            }
        }

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        etDestination = findViewById(R.id.etDestination);
        etStartDate = findViewById(R.id.etStartDate);
        etEndDate = findViewById(R.id.etEndDate);
        etBudget = findViewById(R.id.etBudget);
        etNotes = findViewById(R.id.etNotes);
        cbReminder = findViewById(R.id.cbReminder);
        btnSave = findViewById(R.id.btnSave);

        btnSave.setOnClickListener(v -> {
            String destination = etDestination.getText().toString().trim();
            String startDate = etStartDate.getText().toString().trim();
            String endDate = etEndDate.getText().toString().trim();
            String budget = etBudget.getText().toString().trim();
            String notes = etNotes.getText().toString().trim();
            boolean reminder = cbReminder.isChecked();

            if (destination.isEmpty()) {
                Toast.makeText(this, "Please enter at least the destination", Toast.LENGTH_SHORT).show();
                return;
            }

            FirebaseUser currentUser = mAuth.getCurrentUser();
            if (currentUser == null) {
                Toast.makeText(this, "User not authenticated!", Toast.LENGTH_SHORT).show();
                return;
            }

            String userId = currentUser.getUid();

            Map<String, Object> trip = new HashMap<>();
            trip.put("destination", destination);
            trip.put("startDate", startDate);
            trip.put("endDate", endDate);
            trip.put("budget", budget);
            trip.put("notes", notes);
            trip.put("reminder", reminder);

            db.collection("users")
                    .document(userId)
                    .collection("trips")
                    .add(trip)
                    .addOnSuccessListener(documentReference -> {
                        Toast.makeText(this, "Trip saved successfully!", Toast.LENGTH_SHORT).show();
                        if (reminder) {
                            sendNotification(destination);
                        }
                        finish();
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(this, "Failed to save trip: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    });
        });
    }

    // ارسال نوتیفیکیشن با چک کردن مجوز
    private void sendNotification(String destination) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS)
                    != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Notification permission not granted", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        String channelId = "trip_channel";
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // تعریف کانال نوتیفیکیشن برای اندروید 8+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    channelId,
                    "Trip Notifications",
                    NotificationManager.IMPORTANCE_HIGH
            );
            notificationManager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("Trip Reminder")
                .setContentText("Upcoming trip to: " + destination)
                .setPriority(NotificationCompat.PRIORITY_HIGH);

        notificationManager.notify(1, builder.build());
    }

    // پاسخ به درخواست مجوز
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == NOTIFICATION_PERMISSION_REQUEST) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Notification permission granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Notification permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
