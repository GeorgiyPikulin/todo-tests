package api.tests.websocket;

import api.models.ToDo;
import api.tests.TestBase;
import api.utils.ApiUtils;
import api.websocket.CustomWebSocketClient;
import api.utils.ResponseUtils;
import io.restassured.http.Header;
import org.testng.annotations.Test;

import java.net.URI;

import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

public class WebSocketTest extends TestBase {

    @Test
    public void testWebSocketConnection() throws Exception {
        try (CustomWebSocketClient client = new CustomWebSocketClient(new URI(ApiUtils.getWebSocketUri()))) {
            client.connectBlocking();
            assertTrue(client.isOpen(), "WebSocket connection should be open");

            ResponseUtils.checkResponseStatusCode(
                    apiService.sendPostRequest(new ToDo("13", "TestWS", true)),201);

            String message = client.receiveMessage();
            assertNotNull(message, "Message should be received");
            assertTrue(message.equalsIgnoreCase(
                    "{\"data\":{\"completed\":true,\"id\":13,\"text\":\"TestWS\"},\"type\":\"new_todo\"}"),
                    "Message should contain 'new_todo'");

            ResponseUtils.checkResponseStatusCode(apiService.sendDeleteRequest("13",
                    new Header("Authorization", ApiUtils.getAuthHeader())), 204);
        }
    }
}
