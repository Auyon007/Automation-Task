public class Test4 extends BaseClass{

	public static void main(String[] args) throws Exception {
		
		String test_name = "EXECUTING 4th TEST CASE: Check paysera amount is showing the correct value in the field after applying the filter option";

		try{
			Test4 t4 = new Test4();
			t4.setup(test_name);	
			t4.filter("BUY", 44);
			t4.filter("sell", 555.555);
			tearDown();
		}

		catch(Exception e) {
			log.error(e.getMessage());
			captureScreenShot(driver, "test4_exception");
			log.error("Screenshot taken. Please check test4_exception in screenshot folder.");
			tearDown();
		}
	}
}
