package com.example.firequiz;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.mindrot.jbcrypt.BCrypt;

public class LoginActivity extends AppCompatActivity {

    DatabaseReference mDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        FirebaseApp.initializeApp(this);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        mDatabaseReference = database.getReference("users");
        
        EditText mUsername = findViewById(R.id.username);
        EditText mPassword = findViewById(R.id.password);
        Button mLogin = findViewById(R.id.btnLogin);
        
        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usernameInput = mUsername.getText().toString();
                String passwordInput = mPassword.getText().toString();
                if (usernameInput.isEmpty() | passwordInput.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Champs manquants", Toast.LENGTH_SHORT).show();
                } else {

                    mDatabaseReference.orderByChild("username").equalTo(usernameInput).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.exists()){
                                for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                                    String dbPassword = userSnapshot.child("password").getValue(String.class);

                                    if(BCrypt.checkpw(passwordInput, dbPassword)) {
                                        Toast.makeText(LoginActivity.this, "Login réussi", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                        startActivity(intent);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        finish();
                                        return;
                                    }
                                }
                                Toast.makeText(LoginActivity.this, "Username or password incorrect (password)", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(LoginActivity.this, "Username or password incorrect (username)", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(LoginActivity.this, "Erreur de base de données", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }
}