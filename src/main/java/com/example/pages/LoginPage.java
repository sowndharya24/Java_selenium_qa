package com.example.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class LoginPage
{
    WebDriver driver;

    By usernameField=By.id("username");
    By passwordField=By.id("password");
    By loginButton=By.id("submit");

    public LoginPage(WebDriver driver){
        this.driver=driver;
    }

    public void enterUsername(String username)
    {
        driver.findElement(usernameField).sendKeys(username);
    }

    public void enterPassword(String password)
    {
        driver.findElement(passwordField).sendKeys(password);
    }

    public void clickLogin()
    {
        driver.findElement(loginButton).click();
    }
}
