package org.mixcom.tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import static org.junit.Assert.*;
import org.mixcom.pages.ContentPage;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.Date;

public class SearchTest {
    private WebDriver driver;
    private ContentPage searchPage;

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
        searchPage = new ContentPage(driver);
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
    public void searchTest() {
        // 1. Open test post page
        searchPage.openPostPage("815793312794411008");

        // 2. Hover over search icon
        searchPage.hoverOverSearchIcon();

        // 3. Reset mouse position
        searchPage.resetMousePosition();

        // 4. Click search icon
        searchPage.clickSearchIcon();

        // 5. Hover over search container
        searchPage.hoverOverSearchContainer();

        // 6. Enter search query
        searchPage.enterSearchQuery("cats");

        // 7. Click first search result
        searchPage.clickFirstSearchResult();

        // Add verification steps as needed
    }
}