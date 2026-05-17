package com.dibimbingqa.pages;

import com.dibimbingqa.base.CommonToAllPage;
import com.dibimbingqa.utils.WaitHelpers;
import org.openqa.selenium.By;

public class LoginPage extends CommonToAllPage {

    private By usernameField = By.id("input-username-or-email");
    private By passwordField = By.id("input-password");
    private By signInButton = By.id("button-sign-in");

    public void login(String username, String password) {
        // tunggu field username muncul
        WaitHelpers.waitForVisibility(getDriver(), usernameField, 10);
        type(usernameField, username);

        // tunggu field password muncul
        WaitHelpers.waitForVisibility(getDriver(), passwordField, 10);
        type(passwordField, password);

        // tunggu tombol sign in bisa diklik
        WaitHelpers.waitForClickable(getDriver(), signInButton, 10);
        click(signInButton);
    }
}
