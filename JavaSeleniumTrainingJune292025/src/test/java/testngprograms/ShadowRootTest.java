package testngprograms;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.*;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HandleWebTablesAndpaginationTest{
    private WebDriver driver = null;
    private WebDriverWait wait=null;



    @Parameters({"browser"})
    @BeforeClass(alwaysRun=true)
    public void launchBrowser(@Optional("chrome") String browser){
        Reporter.log("@BeforeClass Annotation will be executed before any test inside the current class",true);
        if(browser.equalsIgnoreCase("chrome")){
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

            // Use new headless mode
         //  options.addArguments("--headless=new");

            // Disable automation flags
            options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
            options.setExperimentalOption("useAutomationExtension", false);

            // Set window size (important for rendering)
            options.addArguments("--window-size=1920,1080");

            // Custom user agent (latest Chrome)
            options.addArguments("user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) " +
                    "AppleWebKit/537.36 (KHTML, like Gecko) Chrome/115.0.0.0 Safari/537.36");

            // Disable Blink automation features
            options.addArguments("--disable-blink-features=AutomationControlled");

            // Optional: disable GPU & sandbox for CI/CD
            options.addArguments("--disable-gpu");
            options.addArguments("--no-sandbox");


            options.addArguments("--window-size=1920,1080");
            //options.addArguments("user-data-dir=C:\\Users\\rames\\AppData\\Local\\Google\\Chrome\\User Data\\NoPasswordProfile");
            options.addArguments("--start-maximized");
            driver = new ChromeDriver(options);
        }else if(browser.equalsIgnoreCase("firefox")){
            // Set Firefox profile preferences
            FirefoxProfile profile = new FirefoxProfile();

            // Disable password manager
            profile.setPreference("signon.rememberSignons", false);
            profile.setPreference("signon.autofillForms", false);
            profile.setPreference("signon.autologin.proxy", false);
            profile.setPreference("security.insecure_password.ui.enabled", false);
            profile.setPreference("security.insecure_field_warning.contextual.enabled", false);

            // Disable notifications
            profile.setPreference("dom.webnotifications.enabled", false);
            profile.setPreference("permissions.default.desktop-notification", 2);

            // Disable infobars or first run
            profile.setPreference("browser.aboutConfig.showWarning", false);
            profile.setPreference("browser.startup.homepage_override.mstone", "ignore");
            profile.setPreference("startup.homepage_welcome_url.additional", "about:blank");

            // Start in private browsing mode (incognito)
            profile.setPreference("browser.privatebrowsing.autostart", true);

            // Maximize window (can be done via driver.manage().window().maximize() too)
            FirefoxOptions options = new FirefoxOptions();
            options.setProfile(profile);
            // Run in headless mode
            options.addArguments("--headless");
            options.addArguments("--width=1920");
            options.addArguments("--height=1080");

            // Initialize the WebDriver
            driver = new FirefoxDriver(options);

        }else if(browser.equalsIgnoreCase("msedge")){
            // Set preferences similar to ChromeOptions
            Map<String, Object> prefs = new HashMap<>();
            prefs.put("credentials_enable_service", false);
            prefs.put("profile.password_manager_enabled", false);
            prefs.put("profile.default_content_setting_values.notifications", 2); // 2 = block

            EdgeOptions options = new EdgeOptions();
            options.setExperimentalOption("prefs", prefs);

            // Add arguments similar to Chrome
            options.addArguments("--incognito");
            options.addArguments("--disable-infobars");
            options.addArguments("--disable-notifications");
            options.addArguments("--disable-save-password-bubble");
            options.addArguments("--no-first-run");
            options.addArguments("--no-default-browser-check");
            options.addArguments("--start-maximized");
            // Run in headless mode
           options.addArguments("--headless=new");
            options.addArguments("--window-size=1920,1080");
            // Initialize the EdgeDriver
            driver = new EdgeDriver(options);
        }
        Reporter.log("Browser has launched",true);
        Reporter.log("Add implicitwait",true);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        Reporter.log("initialize the explicit wait",true);
        wait = new WebDriverWait(driver,Duration.ofMillis(30000));
        Reporter.log("Clear the cookies",true);
        driver.manage().deleteAllCookies();
        driver.get("https://testautomationpractice.blogspot.com/");
    }

    @Test(description="webTableAndPaginationTest",priority=0)
    public void webTableAndPaginationTest() throws InterruptedException{
       Reporter.log("Started executing the webTableAndPaginationTest ",true);

        Reporter.log(" verify the web table header",true);
       String webtableHeaderText= driver.findElement(By.cssSelector("div[id='HTML8'] h2[class='title']")).getText();
        Assert.assertEquals(webtableHeaderText,"Pagination Web Table");
    //fetch the number of columns
        List<WebElement>colsList = driver.findElements(By.cssSelector("table[id='productTable'] thead tr th"));
       Reporter.log("columns size is:"+colsList.size(),true);

        //fetch the number of rows
        List<WebElement>rowsList = driver.findElements(By.cssSelector("table[id='productTable'] tbody tr"));
        Reporter.log("rows size is:"+rowsList.size(),true);
        selectProduct("Smartphone");
        selectProduct("Tablet");
        selectProduct("Wireless Earbuds");

//pagination handling
        List<WebElement>pages = driver.findElements(By.cssSelector("ul#pagination li a"));
        Reporter.log("total no of pages:"+pages.size(),true);
        for(int p =0;p<pages.size();p++){
            if(p>0){
                pages.get(p).click();
                Thread.sleep(2000);
            }
            //Read the data from the page
            Reporter.log("Read the data from the page:"+(p+1),true);
            rowsList = driver.findElements(By.cssSelector("table[id='productTable'] tbody tr"));
            for(int i =0;i<rowsList.size();i++){
                //fetch all tds
                List<WebElement>tds= rowsList.get(i).findElements(By.tagName("td"));
                for(int j=0;j<tds.size()-1;j++){
                    String cellText = tds.get(j).getText();
                    Reporter.log("Row "+(i+1)+", columns "+(j+1)+" :"+cellText);
                }
            }
        }



    }

    private void selectProduct(String productName){
        //fetch the number of rows
        List<WebElement>rowsList = driver.findElements(By.cssSelector("table[id='productTable'] tbody tr"));
        Reporter.log("rows size is:"+rowsList.size(),true);
        for(WebElement row:rowsList){
            if(row.getText().contains(productName)){
                row.findElement(By.cssSelector("input[type='checkbox']")).click();
                Reporter.log("selected product: "+productName);
                break;
            }
        }

    }



    @AfterClass(alwaysRun=true)
    public void closeBrowser(){
        if(driver!=null){
            driver.quit();
        }
    }


}
