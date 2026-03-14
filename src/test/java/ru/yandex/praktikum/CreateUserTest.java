package ru.yandex.praktikum;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.apache.http.HttpStatus;
import org.junit.Test;
import ru.yandex.praktikum.models.User;
import static org.hamcrest.Matchers.equalTo;

public class CreateUserTest extends BaseTest {

    @Test
    @DisplayName("Создание уникального пользователя")
    @Description("Успешное создание пользователя со всеми валидными полями")
    public void createUniqueUser() {
        User user = new User("test_" + System.currentTimeMillis() + "@ya.ru", "1234", "Ivan");

        ValidatableResponse success = userClient.createUser(user)
                .then().assertThat().statusCode(HttpStatus.SC_OK)
                .and().body("success", equalTo(true));
    }

    @Test
    @DisplayName("Создание пользователя без email")
    @Description("Негативный кейс: проверка сообщения об ошибке при отсутствии почты")
    public void createUserWithoutEmail() {
        User user = new User(null, "1234", "Ivan");

        ValidatableResponse message = userClient.createUser(user)
                .then().assertThat().statusCode(HttpStatus.SC_FORBIDDEN)
                .and().body("message", equalTo("Email, password and name are required fields"));
    }

    @Test
    @DisplayName("Создание пользователя без пароля")
    @Description("Негативный кейс: проверка ошибки при отсутствии пароля")
    public void createUserWithoutPassword() {
        User user = new User("test@ya.ru", null, "Ivan");

        ValidatableResponse message = userClient.createUser(user)
                .then().assertThat().statusCode(HttpStatus.SC_FORBIDDEN)
                .and().body("message", equalTo("Email, password and name are required fields"));
    }
}