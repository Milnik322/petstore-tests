package com.petstore.data;

public class GeneralConstants {

    public static final String BASE_URL = "https://petstore3.swagger.io/api/v3";

    // Content type
    public static final String CONTENT_TYPE = "Content-Type";
    public static final String CONTENT_TYPE_JSON = "application/json";
    public static final String CONTENT_TYPE_FORM = "application/x-www-form-urlencoded";

    // Endpoints
    public static final String PET_ENDPOINT = "/pet";
    public static final String FIND_BY_STATUS_ENDPOINT = "/pet/findByStatus";
    public static final String STORE_ORDER_ENDPOINT = "/store/order";
    public static final String STORE_INVENTORY_ENDPOINT = "/store/inventory";
    public static final String USER_ENDPOINT = "/user";
    public static final String USER_LOGIN_ENDPOINT = "/user/login";
    public static final String USER_LOGOUT_ENDPOINT = "/user/logout";

    // Status codes
    public static final int STATUS_OK = 200;
    public static final int STATUS_NOT_FOUND = 404;
    public static final int STATUS_BAD_REQUEST = 400;

    // Pet constants
    public static final String PET_ID = "petId";
    public static final String PET_NAME = "name";
    public static final String PET_STATUS = "status";
    public static final String PET_STATUS_AVAILABLE = "available";
    public static final String PET_STATUS_SOLD = "sold";
    public static final String PET_INVALID_STATUS = "invalidStatus";

    // Store constants
    public static final String ORDER_ID = "orderId";

    // User constants
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    public static final String MESSAGE = "message";
}
