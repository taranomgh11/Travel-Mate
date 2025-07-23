package ir.gh.traveling2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import android.widget.Button;
import android.content.Intent;

public class HomeActivity extends AppCompatActivity {

    TextView tvTripCount;
    RecyclerView rvTrips;
    FloatingActionButton fabAddTrip;
    TripAdapter tripAdapter;
    ArrayList<Trip> tripList;

    ImageButton btnViewMap;
    ImageButton btnViewCalendar;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_home);

            tvTripCount = findViewById(R.id.tvTripCount);
            rvTrips = findViewById(R.id.rvTrips);
            fabAddTrip = findViewById(R.id.fabAddTrip);
            btnViewMap = findViewById(R.id.btnViewMap);

            tripList = new ArrayList<>();
            tripList.add(new Trip("Paris", "2024/07/10", "2024/07/20", "2000$", "Visit Eiffel Tower", true));
            tripList.add(new Trip("Istanbul", "2024/12/20", "2024/12/30", "1500$", "Visit Hagia Sophia", false));

            tripAdapter = new TripAdapter(tripList);
            rvTrips.setLayoutManager(new LinearLayoutManager(this));
            rvTrips.setAdapter(tripAdapter);

            tvTripCount.setText("Number of Trips: " + tripList.size());

            fabAddTrip.setOnClickListener(v -> {
                Intent intent = new Intent(HomeActivity.this, AddTripActivity.class);
                startActivity(intent);
            });

            btnViewMap.setOnClickListener(v -> {
                Intent intent = new Intent(HomeActivity.this, MapActivity.class);
                startActivity(intent);
            });

            btnViewCalendar = findViewById(R.id.btnViewCalendar);

            btnViewCalendar.setOnClickListener(v -> {
                Intent intent = new Intent(HomeActivity.this, CalendarActivity.class);
                startActivity(intent);
            });

            findViewById(R.id.btnProfile).setOnClickListener(v -> {
                Intent intent = new Intent(HomeActivity.this, ProfileActivity.class);
                startActivity(intent);
            });

        }
    }

