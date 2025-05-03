package com.qa.opencart.pages;

import com.aventstack.chaintest.plugins.ChainTestListener;
import com.qa.opencart.utilities.WebDriverUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

public class ProductDetailsPage extends WebDriverUtils{
    private Logger log = LogManager.getLogger(ProductDetailsPage.class);

    //constructor
    public ProductDetailsPage(WebDriver driver){
        super(driver);
        PageFactory.initElements(driver,this);
    }

    @FindBy(css="div[id='content'] h1")
    private WebElement productHeader;

    @FindBy(css="a.thumbnail img")
    private List<WebElement> ProductImageList;

    @FindBy(xpath="(//div[@id='content']/div/div[2]/ul[@class='list-unstyled'])[position()=1]/li")
    private List<WebElement> productMetaDataList;

    @FindBy(xpath="//i[@class='fa fa-home']")
    private WebElement homeIcon;

    @FindBy(xpath="(//div[@id='content']/div/div[2]/ul[@class='list-unstyled'])[position()=2]/li")
    private List<WebElement> productPriceList;

    @FindBy(css="#input-quantity")
    private WebElement quantityEditbox;

    @FindBy(css="#button-cart")
    private WebElement addToCartBtn;

    @FindBy(css=".alert.alert-success.alert-dismissible")
    private WebElement productAddedToCartSucMsg;

    @FindBy(xpath="//a[normalize-space()='shopping cart']")
    private WebElement shoppingCartLink;

    public String getProductDetailsPageTitle(){
        return getTitle();
    }

    public String getProductName() throws InterruptedException{
        return getText(productHeader);
    }

    public String getAddToCartSuccessMessage() throws InterruptedException{
        return getText(productAddedToCartSucMsg);
    }


    public int getProductImageCount() throws InterruptedException{
        return ProductImageList.size();
    }

    public void navigateToShoppingCartPage() throws InterruptedException{
        try{
            ChainTestListener.log("click on shoppingCartLink in success message");
            click(shoppingCartLink);
        }catch(NoSuchElementException ex){
            ChainTestListener.log("not able to navigate to shopping cart page");
            ex.printStackTrace();
        }

    }

    public void clickOnAddToCartBtn() throws InterruptedException{
        try{
            ChainTestListener.log("click on add to cart button ");
            click(addToCartBtn);

        }catch(NoSuchElementException ex){

        }

    }

    public void setQuantity(String quantity) throws InterruptedException{
        ChainTestListener.log("entering the product quantity in quantity editbox");
        type(quantityEditbox,quantity);
    }

    public void navigateToHomePage() throws InterruptedException{
        try{
            ChainTestListener.log("navigate to home page");
            click(homeIcon);

        }catch(NoSuchElementException ex){

        }

    }
    private Map<String,String> productMap;

    public void getProductMetaData(){
        ChainTestListener.log("Fetching product meta data");
        ChainTestListener.log("product meta data count is:"+productMetaDataList.size());
        //Iterate the product metadata list
        for(WebElement pmd:productMetaDataList){
            String metaText = pmd.getText(); //Brand : Apple
            ChainTestListener.log("split metaText based on : ");
            String[] metaDataArray = metaText.split(":");
            ChainTestListener.log("fetch meta key nd meta value ");
            String metaKey = metaDataArray[0].trim();
            String metaValue = metaDataArray[1].trim();
            ChainTestListener.log("insert metakey and meta value: into Map");
            productMap.put(metaKey,metaValue);
        }
    }

    public void getProductPriceData(){
        ChainTestListener.log("product meta price count is:"+productPriceList.size());
        String price = productPriceList.get(0).getText().trim();
        String extraTaxPrice = productPriceList.get(1).getText().trim();
        ChainTestListener.log("store the price value in the map");
        productMap.put("actualPrice",price);
        productMap.put("actualTaxPrice",extraTaxPrice);
    }

    public Map<String,String> getProductInformation(){
        productMap = new HashMap<>();
        getProductMetaData();
        getProductPriceData();
        return productMap;
    }
}
