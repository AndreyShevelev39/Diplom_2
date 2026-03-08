package ru.yandex.praktikum;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Test;
import ru.yandex.praktikum.clients.UserClient;
import ru.yandex.praktikum.models.User;

import static org.hamcrest.Matchers.equalTo;

public class CreateUserTest {
    private final UserClient userClient = new UserClient();
    private String token;

    @After
    public void tearDown() {
        userClient.delete(token);
    }

    @Test
    @DisplayName("Создание уникального пользователя")
    public void createUniqueUser() {
        User user = new User("test_" + System.currentTimeMillis() + "@ya.ru", "1234", "Ivan");
        Response response = userClient.createUser(user);
        token = response.path("accessToken");
        response.then().statusCode(200).body("success", equalTo(true));
    }

    @Test
    @DisplayName("Создание пользователя, который уже зарегистрирован")
    public void createDuplicateUser() {
        User user = new User("duplicate@ya.ru", "1234", "Ivan");
        userClient.createUser(user);
        Response response = userClient.createUser(user);
        response.then().statusCode(403).body("message", equalTo("User already exists"));
    }

    @Test
    @DisplayName("Создание пользователя без обязательного поля")
    public void createUserWithoutField() {
        User user = new User("", "1234", "Ivan");
        userClient.createUser(user).then().statusCode(403);
    }
}