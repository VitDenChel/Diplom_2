package com;

import com.model.Tokens;
import com.model.UserRegisterResponse;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class UserOperations extends Base {

    public static final String EMAIL_POSTFIX = "@yandex.ru";

    /*
     метод регистрации нового пользователя
     возвращает мапу с данными: имя, пароль, имэйл
     если регистрация не удалась, возвращает пустую мапу
     */
    public Map<String, String> register() {

        // с помощью библиотеки RandomStringUtils генерируем имэйл
        // метод randomAlphabetic генерирует строку, состоящую только из букв, в качестве параметра передаём длину строки
        String email = RandomStringUtils.randomAlphabetic(10) + EMAIL_POSTFIX;
        // с помощью библиотеки RandomStringUtils генерируем пароль
        String password = RandomStringUtils.randomAlphabetic(10);
        // с помощью библиотеки RandomStringUtils генерируем имя пользователя
        String name = RandomStringUtils.randomAlphabetic(10);

        // создаём и заполняем мапу для передачи трех параметров в тело запроса
        Map<String, String> inputDataMap = new HashMap<>();
        inputDataMap.put("email", email);
        inputDataMap.put("password", password);
        inputDataMap.put("name", name);

        // отправляем запрос на регистрацию пользователя и десериализуем ответ в переменную response
        UserRegisterResponse response = given()
                .spec(Base.getBaseSpec())
                .and()
                .body(inputDataMap)
                .when()
                .post("auth/register")
                .body()
                .as(UserRegisterResponse.class);

        // возвращаем мапу с данными
        Map<String, String> responseData = new HashMap<>();
        if (response != null) {
            responseData.put("email", response.getUser().getEmail());
            responseData.put("name", response.getUser().getName());
            responseData.put("password", password);

            // токен, нужный для удаления пользователя, кладем в статическое поле класса с токенами
            // убираем слово Bearer в начале токена
            // так же запоминаем refreshToken
            Tokens.setAccessToken(response.getAccessToken().substring(7));
            Tokens.setRefreshToken(response.getRefreshToken());
        }
        return responseData;

    }

    @Step("Create user {user}")
    public Response create(UserForRegistration userForRegistration) {
        return given()
                .spec(getBaseSpec())
                .body(userForRegistration)
                .when()
                .post("auth/register")
                .then()
                .extract().response();
    }

    @Step("CreateUserWithoutPassword {userIncompleteData}")
    public Response createUserWithoutPassword(UserHasEmailAndName userHasEmailAndName) {
        return given()
                .spec(getBaseSpec())
                .body(userHasEmailAndName)
                .when()
                .post("auth/register")
                .then()
                .extract().response();
    }

    @Step("CreateUserWithoutEmail {userIncompleteData}")
    public Response createUserWithoutEmail(UserHasPasswordAndName userHasPasswordAndName) {
        return given()
                .spec(getBaseSpec())
                .body(userHasPasswordAndName)
                .when()
                .post("auth/register")
                .then()
                .extract().response();
    }

    @Step("CreateUserWithoutName {userIncompleteData}")
    public Response createUserWithoutName(UserHasEmailAndPassword userHasEmailAndPassword) {
        return given()
                .spec(getBaseSpec())
                .body(userHasEmailAndPassword)
                .when()
                .post("auth/register")
                .then()
                .extract().response();
    }

    @Step("UserWithIncorrectDataLogin {user}")
    public Response userWithIncorrectDataLogin(CreateUserForAuthorization createUserForAuthorization) {
        return given()
                .spec(getBaseSpec())
                .body(createUserForAuthorization)
                .when()
                .post("auth/login")
                .then()
                .extract().response();
    }

    @Step("UserLogIn {userLogIn}")
    public Response userLogIn(UserForCheckAuthorization userForCheckAuthorization) {
        return given()
                .spec(getBaseSpec())
                .body(userForCheckAuthorization)
                .when()
                .post("auth/login")
                .then()
                .extract().response();
    }

    @Step("GetDataRegisteredUser {user}")
    public Response getDataRegistratedUser() {
        return given()
                .spec(getBaseSpec())
                .auth().oauth2("accessToken()".substring(7))
                .when()
                .get("auth/user")
                .then()
                .extract().response();
    }

    @Step("ChangeDataRegisteredUser {user}")
    public Response changeDataRegistratedUser(String TokenUser, UserForRegistration userForRegistration) {
        return given()
                .spec(getBaseSpec())
                .auth().oauth2(TokenUser.substring(7))
                .body(userForRegistration)
                .when()
                .patch("auth/user")
                .then()
                .extract().response();
    }

    @Step("ChangeDataRegisteredUserWithoutAuthorization {user}")
    public Response changeDataRegistratedUserWithoutAuthorization(UserForRegistration userForRegistration) {
        return given()
                .spec(getBaseSpec())
                .body(userForRegistration)
                .when()
                .patch("auth/user")
                .then()
                .extract().response();
    }

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

    public void delete() {
        if (Tokens.getAccessToken() == null) {
            return;
        }
        given()
                .spec(Base.getBaseSpec())
                .auth().oauth2(Tokens.getAccessToken())
                .when()
                .delete("auth/user")
                .then()
                .statusCode(202);
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

