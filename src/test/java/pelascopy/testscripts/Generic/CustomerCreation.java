package pelascopy.testscripts.Generic;

import org.testng.annotations.Test;

import libraries.core.CommonFunctions;
import libraries.core.ExcelReadWrite;
import libraries.core.reporting.TestCaseReport;
import libraries.core.reporting.TestReport;
import pelascopy.reusable.widgets.CommonMethods;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import java.util.concurrent.TimeUnit;

import org.junit.BeforeClass;
import org.openqa.selenium.WebDriver;
import org.testng.Reporter;
import org.testng.annotations.AfterTest;

public class CustomerCreation {
	//taking parameters from CustomerCreate xml
	@Parameters({"reportFolder","param-testcaseid","param-browser", "param-environment", "param-browserBin", "param-browserProfile"})
  //checking login success
	@Test
  public void LoginSuccess(String strreportfolder, String tcID,String strbrowser, String strEnv, String strBrowserBin, String strBrowserProfile)throws InterruptedException, Exception {
		int x= 1;
		CommonFunctions comm_func = new CommonFunctions();
		ThreadLocal<WebDriver> localdriver = new ThreadLocal<WebDriver>();
		TestCaseReport tc_report=new TestCaseReport(strreportfolder, tcID);	
		try{
			
			if (strbrowser.equals("Firefox")) {
			localdriver.set(comm_func.SetBrowser(tc_report, strbrowser,
					System.getProperty("user.dir")+"\\src\\main\\resources\\tools\\Firefox32\\firefox.exe",
					System.getProperty("user.dir")+"\\src\\main\\resources\\tools\\Firefox32\\DefaultProfile"));
			}
			else {
				localdriver.set(comm_func.SetBrowser(tc_report, strbrowser,strBrowserBin,strBrowserProfile));
			
		}
		
		//creating test report
		localdriver.get().manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		TestReport.addtestCaseTitle(tc_report, "<h2>TestCase run for PELAS flow</h2>");
		TestReport.addtestcaseDescription(tc_report, "<h2>This test case is to verify the login to PELAS env</h2>");
		TestReport.addCSSFiles(tc_report, System.getProperty("user.dir") + "\\src\\main\\resources\\tools\\reporting\\testcaseStyle.css" );
		Reporter.setEscapeHtml(false);
		Reporter.log("<a href=\"" + tc_report.folderName + "/index.html\" target=\"content\">Report for PELAS flow</a><br\\>");
		Thread.sleep(3000);

		ExcelReadWrite excel_xlsx=new ExcelReadWrite(tc_report, System.getProperty("user.dir")+ "\\src\\main\\resources\\TestData\\testdata.xlsx");				
		excel_xlsx.setSheetInstance(tc_report, "PELAS_Login");
		
		//calling the function from Common MEthods
		CommonMethods comm_method = new CommonMethods();
		
		comm_method.Launchpage(tc_report, localdriver.get(), strEnv);
		
		comm_method.Login(tc_report, localdriver.get(), excel_xlsx.getCellValue(tc_report, "A4"), excel_xlsx.getCellValue(tc_report, "B4"));
		
		comm_method.createNewAccount(tc_report, localdriver.get(), excel_xlsx);
		
		comm_method.managePP(tc_report, localdriver.get(), excel_xlsx);
		
		comm_method.createNewContact(tc_report, localdriver.get(), excel_xlsx);
		
		comm_method.createOpportunity(tc_report, localdriver.get(), excel_xlsx);
		
		
		//Close all browsers
		localdriver.get().quit();
		localdriver.remove();
		TestReport.addStep(tc_report, "Logged out Successfully and close the browser", "Logged out Successfully and close the browser", "Logged out Successfully and closed the browser", "Logged out Successfully and closed the browser", "");
		
		}
		catch(AssertionError assertError)
		{
			comm_func.PageScreenshot(tc_report, strEnv, localdriver.get(), tc_report.ObjtestCaseFolder.getAbsolutePath() + "\\LoginPageError.png");
			TestReport.addStep(tc_report, tcID, "Inside Exception", "Successful", "Exception inside main function. Error message is " + 
					assertError.getMessage(), "LoginPageError.png");
			x=2;

		}
		catch(Exception e)
		{
			comm_func.PageScreenshot(tc_report, strEnv, localdriver.get(), tc_report.ObjtestCaseFolder.getAbsolutePath() + "\\LoginPageError.png");
			TestReport.addStep(tc_report, tcID, "Inside Exception", "Successful", "Exception inside main function. Error message is " + 
					e.getMessage(), "LoginPageError.png");
			x=2;
		}
		if (x==2 || tc_report.getTestcaseStatus()=="Failed")
		{
			localdriver.get().quit();
			localdriver.remove();
			throw new Exception();
		}
	}
  @BeforeClass
  public void beforeTest() {
  }

  @AfterTest
  public void afterTest() {
  }

}
