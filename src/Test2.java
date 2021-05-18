public class Test2 extends BaseClass{

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
			tearDown();
		}
	}
}
