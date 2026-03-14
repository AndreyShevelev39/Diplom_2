package ru.yandex.praktikum;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.apache.http.HttpStatus;
import org.junit.Test;
import ru.yandex.praktikum.models.Order;
import java.util.Collections;
import static org.hamcrest.Matchers.equalTo;

public class CreateOrderTest extends BaseTest {

    @Test
    @DisplayName("Создание заказа без ингредиентов")
    @Description("Проверка ошибки 400 и текста сообщения при пустом списке ингредиентов")
    public void createOrderWithoutIngredients() {
        Order order = new Order(Collections.emptyList());
        ValidatableResponse body = orderClient.createOrder(order, null)
                .then().assertThat().statusCode(HttpStatus.SC_BAD_REQUEST)
                .and().body("message", equalTo("Ingredient ids must be provided"));
    }

    @Test
    @DisplayName("Создание заказа с ингредиентами")
    @Description("Успешное создание заказа с валидными хешами ингредиентов")
    public void createOrderWithIngredients() {
        String ingredient;
        ingredient = orderClient.getIngredients().path("data[0]._id");
        Order order = new Order(Collections.singletonList(ingredient));
        ValidatableResponse success = orderClient.createOrder(order, null)
                .then().assertThat().statusCode(HttpStatus.SC_OK)
                .and().body("success", equalTo(true));
    }
}