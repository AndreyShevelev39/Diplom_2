package ru.yandex.praktikum;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.apache.http.HttpStatus;
import org.junit.After;
import org.junit.Test;
import ru.yandex.praktikum.models.User;
import static org.hamcrest.Matchers.equalTo;

public class CreateUserTest extends BaseTest {
    private String token;

    @After
    public void tearDown() { userClient.deleteUser(token); }

    @Test
    @DisplayName("Создание пользователя без имени")
    public void createUserWithoutName() {
        User user = new User("ivan" + System.currentTimeMillis() + "@ya.ru", "1234", "");
        userClient.createUser(user)
                .then().assertThat().statusCode(HttpStatus.SC_FORBIDDEN)
                .and().body("message", equalTo("Email, password and name are required fields"));
    }

    @Test
    @DisplayName("Создание уникального пользователя")
    public void createUniqueUser() {
        User user = new User("ivan" + System.currentTimeMillis() + "@ya.ru", "1234", "Ivan");
        var resp = userClient.createUser(user);
        token = resp.path("accessToken");
        resp.then().assertThat().statusCode(HttpStatus.SC_OK).body("success", equalTo(true));
    }
}