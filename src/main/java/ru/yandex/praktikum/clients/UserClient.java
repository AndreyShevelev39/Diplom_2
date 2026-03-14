package ru.yandex.praktikum.clients;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.yandex.praktikum.models.User;
import static io.restassured.RestAssured.given;

public class UserClient {
    private static final String BASE_URL = "https://stellarburgers.education-services.ru";

    @Step("Создать пользователя")
    public Response createUser(User user) {
        return given()
                .header("Content-type", "application/json")
                .body(user)
                .post(BASE_URL + "/api/auth/register");
    }

    @Step("Логин пользователя")
    public Response login(User user) {
        return given()
                .header("Content-type", "application/json")
                .body(user)
                .post(BASE_URL + "/api/auth/login");
    }

    @Step("Удалить пользователя")
    public Response deleteUser(String token) {
        if (token == null) return null;
        return given()
                .header("Authorization", token)
                .delete(BASE_URL + "/api/auth/user");
    }
}