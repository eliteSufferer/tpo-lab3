package org.mixcom.tests;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import org.mixcom.pages.ContentPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class FiltersTest {
    private WebDriver driver;
    private ContentPage filtersPage;

    @Before
    public void setUp() {
        driver = new FirefoxDriver();
        filtersPage = new ContentPage(driver);
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void filtersTest() {
        // 1. Open post with filters
        filtersPage.openPostPage("993946179345584128");

        // 2. Open filters menu
        filtersPage.openFiltersMenu();

        // 3. Select filter options
        filtersPage.selectFilterOption1();
        filtersPage.selectFilterOption2();
        filtersPage.selectFilterOption3();

        // 4. Select hidden options (using JS click)
        filtersPage.selectHiddenOption1();
        filtersPage.selectHiddenOption2();

        // 5. Apply filters
        filtersPage.applyFilters();
    }
}