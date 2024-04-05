package com.example.gruppe03;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class PopUp1 extends AppCompatActivity {

    public ImageButton buttonZweiSpieler;
    public int REQUEST_CODE = 0;
    public ImageButton buttonDreiSpieler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pop_up1);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        buttonZweiSpieler = findViewById(R.id.imageButton5);

        buttonZweiSpieler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                REQUEST_CODE = 2;
                Intent intent = new Intent(PopUp1.this, TwoPlayers.class);
                intent.putExtra("rC", REQUEST_CODE);
                startActivityForResult(intent, REQUEST_CODE);
            }
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