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

public class MyAccountPageTest extends TestBase{

    @BeforeClass
    public void pageClassInstantiationSetup() throws InterruptedException, IOException{
        homePg = new HomePage(driver);
        regPg = new RegistrationPage(driver);
        loginPg = new LoginPage(driver);
        myaccountPg = new MyAccountPage(driver);
        logoutPg = new LogoutPage(driver);
        resultPg = new SearchResultsPage(driver)
        ChainTestListener.log("navigate to login page");
        homePg.navigateToLoginPage();
        loginPg.doLogin(WebDriverFactory.readPropertyValue("username"),WebDriverFactory.readPropertyValue("pwd"));
        myaccountPg.waitForPageLoad(1000);
        ChainTestListener.log("verify my account page title");
        Assert.assertEquals(myaccountPg.getTitle(),Constants.MYACCOUNT_PAGE_TITLE);
    }

    @Test(description="TC01_Verify my account page title")
    public void TC01_Verify_myAccount_Page_Title_Test(){
        ChainTestListener.log("TC01_Verify my account page title test");
        Assert.assertEquals(myaccountPg.getTitle(),Constants.MYACCOUNT_PAGE_TITLE);

    }

    @Test(description="TC02_Verify myaccount page url test")
    public void TC02_verify_myAccount_page_url_Test(){
        ChainTestListener.log("TC02_Verify myaccount page url test");
        Assert.assertTrue(myaccountPg.getMyAccountPageUrl().contains(Constants.MY_ACCOUNT_PAGE_FRACTION_URL));
    }

    @Test(description="TC03 Verify my account page elements test")
    public void TC03_verify_my_account_page_elements_Test(){
        ChainTestListener.log("TC03 Verify my account page elements test");
        Assert.assertTrue(myaccountPg.isAccountBreadCrumbExits());
        Assert.assertTrue(myaccountPg.isAccountPageDisplayed());

    }

    @Test(description="TC04 verify navigate to registration page from loginpage test")
    public void TC04_verify_navigate_to_registration_page_from_loginpage_test() throws InterruptedException{
        ChainTestListener.log("TC04 verify navigate to registration page from loginpage test");
        loginPg.clickNewCustomerContinueBtn();
        regPg.waitForPageLoad(1000);
        ChainTestListener.log("Verify Registration page title");
        Assert.assertEquals(regPg.getRegisterPageTitle(),Constants.REGISTRATION_PAGE_TITLE);
        ChainTestListener.log("navigate back to login page");
        regPg.clickOnLoginPageLink();
    }


    @Test(description="TC05 verify navigate to forgotpassword page from loginpage test",dependsOnMethods={"TC04_verify_navigate_to_registration_page_from_loginpage_test"})
    public void TC05_verify_navigate_to_forgotpassword_page_test() throws InterruptedException{
        ChainTestListener.log("TC05 verify navigate to forgotpassword page from loginpage test");
       loginPg.clickOnForgotPasswordLink();
        loginPg.waitForPageLoad(1000);
        ChainTestListener.log("navigate back to home page");
       driver.navigate().back();
    }
    @Test(description="TC06 verify empty credentials error message test")
    public void TC06_verify_empty_credentials_error_message_test() throws InterruptedException{
        ChainTestListener.log("TC06 verify empty credentials test");
        loginPg.doLogin(" "," ");
        loginPg.waitForPageLoad(1000);
        ChainTestListener.log("verify empty credentials error message in login page");
       Assert.assertTrue(loginPg.getErrorMessage().contains(Constants.EMPTY_CREDS_ERROR_MSG));
    }

    @Test(description="TC07 verify valid credentials test")
    public void TC07_verify_valid_credentials_test() throws InterruptedException, IOException{
        ChainTestListener.log("TC07 verify valid credentials test");
        loginPg.doLogin(WebDriverFactory.readPropertyValue("username"),WebDriverFactory.readPropertyValue("pwd"));
        myaccountPg.waitForPageLoad(1000);
        ChainTestListener.log("verify my account page title");
        Assert.assertEquals(myaccountPg.getTitle(),Constants.MYACCOUNT_PAGE_TITLE);
    }

    @Test(description="TC08 verify logout from my account page test",dependsOnMethods={"TC07_verify_valid_credentials_test"})
    public void TC08_verify_logout_from_myaccountPage_test() throws InterruptedException, IOException{
        ChainTestListener.log("TC08 verify logout from my account page test");
       myaccountPg.doLogout();
       ChainTestListener.log(("verify logout page exists or not"));
       Assert.assertTrue(logoutPg.isAccountLoggedOffMsgExists());
        Assert.assertTrue(logoutPg.isAccountLogoutHeaderExists());
        Assert.assertTrue(logoutPg.isLogoutBreadCrumbExists());
        ChainTestListener.log(("click continue in logout page"));
        logoutPg.clickContinueBtn();
        homePg.waitForPageLoad(1000);
        Assert.assertEquals(homePg.getHomePageTitle(),Constants.HOME_PAGE_TITLE);
    }

}
