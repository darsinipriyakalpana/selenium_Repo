package com.tricentis.objectrepository;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage 
{
	//initialization
public LoginPage(WebDriver driver) 
{
PageFactory.initElements(driver, this);
}

//declaration
@FindBy(id = "Email")
private WebElement emailTextField;
@FindBy(id = "Password")
private WebElement passwordTextField;

@FindBy(xpath = "//input[@value='Log in']")
private WebElement loginButton;

//getters
public WebElement getEmailTextField() {
	return emailTextField;
}

public WebElement getPasswordTextField() {
	return passwordTextField;
}

public WebElement getLoginButton() {
	return loginButton;
}

}
