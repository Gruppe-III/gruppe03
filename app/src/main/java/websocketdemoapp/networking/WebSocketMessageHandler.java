package websocketdemoapp.networking;

public interface WebSocketMessageHandler<T> {

    void onMessageReceived(T message);
    //zusatz
    //Class<T> getMessageType();
    
}
