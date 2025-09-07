package com.qa.opencart.utilities;

import java.util.Arrays;
import java.util.List;

public class Constants{
    public static final String USER_DIRECTORY=System.getProperty("user.dir");
    public static final String CONFIG_DIRECTORY=USER_DIRECTORY+"\\src\\test\\resources\\config.properties";
    public static final long EXPLICIT_WAIT=60;
    public static final String LOGIN_PAGE_FRACTION_URL="account/login";
    public static final String LOGOUT_PAGE_FRACTION_URL="account/logout";
    public static final String MY_ACCOUNT_PAGE_FRACTION_URL="account/account";
    public static final String MYACCOUNT_PAGE_TITLE="My Account";
    public static final String HOME_PAGE_TITLE="Your Store";
    public static final String REGISTRATION_PAGE_TITLE="Register Account";
    public static final String LOGIN_PAGE_TITLE="Account Login";
    public static final String LOGOUT_PAGE_TITLE="Account Logout";

    public static final String EMPTY_CREDS_ERROR_MSG="No match for E-Mail Address and/or Password.";

    public static final List<String> EXPECTED_MYACCOUNT_MENU_OPTIONS_LIST=Arrays.asList("My Account","Order History","Transactions","Downloads","Logout");
    public static final List<String> EXPECTED_MYACCOUNT_HEADER_OPTIONS_LIST=Arrays.asList("My Account","My Orders","My Affiliate Account","Newsletter");

    public static final String YOUR_ACCNT_CREATED_HEADER="Your Account Has Been Created!";
    public static final String YOUR_ACCNT_CREATED_SUCC_MSG="Congratulations! Your new account has been successfully created!";

    public static final String TEST_DATA_FILE_PATH=USER_DIRECTORY+"/src/test/java/com/qa/opencart/data/OpenCartAppTestData.xlsx";
    public static final String REGISTER_SHEET_NAME="register";
}