package junittests;

import org.junit.jupiter.api.*;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class HerokuAppAlertsTest{
    private static WebDriver driver=null;
    private static WebDriverWait wait = null;

    @BeforeAll
    public static void launchBrowser(){
        driver = new ChromeDriver();
        //maximize the window
        //maximize the window
        driver.manage().window().maximize();

        //add implicitwait
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        //open the url
        driver.get("https://the-internet.herokuapp.com/");

        wait = new WebDriverWait(driver,Duration.ofSeconds(30));

        wait.until(ExpectedConditions.titleContains("The Internet"));

        WebElement jsAlertLink =driver.findElement(By.linkText("JavaScript Alerts"));
        wait.until(ExpectedConditions.elementToBeClickable(jsAlertLink));
        //click on Form Authentication link
        jsAlertLink.click();

    }

    @BeforeEach
    public void preconditionStep(){
        wait.until(ExpectedConditions.urlContains("https://the-internet.herokuapp.com/javascript_alerts"));
       WebElement jsAlertHeaderText= wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div[class='example'] h3")));
        Assertions.assertEquals(jsAlertHeaderText.getText(),"JavaScript Alerts");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='content']/div/p[1]")));
    }

    @Test
    public void simpleAlertTest() throws InterruptedException{
        System.out.println("Started simpleAlertTest....");
        //click on logout button
        driver.findElement(By.xpath("//button[normalize-space()='Click for JS Alert']")).click();
        //switch to the alert
        Alert alt = driver.switchTo().alert();
        System.out.println("simple alert message is:"+alt.getText());
        //accept the alert
        alt.accept();
        verifyAlertResult("You successfully clicked an alert");
    }

    @Test
    public void confirmationAlertTest(){
        System.out.println("started executing the confirmationAlertTest...");
        //click on Click for JS Confirm button
        driver.findElement(By.xpath("//button[text()='Click for JS Confirm']")).click();
        //switch to the alert
        Alert cnf = driver.switchTo().alert();
        System.out.println("confirmation alert message is:"+cnf.getText());
        //accept the alert
        cnf.accept();
        verifyAlertResult("You clicked: Ok");

        //click on Click for JS Confirm button
        driver.findElement(By.xpath("//button[text()='Click for JS Confirm']")).click();
        //switch to the alert
        Alert cnf1 = driver.switchTo().alert();
        System.out.println("confirmation alert message is:"+cnf1.getText());
        //accept the alert
        cnf1.dismiss();
        verifyAlertResult("You clicked: Cancel");
    }

    @Test
    public void promptAlertTest(){
        System.out.println("started executing the promptAlertTest...");
        //click on Click for JS Prompt button
        driver.findElement(By.xpath("//button[text()='Click for JS Prompt']")).click();
        //switch to the alert
        Alert prmpt = driver.switchTo().alert();
        System.out.println("prompt alert message is:"+prmpt.getText());

        //type the value in editbox
        prmpt.sendKeys("Selenium");

        //accept the alert
        prmpt.accept();
        verifyAlertResult("You entered: Selenium");

        //handle cancel scenario
        //click on logout button
        driver.findElement(By.xpath("//button[text()='Click for JS Prompt']")).click();
        //switch to the alert
        Alert prmpt1 = driver.switchTo().alert();
        System.out.println("simple alert message is:"+prmpt1.getText());
        //type the vlaue in editbox
        prmpt1.sendKeys("Selenium");
        //dismiss the alert
        prmpt1.dismiss();
        verifyAlertResult("You entered: null");
    }



    @AfterEach
    public void tearDown(){
        driver.manage().deleteAllCookies();
    }

    @AfterAll
    public static void closeBrowser(){
        if(driver!=null){
            driver.quit();
        }
    }

    private static void verifyAlertResult(String expectedMsg){
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("result")));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("result")));
        String resultTxt = driver.findElement(By.id("result")).getText();
        Assertions.assertEquals(resultTxt,expectedMsg);

    }

}
