import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class Test4 extends BaseClass{

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
				Thread.sleep(delay_2);

				//Click on filter
				filter_btn.click();
				Thread.sleep(delay_2);
				log.info("Clicked on filter button");

				//Wait for table to load
				log.info("Waiting for Currency exchange calculator table to load");
				waitForElementToBeVisible(driver, table_path, 20);
				break;

			case "sell":
				// Input value in the sell field
				WebElement sell= driver.findElement(By.xpath("//*[@id=\'currency-exchange-app\']/div/div/div[2]/div[1]/form/div[1]/input"));
				sell.clear();
				sell.sendKeys(String.valueOf(value));
				log.info("Input provided in the sell field is: "+value);
				Thread.sleep(delay_2);

				//Click on filter
				filter_btn.click();
				Thread.sleep(delay_2);
				log.info("Clicked on filter button");

				//Wait for table to load
				log.info("Waiting for Currency exchange calculator table to load");
				waitForElementToBeVisible(driver, table_path, 20);
				break;
			}
			
			for (int i = 1; i<=31; i++) {

				//Keep the screen at the point of calculation
				String country_path = "//tbody/tr["+i+"]/td[1]";
				scroll_to_element_xpath("//tbody/tr["+i+"]/td[1]");
				String country_name = driver.findElement(By.xpath(country_path)).getText();
				
				// Find Paysera rate
				String paysera_rate = driver.findElement(By.cssSelector("tbody tr:nth-child("+i+") td:nth-child(3)")).getText();
				String  rmv_commas_rate = paysera_rate.replaceAll(",", "");
				double p_rate = Double.parseDouble(rmv_commas_rate) ;
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
				Thread.sleep(delay_2);
			}
		}

		else {
			log.warn("Entered wrong input for the type parameter. Type parameter only accepts 'buy' or 'sell' as an input. Please change and run again.");
		}
	}

	public static void main(String[] args) throws Exception {
		
		String test_name = "EXECUTING 4th TEST CASE: Check paysera amount is showing the correct value in the field after applying the filter option";

		try{
			Test4 t4 = new Test4();
			t4.setup(test_name);	
			t4.filter("buy", 44);
			t4.filter("sell", 555.555);
			tearDown();
		}

		catch(Exception e) {
			log.error(e.getMessage());
			captureScreenShot(driver, "test4_exception");
			log.error("Screenshot taken. Please check test4_exception in screenshot folder.");
			Thread.sleep(delay_2);
			tearDown();
		}
	}
}
