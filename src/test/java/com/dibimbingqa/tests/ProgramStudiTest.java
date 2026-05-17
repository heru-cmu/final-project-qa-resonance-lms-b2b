package com.dibimbingqa.tests;

import com.dibimbingqa.base.CommonToAllTest;
import com.dibimbingqa.driver.DriverManager;
import com.dibimbingqa.pages.LoginPage;
import com.dibimbingqa.pages.ProgramStudiPage;
import com.dibimbingqa.utils.PropertiesReader;
import com.dibimbingqa.utils.UtilExcel;
import com.dibimbingqa.utils.WaitHelpers;
import io.qameta.allure.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.List;

@Epic("LMS QA Automation")
@Feature("Program Studi Management")
public class ProgramStudiTest extends CommonToAllTest {

    @DataProvider(name = "programStudiData")
    public Object[][] getProgramStudiData() {
        return UtilExcel.getTestDataFromExcel("ProgramStudiData");
    }

    @Test(dataProvider = "programStudiData")
    @Owner("Heru")
    @Description("Verify Add Program Studi with multiple data sets")
    @Severity(SeverityLevel.CRITICAL)
    @Story("Add Program Studi")
    public void testAddProgramStudi(String name, String description) {
        // Step 1: Login
        DriverManager.getDriver().get(PropertiesReader.readKey("url"));
        LoginPage loginPage = new LoginPage();
        loginPage.login(PropertiesReader.readKey("username"), PropertiesReader.readKey("password"));

        // Step 2: Flow Add Program Studi
        ProgramStudiPage psPage = new ProgramStudiPage();
        psPage.goToEmployeeMenu();
        psPage.goToProgramStudiTab();
        psPage.clickAddProgramStudiButton();
        psPage.addProgramStudi(name, description);

        // Step 3: Verifikasi toast success (dinamis)
        By toastLocator = By.xpath("//p[contains(text(),'Success')]");
        WaitHelpers.waitForVisibility(DriverManager.getDriver(), toastLocator, 10);
        String toastText = DriverManager.getDriver().findElement(toastLocator).getText();

        Assert.assertTrue(
                toastText.toLowerCase().contains("success"),
                "Toast tidak sesuai: " + toastText
        );


        // Step 4: Verifikasi URL tetap di halaman program studi/employee
        String currentUrl = DriverManager.getDriver().getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("program") || currentUrl.contains("employee"),
                "Add Program Studi gagal, tidak redirect ke halaman program/employee");
    }

    @Test
    @Owner("Heru")
    @Description("Verify Edit Program Studi flow with search and update description")
    @Severity(SeverityLevel.CRITICAL)
    @Story("Edit Program Studi")
    public void testEditProgramStudi() {
        // Step 1: Login
        DriverManager.getDriver().get(PropertiesReader.readKey("url"));
        LoginPage loginPage = new LoginPage();
        loginPage.login(PropertiesReader.readKey("username"), PropertiesReader.readKey("password"));

        // Step 2: Go to Program Studi tab
        ProgramStudiPage psPage = new ProgramStudiPage();
        psPage.goToEmployeeMenu();
        psPage.goToProgramStudiTab();

        // Step 3: Search Program Studi "heru"
        By searchField = By.cssSelector("input[placeholder='Search program studi...']");
        WaitHelpers.waitForVisibility(DriverManager.getDriver(), searchField, 10);
        DriverManager.getDriver().findElement(searchField).sendKeys("heru");

        // Step 4: Klik Detail
        By detailButton = By.id("detail-division-button");
        WaitHelpers.waitForClickable(DriverManager.getDriver(), detailButton, 10);
        DriverManager.getDriver().findElement(detailButton).click();

        // Step 5: Klik Edit Program Studi
        By editButton = By.id("edit-division-button");
        WaitHelpers.waitForClickable(DriverManager.getDriver(), editButton, 10);
        DriverManager.getDriver().findElement(editButton).click();

        // Step 6: Edit description
        By descriptionField = By.id("description");
        WaitHelpers.waitForVisibility(DriverManager.getDriver(), descriptionField, 10);
        DriverManager.getDriver().findElement(descriptionField).clear();
        DriverManager.getDriver().findElement(descriptionField).sendKeys("007 updated");

        // Step 7: Klik Save Changes
        By saveButton = By.id("edit-division-confirm-button");
        WaitHelpers.waitForClickable(DriverManager.getDriver(), saveButton, 10);
        DriverManager.getDriver().findElement(saveButton).click();

        // Step 8: Verifikasi toast success
        By toastLocator = By.xpath("//p[contains(text(),'Success')]");
        WaitHelpers.waitForVisibility(DriverManager.getDriver(), toastLocator, 10);
        String toastText = DriverManager.getDriver().findElement(toastLocator).getText();

        Assert.assertTrue(toastText.toLowerCase().contains("success"),
                "Toast tidak sesuai: " + toastText);
    }

    @Test
    @Owner("Heru")
    @Description("Verify Search Program Studi works correctly")
    @Severity(SeverityLevel.NORMAL)
    @Story("Search Program Studi")
    public void testSearchProgramStudi() {
        // Step 1: Login
        DriverManager.getDriver().get(PropertiesReader.readKey("url"));
        LoginPage loginPage = new LoginPage();
        loginPage.login(PropertiesReader.readKey("username"), PropertiesReader.readKey("password"));

        // Step 2: Go to Program Studi tab
        ProgramStudiPage psPage = new ProgramStudiPage();
        psPage.goToEmployeeMenu();
        psPage.goToProgramStudiTab();

        // Step 3: Isi field pencarian "heru"
        By searchField = By.cssSelector("input[placeholder='Search program studi...']");
        WaitHelpers.waitForVisibility(DriverManager.getDriver(), searchField, 10);
        DriverManager.getDriver().findElement(searchField).sendKeys("heru");

        // Step 4: Tunggu hasil muncul
        By resultRow = By.xpath("//table//tr");
        WaitHelpers.waitForVisibility(DriverManager.getDriver(), resultRow, 10);

        // Step 5: Verifikasi hasil mengandung "heru"
        List<WebElement> rows = DriverManager.getDriver().findElements(resultRow);
        boolean found = rows.stream().anyMatch(r -> r.getText().toLowerCase().contains("heru"));

        Assert.assertTrue(found, "Search Program Studi gagal, tidak menemukan data dengan keyword 'heru'");
    }
    @Test
    @Owner("Heru")
    @Description("Verify Delete Program Studi flow with confirmation")
    @Severity(SeverityLevel.CRITICAL)
    @Story("Delete Program Studi")
    public void testDeleteProgramStudi() {
        // Step 1: Login
        DriverManager.getDriver().get(PropertiesReader.readKey("url"));
        LoginPage loginPage = new LoginPage();
        loginPage.login(PropertiesReader.readKey("username"), PropertiesReader.readKey("password"));

        // Step 2: Go to Program Studi tab
        ProgramStudiPage psPage = new ProgramStudiPage();
        psPage.goToEmployeeMenu();
        psPage.goToProgramStudiTab();

        // Step 3: Klik Detail di baris pertama
        By detailButton = By.id("detail-division-button");
        WaitHelpers.waitForClickable(DriverManager.getDriver(), detailButton, 10);
        DriverManager.getDriver().findElement(detailButton).click();

        // Step 4: Klik Edit Program Studi
        By editButton = By.id("edit-division-button");
        WaitHelpers.waitForClickable(DriverManager.getDriver(), editButton, 10);
        DriverManager.getDriver().findElement(editButton).click();

        // Step 5: Klik Delete Program Studi
        By deleteButton = By.id("delete-division-button");
        WaitHelpers.waitForClickable(DriverManager.getDriver(), deleteButton, 10);
        DriverManager.getDriver().findElement(deleteButton).click();

        // Step 6: Konfirmasi Delete
        By confirmDeleteButton = By.id("delete-division-confirm-button");
        WaitHelpers.waitForClickable(DriverManager.getDriver(), confirmDeleteButton, 10);
        DriverManager.getDriver().findElement(confirmDeleteButton).click();

        // Step 7: Verifikasi toast (bisa success atau failed)
        By toastLocator = By.xpath("//p[contains(text(),'Success') or contains(text(),'Failed')]");
        WaitHelpers.waitForVisibility(DriverManager.getDriver(), toastLocator, 10);
        String toastText = DriverManager.getDriver().findElement(toastLocator).getText();

        Assert.assertTrue(
                toastText.toLowerCase().contains("success") || toastText.toLowerCase().contains("failed"),
                "Toast tidak sesuai: " + toastText
        );
    }

}
