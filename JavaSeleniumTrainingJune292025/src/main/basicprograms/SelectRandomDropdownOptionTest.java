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
import java.util.Random;

public class SelectRandomDropdownOptionTest{
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

        WebElement countryDropdown=driver.findElement(By.id("country"));
        //create Object for Select class
        Select sel=new Select(countryDropdown);

        //fetch all the dropdown options
        List<WebElement> optionsList = sel.getOptions();
        //select last option from dropdown
       int size = optionsList.size();
       //create object for Random class
        Random randObj = new Random();


        for(int i=1;i<=5;i++){
           int randNumber = randObj.nextInt(size);
                   sel.selectByIndex(randNumber);
            Thread.sleep(1000);
            System.out.println(randNumber+ " th random selected option is:"+sel.getFirstSelectedOption().getText());
        }
        Thread.sleep(2000);
        driver.close();
    }
}