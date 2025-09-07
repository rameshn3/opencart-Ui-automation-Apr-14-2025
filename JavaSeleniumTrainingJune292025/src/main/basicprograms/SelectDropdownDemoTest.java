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

public class SelectDropdownDemoTest{
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

        //select an option by visible text/label
        sel.selectByVisibleText("United States");

        //fetch the selected option from dropdown
        System.out.println("selected option by visible text is:"+sel.getFirstSelectedOption().getText());
        Thread.sleep(2000);

        //select an option by value attribute
        sel.selectByValue("uk");

        //fetch the selected option from dropdown
        System.out.println("selected option by value attr is:"+sel.getFirstSelectedOption().getText());
        Thread.sleep(2000);

        //select an option by index
        sel.selectByIndex(3);

        //fetch the selected option from dropdown
        System.out.println("selected option by index 3 is:"+sel.getFirstSelectedOption().getText());
        Thread.sleep(2000);

        //fetch all the dropdown options
        List<WebElement> optionsList = sel.getOptions();
        //select last option from dropdown
        sel.selectByIndex(optionsList.size()-1);
        System.out.println("selected option by index last option is:"+sel.getFirstSelectedOption().getText());
        Thread.sleep(2000);

        for(WebElement option:optionsList){
            System.out.println(option.getText());
        }
        Thread.sleep(2000);
        driver.close();
    }
}