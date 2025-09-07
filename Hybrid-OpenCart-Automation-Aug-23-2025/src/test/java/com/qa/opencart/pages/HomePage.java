package com.qa.opencart.pages;

import com.qa.opencart.utilities.WebDriverUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class HomePage extends WebDriverUtils{
    public HomePage(WebDriver driver){
        super(driver);
        PageFactory.initElements(driver,this);
    }

    @FindBy(id="logo")
    private WebElement openCartLogo;

    @FindBy(css="div[class*='product-layout']")
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
        return openCartLogo.isDisplayed();
    }

    public int getFeaturedSectionCardCount(){
        return featuredProductList.size();
    }

    public void clickMyAccountMenu() throws InterruptedException{
        click(myAccountMenu);
    }

    public void navigateToRegisterPage() throws InterruptedException{
        clickMyAccountMenu();
        click(registerLink);
    }

    public void navigateToLoginrPage() throws InterruptedException{
        clickMyAccountMenu();
        click(loginLink);
    }
}
