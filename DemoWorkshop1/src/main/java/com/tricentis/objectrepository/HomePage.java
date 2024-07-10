package com.tricentis.objectrepository;

import javax.management.loading.PrivateClassLoader;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage 
{
public HomePage(WebDriver driver) 
{
PageFactory.initElements(driver, this);
}

@FindBy(linkText = "Log out")
private WebElement logoutLink;

@FindBy(linkText = "Books")
private WebElement booksLink;


public WebElement getBooksLink() {
	return booksLink;
}


public WebElement getLogoutLink()
{
	return logoutLink;
	
}

}


