package com.example.firequiz;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);

        Button mBtnClassicMode = findViewById(R.id.btnClassicMode);
        Button mBtnTimedMode = findViewById(R.id.btnTimedMode);
        Button mBtnCategories = findViewById(R.id.btnCategories);
        Button mBtnLogout = findViewById(R.id.btnLogout);

        mBtnClassicMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        mBtnTimedMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        mBtnCategories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        mBtnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });

        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                // Empêcher le retour arrière
                // Vous pouvez afficher un message à l'utilisateur ou effectuer une autre action ici
                Toast.makeText(HomeActivity.this, "Utilisez le bouton de déconnexion pour quitter", Toast.LENGTH_SHORT).show();
            }
        };

        // Ajouter le callback à l'activité
        getOnBackPressedDispatcher().addCallback(this, callback);

    }
}