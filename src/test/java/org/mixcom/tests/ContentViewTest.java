package org.mixcom.tests;

import org.mixcom.pages.ContentPage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class ContentViewTest {
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
  public void contentViewTest() {
    contentPage.openPostPage("579187463872864256");
    contentPage.clickJustifyEnd();
    contentPage.clickFirstImage();
    contentPage.scrollToTop();
    contentPage.clickSecondImage();
    contentPage.scrollToTop();
  }
}