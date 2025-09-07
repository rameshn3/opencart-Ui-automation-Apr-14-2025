package com.qa.opencart.pages;

import org.openqa.selenium.WebDriver;

public class PageFactoryManager{
    private WebDriver driver;

    public PageFactoryManager(WebDriver driver) {
        this.driver = driver;
    }

    public HomePage getHomePage() { return new HomePage(driver); }
    public LoginPage getLoginPage() { return new LoginPage(driver); }
    public RegisterPage getRegisterPage() { return new RegisterPage(driver); }
    public MyAccountPage getMyAccountPage() { return new MyAccountPage(driver); }

    public ProductDetailsPage getProductDetailsPage(){
        return  new ProductDetailsPage(driver);
    }

    public ResultsPage getResultsPage(){
        return new ResultsPage(driver);
    }

    public LogoutPage getLogoutPage(){
        return new LogoutPage(driver);
    }
}
