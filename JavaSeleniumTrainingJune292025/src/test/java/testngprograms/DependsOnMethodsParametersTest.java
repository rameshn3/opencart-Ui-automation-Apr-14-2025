import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
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

public class DependsOnMethodsParametersTest{
    private WebDriver driver = null;
    private WebDriverWait wait=null;

    @BeforeClass(alwaysRun=true)
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
    @Test(alwaysRun=true)
    public void homePageTest(){
        Reporter.log("Started executing the @BeforeMethod..",true);
        driver.get("https://www.saucedemo.com/");
        Reporter.log("saucedemo website is launched",true);
        wait.until(ExpectedConditions.titleContains("Swag Labs"));
        Assert.assertEquals(driver.getTitle(),"Swag Labs");

    }
    @Parameters({"username","password"})
    @Test(description="login")
    public void doLoginTest(String username,String password){
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


    @Test
    public void DoLogout(){
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
        data[0][0]="standard_user";
        //1st row +2nd col
        data[0][1]="secret_sauce";
    }




    @AfterClass
    public void closeBrowser(){
        if(driver!=null){
            driver.quit();
        }
    }


}
