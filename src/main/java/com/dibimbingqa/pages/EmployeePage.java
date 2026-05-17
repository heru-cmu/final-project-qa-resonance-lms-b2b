package com.dibimbingqa.pages;

import com.dibimbingqa.base.CommonToAllPage;
import com.dibimbingqa.utils.WaitHelpers;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

public class EmployeePage extends CommonToAllPage {

    private By employeeMenu = By.id("layout-desktop-menu-item-box-employee");
    private By employeeTab = By.id("tabs-admin-employee--tab-0");
    private By addEmployeeButton = By.id("button-add-employee");
    private By submitButton = By.id("button-add-employee-submit");
    private By loader = By.cssSelector(".chakra-spinner");

    public void goToEmployeeMenu() {
        WaitHelpers.waitForClickable(getDriver(), employeeMenu, 15);
        click(employeeMenu);
    }

    public void goToEmployeeTab() {
        WaitHelpers.waitForClickable(getDriver(), employeeTab, 15);
        click(employeeTab);
        WaitHelpers.waitForInvisibility(getDriver(), loader, 15);
    }

    public void clickAddEmployeeButton() {
        WaitHelpers.waitForClickable(getDriver(), addEmployeeButton, 15);
        click(addEmployeeButton);

        // Tunggu modal terbuka
        WaitHelpers.waitForVisibility(getDriver(), By.id("name"), 15);
    }

    public void addEmployee(String name, String id, String email, String phone,
                            String role, String birthDate, String address,
                            String nik, String npwp, String programStudi) {
        type(By.id("name"), name);
        type(By.id("employeeId"), id);
        type(By.id("email"), email);
        type(By.id("phoneNumber"), phone);

        // Klik dropdown Program Studi setelah Phone Number
        WebElement dropdown = getDriver().findElement(By.id("division"));
        dropdown.click(); // trigger render option

        // Tunggu option sesuai Excel muncul
        WaitHelpers.waitForVisibility(getDriver(),
                By.xpath("//select[@id='division']/option[normalize-space(text())='" + programStudi.trim() + "']"), 10);

        // Cari option sesuai Excel dan klik
        List<WebElement> options = dropdown.findElements(By.tagName("option"));
        boolean found = false;
        for (WebElement opt : options) {
            if (opt.getText().trim().equalsIgnoreCase(programStudi.trim())) {
                opt.click();
                found = true;
                break;
            }
        }
        if (!found) {
            throw new RuntimeException("Program Studi '" + programStudi + "' tidak ditemukan di dropdown!");
        }

        // Lanjut isi field lain
        type(By.id("employeeRole"), role);
        type(By.id("dateOfBirth"), birthDate);
        type(By.id("address"), address);
        type(By.id("nik"), nik);
        type(By.id("npwp"), npwp);

        WaitHelpers.waitForClickable(getDriver(), submitButton, 10);
        click(submitButton);

        // Tunggu modal tertutup
        WaitHelpers.waitForInvisibility(getDriver(), By.id("name"), 20);
    }

}
