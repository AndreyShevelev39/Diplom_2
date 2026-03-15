package ru.yandex.praktikum;

import io.qameta.allure.junit4.DisplayName;
import org.apache.http.HttpStatus;
import org.junit.Test;
import ru.yandex.praktikum.models.Order;
import java.util.Collections;
import java.util.List;
import static org.hamcrest.Matchers.equalTo;

public class CreateOrderTest extends BaseTest {

    @Test
    @DisplayName("Создание заказа без авторизации")
    public void createOrderWithoutAuth() {
        String id = orderClient.getIngredients().path("data[0]._id");
        Order order = new Order(List.of(id));
        orderClient.createOrder(order, null)
                .then().assertThat().statusCode(HttpStatus.SC_OK).body("success", equalTo(true));
    }

    @Test
    @DisplayName("Создание заказа с неверным хешем ингредиента")
    public void createOrderWithInvalidHash() {
        Order order = new Order(List.of("invalid_hash_777"));
        orderClient.createOrder(order, null)
                .then().assertThat().statusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR);
    }

    @Test
    @DisplayName("Создание заказа без ингредиентов")
    public void createOrderWithoutIngredients() {
        Order order = new Order(Collections.emptyList());
        orderClient.createOrder(order, null)
                .then().assertThat().statusCode(HttpStatus.SC_BAD_REQUEST)
                .and().body("message", equalTo("Ingredient ids must be provided"));
    }
}