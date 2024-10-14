package com.depositsolutions.posintegration.common;
import org.json.JSONObject;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileUtil {
    private static JSONObject configData;
    static {
        try {
            String content = new String(Files.readAllBytes(Paths.get("src/test/resources/test-data/endPointsConfig.json")));
            configData = new JSONObject(content);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getConfigValue(String key) {
        return configData.getString(key);
    }
}
