public class Test3 extends BaseClass{

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
			tearDown();
		}
	}
}
