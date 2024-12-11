package api;

import listeners.CustomTestListener;
import org.testng.TestNG;

import java.util.List;

public class TestRunner {

    public static void main(String[] args) {
        TestNG testng = new TestNG();
        testng.addListener(new CustomTestListener());
        testng.setTestSuites(List.of("src/test/resources/testng.xml"));
        testng.run();
    }
}
