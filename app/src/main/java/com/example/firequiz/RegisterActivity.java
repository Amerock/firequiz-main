package com.example.firequiz;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.mindrot.jbcrypt.BCrypt;

public class RegisterActivity extends AppCompatActivity {

    DatabaseReference mDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);

        FirebaseApp.initializeApp(this);

        // Initialisation de Firebase Database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        mDatabaseReference = database.getReference("users");

        EditText mUsername = findViewById(R.id.username);
        EditText mPassword = findViewById(R.id.password);
        Button mRegister = findViewById(R.id.btnRegister);

        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usernameInput = mUsername.getText().toString();
                String passwordInput = mPassword.getText().toString();

                String hashedPassword = BCrypt.hashpw(passwordInput, BCrypt.gensalt());

                if (usernameInput.isEmpty() | passwordInput.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Champs manquants", Toast.LENGTH_SHORT).show();
                } else {
                    String userId = mDatabaseReference.push().getKey();
                    User user = new User(usernameInput, hashedPassword);
                    mDatabaseReference.child(userId).setValue(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(RegisterActivity.this, "Success", Toast.LENGTH_LONG).show();
                            finish();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(RegisterActivity.this, "Failure", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });
    }
}