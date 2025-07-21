package org.training.selenium.base;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.*;

public class BaseScript {

    public  WebDriver driver;
    public static Logger log = Logger.getLogger(BaseScript.class);
    public WebDriver initDriver() throws IOException {

        File logDir = new File(System.getProperty("user.dir") + "/log");
        if (!logDir.exists()) {
            logDir.mkdirs();
        }

        //Configure Logger file
        PropertyConfigurator.configure(System.getProperty("user.dir") + "//src//test/java//org//training//selenium//resources//log4j.properties");

        //Configure Config file
        Properties prop = new Properties();
        FileInputStream file = new FileInputStream(System.getProperty("user.dir")+ "//src//test/java//org//training//selenium//resources//Config.properties");

        prop.load(file);
        //String browserName = prop.getProperty("browser");

        String browserName =  System.getProperty("browser")!=null? System.getProperty("browser"):prop.getProperty("browser");

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
                log.info("Chrome browser launched.");
                break;

            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver();
                log.info("Firefox browser launched.");
                break;

            case "edge":
                WebDriverManager.edgedriver().setup();
                driver = new EdgeDriver();
                log.info("Edge browser launched.");
                break;

            default:
                log.error("Invalid browser selected in config: " + browserName);
                throw new IllegalArgumentException("Invalid browser selected: " + browserName);
        }
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
        log.info("Browser maximized and implicit wait set.");
        return driver;
    }

    public String getCurrentTimeStamp()
    {
        return new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
    }
    public String takeScreenShot(String testCaseName,WebDriver driver) throws IOException {
        if(driver != null)
        {
            TakesScreenshot ts = (TakesScreenshot) driver;
            File source = ts.getScreenshotAs(OutputType.FILE);
            String fileName = testCaseName + "_" + getCurrentTimeStamp() + ".png";
            File destFile = Paths.get(System.getProperty("user.dir"), "reports", fileName).toFile();
            FileUtils.copyFile(source, destFile);
            log.info("Screenshot saved: " + destFile.getAbsolutePath());
            return destFile.getAbsolutePath();
        }
        return null;
    }

    @BeforeMethod(alwaysRun = true)
    public WebDriver launchApp() throws IOException {
        log.info("Launching the application...");
        driver = initDriver();
        return driver;
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown()
    {
        if (driver != null) {
            driver.quit();
            log.info("Browser session closed.");
        }else {
            log.warn("Driver is already null. Nothing to quit.");
        }
    }
}