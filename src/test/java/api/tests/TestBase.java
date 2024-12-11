package api.tests;

import api.service.ApiService;
import org.testng.annotations.BeforeSuite;

public class TestBase {
    protected static ApiService apiService;

    @BeforeSuite
    public void setUp() {
        apiService = new ApiService();
    }
}
