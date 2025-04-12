package org.mixcom.tests;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import static org.junit.Assert.*;
import org.mixcom.pages.ContentPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class TagNavigationTest {
    private WebDriver driver;
    private ContentPage contentPage;

    @Before
    public void setUp() {
        driver = new FirefoxDriver();
        contentPage = new ContentPage(driver);
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void tagNavigationTest() {
        // 1. Открываем пост
        contentPage.openPostPage("1160916080357544960");

        // 2. Кликаем на кнопку тега
        contentPage.clickTagButton();

        // 3. Проверяем что страница тега загрузилась
        assertTrue(contentPage.isTagPageLoaded());

        // 4. Двойная прокрутка вверх (как в оригинальном тесте)
        contentPage.doubleScrollToTop();
    }
}