package api.websocket;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class CustomWebSocketClient extends WebSocketClient implements AutoCloseable {

    private final BlockingQueue<String> messageQueue = new LinkedBlockingQueue<>();
    private static final Logger logger = LogManager.getLogger(CustomWebSocketClient.class);

    public CustomWebSocketClient(URI serverUri) {
        super(serverUri);
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        logger.info("WebSocket connection opened");
    }

    @Override
    public void onMessage(String message) {
        if (!messageQueue.offer(message)) {
            logger.warn("Failed to add message to queue: {}", message);
        }
        logger.info("Received message: {}", message);
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        logger.info("WebSocket connection closed. Code: {}, Reason: {}", code, reason);
    }

    @Override
    public void onError(Exception ex) {
        logger.error("WebSocket error occurred", ex);
    }

    public String receiveMessage() throws InterruptedException {
        String message = messageQueue.poll(10, java.util.concurrent.TimeUnit.SECONDS);
        logger.debug("Received message from queue: {}", message);
        return message;
    }
}
