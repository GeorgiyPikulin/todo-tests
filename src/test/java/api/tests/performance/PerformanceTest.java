package api.tests.performance;

import api.models.ToDo;
import api.tests.TestBase;
import api.utils.ApiUtils;
import api.utils.ResponseUtils;
import io.restassured.http.Header;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.testng.Assert.assertTrue;

public class PerformanceTest extends TestBase {

    private static final Logger logger = LogManager.getLogger(PerformanceTest.class);

    @Test
    public void testPostTodosPerformance() {
        int requestCount = 100;
        List<Long> durations = new ArrayList<>();
        List<String> createdIds = new ArrayList<>();
        long totalStartTime = System.nanoTime();

        for (int i = 0; i < requestCount; i++) {
            int counter = i + 1;
            ToDo todo = new ToDo(String.valueOf(counter), "Test Todo " + counter, counter % 2 == 0);
            long startTime = System.nanoTime();
            logger.debug("Sending POST request for ToDo: {}", todo);
            ResponseUtils.checkResponseStatusCode(apiService.sendPostRequest(todo),201);
            long endTime = System.nanoTime();
            durations.add(TimeUnit.NANOSECONDS.toMillis(endTime - startTime));
            createdIds.add(String.valueOf(counter));
        }

        long totalEndTime = System.nanoTime();
        long totalDurationMs = TimeUnit.NANOSECONDS.toMillis(totalEndTime - totalStartTime);

        long maxDuration = durations.stream().max(Long::compareTo).orElse(0L);
        long minDuration = durations.stream().min(Long::compareTo).orElse(0L);
        double avgDuration = durations.stream().mapToLong(Long::longValue).average().orElse(0.0);

        logger.info("Performance test completed for {} requests", requestCount);
        logger.info("Total time: {} ms", totalDurationMs);
        logger.info("Average time per request: {} ms", avgDuration);
        logger.info("Max time per request: {} ms", maxDuration);
        logger.info("Min time per request: {} ms", minDuration);

        assertTrue(totalDurationMs < 5000, "Performance test should complete within 5 seconds");
        assertTrue(avgDuration < 50, "Average time per request should be less than 50 ms");

        logger.info("Deleting created ToDos");
        for (String id : createdIds) {
            ResponseUtils.checkResponseStatusCode(
                    apiService.sendDeleteRequest(
                            id, new Header("Authorization", ApiUtils.getAuthHeader())),204);
        }
        logger.info("All created ToDos deleted successfully");
    }
}
