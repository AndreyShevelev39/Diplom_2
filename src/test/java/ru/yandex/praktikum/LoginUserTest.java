package ru.yandex.praktikum;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.apache.http.HttpStatus;
import org.junit.Test;
import ru.yandex.praktikum.models.User;
import static org.hamcrest.Matchers.equalTo;

public class LoginUserTest extends BaseTest {
    @Test
    @DisplayName("Вход с неверным логином")
    @Description("Проверка ошибки 401 и сообщения при вводе несуществующего email")
    public void loginWithWrongEmail() {
        User user = new User("non_existent_" + System.currentTimeMillis() + "@ya.ru", "pass", "Ivan");
        ValidatableResponse body = userClient.login(user)
                .then().assertThat().statusCode(HttpStatus.SC_UNAUTHORIZED)
                .and().body("message", equalTo("email or password are incorrect"));
    }

    @Test
    @DisplayName("Вход под существующим пользователем")
    @Description("Успешная авторизация с валидными данными")
    public void loginSuccess() {
        User user = new User("login_" + System.currentTimeMillis() + "@ya.ru", "pass123", "Ivan");
        Response user1 = userClient.createUser(user);
        ValidatableResponse success = userClient.login(user)
                .then().assertThat().statusCode(HttpStatus.SC_OK)
                .and().body("success", equalTo(true));
    }
}