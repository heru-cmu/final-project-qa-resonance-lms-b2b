package com.dibimbingqa.tests;

import com.dibimbingqa.base.CommonToAllTest;
import com.dibimbingqa.driver.DriverManager;
import com.dibimbingqa.pages.LoginPage;
import com.dibimbingqa.pages.EmployeePage;
import com.dibimbingqa.utils.PropertiesReader;
import com.dibimbingqa.utils.UtilExcel;
import com.dibimbingqa.utils.WaitHelpers;
import io.qameta.allure.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;



import java.time.Duration;
import java.util.List;

@Epic("LMS QA Automation")
@Feature("Employee Management")
public class EmployeeTest extends CommonToAllTest {

    @DataProvider(name = "employeeData")
    public Object[][] getEmployeeData() {
        return UtilExcel.getTestDataFromExcel("EmployeeData");
    }

    @Test(dataProvider = "employeeData")
    @Owner("Heru")
    @Description("Verify Add Employee with multiple data sets and unique email")
    @Severity(SeverityLevel.CRITICAL)
    @Story("Add Employee")
    public void testAddEmployee(String name, String id, String email, String phone,
                                String role, String birthDate, String address,
                                String nik, String npwp, String programStudi) {

        // Step 1: Login
        DriverManager.getDriver().get(PropertiesReader.readKey("url"));
        LoginPage loginPage = new LoginPage();
        loginPage.login(PropertiesReader.readKey("username"), PropertiesReader.readKey("password"));

        // Step 2: Flow Add Employee
        EmployeePage empPage = new EmployeePage();
        empPage.goToEmployeeMenu();
        empPage.goToEmployeeTab();
        empPage.clickAddEmployeeButton();

        // Step 2a: Generate unique email supaya tidak bentrok
        String uniqueEmail = email.split("@")[0]
                + System.currentTimeMillis()
                + "@" + email.split("@")[1];

        // Step 2b: Add Employee dengan email unik
        empPage.addEmployee(name, id, uniqueEmail, phone, role, birthDate, address, nik, npwp, programStudi);

        // Step 3: Verifikasi toast success
        By toastLocator = By.xpath("//p[contains(text(),'Success')]");
        WaitHelpers.waitForVisibility(DriverManager.getDriver(), toastLocator, 10);
        String toastText = DriverManager.getDriver().findElement(toastLocator).getText();

        Assert.assertTrue(
                toastText.toLowerCase().contains("success"),
                "Toast tidak sesuai: " + toastText
        );

        // Step 4: Verifikasi URL tetap di halaman employee
        String currentUrl = DriverManager.getDriver().getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("employee"),
                "Add Employee gagal, tidak redirect ke halaman employee");
    }

    @Test
    @Owner("Heru")
    @Description("Verify Edit Employee updates address successfully")
    @Severity(SeverityLevel.CRITICAL)
    @Story("Edit Employee")
    public void testEditEmployee() {
        // Step 1: Login
        DriverManager.getDriver().get(PropertiesReader.readKey("url"));
        LoginPage loginPage = new LoginPage();
        loginPage.login(PropertiesReader.readKey("username"), PropertiesReader.readKey("password"));

        // Step 2: Go to Employee List
        EmployeePage empPage = new EmployeePage();
        empPage.goToEmployeeMenu();
        empPage.goToEmployeeTab();

        // Step 3: Search employee "heru"
        By searchField = By.cssSelector("input[placeholder='Search name, e-mail, phone...']");
        WaitHelpers.waitForVisibility(DriverManager.getDriver(), searchField, 10);
        DriverManager.getDriver().findElement(searchField).sendKeys("heru");

        // Step 4: Klik Detail baris pertama
        By detailButton = By.id("button-detail-employee-0");
        WaitHelpers.waitForClickable(DriverManager.getDriver(), detailButton, 10);
        DriverManager.getDriver().findElement(detailButton).click();

        // Step 5: Klik Edit Employee (scroll dulu)
        By editButton = By.id("edit-employee-button");
        WaitHelpers.waitForPresence(DriverManager.getDriver(), editButton, 10);

        // Scroll ke elemen
        WebElement editElement = DriverManager.getDriver().findElement(editButton);
        ((org.openqa.selenium.JavascriptExecutor) DriverManager.getDriver())
                .executeScript("arguments[0].scrollIntoView(true);", editElement);

        // Tunggu sampai bisa diklik
        WaitHelpers.waitForClickable(DriverManager.getDriver(), editButton, 10);
        editElement.click();

        // Step 6: Update Address dengan data unik
        By addressField = By.id("edit-employee-address-textarea");
        WaitHelpers.waitForVisibility(DriverManager.getDriver(), addressField, 10);
        DriverManager.getDriver().findElement(addressField)
                .sendKeys("Address Updated " + System.currentTimeMillis());

        // Step 7: Save Changes
        By saveButton = By.id("edit-employee-save-changes-button");
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
    @Description("Verify Delete Employee with confirmation popup")
    @Severity(SeverityLevel.CRITICAL)
    @Story("Delete Employee")
    public void testDeleteEmployee() {
        // Step 1: Login
        DriverManager.getDriver().get(PropertiesReader.readKey("url"));
        LoginPage loginPage = new LoginPage();
        loginPage.login(PropertiesReader.readKey("username"), PropertiesReader.readKey("password"));

        // Step 2: Go to Employee List
        EmployeePage empPage = new EmployeePage();
        empPage.goToEmployeeMenu();
        empPage.goToEmployeeTab();

        // Step 3: Search employee "heru"
        By searchField = By.cssSelector("input[placeholder='Search name, e-mail, phone...']");
        WaitHelpers.waitForVisibility(DriverManager.getDriver(), searchField, 10);
        DriverManager.getDriver().findElement(searchField).sendKeys("heru");

        // Step 4: Klik Detail baris pertama
        By detailButton = By.id("button-detail-employee-0");
        WaitHelpers.waitForClickable(DriverManager.getDriver(), detailButton, 10);
        DriverManager.getDriver().findElement(detailButton).click();

        // Step 5: Scroll ke tombol Delete Employee
        By deleteButton = By.id("delete-employee-button");
        WaitHelpers.waitForPresence(DriverManager.getDriver(), deleteButton, 10);
        WebElement deleteElement = DriverManager.getDriver().findElement(deleteButton);
        ((org.openqa.selenium.JavascriptExecutor) DriverManager.getDriver())
                .executeScript("arguments[0].scrollIntoView(true);", deleteElement);
        WaitHelpers.waitForClickable(DriverManager.getDriver(), deleteButton, 10);
        deleteElement.click();

        // Step 6: Konfirmasi di popup
        By confirmDeleteButton = By.id("confirm-delete-button");
        WaitHelpers.waitForClickable(DriverManager.getDriver(), confirmDeleteButton, 10);
        DriverManager.getDriver().findElement(confirmDeleteButton).click();

        // Step 7: Verifikasi toast success
        By toastLocator = By.xpath("//p[contains(text(),'Success')]");
        WaitHelpers.waitForVisibility(DriverManager.getDriver(), toastLocator, 10);
        String toastText = DriverManager.getDriver().findElement(toastLocator).getText();

        Assert.assertTrue(toastText.toLowerCase().contains("success"),
                "Toast tidak sesuai: " + toastText);
    }

    @Test
    @Owner("Heru")
    @Description("Verify Employee Search shows correct results")
    @Severity(SeverityLevel.NORMAL)
    @Story("Employee Search")
    public void testSearchEmployee() {
        // Step 1: Login
        DriverManager.getDriver().get(PropertiesReader.readKey("url"));
        LoginPage loginPage = new LoginPage();
        loginPage.login(PropertiesReader.readKey("username"), PropertiesReader.readKey("password"));

        // Step 2: Go to Employee List
        EmployeePage empPage = new EmployeePage();
        empPage.goToEmployeeMenu();
        empPage.goToEmployeeTab();

        // Step 3: Search employee "heru"
        By searchField = By.cssSelector("input[placeholder='Search name, e-mail, phone...']");
        WaitHelpers.waitForVisibility(DriverManager.getDriver(), searchField, 10);
        DriverManager.getDriver().findElement(searchField).sendKeys("heru");

        // Step 4: Validasi hasil tabel mengandung kata "heru"
        By tableLocator = By.cssSelector("table"); // bisa diganti locator row/column spesifik
        WaitHelpers.waitForVisibility(DriverManager.getDriver(), tableLocator, 10);
        String tableText = DriverManager.getDriver().findElement(tableLocator).getText();

        Assert.assertTrue(tableText.toLowerCase().contains("heru"),
                "Hasil pencarian tidak mengandung kata 'heru'. Isi tabel: " + tableText);
    }

    @Test
    @Owner("Heru")
    @Description("Verify Employee Filter by Angkatan works correctly")
    @Severity(SeverityLevel.NORMAL)
    @Story("Employee Filter")
    public void testFilterEmployee() {
        // Step 1: Login
        DriverManager.getDriver().get(PropertiesReader.readKey("url"));
        LoginPage loginPage = new LoginPage();
        loginPage.login(PropertiesReader.readKey("username"), PropertiesReader.readKey("password"));

        // Step 2: Go to Employee List
        EmployeePage empPage = new EmployeePage();
        empPage.goToEmployeeMenu();
        empPage.goToEmployeeTab();

        // Step 3: Klik dropdown Filter by Angkatan (pakai locator stabil)
        By filterDropdown = By.xpath("//p[text()='Filter by Angkatan']/ancestor::button");
        WaitHelpers.waitForClickable(DriverManager.getDriver(), filterDropdown, 10);
        WebElement dropdownElement = DriverManager.getDriver().findElement(filterDropdown);
        ((org.openqa.selenium.JavascriptExecutor) DriverManager.getDriver())
                .executeScript("arguments[0].scrollIntoView(true);", dropdownElement);
        dropdownElement.click();

        // Step 4: Pilih opsi "2024 Genap"
        By filterOption = By.xpath("//button[contains(text(),'2024 Genap')]");
        WaitHelpers.waitForClickable(DriverManager.getDriver(), filterOption, 10);
        DriverManager.getDriver().findElement(filterOption).click();

        // Step 5: Validasi hasil tabel muncul
        By tableLocator = By.cssSelector("table");
        WaitHelpers.waitForVisibility(DriverManager.getDriver(), tableLocator, 10);
        String tableText = DriverManager.getDriver().findElement(tableLocator).getText();

        Assert.assertTrue(!tableText.isEmpty(),
                "Filter tidak menghasilkan data. Isi tabel kosong.");
    }

    @Test
    @Owner("Heru")
    @Description("Verify Employee Sorting by Name column works correctly")
    @Severity(SeverityLevel.NORMAL)
    @Story("Employee Sorting")
    public void testSortEmployeeByName() {
        // Step 1: Login
        DriverManager.getDriver().get(PropertiesReader.readKey("url"));
        LoginPage loginPage = new LoginPage();
        loginPage.login(PropertiesReader.readKey("username"), PropertiesReader.readKey("password"));

        // Step 2: Go to Employee List
        EmployeePage empPage = new EmployeePage();
        empPage.goToEmployeeMenu();
        empPage.goToEmployeeTab();

        // Step 3: Tunggu tabel muncul
        By tableLocator = By.cssSelector("table");
        WaitHelpers.waitForVisibility(DriverManager.getDriver(), tableLocator, 10);

        // Step 4: Ambil list nama sebelum sort
        List<String> beforeSort = DriverManager.getDriver()
                .findElements(By.xpath("//table//tr/td[1]")) // kolom pertama = Name
                .stream().map(WebElement::getText).toList();

        // Step 5: Klik arrow sort Name (desc)
        By sortNameDesc = By.id("sort-name-desc"); // sesuaikan id arrow untuk kolom Name
        WaitHelpers.waitForClickable(DriverManager.getDriver(), sortNameDesc, 10);
        WebElement sortElement = DriverManager.getDriver().findElement(sortNameDesc);
        ((org.openqa.selenium.JavascriptExecutor) DriverManager.getDriver())
                .executeScript("arguments[0].scrollIntoView(true);", sortElement);
        sortElement.click();

        // Step 6: Ambil list nama sesudah sort
        List<String> afterSort = DriverManager.getDriver()
                .findElements(By.xpath("//table//tr/td[1]"))
                .stream().map(WebElement::getText).toList();

        // Step 7: Assert urutan berubah
        Assert.assertNotEquals(beforeSort, afterSort,
                "Sorting tidak mengubah urutan data. Sebelum: " + beforeSort + ", Sesudah: " + afterSort);
    }

    @Test
    @Owner("Heru")
    @Description("Verify Toggle Active/Inactive Employee with confirmation popup")
    @Severity(SeverityLevel.CRITICAL)
    @Story("Toggle Employee Status")
    public void testToggleEmployeeStatus() {
        // Step 1: Login
        DriverManager.getDriver().get(PropertiesReader.readKey("url"));
        LoginPage loginPage = new LoginPage();
        loginPage.login(PropertiesReader.readKey("username"), PropertiesReader.readKey("password"));

        // Step 2: Go to Employee List
        EmployeePage empPage = new EmployeePage();
        empPage.goToEmployeeMenu();
        empPage.goToEmployeeTab();

        // Step 3: Search employee "heru"
        By searchField = By.cssSelector("input[placeholder='Search name, e-mail, phone...']");
        WaitHelpers.waitForVisibility(DriverManager.getDriver(), searchField, 10);
        DriverManager.getDriver().findElement(searchField).sendKeys("heru");

        // Step 4: Klik Detail baris pertama
        By detailButton = By.id("button-detail-employee-0");
        WaitHelpers.waitForClickable(DriverManager.getDriver(), detailButton, 10);
        DriverManager.getDriver().findElement(detailButton).click();

        // Step 5: Scroll ke tombol toggle
        By toggleButton = By.id("activation-employee-button");
        WaitHelpers.waitForPresence(DriverManager.getDriver(), toggleButton, 10);
        WebElement toggleElement = DriverManager.getDriver().findElement(toggleButton);
        ((org.openqa.selenium.JavascriptExecutor) DriverManager.getDriver())
                .executeScript("arguments[0].scrollIntoView(true);", toggleElement);
        toggleElement.click();

        // Step 6: Klik konfirmasi di popup
        By confirmButton = By.id("activation-employee-confirm-button");
        WaitHelpers.waitForClickable(DriverManager.getDriver(), confirmButton, 10);
        DriverManager.getDriver().findElement(confirmButton).click();

        // Step 7: Verifikasi toast success
        By toastLocator = By.xpath("//p[contains(text(),'Success')]");
        WaitHelpers.waitForVisibility(DriverManager.getDriver(), toastLocator, 10);
        String toastText = DriverManager.getDriver().findElement(toastLocator).getText();

        Assert.assertTrue(toastText.toLowerCase().contains("success"),
                "Toast tidak sesuai: " + toastText);
    }

    @Test
    @Owner("Heru")
    @Description("Verify Resend Email Account works correctly with confirmation popup")
    @Severity(SeverityLevel.NORMAL)
    @Story("Resend Employee Email")
    public void testResendEmployeeEmail() {
        // Step 1: Login
        DriverManager.getDriver().get(PropertiesReader.readKey("url"));
        LoginPage loginPage = new LoginPage();
        loginPage.login(PropertiesReader.readKey("username"), PropertiesReader.readKey("password"));

        // Step 2: Go to Employee List
        EmployeePage empPage = new EmployeePage();
        empPage.goToEmployeeMenu();
        empPage.goToEmployeeTab();

        // Step 3: Search employee "heru"
        By searchField = By.cssSelector("input[placeholder='Search name, e-mail, phone...']");
        WaitHelpers.waitForVisibility(DriverManager.getDriver(), searchField, 10);
        DriverManager.getDriver().findElement(searchField).sendKeys("heru");

        // Step 4: Klik Detail baris pertama
        By detailButton = By.id("button-detail-employee-0");
        WaitHelpers.waitForClickable(DriverManager.getDriver(), detailButton, 10);
        DriverManager.getDriver().findElement(detailButton).click();

        // Step 5: Scroll ke tombol Resend Email
        By resendButton = By.id("resend-email-button");
        WaitHelpers.waitForPresence(DriverManager.getDriver(), resendButton, 10);
        WebElement resendElement = DriverManager.getDriver().findElement(resendButton);
        ((org.openqa.selenium.JavascriptExecutor) DriverManager.getDriver())
                .executeScript("arguments[0].scrollIntoView(true);", resendElement);
        resendElement.click();

        // Step 6: Klik konfirmasi di popup
        By confirmResendButton = By.id("resend-email-confirm-button");
        WaitHelpers.waitForClickable(DriverManager.getDriver(), confirmResendButton, 10);
        DriverManager.getDriver().findElement(confirmResendButton).click();

        // Step 7: Verifikasi toast success
        By toastLocator = By.xpath("//p[contains(text(),'Success')]");
        WaitHelpers.waitForVisibility(DriverManager.getDriver(), toastLocator, 10);
        String toastText = DriverManager.getDriver().findElement(toastLocator).getText();

        Assert.assertTrue(toastText.toLowerCase().contains("success"),
                "Toast tidak sesuai: " + toastText);
    }


    @Test
    @Owner("Heru")
    @Description("Verify Transfer Employee to another Program Studi works correctly")
    @Severity(SeverityLevel.CRITICAL)
    @Story("Transfer Employee")
    public void testTransferEmployee() throws InterruptedException {
        // Step 1: Login
        DriverManager.getDriver().get(PropertiesReader.readKey("url"));
        LoginPage loginPage = new LoginPage();
        loginPage.login(PropertiesReader.readKey("username"), PropertiesReader.readKey("password"));

        // Step 2: Go to Employee List
        EmployeePage empPage = new EmployeePage();
        empPage.goToEmployeeMenu();
        empPage.goToEmployeeTab();

        // Step 3: Klik Action -> Transfer
        By actionButton = By.id("menu-button-admin-employee-action");
        WaitHelpers.waitForClickable(DriverManager.getDriver(), actionButton, 10);
        DriverManager.getDriver().findElement(actionButton).click();

        By transferMenu = By.xpath("//button[@data-action='transfer']");
        WaitHelpers.waitForClickable(DriverManager.getDriver(), transferMenu, 10);
        DriverManager.getDriver().findElement(transferMenu).click();

        // Step 4: Search employee "heru"
        By searchField = By.cssSelector("input[placeholder='Search name, ID...']");
        WaitHelpers.waitForVisibility(DriverManager.getDriver(), searchField, 10);
        DriverManager.getDriver().findElement(searchField).sendKeys("heru");

        // Step 5: Tunggu cell dengan teks "heru" muncul (max 20 detik)
        By cellHeru = By.xpath("//table//tr//td[contains(translate(text(),'HERU','heru'),'heru')]");
        WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(20));
        WebElement cell = wait.until(ExpectedConditions.visibilityOfElementLocated(cellHeru));

        // Step 6: Klik tombol + di row yang mengandung "heru"
        WebElement plusButton = cell.findElement(By.xpath("./ancestor::tr//button[contains(@class,'chakra-button')]"));
        ((JavascriptExecutor) DriverManager.getDriver()).executeScript("arguments[0].scrollIntoView(true);", plusButton);
        plusButton.click();

        // Step 7: Scroll ke dropdown target Program Studi
        By programDropdown = By.xpath("//select[@name='Select Target Program Studi']");
        WaitHelpers.waitForPresence(DriverManager.getDriver(), programDropdown, 10);
        WebElement dropdownElement = DriverManager.getDriver().findElement(programDropdown);
        ((JavascriptExecutor) DriverManager.getDriver()).executeScript("arguments[0].scrollIntoView(true);", dropdownElement);

        // Pilih opsi "008"
        new Select(dropdownElement).selectByVisibleText("008");

        // Step 8: Klik Transfer Employee
        By transferButton = By.xpath("//button[contains(text(),'Transfer Employee')]");
        WaitHelpers.waitForClickable(DriverManager.getDriver(), transferButton, 10);
        DriverManager.getDriver().findElement(transferButton).click();

        // Step 9: Verifikasi toast success
        By toastLocator = By.xpath("//p[contains(text(),'Success')]");
        WaitHelpers.waitForVisibility(DriverManager.getDriver(), toastLocator, 10);
        String toastText = DriverManager.getDriver().findElement(toastLocator).getText();

        Assert.assertTrue(toastText.toLowerCase().contains("success"),
                "Toast tidak sesuai: " + toastText);
    }

}
