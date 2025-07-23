package ir.gh.traveling2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class SignUpActivity extends AppCompatActivity {

    EditText etEmail, etPassword;
    Button btnSignUp;
    TextView tvLogin;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // مقداردهی FirebaseAuth
        mAuth = FirebaseAuth.getInstance();

        // ارتباط با ویوها
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnSignUp = findViewById(R.id.btnSignUp);
        tvLogin = findViewById(R.id.tvLogin);

        btnSignUp.setOnClickListener(v -> {
            String email = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            // اعتبارسنجی ورودی‌ها
            if(email.isEmpty() || password.isEmpty()){
                Toast.makeText(SignUpActivity.this, "لطفا ایمیل و رمز را وارد کنید", Toast.LENGTH_SHORT).show();
                return;
            }

            if(password.length() < 6){
                Toast.makeText(SignUpActivity.this, "رمز باید حداقل 6 کاراکتر باشد", Toast.LENGTH_SHORT).show();
                return;
            }

            // ثبت‌نام با Firebase
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> {
                        if(task.isSuccessful()){
                            Toast.makeText(SignUpActivity.this, "ثبت‌نام موفق!", Toast.LENGTH_SHORT).show();

                            // ارسال به صفحه اصلی بعد از ثبت‌نام
                            Intent intent = new Intent(SignUpActivity.this, HomeActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(SignUpActivity.this, "خطا: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
        });

        // رفتن به صفحه ورود
        tvLogin.setOnClickListener(v -> {
            Intent intent = new Intent(SignUpActivity.this, LogInActivity.class);
            startActivity(intent);
            finish();
        });
    }
}
