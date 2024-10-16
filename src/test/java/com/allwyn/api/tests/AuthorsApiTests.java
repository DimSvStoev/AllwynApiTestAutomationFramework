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

public class AuthorsApiTests extends BaseRestApi {
    private String authorByIdEndpoint;
    private String booksByAuthorEndpoint;
    private int randomAuthorId;
    private int randomBookId;
    private static final String AUTHORS_URL = ApiHelper.formatStringToUrl("authorsUrl");

    @BeforeEach
    public void setUp() throws Exception {
        init();
        randomAuthorId = ApiHelper.getRandomAuthorID(restRequest);
        randomBookId = ApiHelper.getRandomBookID(restRequest);
        authorByIdEndpoint = String.format(FileUtil.getConfigValue("authorByIdUrl"), randomAuthorId);
        booksByAuthorEndpoint = String.format(FileUtil.getConfigValue("booksByAuthorUrl"), randomBookId);
    }

    @Test
    public void getAllAuthors_ShouldReturnNonEmptyList() {
        Response response = restRequest.get(AUTHORS_URL);
        response.then().statusCode(HttpStatusCode.OK.getCode())
                .body("size()", greaterThan(0));
    }

    @Test
    public void createAuthor_ShouldReturnCreatedAuthorAndMatchDetails() throws Exception {
        String requestBody = ApiHelper.generateNewAuthorJson(restRequest.get(AUTHORS_URL));
        Response response = restRequest.sendPostRequestWithBody(requestBody, AUTHORS_URL);
        JSONAssert.assertEquals(requestBody, response.getBody().asString(), JSONCompareMode.LENIENT);
        response.then().statusCode(HttpStatusCode.OK.getCode());
    }

    @Test
    public void getAuthorById_ShouldReturnMatchingAuthorDetails() throws Exception {
        Response response = restRequest.get(authorByIdEndpoint);
        int authorId = response.jsonPath().getInt("id");

        response.then().statusCode(HttpStatusCode.OK.getCode())
                .body("id", equalTo(authorId))
                .body("idBook", not(nullValue()))
                .body("firstName", containsString(Integer.toString(authorId)))
                .body("lastName", containsString(Integer.toString(authorId)));
    }

    @Test
    public void updateAuthorById_ShouldReturnSuccessAndMatchUpdatedDetails() throws Exception {
        String requestBody = ApiHelper.generateNewAuthorJson(restRequest.get(authorByIdEndpoint));
        Response response = restRequest.sendPutRequestWithBody(requestBody, authorByIdEndpoint);
        JSONAssert.assertEquals(requestBody, response.getBody().asString(), JSONCompareMode.LENIENT);
        response.then().statusCode(HttpStatusCode.OK.getCode());
    }

    @Test
    public void deleteAuthorById_ShouldReturnSuccessStatus() throws Exception {
        Response response = restRequest.delete(authorByIdEndpoint);
        response.then().statusCode(HttpStatusCode.OK.getCode());
    }

    @Test
    public void getAuthorsByBookId_ShouldReturnAuthorsForSpecifiedBook() throws Exception {
        Response response = restRequest.get(booksByAuthorEndpoint);
        response.then().statusCode(HttpStatusCode.OK.getCode())
                .body("idBook", everyItem(equalTo(randomBookId)))
                .body("firstName", everyItem(not(emptyString())))
                .body("lastName", everyItem(not(emptyString())));
    }
    @Test
    public void createAuthor_WithInvalidBody_ShouldReturnBadRequest() {
        String invalidRequestBody = "#$%@!";

        Response response = restRequest.sendPostRequestWithBody(invalidRequestBody, AUTHORS_URL);
        response.then().statusCode(HttpStatusCode.BAD_REQUEST.getCode());
    }

    @Test
    public void getAuthorById_WithInvalidEndpoint_ShouldReturnBadRequest() {
        String invalidAuthorId = "invalidAuthor123";
        String endpointWithoutId = authorByIdEndpoint.substring(0, authorByIdEndpoint.lastIndexOf('/') + 1);

        String updatedAuthorByIdEndpoint = endpointWithoutId + invalidAuthorId;
        Response response = restRequest.get(updatedAuthorByIdEndpoint);
        response.then().statusCode(HttpStatusCode.BAD_REQUEST.getCode());
    }
}

