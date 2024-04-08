package at.aau.serg.websocketdemoserver.websocket.handler;

import at.aau.serg.websocketdemoserver.domains.User;
import at.aau.serg.websocketdemoserver.msg.BaseMessage;
import at.aau.serg.websocketdemoserver.msg.MessageType;
import at.aau.serg.websocketdemoserver.msg.TestMessage;
import at.aau.serg.websocketdemoserver.msg.UserMessage;
import at.aau.serg.websocketdemoserver.repository.InMemoryUserRepository;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.*;

public class WebSocketHandlerImpl implements WebSocketHandler {

    private Gson gson = new Gson();

    @Autowired
    private InMemoryUserRepository userRepository;


    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        System.out.println("reached point of afterConnectionEstablished");


    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        System.out.println("reached point of handleMessage");

        // TODO handle the messages here
        //session.sendMessage(new TextMessage("echo from handler: " + message.getPayload()));
        String payload = (String) message.getPayload();
        System.out.println("from client" + payload);

        handleMessageByType(session, payload);
    }

    private void handleMessageByType(WebSocketSession session, String payload) throws Exception {
        // Convert JSON to a generic message object to determine its type
        BaseMessage messageBase = gson.fromJson(payload, BaseMessage.class);

        // Check the message type and handle accordingly
        if (messageBase != null) {
            MessageType messageType = messageBase.getMessageType();
            switch (messageType) {
                case TEST:
                    handleTestMessage(session, payload);
                    break;
                case USER:
                    handleUserMessage(session, payload);
                    break;
                default:
                    System.out.println("Unknown message type received");
            }
        }
    }

    private void handleTestMessage(WebSocketSession session, String payload) throws Exception {
        // Convert JSON to TestMessage object
        TestMessage testMessage = gson.fromJson(payload, TestMessage.class);

        // Process the TestMessage
        System.out.println("Received TestMessage: " + testMessage.getText());

        // Send echo back to the client
        session.sendMessage(new TextMessage("echo from handler: " + payload));
    }

    private void handleUserMessage(WebSocketSession session, String payload) throws Exception {
        // Convert JSON to UserMessage object
        UserMessage userMessage = gson.fromJson(payload, UserMessage.class);

        System.out.println("Received TestMessage: " + userMessage.getName());

        // Send echo back to the client
        session.sendMessage(new TextMessage("echo from handler: " + payload));
    }

    public void handleAddAction (WebSocketSession session, UserMessage userMessage) {


    }

    public void handleDeleteAction (WebSocketSession session, UserMessage userMessage) {

    }

    public void handleUpdateAction (WebSocketSession session, UserMessage userMessage) {

    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        System.out.println("WebSocket transport error: " + exception.getMessage());

    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        System.out.println("WebSocket connection closed: " + closeStatus.getCode() + ", " + closeStatus.getReason());


    }

    @Override
    public boolean supportsPartialMessages() {
        System.out.println("reached point of supportsPartialMessages");

        return false;
    }
}
