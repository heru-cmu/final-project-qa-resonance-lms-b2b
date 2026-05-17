package com.dibimbingqa.base;

import com.dibimbingqa.driver.DriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CommonToAllPage {
    public WebDriver getDriver() {
        return DriverManager.getDriver();
    }

    public void click(By locator) {
        getDriver().findElement(locator).click();
    }

    public void type(By locator, String text) {
        getDriver().findElement(locator).sendKeys(text);
    }

    public String getText(By locator) {
        return getDriver().findElement(locator).getText();
    }
}
