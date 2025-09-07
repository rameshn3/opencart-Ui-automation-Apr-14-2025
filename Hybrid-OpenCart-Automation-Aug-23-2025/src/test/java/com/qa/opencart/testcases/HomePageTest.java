package com.qa.opencart.testcases;

import com.aventstack.chaintest.plugins.ChainTestListener;
import com.qa.opencart.factory.TestBase;
import com.qa.opencart.pages.HomePage;
import com.qa.opencart.pages.LoginPage;
import com.qa.opencart.pages.RegisterPage;
import com.qa.opencart.pages.PageFactoryManager;
import com.qa.opencart.utilities.Constants;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class HomePageTest extends TestBase{
public HomePage homePage;
public RegisterPage registerPage;

public LoginPage loginPage;
    @BeforeClass
    public void pageClassesSetup(){
        PageFactoryManager pageFactory = new PageFactoryManager(driver);
        homePage= pageFactory.getHomePage();
        registerPage = pageFactory.getRegisterPage();
        loginPage = pageFactory.getLoginPage();
    }

    @Test(description ="TC01_Verify the opencart page title")
 public void TC01_Verify_the_opencart_page_title_Test(){
        ChainTestListener.log("TC01_Verify the opencart page title");
        Assert.assertEquals(homePage.getHomePageTitle(),Constants.HOME_PAGE_TITLE);
    }

    @Test(description ="TC02_Verify the opencart logo test")
    public void TC02_Verify_the_opencart_logo_Test(){
        ChainTestListener.log("TC02_Verify the opencart logo test");
        Assert.assertTrue(homePage.isOpenCartLogoExists(),"Opencart logo doesnot exists ");
    }

    @Test(description ="TC03_Verify the opencart featured section cards count test")
    public void TC03_Verify_the_opencart_featured_section_cards_count_Test(){
        ChainTestListener.log("TC03_Verify_the_opencart_featured_section_cards_count_Test");
        Assert.assertTrue(homePage.getFeaturedSectionCardCount()==4,"featured section cards count is mismatched ");
    }

    @Test(description ="TC04_Verify navigate to register page from home page test")
    public void TC04_Verify_navigate_to_register_page_from_home_page_Test() throws InterruptedException{
        ChainTestListener.log("TC04_Verify_navigate_to_register_page_from_home_page_Test");
        homePage.navigateToRegisterPage();
        registerPage.waitForPageLoad(1000);
        ChainTestListener.log("Verifyregister_page_title");
        Assert.assertEquals(registerPage.getRegisterPageTitle(),Constants.REGISTRATION_PAGE_TITLE);
        ChainTestListener.log("navigate back to home pge");
        registerPage.navigateToHomePage();
    }

    @Test(description ="TC05_Verify navigate to login page from home page test")
    public void TC05_Verify_navigate_to_login_page_from_home_page_Test() throws InterruptedException{
        ChainTestListener.log("TC05_Verify_navigate_to_login_page_from_home_page_Test");
        homePage.navigateToLoginrPage();
        loginPage.waitForPageLoad(1000);
        ChainTestListener.log("Verify login _page_title");
        Assert.assertEquals(loginPage.getLoginPageTitle(),Constants.LOGIN_PAGE_TITLE);
        ChainTestListener.log("navigate back to home pge");
        loginPage.navigateToHomePage();
    }

}
