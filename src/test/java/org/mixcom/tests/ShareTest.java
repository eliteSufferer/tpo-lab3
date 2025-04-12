package org.mixcom.tests;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import static org.junit.Assert.*;
import org.mixcom.pages.ContentPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class ShareTest {
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
    public void shareTest() {
        contentPage.openPostPage("932639084822662144");
        contentPage.clickShareButton();
    }
}