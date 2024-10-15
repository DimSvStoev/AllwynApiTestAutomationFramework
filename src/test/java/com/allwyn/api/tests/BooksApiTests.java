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
        int randomBookId = ApiHelper.getRandomBookID(restRequest);
        bookByIdEndpoint = String.format(FileUtil.getConfigValue("bookByIdUrl"), randomBookId);
    }

    @Test
    public void getAllBooks_ShouldReturnNonEmptyList() {
        Response response = restRequest.get(BOOKS_URL);
        response.then().statusCode(HttpStatusCode.OK.getCode())
                .body("size()", greaterThan(0));
    }

    @Test
    public void createBook_ShouldReturnCreatedBookAndMatchDetails() throws Exception {
        String requestBody = ApiHelper.generateUpdatedBooksJson(restRequest.get(BOOKS_URL));
        Response response = restRequest.sendPostRequestWithBody(requestBody, BOOKS_URL);
        JSONAssert.assertEquals(requestBody, response.getBody().asString(), JSONCompareMode.LENIENT);
        response.then().statusCode(HttpStatusCode.OK.getCode());
    }

    @Test
    public void getBookById_ShouldReturnBookDetails() throws Exception {
        Response response = restRequest.get(bookByIdEndpoint);
        int retrievedBookId = response.jsonPath().getInt("id");

        response.then().statusCode(HttpStatusCode.OK.getCode())
                .body("id", equalTo(retrievedBookId))
                .body("title", equalTo(String.format("Book %d", retrievedBookId)))
                .body("pageCount", equalTo(retrievedBookId * 100))
                .body("excerpt", not(emptyString()))
                .body("publishDate", not(emptyString()));
    }

    @Test
    public void updateBookById_ShouldReturnSuccessAndMatchUpdatedDetails() throws Exception {
        String requestBody = ApiHelper.generateUpdatedBooksJson(restRequest.get(bookByIdEndpoint));
        Response response = restRequest.sendPutRequestWithBody(requestBody, bookByIdEndpoint);
        JSONAssert.assertEquals(requestBody, response.getBody().asString(), JSONCompareMode.LENIENT);
        response.then().statusCode(HttpStatusCode.OK.getCode());
    }

    @Test
    public void deleteBookById_ShouldReturnSuccessStatus() throws Exception {
        Response response = restRequest.delete(bookByIdEndpoint);
        response.then().statusCode(HttpStatusCode.OK.getCode());
    }
    @Test
    public void createBook_WithInvalidBody_ShouldReturnBadRequest() {
        String invalidRequestBody = "#$%@!";

        Response response = restRequest.sendPostRequestWithBody(invalidRequestBody, BOOKS_URL);
        response.then().statusCode(HttpStatusCode.BAD_REQUEST.getCode());
    }
    @Test
    public void getBookById_WithInvalidEndpoint_ShouldReturnBadRequest() {
        String invalidBookId = "testStringAsWrongData";
        String endpointWithoutId = bookByIdEndpoint.substring(0, bookByIdEndpoint.lastIndexOf('/') + 1);

        String updatedBookByIdEndpoint = endpointWithoutId + invalidBookId;
        Response response = restRequest.get(updatedBookByIdEndpoint);
        response.then().statusCode(HttpStatusCode.BAD_REQUEST.getCode());
    }
}

