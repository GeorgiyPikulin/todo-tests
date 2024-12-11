package api.tests.crud;

import api.models.ToDo;
import api.tests.TestBase;
import api.utils.ParameterBuilder;
import api.utils.ResponseUtils;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class GetTodosTests extends TestBase {

    @Test (dependsOnMethods = "api.tests.crud.CreateTodoTests.createTodo")
    public void checkAllFieldsAfterCreation() {
        Response response = apiService.sendGetRequest();
        ResponseUtils.checkResponseStatusCode(response, 200);
        ResponseUtils.assertResponseContent(response, CreateTodoTests.createdTodos);
    }

    @Test (dependsOnMethods = "api.tests.crud.CreateTodoTests.createTodo")
    public void validateIdsWithLimit() {
        Response response = apiService.sendRequestWithParams(ParameterBuilder.createParams("limit", "2"));
        ResponseUtils.checkResponseStatusCode(response,200);
        ResponseUtils.assertResponseWithLimit(response, CreateTodoTests.createdTodos,2);
    }

    @Test (dependsOnMethods = "api.tests.crud.CreateTodoTests.createTodo")
    public void validateIdsWithOffset() {
        Response response = apiService.sendRequestWithParams(ParameterBuilder.createParams("offset", "1"));
        ResponseUtils.checkResponseStatusCode(response,200);
        ResponseUtils.assertResponseWithOffset(response, CreateTodoTests.createdTodos,1);
    }

    @Test (dependsOnMethods = "api.tests.crud.CreateTodoTests.createTodo")
    public void validateIdsWithOffsetAndLimit() {
        Response response = apiService.sendRequestWithParams(ParameterBuilder.createParams("offset", "1","limit", "1"));
        ResponseUtils.checkResponseStatusCode(response,200);
        ResponseUtils.assertResponseWithOffsetAndLimit(response, CreateTodoTests.createdTodos,1,1);

    }

    @Test (dependsOnMethods = "api.tests.crud.UpdateTodoTests.updateTodo")
    public void checkAllFieldsAfterUpdate() {
        Response response = apiService.sendGetRequest();
        ResponseUtils.checkResponseStatusCode(response, 200);
        ResponseUtils.assertResponseContent(response, UpdateTodoTests.updatedTodos);
    }

    @Test (dependsOnMethods = "api.tests.crud.DeleteTodoTests.deleteTodo")
    public void validateResponseAfterDelete() {
        Response response = apiService.sendGetRequest();
        ResponseUtils.checkResponseStatusCode(response,200);
        List<ToDo> expectedTodos = new ArrayList<>();
        expectedTodos.add(new ToDo("1000", "", true));
        ResponseUtils.assertResponseContent(response, expectedTodos);
    }
//To Add:
    //    limit=0 - empty list
    //    offset=3 - empty list
    //    offset=0&limit=0 - empty list
    //    limit=-1 or offset=-1 - Bad Request
}
