package com.qa.opencart.pages;

import com.aventstack.chaintest.plugins.ChainTestListener;
import com.qa.opencart.utilities.Constants;
import com.qa.opencart.utilities.WebDriverUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class HomePage extends WebDriverUtils{
WebDriverWait wait;
    public HomePage(WebDriver driver){
        super(driver);
        wait = new WebDriverWait(driver,Duration.ofMillis(Constants.EXPLICIT_WAIT));
        PageFactory.initElements(driver,this);
    }

    @FindBy(css="#logo")
    private WebElement opencartLogo;

    @FindBy(xpath="//*[@id='content']/div[2]/div")
    private List<WebElement> featuredProductList;

    @FindBy(xpath="//span[normalize-space()='My Account']")
    private WebElement myAccountMenu;

    @FindBy(linkText="Register")
    private WebElement registerLink;

    @FindBy(linkText="Login")
    private WebElement loginLink;

    public String getHomePageTitle(){
        return getTitle();
    }

    public boolean isOpenCartLogoExists(){
        return isDisplayed(opencartLogo);
    }

    public int getFeaturedProductListCount(){
        return featuredProductList.size();
    }

    public void clickMyAccountMenu() throws InterruptedException{
        ChainTestListener.log("clicking on MyAccount Menu...");
        click(myAccountMenu);
    }

    public void navigateToRegisterPage() throws InterruptedException{
        ChainTestListener.log("navigate to registration page...");
        clickMyAccountMenu();
        wait.until(ExpectedConditions.visibilityOf(registerLink));
        ChainTestListener.log("click on register link under myAccount menu...");
        click(registerLink);
    }

    public void navigateToLoginPage() throws InterruptedException{
        ChainTestListener.log("navigate to login page...");
        clickMyAccountMenu();
        wait.until(ExpectedConditions.visibilityOf(loginLink));
        ChainTestListener.log("click on login link under myAccount menu...");
        click(loginLink);
    }
}
