package org.training.selenium.pages;

import org.training.selenium.util.WaitsUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class ProductPage extends WaitsUtil {
    WebDriver driver;

    public ProductPage(WebDriver driver)
    {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver,this);
    }

    @FindBy(xpath = "//div[@class='inventory_item_description']")
    List<WebElement> products;

    @FindBy(id = "shopping_cart_container")
    WebElement cartIcon;

    private By productsBy = By.xpath("//div[@class='inventory_item_description']");

    public void handleAlert()
    {
        handleAlertIfPresent();
    }
    public List<WebElement> getProductList()
    {
        waitForElementToAppear(productsBy);
        return products;
    }

    public void addProductToCart(List<WebElement> products,String productName)
    {
        for(WebElement product:products) {
            String name = product.findElement(By.className("inventory_item_name")).getText();
            if(name.equals(productName))
            {
                WebElement addToCartButton = product.findElement(By.tagName("button"));
                waitForElementToClick(addToCartButton);
                addToCartButton.click();
                break;
            }
        }
    }

    public CartPage goToCart()
    {
        waitForElementToClick(cartIcon);
        cartIcon.click();
        return new CartPage(driver);
    }
}