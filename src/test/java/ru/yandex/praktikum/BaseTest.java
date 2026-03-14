package ru.yandex.praktikum;

import org.junit.Before;
import ru.yandex.praktikum.clients.UserClient;
import ru.yandex.praktikum.clients.OrderClient;

public class BaseTest {
    protected UserClient userClient = new UserClient();
    protected OrderClient orderClient = new OrderClient();

    @Before
    public void setUp() {
    }
}