package api.utils;

import java.util.HashMap;
import java.util.Map;

public class ParameterBuilder {

    public static Map<String, String> createParams(String... params) {
        Map<String, String> result = new HashMap<>();
        for (int i = 0; i < params.length; i += 2) {
            result.put(params[i], params[i + 1]);
        }
        return result;
    }
}
