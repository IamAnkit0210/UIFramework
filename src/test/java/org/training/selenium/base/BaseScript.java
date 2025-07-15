package org.training.selenium.base;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class BaseScript {

   public  WebDriver driver;
    public WebDriver initDriver() throws IOException {
        Properties prop = new Properties();
        FileInputStream file = new FileInputStream(System.getProperty("user.dir")+ "//src//main/java//resources//Config.properties");
        prop.load(file);
        String browserName = prop.getProperty("browser");

        switch (browserName) {
            case "chrome":
                ChromeOptions options = new ChromeOptions();
                options.setExperimentalOption("excludeSwitches", List.of("enable-automation"));
                options.setExperimentalOption("useAutomationExtension", false);
                options.addArguments("--disable-blink-features=AutomationControlled");
                options.addArguments("disable-popup-blocking");

                Map<String, Object> prefs = new HashMap<>();
                prefs.put("credentials_enable_service", false);
                prefs.put("profile.password_manager_enabled", false);
                options.setExperimentalOption("prefs", prefs);

                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver(options);
                break;

            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver();
                break;

            case "edge":
                WebDriverManager.edgedriver().setup();
                driver = new EdgeDriver();
                break;

            default:
                throw new IllegalArgumentException("Invalid browser selected: " + browserName);
        }
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
        return this.driver;
    }

    @BeforeMethod(alwaysRun = true)
    public WebDriver launchApp() throws IOException {
        driver = initDriver();
        return driver;
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown()
    {
        driver.close();
    }
}