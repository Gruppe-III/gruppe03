package com.example.gruppe03;

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

import java.util.Random;

public class DrawCard extends AppCompatActivity {

    public ImageButton Kartenstapel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_draw_card);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Kartenstapel = findViewById(R.id.imageButtonKarten);

        Kartenstapel.setOnClickListener(new View.OnClickListener() {
            Random random = new Random();
            int draw = random.nextInt(4);
            @Override
            public void onClick(View v) {
                if(draw == 0)
                {
                    Intent intent = new Intent(DrawCard.this, SpinCarrot.class);
                    startActivity(intent);
                }
                if(draw == 1)
                {
                    Intent intent = new Intent(DrawCard.this, GoOneField.class);
                    startActivity(intent);
                }
                if(draw == 2)
                {
                    Intent intent = new Intent(DrawCard.this, GoTwoFields.class);
                    startActivity(intent);
                }
                if(draw == 3)
                {
                    Intent intent = new Intent(DrawCard.this, GoThreeFields.class);
                    startActivity(intent);
                }
            }
        });
    }
}