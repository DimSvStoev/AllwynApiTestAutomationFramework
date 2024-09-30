//package com.depositsolutions.posintegration.baseline.administrationapi;
//
//import java.util.List;
//import java.util.Map;
//
//import org.junit.Test;
//
//import com.depositsolutions.posintegration.common.AssertionUtil;
//import com.depositsolutions.posintegration.common.CsvFileReader;
//import com.depositsolutions.posintegration.common.FileUtil;
//import com.depositsolutions.posintegration.common.LoggerUtil;
//import com.depositsolutions.posintegration.common.StringUtil;
//import com.depositsolutions.posintegration.common.StringUtil.SymbolType;
//import com.depositsolutions.posintegration.common.restassured.Request;
//import com.depositsolutions.posintegration.core.AdminApi;
//import com.depositsolutions.posintegration.core.MailHogApi;
//import com.depositsolutions.posintegration.core.database.DbDataExtractor;
//import com.depositsolutions.posintegration.core.database.enums.Query;
//import com.depositsolutions.posintegration.enums.api.AdminResource;
//import com.depositsolutions.posintegration.enums.api.MailHogResource;
//import com.depositsolutions.posintegration.enums.common.ContentType;
//import com.depositsolutions.posintegration.objects.Order;
//import com.depositsolutions.posintegration.objects.PointOfSale;
//
//import io.restassured.RestAssured;
//import io.restassured.response.Response;
//import io.restassured.specification.RequestSpecification;
//
//public class CreatePointOfSaleTest extends AdminApi {
//
//	private final static String TEST_DATA_FILE = "point-of-sale-create.csv";
//
//	@Test
//	public void createNewPointOfSale() {
////		AssertionUtil assertions = new AssertionUtil();
////		DbDataExtractor dataExtractor = new DbDataExtractor();
////		FileUtil fileUtil = new FileUtil();
////		MailHogApi mailManager = new MailHogApi();
////		AdminApi adminTest = new AdminApi();
////		
////		RequestSpecification mailRequestSpec = mailManager.initConnection();
////		mailRequestSpec.get(MailHogResource.MESSAGES.getPath()).then().log().all();
////		
////		RestAssured.given().auth().basic("deposit_user", "deposit_pass").log().all()
////		.get("http://mailhog-pos-integration-http-deposit-solutions-test.apps.openshift.musala.com/api/v2/messages")
////		.then().log().all();
////		CsvFileReader csvReader = new CsvFileReader();
////		PointOfSale pos = csvReader.extractFirstRowWithSkippedColumnNames(TEST_DATA_FILE, PointOfSale.class);
////		System.out.println(pos);
////		pos.setAuthenticationKey(StringUtil.generateRandomString(6, SymbolType.DIGITS));
////		pos.setName(pos.getName() + StringUtil.generateRandomString(6,SymbolType.DIGITS));
////		
////		System.out.println(pos);
////		
////		List<Order> orders = fileUtil.extractCsvData("orders-batch.csv", Order.class);
////		for (Order order : orders) {
////			System.out.println(order);
////		}
////
////	String resources = AdminResource.ADMIN_POS.getResource();
////	Request adminRequest = new Request(adminTest.initConnection());
////
//		//Response response = adminRequest.sendRequest(ContentType.APP_JSON.getType(), pos).post(resources);
//		//int statusCode = response.getStatusCode();
////		
////		String posID = "POS00S7IBREGCF3";
////		
////List<Map<String, Object>> wholeRecord = dataExtractor.fetchData(Query.SELECT_ALL_RECORDS.getQuery());
////		
////		//List<Map<String, Object>> records = dbManager.fetchData(Query.SELECT_POS_COLUMN_BY_IDENTIFIER.getQuery(ParameterName.NAME.getFieldName(), posID));
////
////		log.logInfoMessage("ITS OOOON");
////		//log.logErrorMessage("WHAAAT", new IndexOutOfBoundsException());
////		for (Map<String, Object> map : wholeRecord) {
////			for (Map.Entry<String, Object> pair : map.entrySet()) {
////				System.out.println(pair.getKey() + ":" + pair.getValue());
////				
////			}
////			System.out.println();
////		}
////
//		//Assert.assertEquals(200, statusCode);
////		JsonPath jsonPath = new JsonPath(response.asString());
////		System.out.println(response.asString());
//	}
//}
