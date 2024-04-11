package websocketdemoapp.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import at.aau.serg.websocketdemoapp.R;
//import at.aau.serg.websocketdemoapp.msg.TestMessage;
//import at.aau.serg.websocketdemoapp.networking.WebSocketClient;
import websocketdemoapp.networking.WebSocketClient;

public class MainActivity extends AppCompatActivity {

    TextView textViewServerResponse;
    WebSocketClient networkHandler;
    ProgressBar progressBar;
    ImageView imageView;

    //preferences over the complete app
    private SharedPreferences sharedPreferences;
    private static final String PREFS_NAME = "MyPrefs";
    private static final String KEY_VALUE = "storedValue";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //init for shared val
        sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        //just to init a value
        if (!sharedPreferences.contains(KEY_VALUE)) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(KEY_VALUE, "initValue");
            editor.apply();
        }

        /**to find the stuff from activity_main.xml*/
        imageView = findViewById(R.id.imageView);
        progressBar = findViewById(R.id.progressBar);

        imageView.setImageResource(R.drawable.img);


        //init websocketclient
        networkHandler = new WebSocketClient();
        //connect to websocket
        connectToWebSocketServer();
        //////////
        loadProgressBar();





        //Button buttonConnect = findViewById(R.id.buttonConnect);
        //buttonConnect.setOnClickListener(v -> connectToWebSocketServer());

        //Button buttonSendMsg = findViewById(R.id.buttonSendMsg);
        //buttonSendMsg.setOnClickListener(v -> sendMessage());

        /**to change from MainActivity to UserActivity*/
        /*Button buttonUserActivity = findViewById(R.id.buttonUserActivity);
        buttonUserActivity.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, UserActivity.class);
            startActivity(intent);
        });

        textViewServerResponse = findViewById(R.id.textViewResponse);

         */



        //connectToWebSocketServer();
        //loadProgressBar();
    }

    private void loadProgressBar() {
        progressBar.setVisibility(ProgressBar.VISIBLE);
        //connect to server
        //connectToWebSocketServer();

        new Handler().postDelayed(() -> {
            progressBar.setVisibility(ProgressBar.INVISIBLE);
            changeToLoginActivity();
        }, 3000);

        //change to other activity


    }

    //change to login mask
    private void changeToLoginActivity() {
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void connectToWebSocketServer() {
        // register a handler for received messages when setting up the connection
        runOnUiThread(() -> {
            networkHandler.connectToServer(this::messageReceivedFromServer);
        });

        //networkHandler.connectToServer(this::messageReceivedFromServer);
    }





    /*
    private void sendMessage() {
        networkHandler.sendMessageToServer("test message");
    }*/

    /*private void sendMessage() {
        TestMessage testMessage = new TestMessage("test message");
        String jsonMessage = new Gson().toJson(testMessage);
        runOnUiThread(() -> {
            networkHandler.sendMessageToServer(jsonMessage);
        });
        //networkHandler.sendMessageToServer(jsonMessage);
    }*/

    private void messageReceivedFromServer(String message) {
        // TODO handle received messages
        runOnUiThread(() -> {
            Log.d("Network", message);
            textViewServerResponse.setText(message);
        });
        //Log.d("Network", message);
        //textViewServerResponse.setText(message);
    }
}