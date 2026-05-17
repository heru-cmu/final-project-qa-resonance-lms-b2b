package com.dibimbingqa.tests;

import com.dibimbingqa.base.CommonToAllTest;
import com.dibimbingqa.driver.DriverManager;
import com.dibimbingqa.pages.LoginPage;
import com.dibimbingqa.utils.PropertiesReader;
import com.dibimbingqa.utils.WaitHelpers;
import io.qameta.allure.*;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

@Epic("LMS QA Automation")
@Feature("Login Management")
public class LoginTest extends CommonToAllTest {

    @Test
    @Owner("Heru")
    @Description("Verify valid login redirects to dashboard/employee menu")
    @Severity(SeverityLevel.CRITICAL)
    @Story("Login with valid credentials")
    public void testLoginValid() {
        DriverManager.getDriver().get(PropertiesReader.readKey("url"));

        LoginPage loginPage = new LoginPage();
        loginPage.login(PropertiesReader.readKey("username"), PropertiesReader.readKey("password"));

        // Tunggu sampai menu Employee muncul (bukti login sukses)
        By employeeMenuLocator = By.id("layout-desktop-menu-item-box-employee");
        WaitHelpers.waitForVisibility(DriverManager.getDriver(), employeeMenuLocator, 15);

        // Verifikasi URL redirect
        String currentUrl = DriverManager.getDriver().getCurrentUrl();
        Assert.assertTrue(
                currentUrl.contains("dashboard") || currentUrl.contains("employee"),
                "Login gagal, tidak redirect ke halaman dashboard/employee"
        );
    }

}
