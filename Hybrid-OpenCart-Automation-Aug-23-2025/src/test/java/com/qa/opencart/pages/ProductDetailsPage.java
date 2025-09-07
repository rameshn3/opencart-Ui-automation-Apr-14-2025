package com.qa.opencart.pages;

import com.aventstack.chaintest.plugins.ChainTestListener;
import com.qa.opencart.utilities.TimeUtils;
import com.qa.opencart.utilities.WebDriverUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Map;
public class ProductDetailsPage extends WebDriverUtils{
    public ProductDetailsPage(WebDriver driver){
        super(driver);
        PageFactory.initElements(driver,this);
    }

    @FindBy(css="div[id='content'] h1")
    private WebElement productNameHeader;

    @FindBy(xpath="//ul[@class='thumbnails']//li")
    private List<WebElement> ProductImageList;

      @FindBy(xpath="(//div[@id='content']/div/div[2]/ul[@class='list-unstyled'])[position()=1]/li")
    private List<WebElement> productMetadataList;

    @FindBy(xpath="(//div[@id='content']/div/div[2]/ul[@class='list-unstyled'])[position()=2]/li")
    private List<WebElement> productPriceList;

    @FindBy(xpath="//i[@class='fa fa-home']")
    private WebElement homeIcon;

    @FindBy(xpath="//ul[@class='breadcrumb']//li[1]")
    private WebElement productNameBreadCrumb;

    @FindBy(id="input-quantity")
    private WebElement quantityEditbox;

    @FindBy(id="button-cart")
    private WebElement addToCartBtn;

    @FindBy(css=".alert.alert-success.alert-dismissible")
    private WebElement productAddedToCartSuccessMessage;
    @FindBy(xpath="//a[normalize-space()='shopping cart']")
    private WebElement shoppingCartLink;
       public String getProductDetailsPageTitle(){
        return getTitle();
    }

    public String getProductName(){
           return productNameHeader.getText();
    }
      public void navigateToShoppingCartPage(){
       try{
           ChainTestListener.log("Click on shoppingcart link");
           click(shoppingCartLink);

       }catch(NoSuchElementException|InterruptedException ex){
           ChainTestListener.log("Unable to click on shoppingcart link");
           ex.printStackTrace();
       }
    }

    public String getAddToCartSuccessMessage(){
           return productAddedToCartSuccessMessage.getText();
    }
    public int getProductImageCount(){
           return ProductImageList.size();
    }



    public void clickAddToCartButton() throws InterruptedException{
           try{

               ChainTestListener.log("click add to cart button");
               click(addToCartBtn);

           }catch(NoSuchElementException ex){
               ex.printStackTrace();
           }

    }

    public void setQuantity(String quantity) throws InterruptedException{
           ChainTestListener.log("Entering the product quantity in quantity editbox");
           type(quantityEditbox,quantity);
    }
    public void navigateToHomePage() throws InterruptedException{
        try{
            ChainTestListener.log("click on Home icon on breadcrumb");
            click(homeIcon);
        }catch(NoSuchElementException ex){
            ChainTestListener.log("Unable to click home icon");
        }

    }
private Map<String,String>productMap;


    private void getProductMetadata(){
        ChainTestListener.log("fetch the product metadata");
        ChainTestListener.log("fproduct metadata count is:"+productMetadataList.size());
     for(WebElement pmd:productMetadataList){
         String metaText = pmd.getText();
         ChainTestListener.log("split metatext based on :");
         String[] metaDataArray = metaText.split(":");
         ChainTestListener.log("fethc meta key and value");
         String metaKey = metaDataArray[0].trim();
         String metaValue = metaDataArray[1].trim();
         //insert key and value into map
         productMap.put(metaKey,metaValue);
     }


    }

    private void getProductPriceData(){
        ChainTestListener.log("product meta price count is:"+productPriceList.size());
        String price = productPriceList.get(0).getText().trim();
        String extraTaxPrice = productPriceList.get(1).getText().trim();
        ChainTestListener.log("store the price value in the map");
        productMap.put("actualPrice",price);
        productMap.put("actualTaxPrice",extraTaxPrice);
    }

    public Map<String,String> getProductInformation(){
        productMap = new HashMap<>();
        getProductMetadata();
        getProductPriceData();
        return productMap;
    }

}
