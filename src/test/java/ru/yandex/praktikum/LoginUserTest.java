package ru.yandex.praktikum;

import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.praktikum.clients.UserClient;
import ru.yandex.praktikum.models.User;

import static org.hamcrest.Matchers.equalTo;

public class LoginUserTest {
    private final UserClient userClient = new UserClient();
    private User user;
    private String token;

    @Before
    public void setUp() {
        user = new User("login_" + System.currentTimeMillis() + "@ya.ru", "p123", "Tester");
        userClient.createUser(user);
    }

    @After
    public void tearDown() {
        userClient.delete(token);
    }

    @Test
    @DisplayName("Логин под существующим пользователем")
    public void loginSuccess() {
        var response = userClient.login(user);
        token = response.path("accessToken");
        response.then().statusCode(200).body("success", equalTo(true));
    }

    @Test
    @DisplayName("Логин с неверным паролем")
    public void loginWithWrongPass() {
        User wrongUser = new User(user.getEmail(), "wrong", "");
        userClient.login(wrongUser).then().statusCode(401);
    }
}