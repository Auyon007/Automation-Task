import java.io.File;
import java.io.IOException;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BaseClass {

	public String baseUrl = "https://www.paysera.lt/v2/en-LT/fees/currency-conversion-calculator#/";
	protected static WebDriver driver;
	protected static JavascriptExecutor je;

	// Logger instance created
	public static Logger log = Logger.getLogger("BaseClass");

	// Used for 3 sec delay
	protected static int delay_2 = 2000;
	protected static int delay_3 = 3000; 
	
	static String table_path = "//tbody/tr[1]/td[1]";

	// ----------------SETUP--------------------
	public void setup(String test_name) throws Exception {

			// configure log4j properties file
			PropertyConfigurator.configure(".\\Resources\\Log4j.properties");

			// Chrome Driver Setup
			String path = System.getProperty("user.dir");
			System.setProperty("webdriver.chrome.driver", path+"\\Resources\\chromedriver.exe");
			driver = new ChromeDriver();
			
			//Print test name
			log.info(test_name);
			log.info("Open Chrome Browser");

			// Maximize window
			driver.manage().window().maximize();
			log.info("Browser Maximized");

			// Go to Currency exchange calculator
			driver.get(baseUrl);
			log.info("Enter Currency exchange calculator UI");
			
			//driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

			// Wait for table components to load
			log.info("Waiting for Currency exchange calculator table to load");
			waitForElementToBeVisible(driver, table_path, 20);
			
	}

	public static void scroll_to_cec() throws InterruptedException {
		je = (JavascriptExecutor) driver;

		//Find element and store in variable       		
		WebElement Element = driver.findElement(By.cssSelector("body.en.layout-plain:nth-child(2) article.content-wrapper:nth-child(2) section.text-section:nth-child(3) div.container div.heading-block-wrapper div.heading-block > h1:nth-child(2)"));

		//Scroll the page till the element is found		
		je.executeScript("arguments[0].scrollIntoView(true);", Element);
		log.info("Scroll down to Currency exchange calculator");

		log.info("Waiting for Currency exchange calculator table to load");
		waitForElementToBeVisible(driver, table_path, 20);
		
	}

	public static void scroll_to_element_xpath(String x) throws InterruptedException {
		je = (JavascriptExecutor) driver;

		//Find element and store in variable       		
		WebElement Element = driver.findElement(By.xpath(x));

		//Scroll the page till the element is found		
		je.executeScript("arguments[0].scrollIntoView(true);", Element);
	}

	// Take Screenshot
	public static void captureScreenShot(WebDriver ldriver, String SName) {

		// Take screenshot and store as a file format
		File src = ((TakesScreenshot) ldriver).getScreenshotAs(OutputType.FILE);
		try {
			// now copy the screenshot to desired location using copyFile method

			FileHandler.copy(src, new File("./Screenshots/" + SName + ".png"));
		}

		catch (IOException e) {
			log.error(e.getMessage());
		}
	}

	// Scroll and Take Screenshot
	public static void scroll_captureScreenShot(WebDriver ldriver, String SName, String x) throws InterruptedException {

		// Scroll down to element location
		scroll_to_element_xpath(x);

		// Take screenshot and store as a file format
		File src = ((TakesScreenshot) ldriver).getScreenshotAs(OutputType.FILE);
		try {
			// now copy the screenshot to desired location using copyFile method

			FileHandler.copy(src, new File("./Screenshots/" + SName + ".png"));
		}

		catch (IOException e) {
			log.error(e.getMessage());
		}
	}

	public static WebElement waitForElementToBeVisible(WebDriver driver, String webElement, int seconds) {

		WebDriverWait wait = new WebDriverWait(driver, seconds);
		WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(webElement)));
		return element;
	}

	// Close browser
	public static void tearDown() throws Exception {
			
		// Close browser
			driver.quit();
			log.info("Browser Closed");
	}


}
