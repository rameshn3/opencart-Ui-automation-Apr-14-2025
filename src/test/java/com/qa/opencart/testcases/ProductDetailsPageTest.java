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

    @BeforeClass
    public void pageClassInstantiationSetUp() throws InterruptedException, IOException{
        homePg = new HomePage(driver);
        regPg = new RegistrationPage(driver);
        loginPg = new LoginPage(driver);
        myaccountPg = new MyAccountPage(driver);
        resultPg = new SearchResultsPage(driver);
        logoutPg = new LogoutPage(driver);
        productDetailPg = new ProductDetailsPage(driver);
        ChainTestListener.log("navigate to login page");
        homePg.navigateToLoginPage();
        loginPg.doLogin(WebDriverFactory.readPropertyValue("username"),WebDriverFactory.readPropertyValue("pwd"));
        ChainTestListener.log("verify my account page title");
        Assert.assertEquals(myaccountPg.getMyAccountPageTitle(),Constants.MYACCOUNT_PAGE_TITLE);
    }

    @Test(description="TC01_Verify product images Test",dataProvider="productImageTestData",priority=1)
    public void TC01_Verify_product_image_Test(String searchKeyword,String productName,int imgCount) throws InterruptedException{
        ChainTestListener.log("TC01_Verify_product_image_Test() - running for :"+searchKeyword);
        myaccountPg.doProductSearch(searchKeyword);
        resultPg.waitForPageLoad(1000);
        ChainTestListener.log("verify results page title");
        SoftAssert sa = new SoftAssert();
        sa.assertEquals(resultPg.getTitle(),"Search - "+searchKeyword);
        sa.assertTrue(resultPg.getProductListSize()>0);
        ChainTestListener.log("select the product");
        resultPg.selectProduct(productName);
        ChainTestListener.log(" verify product details page");
        sa.assertEquals(productDetailPg.getTitle(),productName);
        int actualImageCount =productDetailPg.getProductImageCount();
        ChainTestListener.log("comparing actual image coutn and expected image count");
        sa.assertEquals(actualImageCount,imgCount);
        ChainTestListener.log("navigate back to my account page");
        resultPg.navigateToMyAccountPage();
        myaccountPg.waitForPageLoad(2000);
        sa.assertAll();
    }
    @DataProvider
    public Object[][] productImageTestData(){
        return  new Object[][]{
                {"MacBook","MacBook Air",4},
                {"Apple","Apple Cinema 30",6},
                {"Samsung","Samsung SyncMaster 941BW",1}
        };
    }

    @Test(description="TC02_Verify product ,metadata test",priority=2)
    public void TC02_Verify_product_metadata_Test() throws InterruptedException{
        ChainTestListener.log("TC02_Verify_product_metadata_Test ");
        String productName = "MacBook";
        myaccountPg.doProductSearch(productName);
        resultPg.waitForPageLoad(1000);
        ChainTestListener.log("verify results page title");
        SoftAssert sa = new SoftAssert();
        sa.assertEquals(resultPg.getTitle(),"Search - "+productName);
        sa.assertTrue(resultPg.getProductListSize()>0);
        ChainTestListener.log("select the product");
        resultPg.selectProduct(productName);
        ChainTestListener.log(" verify product details page");
        sa.assertEquals(productDetailPg.getTitle(),productName);
        Map<String,String> actualProductInfoMap =  productDetailPg.getProductInformation();
        sa.assertEquals(actualProductInfoMap.get("Brand"),"Apple");
        sa.assertEquals(actualProductInfoMap.get("Product Code"),"Product 16");
        sa.assertEquals(actualProductInfoMap.get("Reward Points"),"600");
        sa.assertEquals(actualProductInfoMap.get("Availability"),"Out Of Stock");
        sa.assertEquals(actualProductInfoMap.get("actualPrice"),"$602.00");
        sa.assertAll();
    }
    @Test(description="TC03_Verify Add_To_Cart test",dependsOnMethods="TC02_Verify_product_metadata_Test")
    public void TC03_Verify_Add_To_Cart_Test() throws InterruptedException{
        ChainTestListener.log("TC03_Verify_Add_To_Cart_Test()");
        ChainTestListener.log("enter the quantity");
        productDetailPg.setQuantity("2");
        ChainTestListener.log("click on add t ocart button");
        productDetailPg.clickOnAddToCartBtn();
        Assert.assertTrue(productDetailPg.getAddToCartSuccessMessage().contains("Success: You have added MacBook to your shopping cart!"));
        ChainTestListener.log("click on  shoppingcart link");
        productDetailPg.navigateToShoppingCartPage();
    }


    @Test(description="TC05_Verify logout from myAccount test",dependsOnMethods={"TC03_Verify_Add_To_Cart_Test"})
    public void TC05_Verify_logout_Test() throws InterruptedException, IOException{
        ChainTestListener.log("TC05_Verify_logout_Test()");
        myaccountPg.doLogout();
        logoutPg.waitForPageLoad(2000);
        ChainTestListener.log(" verify logout page title");
        Assert.assertEquals(logoutPg.getTitle(),Constants.LOGOUT_PAGE_TITLE);
        ChainTestListener.log(" click continue in logout page");
        logoutPg.clickContinueBtn();
        homePg.waitForPageLoad(2000);
        Assert.assertEquals(homePg.getHomePageTitle(),Constants.HOME_PAGE_TITLE);
    }

}
