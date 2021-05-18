import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class Test2 extends BaseClass{

	public void changeCountry() throws Exception{

		for(int i=2; i<=15; i++) {

			// Get landing URL
			String initialUrl = driver.getCurrentUrl();

			// Click on footer flag
			driver.findElement(By.cssSelector("span[role='button']")).click();
			log.info("Click on footer flag");
			Thread.sleep(delay_2);

			// Click on country dropdown
			driver.findElement(By.id("countries-dropdown")).click();
			log.info("Click on country");
			Thread.sleep(delay_2);

			// Select country
			WebElement country_Element = driver.findElement(By.cssSelector("div[class='dropup open'] li:nth-child("+i+") a:nth-child(1)"));
			String country_name = country_Element.getText();
			country_Element.click();
			String currentUrl = driver.getCurrentUrl();
			log.info(i+". Country selected: "+country_name);
			Thread.sleep(delay_3);

			// Ignore country: Kosovo
			if (country_name.contentEquals("Kosovo")) {
				log.info("Currency exchange calculator not implemented.");
				Thread.sleep(delay_2);
				continue;
			}

			if(!initialUrl.contentEquals(currentUrl) && currentUrl.contains("https://www.paysera")==true) {

				//Scroll to Currency exchange calculator
				scroll_to_cec();

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
				
				Thread.sleep(delay_2);
			}

			else {
				log.warn("### Test Case failed. Rates did not updated ###");
				captureScreenShot(driver, i+"_rate_test_failed_image");
				log.warn("Screenshot taken. Please check "+i+"_rate_test_failed_image in screenshot folder.");
				Thread.sleep(delay_2);
			}
		}
	}

	public static void main(String[] args) throws Exception {

		String test_name= "EXECUTING 2ND TEST CASE: When user selects country (select option is in the footer), rates must be updated and currency option should be changed to the respective default currency for that country";

		try{
			Test2 t2 = new Test2();
			t2.setup(test_name);
			t2.changeCountry();	
			tearDown();
		}

		catch(Exception e) {
			log.error(e.getMessage());
			captureScreenShot(driver, "test2_exception");
			log.error("Screenshot taken. Please check test2_exception in screenshot folder.");
			Thread.sleep(delay_2);
			tearDown();
		}
	}
}
