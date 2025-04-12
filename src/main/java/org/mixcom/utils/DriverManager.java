package org.mixcom.utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import java.io.BufferedReader;

import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.util.Date;

public class DriverManager {


    public static WebDriver getDriver(String browser) {
        WebDriver driver;

        if (browser.equalsIgnoreCase("chrome")) {
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
        } else {
            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver();
        }

        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        return driver;
    }

    public static WebDriver getDriverWithCookies(String browser, String baseUrl, String cookieFilePath, boolean isNetscapeFormat) {
        WebDriver driver = getDriver(browser);

        // Сначала нужно открыть страницу домена, для которого будут куки
        driver.get(baseUrl);

        // Загружаем куки
        try {
            if (isNetscapeFormat) {
                loadNetscapeCookiesFromFile(driver, cookieFilePath);
            } else {
                loadJSONCookiesFromFile(driver, cookieFilePath);
            }
            // Обновляем страницу, чтобы применить куки
            driver.navigate().refresh();
        } catch (Exception e) {
            System.err.println("Ошибка при загрузке куки: " + e.getMessage());
            e.printStackTrace();
        }

        return driver;
    }


    public static WebDriver getDriverWithCookies(String browser, String baseUrl, String cookieFilePath) {
        return getDriverWithCookies(browser, baseUrl, cookieFilePath, false);
    }


    public static WebDriver getFirefoxDriverWithUserProfile(String userProfilePath) {
        WebDriverManager.firefoxdriver().setup();

        FirefoxOptions options = new FirefoxOptions();
        FirefoxProfile profile = new FirefoxProfile();

        if (userProfilePath != null && !userProfilePath.isEmpty()) {
            profile = new FirefoxProfile(new java.io.File(userProfilePath));
        }

        options.setProfile(profile);

        WebDriver driver = new FirefoxDriver(options);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        return driver;
    }

    /**
     * Загружает куки из JSON-файла и добавляет их в драйвер
     *
     * @param driver веб-драйвер
     * @param filePath путь к JSON-файлу с куки
     * @throws IOException если файл не найден
     * @throws Exception если произошла ошибка парсинга JSON
     */
    private static void loadJSONCookiesFromFile(WebDriver driver, String filePath)
            throws Exception {
        JSONParser parser = new JSONParser();
        JSONArray cookiesArray = (JSONArray) parser.parse(new FileReader(filePath));

        for (Object cookieObject : cookiesArray) {
            JSONObject cookieJson = (JSONObject) cookieObject;

            String name = (String) cookieJson.get("name");
            String value = (String) cookieJson.get("value");
            String domain = (String) cookieJson.get("domain");
            String path = (String) cookieJson.get("path");
            Boolean secure = (Boolean) cookieJson.get("secure");
            Boolean httpOnly = (Boolean) cookieJson.get("httpOnly");

            // Обработка срока действия куки, если он есть
            Date expiry = null;
            if (cookieJson.containsKey("expirationDate")) {
                Long expirationDate = (Long) cookieJson.get("expirationDate");
                if (expirationDate != null) {
                    expiry = new Date(expirationDate * 1000); // Конвертируем из секунд в миллисекунды
                }
            }

            Cookie cookie = new Cookie.Builder(name, value)
                    .domain(domain)
                    .path(path)
                    .expiresOn(expiry)
                    .isSecure(secure != null && secure)
                    .isHttpOnly(httpOnly != null && httpOnly)
                    .build();

            driver.manage().addCookie(cookie);
        }

        System.out.println("Куки успешно загружены из JSON-файла: " + filePath);
    }

    /**
     * Загружает куки из файла формата Netscape Cookie File и добавляет их в драйвер
     *
     * @param driver веб-драйвер
     * @param filePath путь к файлу с куки в формате Netscape
     * @throws IOException если файл не найден
     */
    private static void loadNetscapeCookiesFromFile(WebDriver driver, String filePath)
            throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line;
        int cookiesAdded = 0;

        // Пропускаем первую строку, если она является комментарием
        line = reader.readLine();
        if (line != null && line.startsWith("#")) {
            line = reader.readLine();
        }

        while (line != null) {
            if (!line.startsWith("#") && !line.trim().isEmpty()) {
                try {
                    // Формат: DOMAIN FLAG PATH SECURE EXPIRY NAME VALUE
                    String[] parts = line.split("\\t");

                    if (parts.length >= 7) {
                        String domain = parts[0];
                        // Если домен начинается с точки, убираем её для Selenium
                        if (domain.startsWith(".")) {
                            domain = domain.substring(1);
                        }

                        String path = parts[2];
                        boolean secure = parts[3].equals("TRUE");

                        // Получаем срок действия
                        Date expiry = null;
                        try {
                            long expiryTime = Long.parseLong(parts[4]);
                            if (expiryTime > 0) {
                                expiry = new Date(expiryTime * 1000);
                            }
                        } catch (NumberFormatException e) {
                            // Если не удалось преобразовать, игнорируем срок
                        }

                        String name = parts[5];
                        String value = parts[6];

                        if (name.equals("") || value.equals("undefined")) {
                            continue;
                        }

                        Cookie cookie = new Cookie.Builder(name, value)
                                .domain(domain)
                                .path(path)
                                .expiresOn(expiry)
                                .isSecure(secure)
                                .build();

                        driver.manage().addCookie(cookie);
                        cookiesAdded++;
                    }
                } catch (Exception e) {
                    System.err.println("Ошибка при обработке строки: " + line);
                    e.printStackTrace();
                }
            }
            line = reader.readLine();
        }
        reader.close();

        System.out.println("Успешно загружено " + cookiesAdded + " куки из файла: " + filePath);
    }
}