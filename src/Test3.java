import org.openqa.selenium.By;

public class Test3 extends BaseClass{

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
				log.info(i+". For "+currency_name+" Loss amount: "+loss_amount);

				//Check if loss amount displayed or not
				if((driver.findElements(By.xpath("//*[@id='currency-exchange-app']/div/div/div[2]/table/tbody/tr["+i+"]/td[5]/span/span/span[2]"))).size()!= 0) {	
					//Check calculated loss with the loss displayed
					String diff_amount = driver.findElement(By.xpath("//*[@id='currency-exchange-app']/div/div/div[2]/table/tbody/tr["+i+"]/td[5]/span/span/span[2]")).getText();
					String  rmv_commas3 = diff_amount.replaceAll("[(,)]", "");
					double d_amount = Double.parseDouble(rmv_commas3);
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
			Thread.sleep(delay_2);
		}
	}

	public static void main(String[] args) throws Exception {

		String test_name = "EXECUTING 3RD TEST CASE: When bank provider's exchange amount for selling (X) is lower than the amount, provided by Paysera (Y), then a text box is displayed, representing the loss (X-Y)";

		try{
			Test3 t3 = new Test3();
			t3.setup(test_name);	
			t3.lossCalculator();
			tearDown();
		}

		catch(Exception e) {
			log.error(e.getMessage());
			captureScreenShot(driver, "test3_exception");
			log.error("Screenshot taken. Please check test3_exception in screenshot folder.");
			Thread.sleep(delay_2);
			tearDown();
		}
	}
}
