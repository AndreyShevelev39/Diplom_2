package ru.yandex.praktikum;

import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.praktikum.clients.OrderClient;
import ru.yandex.praktikum.clients.UserClient;
import ru.yandex.praktikum.models.Order;
import ru.yandex.praktikum.models.User;

import java.util.List;

import static org.hamcrest.Matchers.equalTo;

public class CreateOrderTest {
    private final UserClient userClient = new UserClient();
    private final OrderClient orderClient = new OrderClient();
    private String token;
    private List<String> ingredients;

    @Before
    public void setUp() {
        User user = new User("order_" + System.currentTimeMillis() + "@ya.ru", "p123", "O");
        token = userClient.createUser(user).path("accessToken");
        ingredients = orderClient.getIngredients().path("data._id");
    }

    @After
    public void tearDown() {
        userClient.delete(token);
    }

    @Test
    @DisplayName("Создание заказа с авторизацией")
    public void createOrderWithAuth() {
        Order order = new Order(List.of(ingredients.get(0), ingredients.get(1)));
        orderClient.createOrder(order, token).then().statusCode(200).body("success", equalTo(true));
    }

    @Test
    @DisplayName("Создание заказа без авторизации")
    public void createOrderWithoutAuth() {
        Order order = new Order(List.of(ingredients.get(0)));
        orderClient.createOrder(order, null).then().statusCode(200);
    }

    @Test
    @DisplayName("Создание заказа без ингредиентов")
    public void createOrderNoIngredients() {
        Order order = new Order(List.of());
        orderClient.createOrder(order, token).then().statusCode(400);
    }

    @Test
    @DisplayName("Создание заказа с неверным хешем")
    public void createOrderInvalidHash() {
        Order order = new Order(List.of("invalid_hash_123"));
        orderClient.createOrder(order, token).then().statusCode(500);
    }
}