package com.tricentis.genericutility;

import java.io.IOException;
import java.lang.reflect.Method;
import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.tricentis.objectrepository.HomePage;
import com.tricentis.objectrepository.LoginPage;
import com.tricentis.objectrepository.WelcomePage;

public class BaseClass 
{
	public static WebDriver driver;
	public static ExtentReports extReports;
	public ExtentTest test;
	public static ExtentTest screenTest;
	
	public FileUtility flib;
	public ExcelUtility elib;
	public JavaUtility jlib;

	public WelcomePage wp;
	public LoginPage lp;
	public HomePage hp;
	
	@BeforeSuite
	public void configreport()
	{
		jlib = new JavaUtility();
		String timeStamp = jlib.getLocalTimeStamp();
		ExtentSparkReporter spark = new ExtentSparkReporter("./HTML Report_"+timeStamp+".html");
		extReports=new ExtentReports();
		extReports.attachReporter(spark);
	}
	
	@Parameters("Browser")
	@BeforeClass
public void launchBrowser(@Optional("chrome") String browserName) throws IOException
{
		if(browserName.equalsIgnoreCase("chrome"))
		{
			driver=new ChromeDriver();
		}
		else if(browserName.equalsIgnoreCase("edge"))
		{
			driver=new EdgeDriver();
		}
		else if(browserName.equalsIgnoreCase("firefox"))
		{
			driver = new FirefoxDriver();
		}
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		flib = new FileUtility();
		driver.get(flib.getDataFromPropertyFile("url"));
}
	
	@BeforeMethod
	public void login(Method method) throws IOException 
	{
		test = extReports.createTest(method.getName());
		screenTest=test;
	wp=new WelcomePage(driver);
	wp.getLoginLink().click();;
	lp= new LoginPage(driver);
	String email = flib.getDataFromPropertyFile("email");
	String password=flib.getDataFromPropertyFile("passowrd");
	lp.getEmailTextField().sendKeys(email);
	lp.getPasswordTextField().sendKeys(password);
	lp.getLoginButton().submit();
	elib = new ExcelUtility();
	String expectedTitle = elib.getStringDataFromExcle("login", 1, 2);
	Assert.assertEquals(driver.getTitle(), expectedTitle,"User failed to login");
	Reporter.log("User logged in successfully", true);
	hp=new HomePage(driver);
	}
	
	@AfterMethod
	public void logout()
	{
		hp.getLogoutLink().click();
	}
	
	@AfterClass
	public void closeBrowser()
	{
		driver.quit();
	}
	@AfterSuite
	public void reportBackup()
	{
		extReports.flush();
	}
}
