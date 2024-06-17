package com.petstore.tests;

import static com.petstore.data.GeneralConstants.CONTENT_TYPE;
import static com.petstore.data.GeneralConstants.CONTENT_TYPE_JSON;
import static com.petstore.data.GeneralConstants.ORDER_ID;
import static com.petstore.data.GeneralConstants.STATUS_BAD_REQUEST;
import static com.petstore.data.GeneralConstants.STATUS_NOT_FOUND;
import static com.petstore.data.GeneralConstants.STATUS_OK;
import static com.petstore.data.GeneralConstants.STORE_INVENTORY_ENDPOINT;
import static com.petstore.data.GeneralConstants.STORE_ORDER_ENDPOINT;
import static io.restassured.RestAssured.given;

import com.petstore.data.order.IOrder;
import com.petstore.data.order.Order;
import com.petstore.data.pet.IPet;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.concurrent.ThreadLocalRandom;

public class StoreTests extends TestRunner {

    @Test(dataProvider = "validOrder")
    public void testPlaceOrder(IOrder validOrder) {
        Response response = given()
                .header(CONTENT_TYPE, CONTENT_TYPE_JSON)
                .body(validOrder)
                .when()
                .post(STORE_ORDER_ENDPOINT)
                .then()
                .statusCode(STATUS_OK)
                .extract()
                .response();

        Order placedOrder = response.as(Order.class);

        this.orderToDeleteInPostConditions = placedOrder;

        assert placedOrder.getId() == validOrder.getId();
        assert placedOrder.getStatus().equals(validOrder.getStatus());
    }

    @Test(dataProvider = "validOrder")
    public void testGetOrderById(IOrder validOrder) {
        placeNewOrder(validOrder);

        Response response = given()
                .pathParam(ORDER_ID, validOrder.getId())
                .when()
                .get(STORE_ORDER_ENDPOINT + "/{" + ORDER_ID + "}")
                .then()
                .statusCode(STATUS_OK)
                .extract()
                .response();

        Order retrievedOrder = response.as(Order.class);
        assert retrievedOrder.getId() == validOrder.getId();
    }

    @Test(dataProvider = "validOrder")
    public void testDeleteOrderById(IOrder validOrder) {
        placeNewOrder(validOrder);

        // Delete the order by ID
        given()
                .pathParam(ORDER_ID, validOrder.getId())
                .when()
                .delete(STORE_ORDER_ENDPOINT + "/{"+ ORDER_ID + "}")
                .then()
                .statusCode(STATUS_OK);

        // Verify the order has been deleted
        given()
                .pathParam(ORDER_ID, validOrder.getId())
                .when()
                .get(STORE_ORDER_ENDPOINT + "/{" + ORDER_ID + "}")
                .then()
                .statusCode(STATUS_NOT_FOUND);
    }


    @Test(dataProvider = "validPet")
    public void testGetInventory(IPet validPet) {
        createNewPet(validPet);

        Response response = given()
                .when()
                .get(STORE_INVENTORY_ENDPOINT)
                .then()
                .statusCode(STATUS_OK)
                .extract()
                .response();

        System.out.println("Inventory Response: " + response.asString());

        // Basic validation that the response contains inventory data
        assert !response.jsonPath().getMap("").isEmpty();
    }

    @Test
    public void testPlaceOrderWithInvalidData() {
        String invalidOrderBody = "{ \"id\": \"invalid\", \"petId\": \"invalid\", \"quantity\": " +
                "\"invalid\", \"shipDate\": \"invalid\", \"status\": \"invalid\", \"complete\": \"invalid\" }";

        given()
                .header(CONTENT_TYPE, CONTENT_TYPE_JSON)
                .body(invalidOrderBody)
                .when()
                .post(STORE_ORDER_ENDPOINT)
                .then()
                .statusCode(STATUS_BAD_REQUEST);
    }

    @Test
    public void testGetOrderByNonExistingId() {
        long nonExistingOrderId = ThreadLocalRandom.current().nextLong(1000000, 2000000);

        given()
                .pathParam(ORDER_ID, nonExistingOrderId)
                .when()
                .get(STORE_ORDER_ENDPOINT + "/{" + ORDER_ID + "}")
                .then()
                .statusCode(STATUS_NOT_FOUND);
    }
}
