package com.allwyn.api.tests;

import com.depositsolutions.posintegration.common.BaseRestApi;
import com.depositsolutions.posintegration.common.FileUtil;
import com.depositsolutions.posintegration.common.ApiHelper;
import com.depositsolutions.posintegration.enums.api.HttpStatusCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import io.restassured.response.Response;
import static org.hamcrest.Matchers.*;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

public class BooksApiTests extends BaseRestApi {

    private static final String BOOKS_URL = ApiHelper.formatStringToUrl("booksUrl");
    private String bookByIdEndpoint;

    @BeforeEach
    public void setUp() throws Exception {
        init();
        int randomBookId = generateRandomBookId();
        bookByIdEndpoint = formatBookByIdEndpoint(randomBookId);
    }

    private int generateRandomBookId() throws Exception {
        return ApiHelper.getRandomBookID(restRequest);
    }

    private String formatBookByIdEndpoint(int randomBookId) {
        return String.format(FileUtil.getConfigValue("bookByIdUrl"), randomBookId);
    }

    @Test
    public void getAllBooks_ShouldReturnNonEmptyList() {
        Response response = sendGetRequest(BOOKS_URL);
        validateNonEmptyBookList(response);
    }

    private Response sendGetRequest(String url) {
        return restRequest.get(url);
    }

    private void validateNonEmptyBookList(Response response) {
        response.then().statusCode(HttpStatusCode.OK.getCode())
                .body("size()", greaterThan(0));
    }

    @Test
    public void createBook_ShouldReturnCreatedBookAndMatchDetails() throws Exception {
        String requestBody = generateNewBookJson();
        Response response = sendPostRequestWithBody(requestBody, BOOKS_URL);
        validateBookDetailsMatch(requestBody, response);
    }

    private String generateNewBookJson() throws Exception {
        return ApiHelper.generateUpdatedBooksJson(restRequest.get(BOOKS_URL));
    }

    private Response sendPostRequestWithBody(String body, String url) {
        return restRequest.sendPostRequestWithBody(body, url);
    }

    private void validateBookDetailsMatch(String expectedRequest, Response response) throws Exception {
        JSONAssert.assertEquals(expectedRequest, response.getBody().asString(), JSONCompareMode.LENIENT);
        response.then().statusCode(HttpStatusCode.OK.getCode());
    }

    @Test
    public void getBookById_ShouldReturnBookDetails() throws Exception {
        Response response = sendGetRequest(bookByIdEndpoint);
        int retrievedBookId = response.jsonPath().getInt("id");
        validateBookDetails(response, retrievedBookId);
    }

    private void validateBookDetails(Response response, int retrievedBookId) {
        response.then().statusCode(HttpStatusCode.OK.getCode())
                .body("id", equalTo(retrievedBookId))
                .body("title", equalTo(String.format("Book %d", retrievedBookId)))
                .body("pageCount", equalTo(retrievedBookId * 100))
                .body("excerpt", not(emptyString()))
                .body("publishDate", not(emptyString()));
    }

    @Test
    public void updateBookById_ShouldReturnSuccessAndMatchUpdatedDetails() throws Exception {
        String requestBody = generateUpdatedBookJson();
        Response response = sendPutRequestWithBody(requestBody, bookByIdEndpoint);
        validateUpdatedBookDetails(requestBody, response);
    }

    private String generateUpdatedBookJson() throws Exception {
        return ApiHelper.generateUpdatedBooksJson(restRequest.get(bookByIdEndpoint));
    }

    private Response sendPutRequestWithBody(String body, String url) {
        return restRequest.sendPutRequestWithBody(body, url);
    }

    private void validateUpdatedBookDetails(String expectedRequest, Response response) throws Exception {
        JSONAssert.assertEquals(expectedRequest, response.getBody().asString(), JSONCompareMode.LENIENT);
        response.then().statusCode(HttpStatusCode.OK.getCode());
    }

    @Test
    public void deleteBookById_ShouldReturnSuccessStatus() throws Exception {
        Response response = sendDeleteRequest(bookByIdEndpoint);
        validateDeleteSuccess(response);
    }

    private Response sendDeleteRequest(String url) {
        return restRequest.delete(url);
    }

    private void validateDeleteSuccess(Response response) {
        response.then().statusCode(HttpStatusCode.OK.getCode());
    }

    @Test
    public void createBook_WithInvalidBody_ShouldReturnBadRequest() {
        String invalidRequestBody = "#$%@!";
        Response response = sendPostRequestWithBody(invalidRequestBody, BOOKS_URL);
        validateBadRequest(response);
    }

    private void validateBadRequest(Response response) {
        response.then().statusCode(HttpStatusCode.BAD_REQUEST.getCode());
    }

    @Test
    public void getBookById_WithInvalidEndpoint_ShouldReturnBadRequest() {
        String invalidBookId = "testStringAsWrongData";
        String updatedBookByIdEndpoint = formatInvalidBookByIdEndpoint(invalidBookId);
        Response response = sendGetRequest(updatedBookByIdEndpoint);
        validateBadRequest(response);
    }

    private String formatInvalidBookByIdEndpoint(String invalidBookId) {
        String endpointWithoutId = bookByIdEndpoint.substring(0, bookByIdEndpoint.lastIndexOf('/') + 1);
        return endpointWithoutId + invalidBookId;
    }
}
