package org.mixcom.tests;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import org.mixcom.pages.ContentPage;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import io.github.bonigarcia.wdm.WebDriverManager;

import java.util.Date;

public class TagFollowTest {
    private WebDriver driver;
    private ContentPage contentPage;

    @Before
    public void setUp() {
        // Инициализация драйвера
        WebDriverManager.firefoxdriver().setup();
        driver = new FirefoxDriver();
        driver.manage().window().maximize();

        // Открываем сайт
        driver.get("https://mix.com");

        // Добавляем необходимые куки для авторизации
        addAuthCookies();

        // Обновляем страницу, чтобы применить куки
        driver.navigate().refresh();

        // Инициализация страницы
        contentPage = new ContentPage(driver);
    }

    private void addAuthCookies() {

        driver.manage().addCookie(new Cookie("token", "s%3AeyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2lkIjo2MTIxNDAxLCJpYXQiOjE3NDQzNzE2NzQsImV4cCI6MTc3NTkyOTI3NCwiaXNzIjoibWl4LmNvbSJ9.BcBgs84l5U8zccnUFPqGq1VFMb9bqDxSzEKUoEnOSJc.hjueqaoU%2BusQbiBI1GHRt3Uf6yhWMLHopAeDjaEcTpo", "mix.com", "/", new Date(1775907677000L), true));

        driver.manage().addCookie(new Cookie("intoprd", "s%3AeyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2lkIjo2MTIxNDAxLCJpYXQiOjE3NDQzNzE2NzQsImV4cCI6MTc3NTkyOTI3NCwiaXNzIjoibWl4LmNvbSJ9.BcBgs84l5U8zccnUFPqGq1VFMb9bqDxSzEKUoEnOSJc.hjueqaoU%2BusQbiBI1GHRt3Uf6yhWMLHopAeDjaEcTpo", "mix.com", "/", new Date(1775907677000L), true));

        driver.manage().addCookie(new Cookie("mix_uid", "6121401", "mix.com", "/", null, true));
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void tagFollowTest() {
        contentPage.openPostPage("717508011534319616");
        contentPage.clickTagButton();
        contentPage.clickFollowButton();
    }
}