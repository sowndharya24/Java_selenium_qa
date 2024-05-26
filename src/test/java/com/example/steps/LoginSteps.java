package com.example.steps;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.example.pages.LoginPage;
import com.example.utils.ExcelUtils;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;

import static java.lang.Thread.sleep;

public class LoginSteps {
    WebDriver driver;

    LoginPage loginPage;
    ExtentReports extent;
    ExtentTest test;
    @BeforeClass
    @Given("The user is on the login page")
    public void the_user_is_on_the_login_page(){
       // System.setProperty("webdriver.chrome.driver", "C:\\Program Files\\chromedriver-win64\\chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        driver = new ChromeDriver(options);
        loginPage = new LoginPage(driver);

        ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter("testoutput/extent-report.html");
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
    }
    /*@DataProvider(name = "loginData")
    public Object[][] getData(){
        return new Object[][]{
                {"student","Password123"},
                {"student","incorrectPassword "},
                {"incorrectUser ","Password123"}
        };
    }*/
    @DataProvider(name = "loginData")
    public Object[][] getLoginData() throws  IOException {
        String excelFilePath = "C:\\Users\\sownd\\Downloads\\fluxExample\\Java_automation\\src\\test\\java\\com\\example\\dataset\\loginData.xlsx";
        String sheetName = "Sheet1";

        ExcelUtils excelUtils = new ExcelUtils(excelFilePath, sheetName);
        Object[][] data = excelUtils.getSheetData();
        excelUtils.close();

        return data;
    }
    @Test(dataProvider = "loginData")
    @When("The user enters valid credentials")
    public void the_user_enters_valid_credentials(String username,String password) throws InterruptedException {

        driver.get("https://practicetestautomation.com/practice-test-login/");
        loginPage.enterUsername(username);
        loginPage.enterPassword(password);
        loginPage.clickLogin();
        sleep(3000);
    }


    @Then("The user should be redirected to next page")
    public void the_user_should_be_redirected_to_next_page(){
        if (driver.getCurrentUrl().contains("successfully")) {
            Assert.assertEquals(driver.getCurrentUrl(), "https://practicetestautomation.com/logged-in-successfully/", "Login successfully");
            driver.quit();
        } else if (driver.getCurrentUrl().contains("practice-test-login")) {
            Assert.assertEquals(driver.findElement(By.id("error")).getText(),"Your password is invalid!","Invalid password");
        } else if (driver.getCurrentUrl().contains("practice-test-login")) {
            Assert.assertEquals(driver.findElement(By.id("error")).getText(),"Your username is invalid!","Invalid username");
        }
    }
    @AfterClass
    public void tearDown(){
        if (driver!=null){
            driver.quit();
        }
    }
}
