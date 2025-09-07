package basicprograms;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class BooleanCommandsDemoTest{
    private static WebDriver driver=null;

    public static void main(String[] args) throws InterruptedException{
        //launch the chrome browser
        driver=new ChromeDriver();

        //maximize the window
        driver.manage().window().maximize();

        //add implicitwait
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        //open the url
        driver.get("https://testautomationpractice.blogspot.com/");

        //explicitwait
        WebDriverWait wait=new WebDriverWait(driver,Duration.ofMillis(30000));

        wait.until(ExpectedConditions.titleContains("Automation Testing Practice"));

        WebElement maleRadioBtn=driver.findElement(By.id("male"));
        if(maleRadioBtn.isDisplayed()&&maleRadioBtn.isEnabled()&&!maleRadioBtn.isSelected()){

            maleRadioBtn.click();
        }
        wait.until(ExpectedConditions.elementToBeSelected(maleRadioBtn));
        //identify the checkbox
        WebElement satCheckbox =driver.findElement(By.id("saturday"));
        if(satCheckbox.isEnabled()&&satCheckbox.isDisplayed()&&!satCheckbox.isSelected()){

            satCheckbox.click();
        }
        wait.until(ExpectedConditions.elementToBeSelected(satCheckbox));
        Thread.sleep(5000);
        driver.close();
    }
}