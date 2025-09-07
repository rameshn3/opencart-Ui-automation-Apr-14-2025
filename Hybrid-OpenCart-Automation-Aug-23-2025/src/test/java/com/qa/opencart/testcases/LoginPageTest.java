package com.qa.opencart.testcases;

import com.aventstack.chaintest.plugins.ChainTestListener;
import com.qa.opencart.factory.TestBase;
import com.qa.opencart.factory.WebDriverFactory;
import com.qa.opencart.pages.*;
import com.qa.opencart.utilities.Constants;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;

public class LoginPageTest extends TestBase{
public HomePage homePage;
public RegisterPage registerPage;

public LoginPage loginPage;

public MyAccountPage myAccountPage;
public LogoutPage logoutPage;
    @BeforeClass
    public void pageClassesSetup() throws InterruptedException{
        PageFactoryManager pageFactory = new PageFactoryManager(driver);
        homePage= pageFactory.getHomePage();
        registerPage = pageFactory.getRegisterPage();
        loginPage = pageFactory.getLoginPage();
        myAccountPage = pageFactory.getMyAccountPage();
        logoutPage = pageFactory.getLogoutPage();
    ChainTestListener.log("navigate to login page");
    homePage.navigateToLoginrPage();
    }

    @Test(description ="TC01_Verify the login page title")
 public void TC01_Verify_the_login_page_title_Test(){
        ChainTestListener.log("TC01_Verify the login page title");
        Assert.assertEquals(loginPage.getLoginPageTitle(),Constants.LOGIN_PAGE_TITLE);
    }

    @Test(description ="TC02_Verify the login breadcrumb test")
    public void TC02_Verify_the_login_breadcrumb_Test(){
        ChainTestListener.log("TC02_Verify_the_breadcrumb_Test");
        Assert.assertTrue(loginPage.isloginBreadCrumbExists(),"login breadcrumb doesnot exists ");
    }

    @Test(description ="TC03_Verify the login page elemnts existance test")
    public void TC03_Verify_the_login_page_elemnts_existance_Test(){
        ChainTestListener.log("TC03_Verify_the_login_page_elemnts_existance_Test");
        Assert.assertTrue(loginPage.isNewCustomerHeaderExists(),"New customer header doesnot exists ");
        Assert.assertTrue(loginPage.isReturningCustomerHeaderExists(),"Returning customer header doesnot exists ");
       }

    @Test(description ="TC04_Verify navigate to register page from login page test")
    public void TC04_Verify_navigate_to_register_page_from_login_page_Test() throws InterruptedException{
        ChainTestListener.log("TC04_Verify_navigate_to_register_page_from_login_page_Test");
       loginPage.clickNewCustomerContinueButton();
        registerPage.waitForPageLoad(1000);
        ChainTestListener.log("Verifyregister_page_title");
        Assert.assertEquals(registerPage.getRegisterPageTitle(),Constants.REGISTRATION_PAGE_TITLE);
        ChainTestListener.log("navigate back to login pge");
        registerPage.navigateToLoginPage();
    }

    @Test(description ="TC05_Verify navigate to forgot password page from login page test",dependsOnMethods={"TC04_Verify_navigate_to_register_page_from_login_page_Test"})
    public void TC05_Verify_navigate_to_forgot_password_page_from_login_page_Test() throws InterruptedException{
        ChainTestListener.log("TC05_Verify_navigate_to_forgot_password_page_from_login_page_Test");
        Assert.assertTrue(loginPage.isReturningCustomerHeaderExists(),"Returning customer header doesnot exists ");
        Assert.assertTrue(loginPage.isloginBreadCrumbExists(),"login breadcrumb doesnot exists ");
        loginPage.navigateToForgotPasswordPage();
        loginPage.waitForPageLoad(1000);

        ChainTestListener.log("navigate back to login pge");
       driver.navigate().back();
    }

    @Test(description ="TC06_Verify empty credentials error message in login page test")
    public void TC06_Verify_empty_credentials_login_page_Test() throws InterruptedException{
        ChainTestListener.log("TC06_Verify_empty_credentials_login_page_Test");
        loginPage.doLogin(" ", " ");
        loginPage.waitForPageLoad(1000);

        ChainTestListener.log("verify the empty credentials error message");
       Assert.assertTrue(loginPage.getEmptyCredentialsErrorMsg().contains(Constants.EMPTY_CREDS_ERROR_MSG));
    }

    @Test(description ="TC07_Verify valid credentials test",dependsOnMethods={"TC06_Verify_empty_credentials_login_page_Test"})
    public void TC07_Verify_valid_credentials_login_page_Test() throws InterruptedException, IOException{
        ChainTestListener.log("TC07_Verify_valid_credentials_login_page_Test");
        loginPage.doLogin(WebDriverFactory.readPropertyValue("username"),WebDriverFactory.readPropertyValue("pwd"));
        myAccountPage.waitForPageLoad(1000);

        ChainTestListener.log("verify the my account page title");
        Assert.assertEquals(myAccountPage.getMyAccountPageTitle(),Constants.MYACCOUNT_PAGE_TITLE);
    }

    @Test(description="TC08_Verify_logout_from_My_Account_Page",dependsOnMethods={"TC07_Verify_valid_credentials_login_page_Test"})
    public void TC08_Verify_logout_from_My_Account_Page_Test() throws InterruptedException{
        ChainTestListener.log("TC08_Verify_logout_from_My_Account_Page_Test..has started");
        myAccountPage.clickLogoutLink();
        logoutPage.waitForPageLoad(2000);
        ChainTestListener.log("Verify logout page title");
        Assert.assertEquals(logoutPage.getLogoutPageTitle(),Constants.LOGOUT_PAGE_TITLE);
        ChainTestListener.log("click continue button in logout page..");
        logoutPage.clickContinueBtn();
        homePage.waitForPageLoad(1000);
        Assert.assertEquals(homePage.getHomePageTitle(),Constants.HOME_PAGE_TITLE);

    }

}
