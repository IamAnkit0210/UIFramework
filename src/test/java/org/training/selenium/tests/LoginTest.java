package org.training.selenium.tests;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.training.selenium.base.BaseScript;
import org.training.selenium.pages.LoginPage;
import org.training.selenium.util.Constants;

public class LoginTest  extends BaseScript {
    LoginPage loginPage;
    @Test(description = "Validate login for all types of users",dataProvider = "login-data")
    public void validateSauceLogin(String username, String password) {
        loginPage = new LoginPage(driver);
        loginPage.goToLoginPage();
        loginPage.loginApp(username,password);
    }

    @DataProvider(name = "login-data")
    public Object[][] validLoginDP(){
        return new Object[][]{
                {Constants.STD_USER_NAME,Constants.USER_PASSWORD},
                {Constants.LOCKED_USER_NAME,Constants.USER_PASSWORD},
                {Constants.PROBLEM_USER_NAME,Constants.USER_PASSWORD},
                {Constants.PERF_USER_NAME,Constants.USER_PASSWORD},
                {Constants.ERROR_USER_NAME,Constants.USER_PASSWORD},
                {Constants.VISUAL_USER_NAME,Constants.USER_PASSWORD},
        };
    }
}
