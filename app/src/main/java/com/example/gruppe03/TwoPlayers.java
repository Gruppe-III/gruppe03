package com.example.gruppe03;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.spielbrett_gruppe3.R;

public class TwoPlayers extends AppCompatActivity {

    public ImageButton TwoPlayersCheck;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_two_players);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        TwoPlayersCheck = findViewById(R.id.ToCheck);
        int rC = getIntent().getIntExtra("requestCode", 0);
        TwoPlayersCheck.setOnClickListener(v -> {
            if(rC==2)
            {
                //Intent to game field
            }
            if(rC==3)
            {
                Intent intent = new Intent(TwoPlayers.this, ThreePlayers.class);
                startActivity(intent);
            }
            if(rC==4)
            {
                Intent intent = new Intent(TwoPlayers.this, FourPlayers.class);
                startActivity(intent);
            }
        });

    }
}