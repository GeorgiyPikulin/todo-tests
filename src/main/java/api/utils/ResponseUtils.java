package api.utils;

import api.models.ToDo;
import io.restassured.response.Response;

import java.util.List;

import static org.testng.Assert.*;

public class ResponseUtils {

    public static void checkResponseStatusCode(Response response, int expectedStatusCode) {
        assertEquals(response.getStatusCode(), expectedStatusCode, "Unexpected status code");
    }

    public static List<ToDo> getTodosFromResponse(Response response) {
        return response.then().extract().body().jsonPath().getList("", ToDo.class);
    }

    public static void assertSize(List<ToDo> actualTodos,int expectedTodosSize) {
        assertEquals(actualTodos.size(), expectedTodosSize,
                "The number of ToDo items does not match the expected number");
    }

    public static void assertResponseContent(Response response, List<ToDo> expectedTodos) {
        List<ToDo> actualTodos = getTodosFromResponse(response);
        assertSize(actualTodos, expectedTodos.size());
        assertEquals(actualTodos, expectedTodos, "The ToDo lists do not match");
    }

    public static void assertResponseWithLimit(Response response, List<ToDo> expectedTodos, int limitValue) {
        List<ToDo> actualTodos = getTodosFromResponse(response);
        assertSize(actualTodos, limitValue);
            for (int i = 0; i < actualTodos.size(); i++) {
                assertEquals(actualTodos.get(i), expectedTodos.get(i),
                        "Mismatched ToDo at index " + i);
            }
    }

    public static void assertResponseWithOffset(Response response, List<ToDo> expectedTodos, int offsetValue) {
        List<ToDo> actualTodos = getTodosFromResponse(response);
        assertSize(actualTodos,expectedTodos.size()-offsetValue);
            for (int i = 0; i < actualTodos.size(); i++) {
                assertEquals(actualTodos.get(i), expectedTodos.get(i+offsetValue),
                        "Mismatched ToDo at index " + i);
            }
    }

    public static void assertResponseWithOffsetAndLimit(
            Response response, List<ToDo> expectedTodos, int offsetValue, int limitValue) {
                List<ToDo> actualTodos = getTodosFromResponse(response);
                assertSize(actualTodos,limitValue);
                for (int i = 0; i < actualTodos.size(); i++) {
                    assertEquals(actualTodos.get(i), expectedTodos.get(i+offsetValue),
                            "Mismatched ToDo at index " + i);
                }
    }
}
