package api.service;

import api.utils.ApiUtils;
import api.enums.HttpMethod;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.Map;

public class ApiService {

    public Response sendPostRequest(Object body) {
        return sendRequestWithBody(body, HttpMethod.POST);
    }

    public Response sendPutRequest(Object body, String pathParam) {
        return sendPutRequestWithBodyAndPathParams(body, pathParam);
    }

    public Response sendGetRequest() {
        return sendRequestWithoutBody(HttpMethod.GET);
    }

    public Response sendDeleteRequest(String pathParam, Header header) {
        return sendDeleteRequestWithPathParams(pathParam, header);
    }

    public Response sendRequestWithParams(Map<String, String> params) {
        RequestSpecification requestSpecification = ApiUtils.buildRequestSpecification();
        params.forEach(requestSpecification::queryParam);
        return requestSpecification.when().get(ApiUtils.getResource());
    }

    private Response sendRequestWithBody(Object body, HttpMethod method) {
        return sendRequest(ApiUtils.buildRequestSpecification().body(body), method);
    }

    private Response sendPutRequestWithBodyAndPathParams(Object body, String pathParam) {
        String resourceWithPathParams = ApiUtils.buildResource("pathParam");
        return ApiUtils.buildRequestSpecification()
                .pathParam("pathParam", pathParam)
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .put(resourceWithPathParams);
    }

    private Response sendDeleteRequestWithPathParams(String pathParam, Header header) {
        String resourceWithPathParams = ApiUtils.buildResource("pathParam");
        RequestSpecification requestSpecification = ApiUtils.buildRequestSpecification();
        if (header != null) {
            requestSpecification.header(header);
        }
        return requestSpecification
                .pathParam("pathParam", pathParam)
                .when()
                .delete(resourceWithPathParams);
    }

    private Response sendRequestWithoutBody(HttpMethod method) {
        return sendRequest(ApiUtils.buildRequestSpecification(), method);
    }

    private Response sendRequest(RequestSpecification requestSpec, HttpMethod method) {
        return switch (method) {
            case POST -> requestSpec.contentType(ContentType.JSON).when().post(ApiUtils.getResource());
            case GET -> requestSpec.when().get(ApiUtils.getResource());
        };
    }
}

