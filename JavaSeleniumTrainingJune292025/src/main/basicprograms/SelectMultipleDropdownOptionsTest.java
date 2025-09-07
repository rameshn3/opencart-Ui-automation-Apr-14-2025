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

public class SelectMultipleDropdownOptionsTest{
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

        WebElement colorsDropdown=driver.findElement(By.id("colors"));
        //create Object for Select class
        Select sel=new Select(colorsDropdown);

        //select an option by visible text/label
        sel.selectByVisibleText("Red");

        //fetch the selected option from dropdown
        System.out.println("selected option by visible text is:"+sel.getFirstSelectedOption().getText());
        //select an option by value attribute
        sel.selectByValue("green");

        //fetch the selected option from dropdown
        System.out.println("selected option by value attr is:"+sel.getFirstSelectedOption().getText());

        //select an option by index
        sel.selectByIndex(1);

        //fetch the selected option from dropdown
        System.out.println("selected option by index 3 is:"+sel.getFirstSelectedOption().getText());

        sel.selectByValue("white");
        sel.selectByVisibleText("Green");

        //fetch all the selected options
        List<WebElement>selectedOptionsList = sel.getAllSelectedOptions();
        System.out.println("selected optionslist:"+selectedOptionsList.size());
        for(WebElement slctdOption:selectedOptionsList){
            System.out.println("selected option is:"+slctdOption.getText());
        }

        //deselect an option by visibletext
        sel.deselectByVisibleText("Green");
        //deseelct by value attribute
        sel.deselectByValue("white");
        //deselect an option by index
        sel.deselectByIndex(1);

        //fetch all the selected options
        List<WebElement>selectedOptionsList1 = sel.getAllSelectedOptions();
        System.out.println("selected optionslist after deselect 3:"+selectedOptionsList1.size());
        for(WebElement slctdOption1:selectedOptionsList1){
            System.out.println("selected option is:"+slctdOption1.getText());
        }

        //deselect all
        sel.deselectAll();
        //fetch all the selected options
        List<WebElement>selectedOptionsList2 = sel.getAllSelectedOptions();
        System.out.println("selected optionslist after deselect 3:"+selectedOptionsList2.size());

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