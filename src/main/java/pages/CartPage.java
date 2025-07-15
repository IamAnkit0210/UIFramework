package pages;

import util.WaitsUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class CartPage extends WaitsUtil {
    WebDriver driver;
    public CartPage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver,this);
    }
    private By cartItemBy = By.className("inventory_item_name");

    public String getCartProducts()
    {
        waitForElementToAppear(cartItemBy);
        return driver.findElement(By.className("inventory_item_name")).getText();
    }
}