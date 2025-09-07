package com.qa.opencart.pages;

import com.qa.opencart.utilities.WebDriverUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class LoginPage extends WebDriverUtils{
    public LoginPage(WebDriver driver){
        super(driver);
        PageFactory.initElements(driver,this);
    }

    @FindBy(xpath="//h2[normalize-space()='New Customer']")
    private WebElement newCustomerHeader;

    @FindBy(css="a[class='btn btn-primary']")
    private WebElement newCustomerContinueBtn;

    @FindBy(xpath="//h2[normalize-space()='Returning Customer']")
    private WebElement returningCustomerHeader;

    @FindBy(id="input-email")
    private WebElement emailAddressEditbox;

    @FindBy(name="password")
    private WebElement passwordEditbox;

    @FindBy(css="input[value='Login']")
    private WebElement loginBtn;

    @FindBy(linkText="Forgotten Password")
    private WebElement forgottenPasswordLink;

    @FindBy(xpath="//i[@class='fa fa-home']")
    private WebElement homeIcon;

    @FindBy(xpath="//ul[@class='breadcrumb']//a[normalize-space()='Login']")
    private WebElement loginBreadCrumb;

    @FindBy(xpath="//div[@class='alert alert-danger alert-dismissible']")
    private WebElement emptyCredentialsErrorMsg;

    public String getLoginPageTitle(){
        return getTitle();
    }

    public boolean isloginBreadCrumbExists(){
        return loginBreadCrumb.isDisplayed();
    }

    public boolean isNewCustomerHeaderExists(){
        return newCustomerHeader.isDisplayed();
    }

    public boolean isReturningCustomerHeaderExists(){
        return returningCustomerHeader.isDisplayed();
    }
    public void clickNewCustomerContinueButton() throws InterruptedException{
        click(newCustomerContinueBtn);
    }

    public void navigateToForgotPasswordPage() throws InterruptedException{
        click(forgottenPasswordLink);
    }

    public void doLogin(String username,String password) throws InterruptedException{
       type(emailAddressEditbox,username);
       type(passwordEditbox,password);
       click(loginBtn);
    }

    public void navigateToHomePage() throws InterruptedException{
        click(homeIcon);
    }

    public String getEmptyCredentialsErrorMsg(){
        return emptyCredentialsErrorMsg.getText();
    }
}
