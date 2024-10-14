package com.depositsolutions.posintegration.common;
import com.depositsolutions.posintegration.core.RestRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class ApiHelper {

    public static int getRandomExistID(RestRequest restRequest, String resourcePath) throws Exception {
        Response response = restRequest.get(formatStringToUrl(resourcePath));
        response.then().statusCode(200);

        List<Integer> ids = response.jsonPath().getList("id");

        if (ids.isEmpty()) {
            throw new Exception("No IDs found in response from " + resourcePath);
        }

        Random random = new Random();
        return ids.get(random.nextInt(ids.size()));
    }

    public static int getRandomBookID(RestRequest restRequest) throws Exception {
        return getRandomExistID(restRequest, "booksUrl");
    }

    public static int getRandomAuthorID(RestRequest restRequest) throws Exception {
        return getRandomExistID(restRequest, "authorsUrl");
    }

    public static String generateJsonWithParams(Map<String, Object> params) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(params);
    }

    public static String generateUpdatedBooksJson(Response response) throws Exception {
        Random random = new Random();
        int bookId = extractId(response);

        Map<String, Object> bookData = new HashMap<>();
        bookData.put("id", bookId);
        bookData.put("title", RandomStringUtils.randomAlphanumeric(10) + bookId);
        bookData.put("description", RandomStringUtils.randomAlphanumeric(10) + bookId);
        bookData.put("pageCount", random.nextInt(500));
        bookData.put("excerpt", RandomStringUtils.randomAlphanumeric(10) + random.nextInt(1000));
        String publishDate = ZonedDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"));
        bookData.put("publishDate", publishDate);

        return generateJsonWithParams(bookData);
    }

    public static String generateNewAuthorJson(Response response) throws Exception {
        int authorId = extractId(response) + 1;

        Map<String, Object> authorData = new HashMap<>();
        authorData.put("id", authorId);
        authorData.put("firstName", RandomStringUtils.randomAlphabetic(20));
        authorData.put("lastName", RandomStringUtils.randomAlphabetic(20));

        return generateJsonWithParams(authorData);
    }

    public static int extractId(Response response) throws Exception {
        Object idObject = response.jsonPath().get("id");
        if (idObject instanceof List) {
            List<Integer> ids = response.jsonPath().getList("id", Integer.class);
            return ids.get(ids.size() - 1);
        } else if (idObject instanceof Integer) {
            return (Integer) idObject;
        }
        throw new Exception("No valid ID found in response");
    }

    public static String formatStringToUrl(String endpoint) {
        return String.format(FileUtil.getConfigValue(endpoint));
    }
}