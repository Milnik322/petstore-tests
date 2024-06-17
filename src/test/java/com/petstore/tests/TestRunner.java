package com.petstore.tests;

import static com.petstore.data.GeneralConstants.BASE_URL;
import static com.petstore.data.GeneralConstants.CONTENT_TYPE;
import static com.petstore.data.GeneralConstants.CONTENT_TYPE_JSON;
import static com.petstore.data.GeneralConstants.ORDER_ID;
import static com.petstore.data.GeneralConstants.PET_ENDPOINT;
import static com.petstore.data.GeneralConstants.PET_ID;
import static com.petstore.data.GeneralConstants.STATUS_OK;
import static com.petstore.data.GeneralConstants.STORE_ORDER_ENDPOINT;
import static com.petstore.data.GeneralConstants.USERNAME;
import static com.petstore.data.GeneralConstants.USER_ENDPOINT;
import static com.petstore.data.order.OrderRepository.getValidOrder;
import static com.petstore.data.pet.PetsRepository.getValidPet;
import static com.petstore.data.user.UsersRepository.getValidUser;
import static io.restassured.RestAssured.given;

import com.petstore.data.order.IOrder;
import com.petstore.data.order.Order;
import com.petstore.data.pet.IPet;
import com.petstore.data.pet.Pet;
import com.petstore.data.user.IUser;
import com.petstore.data.user.User;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.Response;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;

public abstract class TestRunner {

    protected IPet petToDeleteInPostConditions;
    protected IOrder orderToDeleteInPostConditions;
    protected IUser userToDeleteInPostConditions;

    @DataProvider
    public Object[][] validPet() {
        return new Object[][] {
                { getValidPet()},
        };
    }

    @DataProvider
    public Object[][] validOrder() {
        return new Object[][] {
                { getValidOrder()},
        };
    }

    @DataProvider
    public Object[][] validUser() {
        return new Object[][]{
                { getValidUser()},
        };
    }

    @BeforeSuite
    public void setup() {
        RestAssured.baseURI = BASE_URL;
        RestAssured.filters(new RequestLoggingFilter(), new RequestLoggingFilter());
        RestAssured.filters(new ResponseLoggingFilter(), new ResponseLoggingFilter());
    }

    @AfterTest
    public void afterTest() {
        if (petToDeleteInPostConditions != null) {
            given()
                    .pathParam(PET_ID, petToDeleteInPostConditions.getId())
                    .when()
                    .delete(PET_ENDPOINT + "/{" + PET_ID + "}")
                    .then()
                    .statusCode(STATUS_OK);
        }
        if (orderToDeleteInPostConditions != null) {
            given()
                    .pathParam(ORDER_ID, orderToDeleteInPostConditions.getId())
                    .when()
                    .delete(STORE_ORDER_ENDPOINT + "/{" + ORDER_ID + "}")
                    .then()
                    .statusCode(STATUS_OK);
        }
        if (userToDeleteInPostConditions != null) {
            given()
                    .pathParam(USERNAME, userToDeleteInPostConditions.getUsername())
                    .when()
                    .delete(USER_ENDPOINT + "/{" + USERNAME + "}")
                    .then()
                    .statusCode(STATUS_OK);
        }
    }

    public void createNewPet(IPet pet) {
        Response response = given()
                .header(CONTENT_TYPE, CONTENT_TYPE_JSON)
                .body(pet)
                .when()
                .post(PET_ENDPOINT)
                .then()
                .statusCode(STATUS_OK)
                .extract()
                .response();

        petToDeleteInPostConditions = response.as(Pet.class);
    }

    public void placeNewOrder(IOrder order) {
        Response response = given()
                .header(CONTENT_TYPE, CONTENT_TYPE_JSON)
                .body(order)
                .when()
                .post(STORE_ORDER_ENDPOINT)
                .then()
                .statusCode(STATUS_OK)
                .extract()
                .response();

        orderToDeleteInPostConditions = response.as(Order.class);
    }

    public void createNewUser(IUser user) {
        Response response = given()
                .header(CONTENT_TYPE, CONTENT_TYPE_JSON)
                .body(user)
                .when()
                .post(USER_ENDPOINT)
                .then()
                .statusCode(STATUS_OK)
                .extract()
                .response();

        userToDeleteInPostConditions = response.as(User.class);
    }
}
