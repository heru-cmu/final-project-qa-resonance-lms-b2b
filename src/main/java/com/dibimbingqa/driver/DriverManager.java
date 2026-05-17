package com.dibimbingqa.driver;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class DriverManager {
    private static WebDriver driver;

    public static WebDriver getDriver() {
        return driver;
    }

    public static void init() {
        // otomatis download & setup chromedriver sesuai versi Chrome
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized"); // buka langsung full screen

        driver = new ChromeDriver(options);
    }

    public static void down() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}
