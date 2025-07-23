package ir.gh.traveling2;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.content.SharedPreferences;
import android.widget.Toast;
import android.widget.Button;
import android.widget.EditText;



public class ProfileActivity extends AppCompatActivity {

    EditText etFirstName, etLastName, etPhone, etNote;
    Button btnSave;
    SharedPreferences preferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        etFirstName = findViewById(R.id.etFirstName);
        etLastName = findViewById(R.id.etLastName);
        etPhone = findViewById(R.id.etPhone);
        etNote = findViewById(R.id.etNote);
        btnSave = findViewById(R.id.btnSave);

        preferences = getSharedPreferences("user_profile", MODE_PRIVATE);

        // اگر قبلاً ذخیره شده بود، لود کن
        etFirstName.setText(preferences.getString("first_name", ""));
        etLastName.setText(preferences.getString("last_name", ""));
        etPhone.setText(preferences.getString("phone", ""));
        etNote.setText(preferences.getString("note", ""));

        btnSave.setOnClickListener(v -> {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("first_name", etFirstName.getText().toString());
            editor.putString("last_name", etLastName.getText().toString());
            editor.putString("phone", etPhone.getText().toString());
            editor.putString("note", etNote.getText().toString());
            editor.apply();

            Toast.makeText(ProfileActivity.this, "Saved!", Toast.LENGTH_SHORT).show();
        });
    }
}
