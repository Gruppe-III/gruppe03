package com.example.gruppe03;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.spielbrett_gruppe3.R;

public class FirstScreenActivity extends AppCompatActivity {

    private Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_first_screen);

        button = findViewById(R.id.button1);
        button.setOnClickListener(v -> {
            Intent intent = new Intent(FirstScreenActivity.this, MenuScreenActivity.class);
            startActivity(intent);
        });
    }
    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");
        // Hier können zusätzliche Initialisierungen und Vorbereitungen erfolgen
    }
    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
    // Aktualisierungen der UI und der Daten können hier erfolgen
    }
    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
        // Speichern von Zuständen und Freigeben von Ressourcen, die nicht mehr benötigt werden
    }
    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");
        // Optionales Aufräumen, wenn die Activity gestoppt wird
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
        // Freigeben von Ressourcen und Beenden von Hintergrundprozessen
    }
}