package websocketdemoapp.activities;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.util.PatternsCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.gson.Gson;

import at.aau.serg.websocketdemoapp.R;

import websocketdemoapp.domain.User;
import websocketdemoapp.msg.UserMessage;
import websocketdemoapp.networking.WebSocketClient;

public class UserRegistrationActivity extends AppCompatActivity {

    TextView textViewServerResponse;

    //Button buttonAdd;


    WebSocketClient networkHandler;

    //neu
    //fields for activity_login.xml
    EditText editTextEmail;
    EditText editTextPassword;
    Button registerButton;
    Button backButton;

    String emailToSend;
    String passwordToSend;

    private SharedPreferences sharedPreferences;
    private static final String PREFS_NAME = "MyPrefs";
    private static final String KEY_VALUE = "storedValue";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_userregistration);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        networkHandler = new WebSocketClient();

        textViewServerResponse = findViewById(R.id.textViewResponse);
        //buttons
        registerButton = findViewById(R.id.registerButton);
        backButton = findViewById(R.id.backButton);
        // register also the fields
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        //click listener
        registerButton.setOnClickListener(v ->registerUser());
        backButton.setOnClickListener(v -> goBackToLogin());

    }
    private void goBackToLogin() {
        startActivity(new Intent(UserRegistrationActivity.this, LoginActivity.class));
        finish();
    }

    private void registerUser() {
        String email = editTextEmail.getText().toString();
        String password = editTextPassword.getText().toString();

        /*if (!isValidEmail(email)) {
            editTextEmail.setError("please enter a valid email address");
            return;
        }


        if (!isValidPassword(password)) {
            editTextPassword.setError("you need at least 8 chars for the pw...");
        }*/

        emailToSend = email;
        passwordToSend = password;
        UserMessage userMessage = new UserMessage(emailToSend, passwordToSend);
        userMessage.setActionType(UserMessage.ActionType.REGISTER);

        String jsonMessage = new Gson().toJson(userMessage);
        networkHandler.sendMessageToServer(jsonMessage);

        //logic for handling if it is a duplicate or not...
        //TODO
        //sendMessage();
        sendMessage(UserMessage.ActionType.REGISTER);
        connectToWebSocketServer();

        //calling response from server
        //networkHandler.connectToServer(this::messageReceivedFromServer);


    }

    private boolean isValidEmail(String email) {
        return PatternsCompat.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean isValidPassword(String password) {
        return password.length() >= 8;
    }

    private void sendMessage(UserMessage.ActionType actionType) {

        UserMessage userMessage = new UserMessage(emailToSend, passwordToSend);
        userMessage.setActionType(UserMessage.ActionType.REGISTER);
        //
        String jsonMessage = new Gson().toJson(userMessage);
        networkHandler.sendMessageToServer(jsonMessage);
    }

    @Override
    protected void onResume() {
        super.onResume();
        connectToWebSocketServer();
    }

    private void connectToWebSocketServer() {
        // register a handler for received messages when setting up the connection
        networkHandler.connectToServer(this::messageReceivedFromServer);
    }

    private void messageReceivedFromServer(String message) {
        Log.d("Network", message);

        //logic for adding, delete, update, usw.
        UserMessage userMessage = new Gson().fromJson(message, UserMessage.class);

        //some additional checks
        if (userMessage.getActionType().equals(UserMessage.ActionType.REGISTER_OK)) {
            //System.out.println("x");
            Log.d("network", "registration accepted");
            //sharedValues
            //init for shared val
            sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

            //set the value of the id identifier of the person as global val
            if (!sharedPreferences.contains(KEY_VALUE)) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(KEY_VALUE, userMessage.getId());
                editor.apply();

                //
                //if both checks are fine ... continue to start menu

            }

            startActivity(new Intent(UserRegistrationActivity.this, StartMenuActivity2.class));
            finish();




        }
        else if (userMessage.getActionType().equals(UserMessage.ActionType.REGISTER_ERR)) {
            Log.d("Network", "registration failed");
            textViewServerResponse.setText("registration failed...\n user already exist!");

        }



    }




}

