package api.utils;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import static io.restassured.RestAssured.given;

public class ApiUtils {

    private static final Properties properties = new Properties();
    private static String resource;
    private static String webSocketUri;
    private static final Logger logger = LogManager.getLogger(ApiUtils.class);

    static {
        try (FileInputStream inputStream = new FileInputStream("src/main/resources/global.properties")) {
            properties.load(inputStream);
            resource = properties.getProperty("resource");
            webSocketUri = properties.getProperty("webSocketUri");
        } catch (IOException e) {
            logger.error("Failed to load properties", e);
        }
    }

    public static String getResource() {
        return resource;
    }

    public static String buildResource(String propertyName) {
        return resource + properties.getProperty(propertyName);
    }

    public static String getWebSocketUri() {
        return webSocketUri;
    }

    public static String getAuthHeader() {
        return properties.getProperty("authHeader");
    }

    public static RequestSpecification buildRequestSpecification() {
        return given().spec(new RequestSpecBuilder()
                .setBaseUri(properties.getProperty("baseUri"))
                .build());
    }
}

