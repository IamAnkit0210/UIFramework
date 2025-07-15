package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage {

    WebDriver driver;
    public LoginPage(WebDriver driver)
    {
        this.driver = driver;
    }

    private By userName = By.xpath("//input[@id='user-name']");
    private By passwordEle = By.xpath("//input[@id='password']");
    private By loginBtn = By.xpath("//input[@id='login-button']");

    public void loginApp(String email, String password)
    {
        driver.findElement(userName).sendKeys(email);
        driver.findElement(passwordEle).sendKeys(password);
        driver.findElement(loginBtn).click();
    }

    public void goToLoginPage()
    {
        driver.get("https://www.saucedemo.com/");
    }
}