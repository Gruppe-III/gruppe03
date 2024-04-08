package at.aau.serg.websocketdemoapp.activities;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.gson.Gson;

import at.aau.serg.websocketdemoapp.R;
import at.aau.serg.websocketdemoapp.domain.User;
import at.aau.serg.websocketdemoapp.msg.UserMessage;
import at.aau.serg.websocketdemoapp.networking.WebSocketClient;
import at.aau.serg.websocketdemoapp.repo.LocalRepoUser;

public class UserActivity extends AppCompatActivity {

    TextView textViewServerResponse;
    EditText editTextUserName;
    Button buttonAdd;
    Button buttonDelete;
    Button buttonUpdate;

    WebSocketClient networkHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_user);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        textViewServerResponse = findViewById(R.id.textViewResponse);
        editTextUserName = findViewById(R.id.editTextUserName);
        buttonAdd = findViewById(R.id.buttonAdd);
        buttonDelete = findViewById(R.id.buttonDelete);
        buttonUpdate = findViewById(R.id.buttonUpdate);

        networkHandler = new WebSocketClient();

        buttonAdd.setOnClickListener(v -> sendMessage(UserMessage.ActionType.ADD));
        buttonDelete.setOnClickListener(v -> sendMessage(UserMessage.ActionType.DELETE));
        buttonUpdate.setOnClickListener(v -> sendMessage(UserMessage.ActionType.UPDATE));

    }

    private void sendMessage(UserMessage.ActionType actionType) {
        String name = editTextUserName.getText().toString();
        UserMessage userMessage = new UserMessage(name);
        userMessage.setActionType(actionType);
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

        //as a trial if i reach the point
        //textViewServerResponse.setText(message);

        // Überprüfe den ActionType und handle entsprechend
        /*switch (userMessage.getActionType()) {
            case ADD:
                handleAddAction(userMessage);
                break;
            case DELETE:
                handleDeleteAction(userMessage);
                break;
            case UPDATE:
                handleUpdateAction(userMessage);
                break;
            default:
                Log.e("Network", "Unknown action type received");
                break;
        }*/

        textViewServerResponse.setText(message);
    }

    void handleAddAction(UserMessage userMessage) {
        //extract info from message
        User userToAdd = new User();
        userToAdd.setId(userMessage.getId());
        userToAdd.setUsername(userMessage.getName());
        userToAdd.setPoints(userMessage.getPoints());

        //now real add to repository
        LocalRepoUser.getInstance().addUser(userToAdd);
        // message on screen
        textViewServerResponse.setText("User added successfully");
    }

    void handleDeleteAction(UserMessage userMessage) {

    }

    void handleUpdateAction(UserMessage userMessage) {

    }


}

