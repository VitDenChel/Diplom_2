package com;

import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class OrderOperations extends Base {


    @Step("GetListOrdersUnauthorizedUser {getListOrdersUser}")
    public Response getListOrdersRegistratedUser() {
        return given()
                .spec(getBaseSpec())
                .when()
                .get("orders")
                .then()
                .extract().response();
    }

    @Step("GetListOrdersAuthorizedUser {getListOrdersUser}")
    public Response getListOrdersAuthorizedUser(String TokenUser) {
        return given()
                .spec(getBaseSpec())
                .auth().oauth2(TokenUser.substring(7))
                .when()
                .get("orders")
                .then()
                .extract().response();
    }

    public Response createOrder() {
        return given()
                .spec(Base.getBaseSpec())
                .when()
                .post("orders")
                .then()
                .extract().response();
    }

    public Response createOrderWithIngredients(IngredientsForOrder ingredientsForOrder) {
        return given()
                .spec(Base.getBaseSpec())
                .body(ingredientsForOrder)
                .when()
                .post("orders")
                .then()
                .extract().response();

    }

    public Response createOrderWithInccorectHashIngredients(IngredientsForOrderIncorrectHash ingredientsForOrderIncorrectHash) {
        return given()
                .spec(Base.getBaseSpec())
                .body(ingredientsForOrderIncorrectHash)
                .when()
                .post("orders")
                .then()
                .extract().response();

    }
}
