package com.petstore.tests;

import static com.petstore.data.GeneralConstants.CONTENT_TYPE;
import static com.petstore.data.GeneralConstants.CONTENT_TYPE_JSON;
import static com.petstore.data.GeneralConstants.MESSAGE;
import static com.petstore.data.GeneralConstants.PASSWORD;
import static com.petstore.data.GeneralConstants.STATUS_BAD_REQUEST;
import static com.petstore.data.GeneralConstants.STATUS_NOT_FOUND;
import static com.petstore.data.GeneralConstants.STATUS_OK;
import static com.petstore.data.GeneralConstants.USERNAME;
import static com.petstore.data.GeneralConstants.USER_ENDPOINT;
import static com.petstore.data.GeneralConstants.USER_LOGIN_ENDPOINT;
import static com.petstore.data.GeneralConstants.USER_LOGOUT_ENDPOINT;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import com.petstore.data.user.IUser;
import com.petstore.data.user.User;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.concurrent.ThreadLocalRandom;

public class UserTests extends TestRunner {

    @Test(dataProvider = "validUser")
    public void testCreateUser(IUser validUser) {

        Response response = given()
                .header(CONTENT_TYPE, CONTENT_TYPE_JSON)
                .body(validUser)
                .when()
                .post(USER_ENDPOINT)
                .then()
                .statusCode(STATUS_OK)
                .extract()
                .response();

        User createdUser = response.as(User.class);

        this.userToDeleteInPostConditions = createdUser;

        assert createdUser.getId() == validUser.getId();
    }

    @Test(dataProvider = "validUser")
    public void testGetUserByUsername(IUser validUser) {
        createNewUser(validUser);

        Response response = given()
                .pathParam(USERNAME, validUser.getUsername())
                .when()
                .get(USER_ENDPOINT + "/{" + USERNAME + "}")
                .then()
                .statusCode(STATUS_OK)
                .extract()
                .response();

        User retrievedUser = response.as(User.class);
        assert retrievedUser.getUsername().equals(validUser.getUsername());
    }

    @Test(dataProvider = "validUser")
    public void testUpdateUser(IUser validUser) {
        createNewUser(validUser);

        // Update the user details
        validUser.setFirstname("Jane");
        validUser.setLastname("Smith");

        given()
                .header(CONTENT_TYPE, CONTENT_TYPE_JSON)
                .pathParam(USERNAME, validUser.getUsername())
                .body(validUser)
                .when()
                .put(USER_ENDPOINT + "/{" + USERNAME + "}")
                .then()
                .statusCode(STATUS_OK);

        // Retrieve the updated user to verify changes
        Response response = given()
                .pathParam(USERNAME, validUser.getUsername())
                .when()
                .get(USER_ENDPOINT + "/{"  + USERNAME + "}")
                .then()
                .statusCode(STATUS_OK)
                .extract()
                .response();

        User updatedUser = response.as(User.class);
        assert updatedUser.getFirstname().equals(validUser.getUsername());
        assert updatedUser.getLastname().equals(validUser.getLastname());
    }

    @Test(dataProvider = "validUser")
    public void testDeleteUser(IUser validUser) {
        createNewUser(validUser);

        // Delete the user by username
        given()
                .pathParam(USERNAME, validUser.getUsername())
                .when()
                .delete(USER_ENDPOINT + "/{" + USERNAME + "}")
                .then()
                .statusCode(STATUS_OK);

        // Verify the user has been deleted
        given()
                .pathParam(USERNAME, validUser.getUsername())
                .when()
                .get(USER_ENDPOINT + "/{" + USERNAME + "}")
                .then()
                .statusCode(STATUS_NOT_FOUND);
    }

    @Test(dataProvider = "validUser")
    public void testLoginUser(IUser validUser) {
        createNewUser(validUser);

        // Log in with the created user
        given()
                .queryParam(USERNAME, validUser.getUsername())
                .queryParam(PASSWORD, validUser.getPassword())
                .when()
                .get(USER_LOGIN_ENDPOINT)
                .then()
                .statusCode(STATUS_OK)
                .body(MESSAGE, containsString("logged in user session"));
    }

    @Test(dataProvider = "validUser")
    public void testLogoutUser(IUser validUser) {
        createNewUser(validUser);

        // Log in with the created user
        given()
                .queryParam(USERNAME, validUser.getUsername())
                .queryParam(PASSWORD, validUser.getPassword())
                .when()
                .get(USER_LOGIN_ENDPOINT)
                .then()
                .statusCode(STATUS_OK)
                .body(MESSAGE, containsString("logged in user session"));

        // Log out the current user
        given()
                .when()
                .get(USER_LOGOUT_ENDPOINT)
                .then()
                .statusCode(STATUS_OK);
    }

    @Test
    public void testCreateUserWithInvalidData() {
        String invalidUserBody = "{ \"id\": \"invalid\", \"username\": 12345, \"firstName\": true, \"lastName\": 12345," +
                " \"email\": false, \"password\": 12345, \"phone\": false, \"userStatus\": \"invalid\" }";

        given()
                .header(CONTENT_TYPE, CONTENT_TYPE_JSON)
                .body(invalidUserBody)
                .when()
                .post(USER_ENDPOINT)
                .then()
                .statusCode(STATUS_BAD_REQUEST);
    }

    @Test
    public void testGetUserByNonExistingUsername() {
        String nonExistingUsername = "nonexistinguser" + ThreadLocalRandom.current().nextInt(1000000, 2000000);

        given()
                .pathParam(USERNAME, nonExistingUsername)
                .when()
                .get(USER_ENDPOINT + "/{" + USERNAME + "}")
                .then()
                .statusCode(STATUS_NOT_FOUND);
    }
}
