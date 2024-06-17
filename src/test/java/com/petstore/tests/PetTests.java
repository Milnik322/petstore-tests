package com.petstore.tests;

import static com.petstore.data.GeneralConstants.CONTENT_TYPE;
import static com.petstore.data.GeneralConstants.CONTENT_TYPE_FORM;
import static com.petstore.data.GeneralConstants.CONTENT_TYPE_JSON;
import static com.petstore.data.GeneralConstants.FIND_BY_STATUS_ENDPOINT;
import static com.petstore.data.GeneralConstants.PET_ENDPOINT;
import static com.petstore.data.GeneralConstants.PET_ID;
import static com.petstore.data.GeneralConstants.PET_INVALID_STATUS;
import static com.petstore.data.GeneralConstants.PET_NAME;
import static com.petstore.data.GeneralConstants.PET_STATUS;
import static com.petstore.data.GeneralConstants.PET_STATUS_AVAILABLE;
import static com.petstore.data.GeneralConstants.PET_STATUS_SOLD;
import static com.petstore.data.GeneralConstants.STATUS_BAD_REQUEST;
import static com.petstore.data.GeneralConstants.STATUS_NOT_FOUND;
import static com.petstore.data.GeneralConstants.STATUS_OK;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

import com.petstore.data.pet.IPet;
import com.petstore.data.pet.Pet;
import io.restassured.response.Response;

import org.testng.annotations.Test;

public class PetTests extends TestRunner {

    @Test(dataProvider = "validPet")
    public void testAddPet(IPet validPet) {
        Response response = given()
                .header(CONTENT_TYPE, CONTENT_TYPE_JSON)
                .body(validPet)
                .when()
                .post(PET_ENDPOINT)
                .then()
                .statusCode(STATUS_OK)
                .extract()
                .response();

        Pet createdPet = response.as(Pet.class);

        this.petToDeleteInPostConditions = createdPet;

        assert createdPet.getName().equals(validPet.getName());
    }

    @Test(dataProvider = "validPet")
    public void testGetPetById(IPet validPet) {
        createNewPet(validPet);

        Response response = given()
                .pathParam(PET_ID, validPet.getId())
                .when()
                .get(PET_ENDPOINT + "/{" + PET_ID + "}")
                .then()
                .statusCode(STATUS_OK)
                .extract()
                .response();

        Pet receivedPet = response.as(Pet.class);
        assert receivedPet.getName().equals(validPet.getName());
    }

    @Test(dataProvider = "validPet")
    public void testUpdatePet(IPet validPet) {
        createNewPet(validPet);
        validPet.setName("UpdatedDoggie");

        Response response = given()
                .header(CONTENT_TYPE, CONTENT_TYPE_JSON)
                .body(validPet)
                .when()
                .put(PET_ENDPOINT)
                .then()
                .statusCode(STATUS_OK)
                .extract()
                .response();

        Pet createdPet = response.as(Pet.class);
        assert createdPet.getName().equals(validPet.getName());
        assert createdPet.getStatus().equals(validPet.getStatus());
    }

    @Test(dataProvider = "validPet")
    public void testDeletePet(IPet validPet) {
        createNewPet(validPet);

        given()
                .pathParam(PET_ID, validPet.getId())
                .when()
                .delete(PET_ENDPOINT + "/{" + PET_ID + "}")
                .then()
                .statusCode(STATUS_OK);
    }

    @Test(dataProvider = "validPet")
    public void testFindPetsByStatus(IPet validPet) {
        createNewPet(validPet);

        given()
                .queryParam(PET_STATUS, PET_STATUS_AVAILABLE)
                .when()
                .get(FIND_BY_STATUS_ENDPOINT)
                .then()
                .statusCode(STATUS_OK)
                .body("size()", notNullValue());
    }

    @Test
    public void testFindPetsByInvalidStatus() {
        given()
                .queryParam(PET_STATUS, PET_INVALID_STATUS)
                .when()
                .get(FIND_BY_STATUS_ENDPOINT)
                .then()
                .statusCode(STATUS_BAD_REQUEST);
    }

    @Test(dataProvider = "validPet")
    public void testUpdatePetWithForm(IPet validPet) {
        createNewPet(validPet);
        validPet.setName("UpdatedDoggie");
        validPet.setStatus(PET_STATUS_SOLD);

        Response response = given()
                .header(CONTENT_TYPE, CONTENT_TYPE_FORM)
                .queryParam(PET_NAME, validPet.getName())
                .queryParam(PET_STATUS, validPet.getStatus())
                .pathParam(PET_ID, validPet.getId())
                .when()
                .post(PET_ENDPOINT + "/{" + PET_ID + "}")
                .then()
                .statusCode(STATUS_OK)
                .extract()
                .response();

        Pet pet = response.as(Pet.class);
        assert pet.getName().equals(validPet.getName());
        assert pet.getStatus().equals(validPet.getStatus());
    }

    @Test
    public void testGetPetByNonExistingId() {
        given()
                .pathParam(PET_ID, 99999)
                .when()
                .get(PET_ENDPOINT + "/{" + PET_ID + "}")
                .then()
                .statusCode(STATUS_NOT_FOUND);
    }

    @Test
    public void testAddPetWithInvalidData() {
        String requestBody = "{ \"id\": \"invalidId\", \"name\": 12345, \"status\": true }";

        given()
                .header(CONTENT_TYPE, CONTENT_TYPE_JSON)
                .body(requestBody)
                .when()
                .post(PET_ENDPOINT)
                .then()
                .statusCode(STATUS_BAD_REQUEST);
    }
}
