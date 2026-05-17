package com.dibimbingqa.pages;

import com.dibimbingqa.base.CommonToAllPage;
import com.dibimbingqa.utils.WaitHelpers;
import org.openqa.selenium.By;

public class ProgramStudiPage extends CommonToAllPage {

    private By employeeMenu = By.id("layout-desktop-menu-item-box-employee");
    private By programStudiTab = By.id("tabs-admin-employee--tab-1");
    private By addProgramStudiButton = By.id("add-division-button");
    private By submitButton = By.id("add-division-confirm-button");
    private By loader = By.cssSelector(".chakra-spinner");

    public void goToEmployeeMenu() {
        WaitHelpers.waitForClickable(getDriver(), employeeMenu, 15);
        click(employeeMenu);
    }

    public void goToProgramStudiTab() {
        WaitHelpers.waitForClickable(getDriver(), programStudiTab, 15);
        click(programStudiTab);
        WaitHelpers.waitForInvisibility(getDriver(), loader, 15);
    }

    public void clickAddProgramStudiButton() {
        WaitHelpers.waitForClickable(getDriver(), addProgramStudiButton, 15);
        click(addProgramStudiButton);

        // Tunggu modal terbuka
        WaitHelpers.waitForVisibility(getDriver(), By.id("name"), 15);
        WaitHelpers.waitForVisibility(getDriver(), By.id("description"), 15);
    }

    public void addProgramStudi(String name, String description) {
        type(By.id("name"), name);
        type(By.id("description"), description);

        WaitHelpers.waitForClickable(getDriver(), submitButton, 10);
        click(submitButton);

        // Tunggu modal tertutup
        WaitHelpers.waitForInvisibility(getDriver(), By.id("name"), 10);
    }

}
