import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class Test1 extends BaseClass{

	public void checkBuySell() throws Exception{

		//Scroll to Currency exchange calculator
		scroll_to_cec();

		//Get data from the Sell field
		WebElement sell= driver.findElement(By.cssSelector("input[class='form-control ng-pristine ng-untouched ng-valid ng-not-empty']"));
		String sell_value = sell.getAttribute("value");
		String s_value = String.format("Predefined value of sell field is: %s", sell_value);
		log.info(s_value);
		Thread.sleep(delay_3);

		//Input value in the Buy field
		WebElement buy = driver.findElement(By.cssSelector("body > main:nth-child(1) > article:nth-child(2) > section:nth-child(3) > div:nth-child(1) > div:nth-child(3) > div:nth-child(2) > div:nth-child(1) > div:nth-child(2) > div:nth-child(1) > form:nth-child(1) > div:nth-child(3) > input:nth-child(2)"));
		int bValue= 200;
		buy.sendKeys(Integer.toString(bValue));
		String b_value = String.format("Input value %s in the Buy field", bValue);
		log.info(b_value);
		Thread.sleep(delay_3);

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
		Thread.sleep(delay_3);

		//Input New value in the Sell field
		int sValue= 500;
		sell.sendKeys(Integer.toString(sValue));
		String s_value_new = String.format("Input value %s in the Sell field", sValue);
		log.info(s_value_new);
		Thread.sleep(delay_3);

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
		Thread.sleep(delay_3);
	}

	public static void main(String[] args) throws Exception{

		String test_name = "EXECUTING 1ST TEST CASE: When user fills \"BUY\" amount, \"SELL\" amount box is being emptied and vice versa";

		try{
			Test1 t1 = new Test1();
			t1.setup(test_name);
			t1.checkBuySell();
			tearDown();
		}

		catch(Exception e) {
			log.error(e.getMessage());
			captureScreenShot(driver, "test1_exception");
			log.error("Screenshot taken. Please check test1_exception in screenshot folder.");
			Thread.sleep(delay_2);
			tearDown();
		}
	}
}
