package testngprograms;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.*;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class DatadrivenTest{
    private WebDriver driver = null;
    private WebDriverWait wait=null;

    @BeforeSuite
    public void beforeSuite(){
        Reporter.log("Started executing the @BeforeSuite...",true);
        Reporter.log("This annotated method will be executed before all the tests in the suite",true);
    }

    @BeforeTest
    public void beforeTest(){
        Reporter.log("Started executing the @BeforeTest...",true);
        Reporter.log("This annotated method will be executed before any test inside the test tag classes ",true);
    }

    @BeforeClass
    public void launchBrowser(){
        Reporter.log("Started executing the @BeforeCLass...",true);
        ChromeOptions options = new ChromeOptions();
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("credentials_enable_service", false);
        prefs.put("profile.password_manager_enabled", false);
        prefs.put("profile.default_content_setting_values.notifications", 2);
        options.setExperimentalOption("prefs", prefs);
        options.addArguments("--incognito");
        options.addArguments("--disable-infobars");
        options.addArguments("--disable-notifications");
        options.addArguments("--disable-save-password-bubble");
        options.addArguments("--no-first-run");
        options.addArguments("--no-default-browser-check");
        options.addArguments("user-data-dir=C:\\Users\\rames\\AppData\\Local\\Google\\Chrome\\User Data\\NoPasswordProfile");
        options.addArguments("--start-maximized");


        driver = new ChromeDriver(options);
        Reporter.log("Browser has launched",true);
        Reporter.log("Add implicitwait",true);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        Reporter.log("initialize the explicit wait",true);
        wait = new WebDriverWait(driver,Duration.ofMillis(30000));
        Reporter.log("Clear the cookies",true);
        driver.manage().deleteAllCookies();

    }
    @BeforeMethod
    public void openApplication(){
        Reporter.log("Started executing the @BeforeMethod..",true);
        driver.get("https://www.saucedemo.com/");
        Reporter.log("saucedemo website is launched",true);
        wait.until(ExpectedConditions.titleContains("Swag Labs"));
        Assert.assertEquals(driver.getTitle(),"Swag Labs");

    }

    @Test(description="login scenario",dataProvider="logindata")
    public void loginTest(String username,String password){
       Reporter.log("Started executing the loginTest for :"+username+ " -- "+password);
       String headerTxt = driver.findElement(By.xpath("//*[@id='root']/div/div[1]")).getText();
       Assert.assertTrue(headerTxt.contains("Swag Labs"));
       Reporter.log("type the username"+username,true);
        WebElement userNameEditbox =driver.findElement(By.id("user-name"));
        userNameEditbox.clear();
        userNameEditbox.sendKeys(username);
        Reporter.log("type the password"+password,true);
        WebElement passwordEditbox =driver.findElement(By.name("password"));
        passwordEditbox.clear();
        passwordEditbox.sendKeys(password);
        Reporter.log("click on login button",true);
        driver.findElement(By.cssSelector("#login-button")).click();


    }


    @AfterMethod
    public void logout(){
        Reporter.log("Verify the products header text",true);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".title")));
        wait.until(ExpectedConditions.textToBePresentInElementLocated(By.cssSelector(".title"),"Products"));
        Assert.assertTrue(driver.findElement(By.cssSelector(".title")).isDisplayed());
        Actions actions = new Actions(driver);
        actions.sendKeys(Keys.ESCAPE).perform();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".app_logo")));
        Reporter.log("Click on menu bar",true);
        driver.findElement(By.xpath("//button[@id='react-burger-menu-btn']")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@id='logout_sidebar_link']")));
        Reporter.log("Click on logout link",true);
        driver.findElement(By.xpath("//a[@id='logout_sidebar_link']")).click();
        wait.until(ExpectedConditions.urlContains("https://www.saucedemo.com/"));
        wait.until(ExpectedConditions.titleContains("Swag Labs"));
        Assert.assertEquals(driver.getTitle(),"Swag Labs");

    }


    @DataProvider(name="logindata")
    public Object[][] testData(){
        Object[][] data = new Object[3][2];
        //1st row + 1st col
        data[0][0]="standard_user";
        //1st row +2nd col
        data[0][1]="secret_sauce";

        //2nd row + 1st col
        data[1][0]="problem_user";
        //2nd row +2nd col
        data[1][1]="secret_sauce";

        //3rd row + 1st col
        data[2][0]="error_user";
        //3rd row +2nd col
        data[2][1]="secret_sauce";

        return data;
    }

    @AfterClass
    public void closeBrowser(){
        if(driver!=null){
            driver.quit();
        }
    }

    @AfterTest
    public void afterTest(){
        Reporter.log("Started executing the @AfterTest...",true);
        Reporter.log("This annotated method will be executed after any test inside the test tag classes ",true);
    }

    @AfterSuite
    public void afterSuite(){
        Reporter.log("Started executing the @AfterSuite...",true);
        Reporter.log("This annotated method will be executed after all test inside the suite have run ",true);

    }
}
