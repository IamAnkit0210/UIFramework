package org.training.selenium.tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class DemoTest {
    public static void main(String[] args) {

        String productName = "Sauce Labs Backpack";
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("https://www.saucedemo.com/");
        driver.manage().window().maximize();

        driver.findElement(By.xpath("//input[@id='user-name']")).sendKeys("standard_user");
        driver.findElement(By.xpath("//input[@id='password']")).sendKeys("secret_sauce");
        driver.findElement(By.xpath("//input[@id='login-button']")).click();


        List<WebElement> products = driver.findElements(By.xpath("//div[@class='inventory_item_description']"));
        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(5));

        for(WebElement product:products) {
            String name = product.findElement(By.className("inventory_item_name")).getText();
            if(name.equals(productName))
            {
                WebElement addToCartButton = product.findElement(By.tagName("button"));
                wait.until(ExpectedConditions.elementToBeClickable(addToCartButton)).click();
                break;
            }
        }

        // Go to cart
        WebElement cartIcon = driver.findElement(By.id("shopping_cart_container"));
        wait.until(ExpectedConditions.elementToBeClickable(cartIcon)).click();

        // Confirm product in cart
        WebElement cartItem = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("inventory_item_name")));
        System.out.println("Item in cart: " + cartItem.getText());

        driver.quit();

    }
}
