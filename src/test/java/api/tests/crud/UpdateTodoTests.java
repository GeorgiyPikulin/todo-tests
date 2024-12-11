package api.tests.crud;

import api.models.ToDo;
import api.tests.TestBase;
import api.utils.ResponseUtils;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class UpdateTodoTests extends TestBase {

    public static List<ToDo> updatedTodos = new ArrayList<>();

    @Test (dataProvider = "UpdateToDoData")
    public void updateTodo(ToDo todo, String pathParam, int expectedStatusCode) {
        ResponseUtils.checkResponseStatusCode(apiService.sendPutRequest(todo,pathParam), expectedStatusCode);
        if (expectedStatusCode == 200) {
            updatedTodos.add(todo);
        }
    }

    @DataProvider(name = "UpdateToDoData")
    Object[][] getData() {
        return new Object[][] {
                {new ToDo("0", "Test", false), "0", 200},
                {new ToDo("18446744073709551615", "Test", true), "18446744073709551615", 200},
                {new ToDo("1000", "", true), "999", 200},
                {new ToDo("77","Test", true), "77",404}
        };
    }
//To Add:
    //update id and check order of ids - logic not supported
    //update id to -1 - Unauthorized status code
    //empty body - Unauthorized status code
    //empty id - Unauthorized status code
    //without text - Unauthorized status code
    //without completed - Unauthorized status code
    //wrong data type in each - Unauthorized status code
    //wrong body data type - Unauthorized status code
}
