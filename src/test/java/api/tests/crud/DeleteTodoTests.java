package api.tests.crud;

import api.tests.TestBase;
import api.utils.ApiUtils;
import api.utils.ResponseUtils;
import io.restassured.http.Header;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;


public class DeleteTodoTests extends TestBase {

    private final Header expectedHeader = new Header("Authorization",ApiUtils.getAuthHeader());

    @Test (dataProvider = "DeleteToDoData")
    public void deleteTodo(String pathParam, Header header, int expectedStatusCode) {
        ResponseUtils.checkResponseStatusCode(apiService.sendDeleteRequest(pathParam, header), expectedStatusCode);
    }

    @Test (dependsOnMethods = "api.tests.crud.GetTodosTests.validateResponseAfterDelete")
    public void deleteLastTodo() {
        ResponseUtils.checkResponseStatusCode(
                apiService.sendDeleteRequest("1000", expectedHeader), 204);
    }

    @DataProvider(name = "DeleteToDoData")
    Object[][] getData() {
        return new Object[][] {
                {"0", expectedHeader, 204},
                {"18446744073709551615", expectedHeader, 204},
                {"999", expectedHeader, 404},
                {"1000", new Header("Authorization", "Basic YWRtaW46"), 401},
                {"1000", null, 401}
        };
    }
//To Add:
    //Delete without id
    //pass wrong id data type "null" as ex
    //deleting the same todo twice
}