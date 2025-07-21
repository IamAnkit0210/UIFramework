package org.training.selenium.pages;

import org.training.selenium.util.WaitsUtil;
import org.openqa.selenium.By;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class CartPage extends WaitsUtil {
    public static Logger log = Logger.getLogger(CartPage.class);

    WebDriver driver;
    public CartPage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver,this);
        log.info("CartPage initialized.");
    }
    private By cartItemBy = By.className("inventory_item_name");

    public String getCartProducts()
    {
        log.info("Waiting for cart item to appear: " + cartItemBy.toString());
        waitForElementToAppear(cartItemBy);

        String cartItem = driver.findElement(cartItemBy).getText();
        log.info("Product found in cart: " + cartItem);
        return cartItem;
    }
}