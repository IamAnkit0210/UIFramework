package org.training.selenium.tests;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.training.selenium.base.BaseScript;
import org.training.selenium.pages.CartPage;
import org.training.selenium.pages.LoginPage;
import org.training.selenium.pages.ProductPage;
import org.training.selenium.util.Constants;
import org.training.selenium.util.Retry;

import java.util.List;

public class CartTest extends BaseScript {
    LoginPage loginPage;
    ProductPage productPage;
    CartPage cartPage;
    @Test(description = "Validate user is able to add product in the cart",retryAnalyzer = Retry.class)
    public void TC01_validateCartProductAdd() {

        String productName = Constants.PRODUCT_NAME;

        loginPage = new LoginPage(driver);
        loginPage.goToLoginPage();
        loginPage.loginApp(Constants.STD_USER_NAME,Constants.USER_PASSWORD);

        productPage = new ProductPage(driver);
        List<WebElement> products = productPage.getProductList();
        productPage.addProductToCart(products,productName);

        //Go to cart
        cartPage = productPage.goToCart();

        //Confirm product in cart
        Assert.assertEquals(productName,cartPage.getCartProducts(),"Same product reflects");
    }
}