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
import java.util.List;

public class HerokuAppNestedFramesTest{
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
        driver.manage().deleteAllCookies();
        //open the url
        driver.get("https://the-internet.herokuapp.com/");

        wait = new WebDriverWait(driver,Duration.ofSeconds(30));

        wait.until(ExpectedConditions.titleContains("The Internet"));

        WebElement framestLink =driver.findElement(By.linkText("Frames"));
        wait.until(ExpectedConditions.elementToBeClickable(framestLink));
        //click on Form Authentication link
        framestLink.click();

            }

            @BeforeEach
            public void setup(){
                wait.until(ExpectedConditions.urlContains("https://the-internet.herokuapp.com/frames"));
                WebElement framesHeaderText= wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div[class='example'] h3")));
                Assertions.assertEquals(framesHeaderText.getText(),"Frames");
            }

            @Disabled
      @Test
    public void nestedFramesTest() throws InterruptedException{
        System.out.println("Started nestedFramesTest....");
        //click on nested frames link
        driver.findElement(By.xpath("//a[normalize-space()='Nested Frames']")).click();

        wait.until(ExpectedConditions.urlContains("/nested_frames"));

        //fetch total no of frames in the page
          List<WebElement> framesList = driver.findElements(By.tagName("frame"));
          System.out.println("No of frames in the page is:"+framesList.size());
        //switch to the top frame
        driver.switchTo().frame("frame-top");
          List<WebElement> childframesList = driver.findElements(By.tagName("frame"));
          System.out.println("No of child frames in the page is:"+childframesList.size());
          //switch to middle frame
          driver.switchTo().frame(1);
          //fetch the text
          String txt = driver.findElement(By.id("content")).getText();
          Assertions.assertEquals(txt,"MIDDLE");

          //switch back to original position
          driver.switchTo().defaultContent();

    }

    @Test
    public void iframeTest(){
        System.out.println("started executing the iframeTest....");
        //click on iFrame link
        driver.findElement(By.linkText("iFrame")).click();

        //wait for the url /iframe
        wait.until(ExpectedConditions.urlContains("/iframe"));

        //wait for the header
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div[class='example'] h3")));
        String headerTxt = driver.findElement(By.cssSelector("div[class='example'] h3")).getText();

        Assertions.assertEquals(headerTxt,"An iFrame containing the TinyMCE WYSIWYG Editor");
        //close the Tinumce notification
        driver.findElement(By.cssSelector(".tox-notification__dismiss.tox-button.tox-button--naked.tox-button--icon")).click();
        //identify the iframe webelement
        WebElement iframeElement =driver.findElement(By.id("mce_0_ifr"));
        //switch to the ifrae
        driver.switchTo().frame(iframeElement);
        //clear the content
        String txt = driver.findElement(By.cssSelector("body p")).getText();
        Assertions.assertEquals(txt,"Your content goes here.");
        //switch back to normla position
        driver.switchTo().defaultContent();
    }

    @AfterEach
    public void navigateBackToFrames() throws InterruptedException{
        driver.navigate().back();
        Thread.sleep(3000);
    }
    @AfterAll
    public static void closeBrowser(){
        if(driver!=null){
            driver.quit();
        }
    }


}
