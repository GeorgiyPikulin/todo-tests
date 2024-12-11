package api.tests.crud;

import api.models.ToDo;
import api.tests.TestBase;
import api.utils.ResponseUtils;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class CreateTodoTests extends TestBase {

    public static List<ToDo> createdTodos = new ArrayList<>();

    @Test (dataProvider = "CreateToDoData")
    public void createTodo(ToDo todo, int expectedStatusCode) {
        ResponseUtils.checkResponseStatusCode(apiService.sendPostRequest(todo), expectedStatusCode);
        if (expectedStatusCode == 201) {
            createdTodos.add(todo);
        }
    }

    @DataProvider(name = "CreateToDoData")
    Object[][] getData() {
        return new Object[][] {
                {new ToDo("0", "Test", true), 201},
                {new ToDo("18446744073709551615", "Test", false), 201},
                {new ToDo("999",
                        " Test 3!'@#$%:^&*;\"/{}[]?~`§±()+=-_,.№<>   Тест Ё Й   ывапынппмчрмрВРОЛЛЫарПППШШ ",
                        true), 201},
                {new ToDo("Test4", false), 400},
                {new ToDo("999","Test5", false), 400}
        };
    }
//To Add:
    //order of ids - logic not supported
    //id -1
    //with empty text "" - allows to create
    //id - 18446744073709551615 + 1
    //empty body
    //without text
    //without completed
    //wrong data type in each + add validation of response
    //wrong body data type
}
