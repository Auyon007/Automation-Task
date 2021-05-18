public class Test1 extends BaseClass{

	public static void main(String[] args) throws Exception{

		String test_name = "EXECUTING 1ST TEST CASE: When user fills \"BUY\" amount, \"SELL\" amount box is being emptied and vice versa";
		int number = 300;
		try{
			Test1 t1 = new Test1();
			t1.setup(test_name);
			t1.checkBuySell(45.44);
			tearDown();
		}

		catch(Exception e) {
			log.error(e.getMessage());
			captureScreenShot(driver, "test1_exception");
			log.error("Screenshot taken. Please check test1_exception in screenshot folder.");
			tearDown();
		}
	}
}
