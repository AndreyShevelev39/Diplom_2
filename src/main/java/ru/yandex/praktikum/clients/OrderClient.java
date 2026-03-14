package ru.yandex.praktikum.clients;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.yandex.praktikum.models.Order;
import static io.restassured.RestAssured.given;

public class OrderClient {
    private static final String BASE_URL = "https://stellarburgers.education-services.ru/api";

    @Step("Получить ингредиенты")
    public Response getIngredients() {
        return given().get(BASE_URL + "/ingredients");
    }

    @Step("Создать заказ")
    public Response createOrder(Order order, String token) {
        var spec = given().header("Content-type", "application/json");
        if (token != null) spec.header("Authorization", token);
        return spec.body(order).post(BASE_URL + "/orders");
    }
}