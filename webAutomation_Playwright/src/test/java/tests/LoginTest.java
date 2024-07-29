package tests;

import common.BaseTest;
import constants.AppConstants;
import factory.PageManager;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.LoginPage;

public class LoginTest extends BaseTest {
    LoginPage loginPage;

    @Test
    public void loginOnStaging() {
        loginPage = new LoginPage(PageManager.getPage());
        loginPage.navigateToStagURL();
        String url = loginPage.getPageURL();
        Assert.assertEquals(url, AppConstants.STAG_URL);
        loginPage.login(properties.getProperty("username"), properties.getProperty("password"));
    }
}

