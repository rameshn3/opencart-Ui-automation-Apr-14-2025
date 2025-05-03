package com.qa.opencart.testcases;

import com.aventstack.chaintest.plugins.ChainTestListener;
import com.qa.opencart.factory.TestBase;
import com.qa.opencart.pages.HomePage;
import com.qa.opencart.pages.LoginPage;
import com.qa.opencart.pages.RegistrationPage;
import com.qa.opencart.utilities.Constants;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class HomePageTest extends TestBase{

    @BeforeClass
    public void pageClassInstantiationSetup(){
        homePg = new HomePage(driver);
        regPg = new RegistrationPage(driver);
        loginPg = new LoginPage(driver);
    }

    @Test(description="TC01_Verify the opencart page title")
    public void TC01_Verify_Opencart_Page_Title_Test(){
        ChainTestListener.log("TC01_Verify the opencart page title");
        Assert.assertEquals(homePg.getHomePageTitle(),Constants.HOME_PAGE_TITLE);

    }

    @Test(description="TC02_Verify opencart logo test")
    public void TC02_verify_opencart_logo_Test(){
        ChainTestListener.log("TC02_Verify opencart logo test");
        Assert.assertTrue(homePg.isOpenCartLogoExists());
    }

    @Test(description="TC03 Verify Opencart Featured Section cards count test")
    public void TC03_verify_opencart_featured_Section_cards_count_Test(){
        ChainTestListener.log("TC03 Verify Opencart Featured Section cards count test");
        Assert.assertTrue(homePg.getFeaturedProductListCount()==4);
    }

    @Test(description="TC04 verify navigate to registration page from homepage test")
    public void TC04_verify_navigate_to_registration_page_test() throws InterruptedException{
        ChainTestListener.log("TC04 verify navigate to registration page from homepage test");
        homePg.navigateToRegisterPage();
        regPg.waitForPageLoad(1000);
        ChainTestListener.log("Verify Registration page title");
        Assert.assertEquals(regPg.getRegisterPageTitle(),Constants.REGISTRATION_PAGE_TITLE);
        ChainTestListener.log("navigate back to home page");
        regPg.clickBreadCrumbHomeIcon();
    }


    @Test(description="TC05 verify navigate to login page from homepage test")
    public void TC05_verify_navigate_to_login_page_test() throws InterruptedException{
        ChainTestListener.log("TC05 verify navigate to login page from homepage test");
        homePg.navigateToLoginPage();
        loginPg.waitForPageLoad(1000);
        ChainTestListener.log("Verify Registration page title");
        Assert.assertEquals(loginPg.getLoginPageTitle(),Constants.LOGIN_PAGE_TITLE);
        ChainTestListener.log("navigate back to home page");
        loginPg.navigateToHomePage();
    }
}
