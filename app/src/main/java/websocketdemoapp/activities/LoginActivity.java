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
import websocketdemoapp.msg.UserMessage;
import websocketdemoapp.networking.WebSocketClient;

public class LoginActivity extends AppCompatActivity {

    //fields for activity_login.xml
    EditText editTextEmail;
    EditText editTextPassword;
    Button loginButton;
    Button registerButton;

    TextView textViewServerResponse;

    WebSocketClient networkHandler;

    String emailToSend;
    String passwordToSend;


    private SharedPreferences sharedPreferences;
    private static final String PREFS_NAME = "MyPrefs";
    private static final String KEY_VALUE = "storedValue";


    //@SuppressLint("MissingInflatedId") //just for now
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        networkHandler = new WebSocketClient();


        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        loginButton = findViewById(R.id.loginButton);
        registerButton = findViewById(R.id.registerButton);

        loginButton.setOnClickListener(v -> loginUser());
        registerButton.setOnClickListener(v -> registerUser());
    }


    private void loginUser() {
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
        userMessage.setActionType(UserMessage.ActionType.LOGIN);
        //userMessage.setId("empty");

        String jsonMessage = new Gson().toJson(userMessage);
        networkHandler.sendMessageToServer(jsonMessage);

        //logic for handling if it is a duplicate or not...
        //TODO
        sendMessage(UserMessage.ActionType.LOGIN);
        connectToWebSocketServer();


        //calling response from server
        //networkHandler.connectToServer(this::messageReceivedFromServer);


    }

    @Override
    protected void onResume() {
        super.onResume();
        connectToWebSocketServer();
    }

    private void connectToWebSocketServer() {
        networkHandler.connectToServer(this::messageReceivedFromServer);
    }

    /**small methods for handling stuff*/

    //refactor to a own class --> maybe...
    private boolean isValidEmail(String email) {
        return PatternsCompat.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean isValidPassword(String password) {
        return password.length() >= 8;
    }

    private void registerUser() {
        //TODO go to register user plattform maybe reuse the userActivity
        startActivity(new Intent(LoginActivity.this, UserRegistrationActivity.class));
        finish();
    }

    private void sendMessage(UserMessage.ActionType actionType) {
        /// sofern die daten durch den check gegangen sind,
        // können sie auch gesendet werden

        UserMessage userMessage = new UserMessage(emailToSend, passwordToSend);
        userMessage.setActionType(actionType);
        String jsonMessage = new Gson().toJson(userMessage);

        networkHandler.sendMessageToServer(jsonMessage);
    }

    private void messageReceivedFromServer(String message) {
        Log.d("Network", message);

        //logic for adding, delete, update, usw.
        UserMessage userMessage = new Gson().fromJson(message, UserMessage.class);

        if (userMessage.getActionType().equals(UserMessage.ActionType.LOGIN_OK)) {
            Log.d("network", "Login accepted");

            sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

            if (!sharedPreferences.contains(KEY_VALUE)) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(KEY_VALUE, userMessage.getId());
                editor.apply();
            }

            startActivity(new Intent(LoginActivity.this, StartMenuActivity2.class));
            finish();

        } else if (userMessage.getActionType().equals(UserMessage.ActionType.LOGIN_ERR)) {
            Log.d("Network", "login failed");
            textViewServerResponse.setText("login failed...\n email or password wrong!");
            // Don't navigate to another activity here
        }
    }



}