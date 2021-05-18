public class RunTestSuite extends BaseClass{

public static void main(String[] args) throws Exception {
		
		String test_name = "EXECUTING All TEST CASES";

		try{
			RunTestSuite testSuite = new RunTestSuite();
			
			// Run setup
			testSuite.setup(test_name);	
			
			// Test case 1
			log.info("EXECUTING 1ST TEST CASE: When user fills \\\"BUY\\\" amount, \\\"SELL\\\" amount box is being emptied and vice versa");
			testSuite.checkBuySell(101.11);
			
			// Test case 2
			driver.get(baseUrl);
			log.info("EXECUTING 2ND TEST CASE: When user selects country (select option is in the footer), rates must be updated and currency option should be changed to the respective default currency for that country");
			testSuite.changeCountry();
			
			// Test case 3
			driver.get(baseUrl);
			log.info("EXECUTING 3RD TEST CASE: When bank provider's exchange amount for selling (X) is lower than the amount, provided by Paysera (Y), then a text box is displayed, representing the loss (X-Y)");
			testSuite.lossCalculator();
			
			// Test case 4
			driver.get(baseUrl);
			log.info("EXECUTING 4th TEST CASE: Check paysera amount is showing the correct value in the field after applying the filter option");
			testSuite.filter("buy", 44);
			testSuite.filter("sell", 555.555);
			
			// Close Browser
			tearDown();
		}

		catch(Exception e) {
			log.error(e.getMessage());
			captureScreenShot(driver, "runTestSuite_exception");
			log.error("Screenshot taken. Please check runTestSuite_exception in screenshot folder.");
			tearDown();
		}
	}
}
