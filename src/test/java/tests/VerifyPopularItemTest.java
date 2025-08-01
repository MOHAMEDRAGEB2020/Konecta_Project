package tests;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.*;
import setup.Setup;
import utils.testdata.TestData;

public class VerifyPopularItemTest extends Setup {
    @Test
    public void VerifyPopularItem(){
        String username = TestData.username;
        String password = TestData.password;

        test.info("Test case started");
        try{
            Assert.assertTrue(homePage.CheckHomePageIsVisible());
            test.log(Status.PASS,"Home Page verified");
        }catch (Exception e){
            test.log(Status.FAIL,e.getCause() + e.getMessage());
        }
        LoginPage loginPage = homePage.ClickLoginOrSignUpButton();
        loginPage.enterUsername(username);
        loginPage.enterPassword(password);
        loginPage.clickLoginButton();
        try{
            Assert.assertTrue(homePage.CheckHomePageIsVisible());
            test.log(Status.PASS,"LoggedIn and Home Page Is Visible");
        }catch (Exception e){
            test.log(Status.FAIL,e.getCause() + e.getMessage());
        }

        try{
            Assert.assertEquals(username, homePage.UserLoggedInUser());
            test.log(Status.PASS,"User Matches Logged In User Name");
        }catch (AssertionError e){
            test.log(Status.FAIL,e.getCause() + e.getMessage());
        }

        HPEliteBookFolioDetailsPage HPEliteBookPage=homePage.clickHPEliteBookFolioButton();
        try{
            Assert.assertTrue(HPEliteBookPage.VerifyEliteBookPDP());
            test.log(Status.PASS,"Successful product detail page redirection");
        }catch (AssertionError e){
            test.log(Status.FAIL,e.getCause() + e.getMessage());
        }

        try {
            Assert.assertEquals( HPEliteBookPage.VerifyEliteBookName(),"HP ELITEBOOK FOLIO");
            test.log(Status.PASS, "Correct product details page of HP ELITEBOOK FOLIO");
        } catch (AssertionError e) {
            // Take screenshot as base64
            String base64Image = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);

            // Attach to Extent Report as embedded Base64 image
            test.fail("Bug: Unexpected Item Name And Bread Crumb Name , "+e.getMessage(),
                    MediaEntityBuilder.createScreenCaptureFromBase64String(base64Image).build());

        }
        SearchPage searchPage=new SearchPage(driver);
        searchPage.clickSearchButton();
        try{
            Assert.assertTrue(searchPage.isSearchBarPresent());
            test.log(Status.PASS,"Search bar is present");
        }catch (AssertionError e){
            test.log(Status.FAIL,e.getCause() + e.getMessage());
        }
        searchPage.SearchForProduct("HP ELITEBOOK FOLIO");
        try {
            Assert.assertEquals(  searchPage.VerifyNoResultsMessageIsDisplayed(),"HP ELITEBOOK FOLIO");
            test.log(Status.PASS, "Correct Display of HP ELITEBOOK FOLIO");
        } catch (AssertionError e) {
            // Take screenshot as base64
            String base64Image = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);

            // Attach to Extent Report as embedded Base64 image
            test.fail("Bug:No Item Found , "+e.getMessage(),
                    MediaEntityBuilder.createScreenCaptureFromBase64String(base64Image).build());

        }
       /* try{
            Assert.assertEquals("HP ELITEBOOK FOLIO", searchPage.VerifyNoResultsMessageIsDisplayed());

            test.log(Status.PASS,"Correct display of no available product");
        }catch (AssertionError e){
            test.log(Status.FAIL,e.getCause() + e.getMessage());
        }*/
        test.info("Test case Ended");


    }
}
