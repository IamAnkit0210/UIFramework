package org.training.selenium.pages;

import org.openqa.selenium.By;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

public class LoginPage {

    WebDriver driver;
    public static Logger log = Logger.getLogger(LoginPage.class);
    public LoginPage(WebDriver driver)
    {
        this.driver = driver;
    }

    private By userName = By.xpath("//input[@id='user-name']");
    private By passwordEle = By.xpath("//input[@id='password']");
    private By loginBtn = By.xpath("//input[@id='login-button']");

    public void loginApp(String email, String password)
    {
        log.info("Entering username: " + email);
        driver.findElement(userName).sendKeys(email);
        log.info("Entering password (masked for security)");
        driver.findElement(passwordEle).sendKeys(password);
        log.info("Clicking on Login button");
        driver.findElement(loginBtn).click();
    }

    public void goToLoginPage()
    {
        driver.get("https://www.saucedemo.com/");
    }
}