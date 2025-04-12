package org.mixcom.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class ContentPage extends BasePage {
    // Общие элементы
    private static final String JUSTIFY_END_ELEMENT = "//div[contains(@class, 'justify-end')]";
    private static final String FIRST_IMAGE = "(//img[contains(@class, 'size-full')])[1]";
    private static final String SECOND_IMAGE = "(//img[contains(@class, 'size-full')])[2]";

    private static final String SHARE_BUTTON = "//button[contains(@class, 'rounded-full')]/*[contains(@class, 'size-10')]";

    private static final String TAG_BUTTON = "//*[@id=\"__next\"]/main/div[1]/div/div[2]/div[1]/div[2]/div/div/div/div/div[1]/div[2]/div/a[2]/div/div/span";
    private static final String TAG_PAGE_HEADER = "//*[@id=\"scrolling-grid\"]/div[1]/div/div[1]/p";

    private static final String FOLLOW_BUTTON = "//button[contains(@class, 'btn-primary')]";
    private static final String FOLLOW_CONFIRMATION  = "//button[contains(text(), 'Following')]";

    private static final String SEARCH_ICON = "/html/body/div[1]/main/div[1]/div/div[1]/div/div[3]/a";
    private static final String SEARCH_INPUT = "//input[contains(@class, 'border-none')]";
    private static final String SEARCH_RESULT = "//div[contains(@class, 'relative')][1]//div[contains(@class, 'absolute')]";
    private static final String SEARCH_CONTAINER = "//div[contains(@class, 'h-fit')]";

    private static final String FILTERS_MENU_BUTTON = "//*[name()='path'][9]";
    private static final String FILTER_OPTION_1 = "//div[contains(@class, 'justify-between')][2]/*[contains(@class, 'size-9')]";
    private static final String FILTER_OPTION_2 = "//div[contains(@class, 'relative')][3]/*[contains(@class, 'size-9')]";
    private static final String FILTER_OPTION_3 = "//div[contains(@class, 'relative')][4]/*[contains(@class, 'size-9')]";
    private static final String HIDDEN_OPTION_1 = "//div[contains(@class, 'flex')][3]/*[contains(@class, 'invisible')]";
    private static final String HIDDEN_OPTION_2 = "//div[contains(@class, 'relative')][2]/*[contains(@class, 'invisible')]";
    private static final String APPLY_BUTTON = "//button[contains(@class, 'bg-orange-500')]";

    public ContentPage(WebDriver driver) {
        super(driver);
    }

    // Методы для работы с контентом
    public void openPostPage(String postId) {
        driver.get("https://mix.com/!" + postId);
        setWindowSize(1200, 900);
        waitForPageLoad();
    }

    private void waitForPageLoad() {
        wait.until(d -> js.executeScript("return document.readyState").equals("complete"));
    }

    public void clickJustifyEnd() {
        waitAndClick(JUSTIFY_END_ELEMENT);
    }

    public void clickFirstImage() {
        clickWithJS(FIRST_IMAGE);
    }

    public void clickSecondImage() {
        clickWithJS(SECOND_IMAGE);
    }

    // Методы для шеринга
    public void clickShareButton() {
        waitAndClick(SHARE_BUTTON);
    }

    public void clickTagButton() {
        waitAndClick(TAG_BUTTON);
    }

    public boolean isTagPageLoaded() {
        return isElementVisible(TAG_PAGE_HEADER);
    }

    public void doubleScrollToTop() {
        scrollToTop();
        scrollToTop();
    }

    public void clickFollowButton() {
        waitAndClick(FOLLOW_BUTTON);
    }
    public boolean isFollowingConfirmed() {
        return isElementVisible(FOLLOW_CONFIRMATION);
    }

    public void hoverOverSearchIcon() {
        WebElement searchIcon = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(SEARCH_ICON)));
        new Actions(driver).moveToElement(searchIcon).perform();
        pauseForAnimation();
    }

    public void clickSearchIcon() {
        waitAndClick(SEARCH_ICON);
    }

    public void hoverOverSearchContainer() {
        WebElement container = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(SEARCH_CONTAINER)));
        new Actions(driver).moveToElement(container).perform();
        pauseForAnimation();
    }

    public void enterSearchQuery(String query) {
        WebElement searchInput = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(SEARCH_INPUT)));
        searchInput.clear();
        searchInput.sendKeys(query);
    }

    public void clickFirstSearchResult() {
        waitAndClick(SEARCH_RESULT);
    }

    public void resetMousePosition() {
        new Actions(driver).moveToElement(driver.findElement(By.tagName("body")), 0, 0).perform();
    }

    public void openFiltersMenu() {
        waitAndClick(FILTERS_MENU_BUTTON);
    }

    public void selectFilterOption1() {
        waitAndClick(FILTER_OPTION_1);
    }

    public void selectFilterOption2() {
        waitAndClick(FILTER_OPTION_2);
    }

    public void selectFilterOption3() {
        waitAndClick(FILTER_OPTION_3);
    }

    public void selectHiddenOption1() {
        clickWithJS(HIDDEN_OPTION_1);
    }

    public void selectHiddenOption2() {
        clickWithJS(HIDDEN_OPTION_2);
    }

    public void applyFilters() {
        waitAndClick(APPLY_BUTTON);
    }
}