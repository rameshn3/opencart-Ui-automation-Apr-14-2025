package com.qa.opencart.testcases;

import com.aventstack.chaintest.plugins.ChainTestListener;
import com.qa.opencart.factory.TestBase;
import com.qa.opencart.factory.WebDriverFactory;
import com.qa.opencart.pages.*;
import com.qa.opencart.utilities.Constants;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class ProductDetailsPageTest extends TestBase{
public HomePage homePage;
public RegisterPage registerPage;

public LoginPage loginPage;

public MyAccountPage myAccountPage;
public LogoutPage logoutPage;

public ResultsPage resultsPage;
public ProductDetailsPage productDetailsPage;
    @BeforeClass
    public void pageClassesSetup() throws InterruptedException, IOException{
        PageFactoryManager pageFactory = new PageFactoryManager(driver);
        homePage= pageFactory.getHomePage();
        registerPage = pageFactory.getRegisterPage();
        loginPage = pageFactory.getLoginPage();
        myAccountPage = pageFactory.getMyAccountPage();
        logoutPage = pageFactory.getLogoutPage();
        resultsPage = pageFactory.getResultsPage();
        productDetailsPage = pageFactory.getProductDetailsPage();
    ChainTestListener.log("navigate to login page");
    homePage.navigateToLoginrPage();
        loginPage.doLogin(WebDriverFactory.readPropertyValue("username"),WebDriverFactory.readPropertyValue("pwd"));
        myAccountPage.waitForPageLoad(1000);

        ChainTestListener.log("verify the my account page title");
        Assert.assertEquals(myAccountPage.getMyAccountPageTitle(),Constants.MYACCOUNT_PAGE_TITLE);
    }

    @Test(description ="TC01_Verify_Product_Image_Test",dataProvider="productImageTestData")
 public void TC01_Verify_Product_Image_Test(String searchkeyword,String productName,int imageCount) throws InterruptedException{
        ChainTestListener.log("TC01_Verify_Product_Image_Test");
        resultsPage = myAccountPage.doProductSearch(searchkeyword);
        resultsPage.waitForPageLoad(2000);
        ChainTestListener.log("Verify the results page title");
        SoftAssert sa = new SoftAssert();
       sa.assertEquals(resultsPage.getResultsPageTitle(),"Search - "+searchkeyword);
        sa.assertTrue(resultsPage.getProductListSize()>0);
        ChainTestListener.log("select the product in results page");
        resultsPage.selectProduct(productName);
        ChainTestListener.log("get the total image count in product details page");
        int actualImageCount =productDetailsPage.getProductImageCount();
        sa.assertEquals(actualImageCount,imageCount);
        ChainTestListener.log("navigate back to my account page");
        driver.navigate().back();
        resultsPage.clickMyAccountMenu();
        myAccountPage.waitForPageLoad(2000);
        sa.assertAll();

    }

    @DataProvider
    public Object[][] productImageTestData(){
        return new Object[][]{
                {"MacBook","MacBook Air",4},
                {"Apple","Apple Cinema 30\"",6},
                {"Samsung","Samsung SyncMaster 941BW",1}
        };
    }


    @Test(description ="TC02_Verify_Product_metadata_Test",dependsOnMethods={"TC01_Verify_Product_Image_Test"})
    public void TC02_Verify_Product_metadata_Test() throws InterruptedException, IOException{
        ChainTestListener.log("TC02_Verify_Product_metadata_Test");
        String productName = "MacBook";
        resultsPage = myAccountPage.doProductSearch(productName);

        resultsPage.waitForPageLoad(1000);
        SoftAssert sa = new SoftAssert();
        ChainTestListener.log("verify results page title");
        sa.assertEquals(resultsPage.getResultsPageTitle(),"Search - "+productName);
        sa.assertTrue(resultsPage.getProductListSize()>0);
        ChainTestListener.log("select the product in results page");
        resultsPage.selectProduct(productName);
        Map<String,String> actualProductInfoMap = productDetailsPage.getProductInformation();
        sa.assertEquals(actualProductInfoMap.get("Brand"),"Apple");
        sa.assertEquals(actualProductInfoMap.get("Product Code"),"Product 16");
        sa.assertEquals(actualProductInfoMap.get("Reward Points"),"600");
        sa.assertEquals(actualProductInfoMap.get("Availability"),"Out Of Stock");
        sa.assertEquals(actualProductInfoMap.get("actualPrice"),"$602.00");
        ChainTestListener.log("navigate back to my account page");
        resultsPage.clickMyAccountMenu();
        myAccountPage.waitForPageLoad(2000);
        sa.assertAll();
    }



    @Test(description="TC08_Verify_logout_from_My_Account_Page",dependsOnMethods={"TC02_Verify_Product_metadata_Test"})
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
