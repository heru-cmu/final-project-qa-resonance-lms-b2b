package com.dibimbingqa.base;

import com.dibimbingqa.driver.DriverManager;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class CommonToAllTest {

    @BeforeMethod
    public void setUp() {
        DriverManager.init();
    }

    @AfterMethod
    public void tearDown() {
        DriverManager.down();
    }
}
