package com.depositsolutions.posintegration.baseline.administrationapi;

import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;

import com.depositsolutions.posintegration.common.AssertionUtil;
import com.depositsolutions.posintegration.common.CsvFileReader;
import com.depositsolutions.posintegration.common.DateUtil;
import com.depositsolutions.posintegration.common.DateUtil.DateFormat;
import com.depositsolutions.posintegration.common.JsonMapper;
import com.depositsolutions.posintegration.common.MailHogUtil;
import com.depositsolutions.posintegration.common.Wait;
import com.depositsolutions.posintegration.core.AdminApi;
import com.depositsolutions.posintegration.core.MailHogApi;
import com.depositsolutions.posintegration.core.PointOfSaleApi;
import com.depositsolutions.posintegration.core.RestRequest;
import com.depositsolutions.posintegration.core.database.DbDataExtractor;
import com.depositsolutions.posintegration.core.database.enums.Query;
import com.depositsolutions.posintegration.core.database.enums.SwaggerInputParameter;
import com.depositsolutions.posintegration.enums.api.AdminResource;
import com.depositsolutions.posintegration.enums.api.MailHogResource;
import com.depositsolutions.posintegration.enums.api.PointOfSaleResource;
import com.depositsolutions.posintegration.enums.common.RequestType;
import com.depositsolutions.posintegration.enums.common.TransactionRequestStatus;
import com.depositsolutions.posintegration.objects.Order;
import com.depositsolutions.posintegration.objects.PointOfSale;
import com.depositsolutions.posintegration.objects.Transaction;
import com.depositsolutions.posintegration.objects.TransactionRequest;
import com.google.common.collect.ImmutableMap;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class CompleteSettlementProcessForPayOutTest {
	private final String ORDERS_BATCH = "test-data/orders-batch.csv";
	
	private final String POINT_OF_SALE_INFO = "test-data/point-of-sale.json";

	@Test
	public void completeSettlementForPayout() {
//		RequestSpecification spec =  RestAssured.given()
//		.auth().basic("posintegration", "posintegration")
//		.baseUri("http://pos-integration-deposit-solutions-test.apps.openshift.musala.com/posintegration")
//		.header("X-DS-Tenant-Id", "gold_auth");
//		
//		spec
//		.pathParam(SwaggerInputParameter.POS_IDENTIFIER.getName(), "POS00JNBPCG85JD")
//		.pathParam(SwaggerInputParameter.CUSTOMER_REFERENCE.getName(), "1234567890")
//		.contentType(ContentType.JSON)
//		.get("/admin/pos/{posIdentifier}/customer-approval/{customerReference}")
//		.then().log().all();
//		
//		System.out.println("----------------------------------------------------------------------------");
//		
//		spec
//		.pathParam(SwaggerInputParameter.POS_IDENTIFIER.getName(), "POS00JNBPCG85JD")
//		.pathParam(SwaggerInputParameter.CUSTOMER_REFERENCE.getName(), "1234567890")
//		.contentType(ContentType.JSON)
//		.get("/pos/{posIdentifier}/customers/{customerReference}")
//		.then().log().all();
		
		// Load PoS info from json file
		PointOfSale pos = JsonMapper.convertJsonToObject(POINT_OF_SALE_INFO, PointOfSale.class);
		String batchId = RandomStringUtils.randomAlphabetic(6);

		PointOfSaleApi posApi = new PointOfSaleApi();
		RestRequest uploadFileRequest = posApi.initConnection(pos.getAuthenticationKey());

		// Upload daily orders batch file
		Response uploadFileResponse = uploadFileRequest.uploadFile(SwaggerInputParameter.INPUT_FILE.getName(),
				ORDERS_BATCH,
				ImmutableMap.of(SwaggerInputParameter.POS_IDENTIFIER.getName(), pos.getIdentifier(),
						SwaggerInputParameter.BATCH_ID.getName(), batchId),
				PointOfSaleResource.ORDERS_BATCH_PATH.getPath());

		AssertionUtil assertion = new AssertionUtil();

		assertion.verifyIfEquals(
				String.format("Verify that request is received with status code %s", HttpURLConnection.HTTP_ACCEPTED),
				uploadFileResponse.getStatusCode(), HttpURLConnection.HTTP_ACCEPTED);

		// Verify notification in MailHog
		MailHogApi mailHogApi = new MailHogApi();
		MailHogUtil mailHogUtil = new MailHogUtil(mailHogApi);
		int initialMessagesCount = mailHogUtil.getInitialMessageCount();
		Wait.until(mailHogUtil.notificationIsReceived(initialMessagesCount));
		
		RestRequest mailRequest = mailHogApi.initConnection();
		Response mailResponse = mailRequest.get(MailHogResource.MESSAGES.getPath());

		String json = mailResponse.then().extract().response().asString();
		JsonPath jsonPath = new JsonPath(json);
		// jsonPath.prettyPrint();
		// Map<String, String[]> headers = jsonPath.getMap("items[0].Content.Headers");
		String mailBody = jsonPath.getString("items[0].Content.Body");
		assertion.verifyIfContains(String.format("Verify that mail message contains batch id %s", batchId), mailBody,
				batchId);

		List<String> mailSubjects = jsonPath.getList("items[0].Content.Headers.Subject");
		String subject = mailSubjects.get(0);
		assertion.verifyIfEquals("Verify mail subject", subject,
				String.format("Transaction Request Notification for POS: %s", pos.getBic()));

		// Send PayOut transaction
		CsvFileReader csvReader = new CsvFileReader();
		Order order = csvReader.extractFirstRow(ORDERS_BATCH, Order.class);

		Transaction payoutTransaction = new Transaction(new BigDecimal(order.getAmount()).abs(), order.getCurrency(),
				order.getProductId(), LocalDateTime.now().toString());

		RestRequest payOutRequest = posApi.initConnection(pos.getAuthenticationKey());
		Response payOutResponse = payOutRequest.sendPostRequest(
				ImmutableMap.of(SwaggerInputParameter.POS_IDENTIFIER.getName(), pos.getIdentifier(),
						SwaggerInputParameter.BATCH_ID.getName(), batchId),
				Collections.singletonList(payoutTransaction), PointOfSaleResource.PAYOUT_TRANSACTION_PATH.getPath());

		System.out.println("BATCH: " + batchId);

		assertion.verifyIfEquals(
				String.format("Verify that request is received with status code %s", HttpURLConnection.HTTP_ACCEPTED),
				payOutResponse.getStatusCode(), HttpURLConnection.HTTP_ACCEPTED);

		// Trigger settlement
		String transferDate = DateUtil.formatToString(LocalDateTime.now(), DateFormat.YYYY_MM_DD);

		AdminApi adminApi = new AdminApi();
		RestRequest settlementRequest = adminApi.initConnection();
		Response settlementResponse = settlementRequest.sendPostRequestWithQueryParams(
				ImmutableMap.of(SwaggerInputParameter.DATE.getName(), transferDate),
				AdminResource.TRANSACTIONS_VALIDATION_PATH.getPath());

		List<TransactionRequest> allTransactionsRequests = Arrays
				.asList(settlementResponse.getBody().as(TransactionRequest[].class));
		// allTransactionsRequests.forEach(k -> System.out.println(k));

		TransactionRequest currentTransactionRequest = allTransactionsRequests.stream()
				.filter(transReq -> transReq.getOrdersBatchId().equals(batchId)
						&& transReq.getProductIdentifier().equals(payoutTransaction.getProductIdentifier())
						&& transReq.getAmount().compareTo(payoutTransaction.getAmount()) == 0
						&& transReq.getStatus().equals(TransactionRequestStatus.CONFIRMED.name())
						&& transReq.getRequestType().equals(RequestType.PAY_OUT.getType()))
				.findAny().orElse(null);

		// responseSettlement.then().extract().response().prettyPrint();

		assertion.verifyIfEquals("Verify that request is received with status code %s",
				settlementResponse.getStatusCode(), HttpURLConnection.HTTP_OK);
		assertion.verifyIfNotNull(String.format(
				"Verify that transaction request of type %s with Batch id: %s for Product %s has status: %s",
				RequestType.PAY_OUT.getType(), batchId, payoutTransaction.getProductIdentifier(),
				TransactionRequestStatus.CONFIRMED.name()), currentTransactionRequest);
	}

//	private int getInitialMessageCount(MailHogApi mailhogApi) {
//		RestRequest mailRequest = mailhogApi.initConnection();
//		Response mailResponse = mailRequest.get(MailHogResource.MESSAGES.getPath());
//
//		String json = mailResponse.then().extract().response().asString();
//		JsonPath jsonPath = new JsonPath(json);
//
//		return jsonPath.getInt("count");
//	}
//
//	private Callable<Boolean> mailNotificationIsReceived(MailHogApi mailhogApi, int initialCount) {
//		return new Callable<Boolean>() {
//			public Boolean call() throws Exception {
//				RestRequest mailRequest = mailhogApi.initConnection();
//				Response mailResponse = mailRequest.get(MailHogResource.MESSAGES.getPath());
//
//				String json = mailResponse.then().extract().response().asString();
//				JsonPath jsonPath = new JsonPath(json);
//				// jsonPath.prettyPrint();
//				int currentCount = jsonPath.getInt("count");
//
//				return currentCount == initialCount + 1; // The condition that must be fulfilled
//			}
//		};
//	}
}
