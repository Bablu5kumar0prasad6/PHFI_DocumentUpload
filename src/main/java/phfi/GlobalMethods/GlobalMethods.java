package phfi.GlobalMethods;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import jxl.Sheet;
import jxl.Workbook;
import phfi.Scenarios.UploadPdfDocument;

public class GlobalMethods<var> {

	public static WebDriver driver;

	public GlobalMethods() {
		PageFactory.initElements(driver, this);
	}

	static GlobalWait GWait = new GlobalWait(driver);
	Actions action = new Actions(driver);

	public static void LaunchBrowser(String browserName, String Url) {
		if (browserName.equals("firefox")) {
			System.setProperty("webdriver.firefox.driver",
					System.getProperty("user.dir") + "/src/main/resources/win/geckodriver.exe");
			driver = new FirefoxDriver();
		} else if (browserName.equals("chrome")) {
			System.setProperty("webdriver.chrome.driver",
					System.getProperty("user.dir") + "/src/main/resources/win/chromedriver.exe");
			driver = new ChromeDriver();
		} else if (browserName.equals("ie")) {
			System.setProperty("webdriver.ie.driver",
					System.getProperty("user.dir") + "/src/main/resources/win/IEDriverServer.exe");
			driver = new InternetExplorerDriver();
		}

		driver.manage().window().maximize();
		driver.get(Url);
	}

	public static void PI_Login() throws Exception {

		FileInputStream fi = new FileInputStream(System.getProperty("user.dir") + "/src/main/resources/UploadDocument.xls");
		Workbook wb = Workbook.getWorkbook(fi);
		Sheet r1 = wb.getSheet("LoginDetails");

		String UserName_Data = r1.getCell(2, 4).getContents();
		String Password_Data = r1.getCell(3, 4).getContents();

		GWait.Wait_GetElementById("txtUserName").sendKeys(UserName_Data);
		WebElement sas = GWait.Wait_GetElementById("txtPassword");
		sas.sendKeys(Password_Data);
		driver.findElement(By.id("ctl00_ContentPlaceHolder1_LoginButton")).click();

	}
	
	public static void Superadmin_Login() throws Exception {

		FileInputStream fi = new FileInputStream(System.getProperty("user.dir") + "/src/main/resources/UploadDocument.xls");
		Workbook wb = Workbook.getWorkbook(fi);
		Sheet r1 = wb.getSheet("LoginDetails");

		String UserName_Data = r1.getCell(2, 2).getContents();
		String Password_Data = r1.getCell(3, 2).getContents();

		GWait.Wait_GetElementById("txtUserName").sendKeys(UserName_Data);
		WebElement sas = GWait.Wait_GetElementById("txtPassword");
		sas.sendKeys(Password_Data);
		driver.findElement(By.id("ctl00_ContentPlaceHolder1_LoginButton")).click();

	}
	
	public static void scrollToBottom() {
		((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript(
				"window.scrollTo(0, Math.max(document.documentElement.scrollHeight, document.body.scrollHeight, document.documentElement.clientHeight));");

	}
	
	

	public static void openNewTab() {
		driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL + "t");
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("window.open()");
	}

	public static void CloseNewTab() {
		driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL + "w");
		// Switch first tab
		driver.switchTo().defaultContent();
		driver.close();
	}


	public static void UserCreationMailFunctionality() throws Exception {

		openNewTab();
		driver.switchTo().defaultContent();
		String URL = "https://accounts.google.com/signin/v2/identifier?service=mail&passive=true&rm=false&continue=https%3A%2F%2Fmail.google.com%2Fmail%2F%3Ftab%3Dwm&scc=1&ltmpl=default&ltmplcache=2&emr=1&osid=1&flowName=GlifWebSignIn&flowEntry=ServiceLogin";
		driver.get(URL);

		FileInputStream fi = new FileInputStream(System.getProperty("user.dir") + "/src/main/resources/CTMS.xls");
		Workbook wb = Workbook.getWorkbook(fi);
		Sheet r1 = wb.getSheet("UserMNGMT");

		String Emaillink_data = r1.getCell(2, 1).getContents();
		String CTMS_url = "http://ctmsweb.com:8010/#/login";

		WebElement email_field1 = GWait.Wait_GetElementById("identifierId");
		email_field1.sendKeys(Emaillink_data);

		WebElement nextbutton = GWait.Wait_GetElementByXpath("//div[@id='identifierNext']/content/span");
		nextbutton.click();
		WebElement pwd_field1 = GWait.Wait_GetElementByName("password");
		pwd_field1.sendKeys("qa@123456");
		WebElement nextbutton1 = GWait.Wait_GetElementByXpath("//div[2]/div/div/content/span");
		nextbutton1.click();
		Thread.sleep(4500);
		WebElement link1 = GWait.Wait_GetElementByCSS(".asf.T-I-J3.J-J5-Ji");
		link1.click();
		List<WebElement> a = driver.findElements(By.xpath("//span/b[text()='Please login with below details']"));
		System.out.println(a.size());

		if (a.get(0).getText().equalsIgnoreCase("Forgot Password Details")) {
			a.get(0).click();
			WebElement pass1 = GWait.Wait_GetElementByXpath("//div[2]/div[3]/div[3]/div[1]/table/tbody/tr[7]/td");
			System.out.println(pass1.getText());
			String pass2 = pass1.getText();
			String[] passwordSplit = pass2.split(" ");
			System.out.println("1st: " + passwordSplit[0]);
			System.out.println("2nd: " + passwordSplit[1]);
			System.out.println("3rd: " + passwordSplit[2]);

			String finalPass = passwordSplit[2];
			Thread.sleep(2000);
			WebElement emaillogout = GWait.Wait_GetElementByXpath("//div[1]/div/div[5]/div[1]/a/span");
			emaillogout.click();
			WebElement emailsingoutBTN = GWait.Wait_GetElementByXpath("//div/div[5]/div[2]/div[4]/div[2]/a");
			emailsingoutBTN.click();

			Thread.sleep(1000);
			openNewTab();
			driver.switchTo().defaultContent();
			CloseNewTab();
			driver.get(CTMS_url);

			

			// -----Login-----//
			GWait.Wait_GetElementById("txtUserName", 120).sendKeys("");
			GWait.Wait_GetElementById("txtPassword", 120).sendKeys(finalPass);
			GWait.Wait_GetElementById("LoginButton", 120).click();

			// ----Reset Password----//
			Sheet r = wb.getSheet("LoginDetails");

			String SetNewPassword = r.getCell(4, 3).getContents();

			GWait.Wait_GetElementById("ChangePassword1_CurrentPassword", 120).sendKeys(finalPass);
			GWait.Wait_GetElementById("ChangePassword1_NewPassword", 120).sendKeys(SetNewPassword);
			GWait.Wait_GetElementById("ChangePassword1_ConfirmNewPassword", 120).sendKeys(SetNewPassword);
			GWait.Wait_GetElementById("ChangePassword1_ChangePasswordPushButton", 120).click();
			driver.switchTo().defaultContent();
		}

	}

}