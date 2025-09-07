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
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.*;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SocialLinksMenuRightClickTest{
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
         options.addArguments("--headless=new");

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

    }

    @Test(description="rightclick and menu items",priority=0)
    public void rightCLickMenuItemsTest() throws InterruptedException{
       Reporter.log("Started executing the dragAndDropTest ",true);
        driver.get("https://qaplayground.dev/apps/context-menu/");
        String title = "Right-Click Context Menu";
        wait.until(ExpectedConditions.titleIs(title));
        Assert.assertEquals(driver.getTitle(),title);
        Reporter.log("fetch all the elements",true);
        List<WebElement> elementList = driver.findElements(By.cssSelector("ul.share-menu li span"));
        Reporter.log("create object for Actions class",true);
        Actions act = new Actions(driver);

        for(WebElement element:elementList){
            //perform the right click operation on the page
            act.contextClick(driver.findElement(By.tagName("body"))).perform();
            //move the cursor to the share element
            act.moveToElement(driver.findElement(By.cssSelector("li.menu-item.share"))).perform();
            //click on each element
            element.click();
            //identif ythe mesg locator
            String msgLocator = "p#msg";
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(msgLocator)));
            if(element.getText().equalsIgnoreCase("Twitter")){
                String twitterMsg = driver.findElement(By.cssSelector(msgLocator)).getText();
                String expMsg = "Menu item Twitter clicked";
                Assert.assertEquals(twitterMsg,expMsg);

            }else if(element.getText().equalsIgnoreCase("Instagram")){
                String instagramMsg = driver.findElement(By.cssSelector(msgLocator)).getText();
                String expMsg = "Menu item Instagram clicked";
                Assert.assertEquals(instagramMsg,expMsg);

            }else if(element.getText().equalsIgnoreCase("Dribble")){
                String dribbleMsg = driver.findElement(By.cssSelector(msgLocator)).getText();
                String expMsg = "Menu item Dribble clicked";
                Assert.assertEquals(dribbleMsg,expMsg);

            }else if(element.getText().equalsIgnoreCase("Telegram")){
                String telegramMsg = driver.findElement(By.cssSelector(msgLocator)).getText();
                String expMsg = "Menu item Telegram clicked";
                Assert.assertEquals(telegramMsg,expMsg);

            }
        }
        Thread.sleep(3000);

    }



    @AfterClass(alwaysRun=true)
    public void closeBrowser(){
        if(driver!=null){
            driver.quit();
        }
    }


}
