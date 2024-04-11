package activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.spielbrett_gruppe3.R;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class UserActivity extends AppCompatActivity {


    private Socket socket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Button buttonSubmit;
        EditText editTextUserName;
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(com.example.spielbrett_gruppe3.R.layout.user);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.user), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        editTextUserName = findViewById(R.id.editUserName);
        buttonSubmit = findViewById(R.id.createUser);

        buttonSubmit.setOnClickListener(v -> {
            String username = editTextUserName.getText().toString().trim();
            if(!username.isEmpty()) {
                // Connect to server
                connectToServer(username);
            }
            else {
                Toast.makeText(UserActivity.this, "Please enter a username", Toast.LENGTH_SHORT).show();
            }
        });

        // Back button click listener
        Button backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> {
            // Finish this activity to go back to MainActivity
            finish();
        });
    }

    private void connectToServer(final String username) {
        new Thread(() -> {
            try {
                // Connect to server
                socket = new Socket("127.0.0.1", 57587);

                // Send username to server
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                writer.write("createUser:" + username + "\n");
                writer.flush();

                // Receive response from server
                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                final String response = reader.readLine();

                // Display response on UI thread
                runOnUiThread(() -> {
                    if (response != null && response.equals("user created")) {
                        Toast.makeText(UserActivity.this, "User successfully created", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(UserActivity.this, "Error: Username already exists", Toast.LENGTH_SHORT).show();
                    }
                });

                // Close socket connection
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
                runOnUiThread(() -> Toast.makeText(UserActivity.this, "Failed to connect to server", Toast.LENGTH_SHORT).show());
            }
        }).start();
    }
}
