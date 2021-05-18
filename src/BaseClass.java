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

	public static String baseUrl = "https://www.paysera.lt/v2/en-LT/fees/currency-conversion-calculator#/";
	protected static WebDriver driver;
	protected static JavascriptExecutor je;

	// Logger instance created
	public static Logger log = Logger.getLogger(BaseClass.class);

	// Used for 3 sec delay
	protected static int delay_1 = 1000;
	protected static int delay_2 = 2000; 

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

		// Wait for element
		waitForElementToBeVisible(driver, table_path, 20);

	}

	public static void scroll_to_cec() throws InterruptedException {
		// Wait for element
		waitForElementToBeVisible(driver, table_path, 20);

		je = (JavascriptExecutor) driver;

		//Find element and store in variable       		
		WebElement Element = driver.findElement(By.cssSelector("body.en.layout-plain:nth-child(2) article.content-wrapper:nth-child(2) section.text-section:nth-child(3) div.container div.heading-block-wrapper div.heading-block > h1:nth-child(2)"));

		//Scroll the page till the element is found		
		je.executeScript("arguments[0].scrollIntoView(true);", Element);
		log.info("Scroll down to Currency exchange calculator");

	}

	public static void scroll_to_element_xpath(String x) throws InterruptedException {
		// Wait for element
		waitForElementToBeVisible(driver, x, 20);

		je = (JavascriptExecutor) driver;

		//Find element and store in variable       		
		WebElement Element = driver.findElement(By.xpath(x));

		//Scroll the page till the element is found		
		je.executeScript("arguments[0].scrollIntoView(true);", Element);
	}

	// Take Screenshot
	public static void captureScreenShot(WebDriver ldriver, String SName) throws IOException {

		// Take screenshot and store as a file format
		File src = ((TakesScreenshot) ldriver).getScreenshotAs(OutputType.FILE);

		// now copy the screenshot to desired location using copyFile method

		FileHandler.copy(src, new File("./Screenshots/" + SName + ".png"));
	}

	// Scroll and Take Screenshot
	public static void scroll_captureScreenShot(WebDriver ldriver, String SName, String x) throws InterruptedException, IOException {

		// Scroll down to element location
		scroll_to_element_xpath(x);

		// Take screenshot and store as a file format
		File src = ((TakesScreenshot) ldriver).getScreenshotAs(OutputType.FILE);

			// now copy the screenshot to desired location using copyFile method
			FileHandler.copy(src, new File("./Screenshots/" + SName + ".png"));
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

	// Test 1
	public void checkBuySell(double number) throws Exception{

		//Scroll to Currency exchange calculator
		scroll_to_cec();

		//Get data from the Sell field
		WebElement sell= driver.findElement(By.cssSelector("input[class='form-control ng-pristine ng-untouched ng-valid ng-not-empty']"));
		String sell_value = sell.getAttribute("value");
		String s_value = String.format("Predefined value of sell field is: %s", sell_value);
		log.info(s_value);
		Thread.sleep(delay_2);

		//Input value in the Buy field
		WebElement buy = driver.findElement(By.cssSelector("body > main:nth-child(1) > article:nth-child(2) > section:nth-child(3) > div:nth-child(1) > div:nth-child(3) > div:nth-child(2) > div:nth-child(1) > div:nth-child(2) > div:nth-child(1) > form:nth-child(1) > div:nth-child(3) > input:nth-child(2)"));
		buy.sendKeys(Double.toString(number));
		String b_value = String.format("Input value %s in the Buy field", number);
		log.info(b_value);
		Thread.sleep(delay_2);

		//Check Sell field value
		String sell_value1 = sell.getAttribute("value");
		if(sell_value1.isEmpty())
		{
			log.info("*** Test Passed. Sell field got emptied ***");
		}
		else
		{
			log.warn("### Test Failed. Sell field did not get emptied ###");
			captureScreenShot(driver, "sell_failed");
			log.warn("Screenshot taken. Please check sell_failed in screenshot folder.");
		}
		Thread.sleep(delay_2);

		//Input New value in the Sell field
		sell.sendKeys(Double.toString(number));
		String s_value_new = String.format("Input value %s in the Sell field", number);
		log.info(s_value_new);
		Thread.sleep(delay_2);

		//Check Buy field value
		String buy_value1 = buy.getAttribute("value");
		if(buy_value1.isEmpty())
		{
			log.info("*** Test Passed. Buy field got emptied ***");
		}
		else
		{
			log.warn("### Test Failed. Buy field did not get emptied ###");
			captureScreenShot(driver, "buy_failed");
			log.warn("Screenshot taken. Please check buy_failed in screenshot folder.");
		}
		Thread.sleep(delay_2);
	}

	// Test 2
	public void changeCountry() throws Exception{

		for(int i=2; i<=15; i++) {

			// Get landing URL
			String initialUrl = driver.getCurrentUrl();

			// Click on footer flag
			driver.findElement(By.cssSelector("span[role='button']")).click();
			log.info("Click on footer flag");
			Thread.sleep(delay_1);

			// Click on country dropdown
			driver.findElement(By.cssSelector("#countries-dropdown")).click();
			log.info("Click on country");
			Thread.sleep(delay_1);

			// Select country
			WebElement country_Element = driver.findElement(By.cssSelector("div[class='dropup open'] li:nth-child("+i+") a:nth-child(1)"));
			String country_name = country_Element.getText();
			country_Element.click();
			String currentUrl = driver.getCurrentUrl();
			log.info(i+". Country selected: "+country_name);
			Thread.sleep(delay_2);

			// Ignore country: Kosovo
			if (country_name.contentEquals("Kosovo")) {
				log.info("Currency exchange calculator not implemented.");
				Thread.sleep(delay_1);
				continue;
			}

			if(!initialUrl.contentEquals(currentUrl) && currentUrl.contains("https://www.paysera")==true) {

				//Scroll to Currency exchange calculator
				scroll_to_cec();
				Thread.sleep(delay_1);

				String currency_Option = driver.findElement(By.xpath("//*[@id=\'currency-exchange-app\']/div/div/div[2]/div[1]/form/div[1]/div/div[1]/span")).getText();
				String test_Pass = "*** Test Case passed. Rates updated and currency option matched***";
				String test_Fail = "### Test Case failed. Rates updated but currency option did not matched ###";
				String screenshot_output = "Screenshot taken. Please check "+i+"_"+country_name+"_failed_image in screenshot folder.";
				log.info("Currency option is: "+currency_Option);

				// Check currency
				switch (country_name) {
				case "Latvia":
				case "Estonia":
				case "Spain":
				case "Germany":
				case "Another country":					

					if(currency_Option.contains("EUR")){
						log.info(test_Pass);
					}
					else {
						log.warn(test_Fail);
						captureScreenShot(driver, i+"_"+country_name+"_failed_mage");
						log.warn(screenshot_output);
					}
					break;

				case "Bulgaria":
					if(currency_Option.contains("BGN")){
						log.info(test_Pass);
					}
					else {
						log.warn(test_Fail);
						captureScreenShot(driver, i+"_"+country_name+"_failed_mage");
						log.warn(screenshot_output);
					}
					break;

				case "Romania":
					if(currency_Option.contains("RON")){
						log.info(test_Pass);
					}
					else {
						log.warn(test_Fail);
						captureScreenShot(driver, i+"_"+country_name+"_failed_mage");
						log.warn(screenshot_output);
					}
					break;

				case "Poland":
					if(currency_Option.contains("PLN")){
						log.info(test_Pass);
					}
					else {
						log.warn(test_Fail);
						captureScreenShot(driver, i+"_"+country_name+"_failed_mage");
						log.warn(screenshot_output);
					}
					break;

				case "United Kingdom":
					if(currency_Option.contains("GBP")){
						log.info(test_Pass);
					}
					else {
						log.warn(test_Fail);
						captureScreenShot(driver, i+"_"+country_name+"_failed_image");
						log.warn(screenshot_output);
					}
					break;

				case "Russia":
					if(currency_Option.contains("RUB")){
						log.info(test_Pass);
					}
					else {
						log.warn(test_Fail);
						captureScreenShot(driver, i+"_"+country_name+"_failed_mage");
						log.warn(screenshot_output);
					}
					break;

				case "Algeria":
					if(currency_Option.contains("DZD")){
						log.info(test_Pass);
					}
					else {
						log.warn(test_Fail);
						captureScreenShot(driver, i+"_"+country_name+"_failed_mage");
						log.warn(screenshot_output);
					}
					break;

				case "Albania":
					if(currency_Option.contains("ALL")){
						log.info(test_Pass);
					}
					else {
						log.warn(test_Fail);
						captureScreenShot(driver, i+"_"+country_name+"_failed_mage");
						log.warn(screenshot_output);
					}
					break;

				case "Ukraine":
					if(currency_Option.contains("UAH")){
						log.info(test_Pass);
					}
					else {
						log.warn(test_Fail);
						captureScreenShot(driver, i+"_"+country_name+"_failed_image");
						log.warn(screenshot_output);
					}
					break;
				}

				//Thread.sleep(delay_1);
			}

			else {
				log.warn("### Test Case failed. Rates did not updated ###");
				captureScreenShot(driver, i+"_rate_test_failed_image");
				log.warn("Screenshot taken. Please check "+i+"_rate_test_failed_image in screenshot folder.");
				Thread.sleep(delay_1);
			}
		}
	}

	// Test 3
	public void lossCalculator() throws Exception{

		for(int i=1;i<=31;i++) {

			String paysera_path = "//*[@id='currency-exchange-app']/div/div/div[2]/table/tbody/tr["+i+"]/td[4]";
			String swedbank_path = "//*[@id=\'currency-exchange-app\']/div/div/div[2]/table/tbody/tr["+i+"]/td[5]/span/span/span[1]";
			String currency_path = "//tbody/tr["+i+"]/td[1]";

			//Scroll the page to the calculation UI
			scroll_to_element_xpath(paysera_path);

			String currency_name = driver.findElement(By.xpath(currency_path)).getText();

			//Get paysera amount
			String paysera_amount = driver.findElement(By.xpath(paysera_path)).getText();
			String  rmv_commas1 = paysera_amount.replaceAll(",", "");
			double p_amount = Double.parseDouble(rmv_commas1) ;
			log.info(i+". For "+currency_name+" Paysera amount is: "+p_amount);

			//Check if swedbank amount present or not
			if((driver.findElements(By.xpath(swedbank_path))).size()!= 0) {

				//Get swedbank amount
				String swedbank_amount = driver.findElement(By.xpath("//*[@id=\'currency-exchange-app\']/div/div/div[2]/table/tbody/tr["+i+"]/td[5]/span/span/span[1]")).getText();
				String  rmv_commas2 = swedbank_amount.replaceAll(",", "");
				double s_amount = Double.parseDouble(rmv_commas2);
				log.info(i+". For "+currency_name+" Swedbank amount is: "+s_amount);

				//Loss calculation
				double loss_amount = Double.parseDouble((String.format("%.2f",s_amount-p_amount)));
				log.info(i+". For "+currency_name+" Expected loss amount: "+loss_amount);

				//Check if loss amount displayed or not
				if((driver.findElements(By.xpath("//*[@id='currency-exchange-app']/div/div/div[2]/table/tbody/tr["+i+"]/td[5]/span/span/span[2]"))).size()!= 0) {	
					//Check calculated loss with the loss displayed
					String diff_amount = driver.findElement(By.xpath("//*[@id='currency-exchange-app']/div/div/div[2]/table/tbody/tr["+i+"]/td[5]/span/span/span[2]")).getText();
					String  rmv_commas3 = diff_amount.replaceAll("[(,)]", "");
					double d_amount = Double.parseDouble(rmv_commas3);
					log.info(i+". For "+currency_name+" Actual loss amount: "+loss_amount);

					if(loss_amount == d_amount) {
						log.info("*** Test Case Passed. The exact loss amount for "+currency_name+" is displayed ***");
					}
					else {
						log.warn("### Test Case Failed. Wrong loss amount for "+currency_name+" is displayed ###");
						scroll_captureScreenShot(driver, i+"_wrong_loss",paysera_path);
						log.warn("Screenshot taken. Please check "+i+"_wrong_loss in screenshot folder.");
					}
				}
				else {
					log.warn("### Test Case Failed. No loss amount is displayed for "+currency_name+" ###");
					scroll_captureScreenShot(driver, i+"_no_loss",paysera_path);
					log.warn("Screenshot taken. Please check "+i+"_no_loss in screenshot folder.");
				}
			}
			else {
				log.info(i+". Swedbank amount not present for "+currency_name);

			}
			Thread.sleep(delay_1);
		}
	}

	// Test 4
	public void filter(String type, double value) throws Exception{

		// Scroll to Currency exchange calculator
		scroll_to_cec();

		WebElement filter_btn= driver.findElement(By.xpath("//button[normalize-space()='Filter']"));

		if(type.toLowerCase().contentEquals("buy") == true || type.toLowerCase().contentEquals("sell") == true) {
			switch (type.toLowerCase()) {
			case "buy":
				// Input value in the buy field
				WebElement buy= driver.findElement(By.xpath("//*[@id=\'currency-exchange-app\']/div/div/div[2]/div[1]/form/div[3]/input"));
				buy.clear();
				buy.sendKeys(String.valueOf(value));
				log.info("Input provided in the buy field is: "+value);
				Thread.sleep(delay_1);

				//Click on filter
				filter_btn.click();
				Thread.sleep(delay_1);
				log.info("Clicked on filter button");

				// Wait for element
				waitForElementToBeVisible(driver, table_path, 20);
				break;

			case "sell":
				// Input value in the sell field
				WebElement sell= driver.findElement(By.xpath("//*[@id=\'currency-exchange-app\']/div/div/div[2]/div[1]/form/div[1]/input"));
				sell.clear();
				sell.sendKeys(String.valueOf(value));
				log.info("Input provided in the sell field is: "+value);
				Thread.sleep(delay_1);

				//Click on filter
				filter_btn.click();
				Thread.sleep(delay_1);
				log.info("Clicked on filter button");

			}

			for (int i = 1; i<=31; i++) {

				//Keep the screen at the point of calculation
				String country_path = "//tbody/tr["+i+"]/td[1]";
				scroll_to_element_xpath("//tbody/tr["+i+"]/td[1]");
				String country_name = driver.findElement(By.xpath(country_path)).getText();

				// Find Paysera rate
				String paysera_rate = driver.findElement(By.cssSelector("tbody tr:nth-child("+i+") td:nth-child(3)")).getText();
				String  rmv_commas_rate = paysera_rate.replaceAll(",", "");
				double p_rate = Double.parseDouble(rmv_commas_rate);
				log.info(i+". For "+country_name+" Paysera rate is: "+p_rate);

				// Find Paysera amount
				String paysera_amount = driver.findElement(By.cssSelector("tbody tr:nth-child("+i+") td:nth-child(4)")).getText();
				String  rmv_commas_amount = paysera_amount.replaceAll(",", "");
				double p_amount = Double.parseDouble(rmv_commas_amount) ;
				log.info(i+". For "+country_name+" Paysera amount is: "+p_amount);

				// Paysera amount calculation
				double amount_calculation = Double.parseDouble((String.format("%.2f",value*p_rate)));
				log.info(i+". For "+country_name+" expected paysera amount is: "+amount_calculation);			

				// Check Paysera amount calculation
				if(p_amount == amount_calculation) {
					log.info("*** Test case passed. Paysera amount for "+country_name+" is showing the correct value ***");
				}

				else {
					log.warn("### Test case failed. Paysera amount for "+country_name+" is showing the wrong value");
				}
				Thread.sleep(delay_1);
			}
		}

		else {
			log.warn("Entered wrong input for the type parameter. Type parameter only accepts 'buy' or 'sell' as an input. Please change and run again.");
		}
	}
}

