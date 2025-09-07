package basicprograms;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.Date;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ScrollAndTakesScreenshot{

    private static WebDriver driver;
    public static void main(String[] args) throws IOException, InterruptedException{
        driver = new ChromeDriver();
//a   //maximize the window
                driver.manage().window().maximize();

                //add implicitwait
                driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

                //open the url
                driver.get("https://www.amazon.in/");

                //explicitwait
                WebDriverWait wait=new WebDriverWait(driver,Duration.ofMillis(30000));

                wait.until(ExpectedConditions.titleContains("Amazon.in"));
                //type the value in search editbox
        WebElement searchEditbox =driver.findElement(By.name("field-keywords"));
        searchEditbox.clear();
        searchEditbox.sendKeys("Iphone 16");
        //PRESS ENTER key from keyboard
        searchEditbox.sendKeys(Keys.ENTER);
        //click on search torch icon
     //   driver.findElement(By.cssSelector("#nav-search-submit-button")).click();
        //   driver.findElement(By.cssSelector("#nav-search-submit-button")).submit();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("(//div[contains(@class,'s-breadcrumb')])[1]")));
        wait.until(ExpectedConditions.titleContains("Amazon.in : Iphone 16"));
        captureScreenshot("resultsscreen");
        //scrolldown
        JavascriptExecutor jsx =(JavascriptExecutor)driver;
        jsx.executeScript("window.scrollBy(0,2000)","");
        captureScreenshot("scrollDown");
        //ScrollUp
        jsx.executeScript("window.scrollBy(0,-2000)","");
        captureScreenshot("scrollUP");
        WebElement backToTop =driver.findElement(By.id("navBackToTop"));
        jsx.executeScript("arguments[0].scrollIntoView(true);",backToTop);
        captureScreenshot(backToTop,"backToTopWebElement");
        //click on backtotop
        backToTop.click();
        //scroll based on keys enum
        for(int i=1;i<=10;i++){
            driver.findElement(By.tagName("body")).sendKeys(Keys.DOWN);
        }
        captureScreenshot("scrollDownByKeys");

        for(int i=1;i<=10;i++){
            driver.findElement(By.tagName("body")).sendKeys(Keys.UP);
        }
        captureScreenshot("scrollUPByKeys");
        Thread.sleep(3000);
        driver.quit();
    }
    /**
     * Taking the entire browser screenshot
     * @param screenName
     * @throws IOException
     */
    private static void captureScreenshot(String screenName) throws IOException {

        //take screenshot
        File src=((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);

        //create Object for Date class
        Date d = new Date();
        screenName=screenName+"-"+d.toString().replace(":", "-").replace(" ", "-")+".jpg";

        //copy the file name under project directory
        FileUtils.copyFile(src, new File(System.getProperty("user.dir")+"\\src\\test\\resources\\screenshots\\"+screenName));


    }
    /**
     * taking the element screenshot
     * @param element
     * @param screenName
     * @throws IOException
     */
    private static void captureScreenshot(WebElement element,String screenName) throws IOException {

        //take screenshot
        File src=((TakesScreenshot)element).getScreenshotAs(OutputType.FILE);

        //create Object for Date class
        Date d = new Date();
        screenName=screenName+"-"+d.toString().replace(":", "-").replace(" ", "-")+".jpg";

        //copy the file name under project directory
        FileUtils.copyFile(src, new File(System.getProperty("user.dir")+"\\src\\test\\resources\\screenshots\\"+screenName));


    }
}
