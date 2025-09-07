package junittests;

import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class HerokuAppLoginTest{
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

        WebElement formAuthentiationLink =driver.findElement(By.linkText("Form Authentication"));
        wait.until(ExpectedConditions.elementToBeClickable(formAuthentiationLink));
        //click on Form Authentication link
        formAuthentiationLink.click();

    }

    @BeforeEach
    public void preconditionStep(){
        wait.until(ExpectedConditions.urlContains("https://the-internet.herokuapp.com/login"));
       WebElement loginHeaderText= wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div[class='example'] h2")));
        Assertions.assertEquals(loginHeaderText.getText(),"Login Page");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".subheader")));
    }

    @Test
    public void validCredentialTest() throws InterruptedException{
        System.out.println("Started validCredentialTest....");
        doLogin("tomsmith","SuperSecretPassword!");
        wait.until(ExpectedConditions.urlContains("/secure"));
        //wait for the successmessage
        verifyMessage("You logged into a secure area!");
       wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div[class='example'] h2")));
        //click on close
        driver.findElement(By.className("close")).click();
        Thread.sleep(3000);
        //click on logout button
        driver.findElement(By.partialLinkText("Logout")).click();
    }

    @Test
    public void validUserNameAndInvalidPasswordTest(){
        System.out.println("started executing the validUserNameAndInvalidPasswordTest...");
        doLogin("tomsmith","cretPassword!");
        verifyMessage("Your password is invalid!");
    }

    @Test
    public void invalidUserNameAndValidPasswordTest(){
        System.out.println("started executing the invalidUserNameAndValidPasswordTest...");
        doLogin("toms","SuperSecretPassword!");
        verifyMessage("Your username is invalid!");
    }


    @Test
    public void emptyCredentialsTest(){
        System.out.println("started executing the invalidUserNameAndValidPasswordTest...");
        doLogin(" "," ");
        verifyMessage("Your username is invalid!");
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


    private static void doLogin(String username,String password){
        WebElement userNameEditbox =driver.findElement(By.name("username"));
        WebElement passwordEditbox =driver.findElement(By.id("password"));
        WebElement loginBtn =driver.findElement(By.xpath("//button[@type='submit']"));
        //clear the content in the userNameEditbox
        userNameEditbox.clear();
        userNameEditbox.sendKeys(username);
        //clear the passwordEditbox
        passwordEditbox.clear();
        passwordEditbox.sendKeys(password);
        //click on login button
        loginBtn.click();
    }


    private static void verifyMessage(String expectedMsg){
       wait.until(ExpectedConditions.presenceOfElementLocated(By.id("flash")));
        String msgTxt = driver.findElement(By.id("flash")).getText();
        Assertions.assertTrue(msgTxt.contains(expectedMsg));
    }
}
