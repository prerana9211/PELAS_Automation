package pelascopy.reusable.widgets;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.Reporter;

import libraries.core.CommonFunctions;
import libraries.core.ExcelReadWrite;
import libraries.core.reporting.TestCaseReport;
import libraries.core.reporting.TestReport;
import pelascopy.pageobjects.AccountPageConstants;
import pelascopy.pageobjects.ContactPageConstant;
import pelascopy.pageobjects.LoginPageConstants;
import pelascopy.pageobjects.OpportunityPageConstant;


public class CommonMethods extends CommonFunctions{
	CommonFunctions comm_func = new CommonFunctions();
	String accName="";
	public void Launchpage(TestCaseReport tc_repo,WebDriver wd,String strenv)throws Exception{
	
			Properties prop=loadPropertyFile(tc_repo, System.getProperty("user.dir")+"\\src\\main\\resources\\propertyFiles\\"+strenv+".properties");
			wd.get(prop.getProperty("appUrl"));	
			TestReport.addStep(tc_repo, "Launching Application",
					"Open App Url", "App URL opened", "App URL opened", "");
			Reporter.log("Application has opened");
			Thread.sleep(1000);
			comm_func.maximizeWindow(tc_repo, wd);
			Thread.sleep(1000);
		
		}
	public void Login(TestCaseReport tc_repo,WebDriver wd,String username, String password) throws Exception{
		try{
			comm_func.sendValueToTextBox(tc_repo, wd, LoginPageConstants.Username, "username", username);
			comm_func.sendValueToTextBox(tc_repo, wd, LoginPageConstants.Password, "password", password);
			Thread.sleep(2000);
			comm_func.click(tc_repo, wd, LoginPageConstants.Login_Button, "Login_button");
			Thread.sleep(30000);
		}
		catch (Exception e){
			TestReport.addStep(tc_repo, "Click on Login Button", "Click on Login Button", "LOGIN SUCCESSFULL", "LOGIN NOT SUCCESSFULL", "");
			e.printStackTrace();
			Assert.fail();
		}
	}
	public void createNewAccount (TestCaseReport tc_repo, WebDriver wd, ExcelReadWrite xls)throws InterruptedException, IOException{
		try {
			xls.setSheetInstance(tc_repo, "PELAS_Account");
			accName=xls.getCellValue(tc_repo, "B3");
			comm_func.click(tc_repo, wd, AccountPageConstants.Acctab, "Acctab");
			Thread.sleep(3000);
			comm_func.click(tc_repo, wd, AccountPageConstants.AccNewBtn, "AccNewBtn");
			comm_func.selectFromDropdown(tc_repo, wd, AccountPageConstants.Acc_type, "Account_type", "selectByVisibleText", xls.getCellValue(tc_repo, "A3"));
			comm_func.click(tc_repo, wd, AccountPageConstants.Continue_btn, "Continue_btn");
			Thread.sleep(3000);
			comm_func.sendValueToTextBox(tc_repo, wd, AccountPageConstants.Account_Name, "Account_name", xls.getCellValue(tc_repo, "B3"));
			comm_func.sendValueToTextBox(tc_repo, wd, AccountPageConstants.Comapny_legal_Name, "Company", xls.getCellValue(tc_repo, "C3"));
			
			String val = xls.getCellValue(tc_repo, "D3");
			wd.findElement(By.id("00N2400000FBiLw")).sendKeys(Keys.TAB,val);
			Thread.sleep(1000);
			//comm_func.sendValueToTextBox(tc_repo, wd, AccountPageConstants.Description, "Description", xls.getCellValue(tc_repo, "D3"));
			
			comm_func.sendValueToTextBox(tc_repo, wd, AccountPageConstants.Master_agreement_no, "MAN", xls.getCellValue(tc_repo, "E3"));
			comm_func.click(tc_repo, wd, AccountPageConstants.save_btn, "Save");
			Thread.sleep(4000);
			
		} 
		
		catch (Exception e) {
			// TODO Auto-generated catch block
			TestReport.addStep(tc_repo, "Create New Account", "Create New Account", "account SUCCESSFULL", "Account NOT SUCCESSFULL", "");
			e.printStackTrace();
			Assert.fail();
		}
	}
	
	public void managePP(TestCaseReport tc_repo, WebDriver wd, ExcelReadWrite xls) throws IOException{
		try{
			String currentUrlw =  wd.getCurrentUrl();
			comm_func.click(tc_repo, wd, AccountPageConstants.Manage_PP, "Manage_PP");
			Thread.sleep(2000);
			wd.findElement(By.linkText("Enable As Partner")).click();
			Thread.sleep(15000);
			// creating object of robot class 
            Robot robot = new Robot();
               Thread.sleep(3000);
          // handle the keypress event on default selected option
            robot.keyPress(KeyEvent.VK_ENTER);
            robot.keyRelease(KeyEvent.VK_ENTER);
            System.out.println("finding element");
            Thread.sleep(5000);
            System.out.println("waiting for click");
            comm_func.click(tc_repo, wd, AccountPageConstants.Contact_link, "Contact_link");
            Thread.sleep(1000);
            comm_func.click(tc_repo, wd, AccountPageConstants.New_contact_btn, "new_contact_btn");
            System.out.println("nahi hua");
			
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			TestReport.addStep(tc_repo, "Manage Partner Portal", "Manage Partner Portal", "PP SUCCESSFULL", "PP NOT SUCCESSFULL", "");
			e.printStackTrace();
			Assert.fail();
		}
	}

	public void createNewContact (TestCaseReport tc_repo, WebDriver wd, ExcelReadWrite xls) throws IOException{
		try{
			xls.setSheetInstance(tc_repo, "PELAS_Contact");
			comm_func.selectFromDropdown(tc_repo, wd, ContactPageConstant.Contact_type, "Contact_type", "selectByVisibleText", xls.getCellValue(tc_repo, "A3"));
			comm_func.click(tc_repo, wd, ContactPageConstant.Continue_btn, "Continue_btn");
			Thread.sleep(2000);
			comm_func.sendValueToTextBox(tc_repo, wd, ContactPageConstant.First_Name, "First_name", xls.getCellValue(tc_repo, "B3"));
			comm_func.sendValueToTextBox(tc_repo, wd, ContactPageConstant.Last_Name, "Last_name", xls.getCellValue(tc_repo, "C3"));
			comm_func.sendValueToTextBox(tc_repo, wd, ContactPageConstant.Email, "Email",xls.getCellValue(tc_repo, "D3"));
			comm_func.sendValueToTextBox(tc_repo, wd, ContactPageConstant.Phone, "Phone",xls.getCellValue(tc_repo, "E3"));
			comm_func.click(tc_repo, wd, ContactPageConstant.save_btn, "save_btn");
			Thread.sleep(2000);
			//click on link
			wd.findElement(By.linkText(accName)).click();
			Thread.sleep(4000);
			comm_func.click(tc_repo, wd, AccountPageConstants.New_oppty, "New_oppty");
		}
		
		catch (Exception e) {
			// TODO Auto-generated catch block
			TestReport.addStep(tc_repo, "Create New Account", "Create New Account", "account SUCCESSFULL", "Account NOT SUCCESSFULL", "");
			e.printStackTrace();
			Assert.fail();
	}
}
	public void createOpportunity (TestCaseReport tc_repo, WebDriver wd, ExcelReadWrite xls) throws IOException{
		try{
			xls.setSheetInstance(tc_repo, "Create_Opp");
			comm_func.sendValueToTextBox(tc_repo, wd, OpportunityPageConstant.Oppty_Name, "Oppty_Name", xls.getCellValue(tc_repo, "A3"));
			
			//selecting date from calendar
			comm_func.click(tc_repo, wd, OpportunityPageConstant.Close_Date, "Close_Date");
			WebElement cal=wd.findElement(By.id("datePickerCalendar"));
			List<WebElement> columns=cal.findElements(By.tagName("td"));  
            //comparing the text of cell with today's date and clicking it.
            for (WebElement cell : columns)
            {
               if (cell.getText().equals(xls.getCellValue(tc_repo, "B3")))
               {
                  cell.click();
                  break;
               }
            }
			
			//
			Thread.sleep(2000);
			comm_func.click(tc_repo, wd, OpportunityPageConstant.save_btn, "save_btn");
		}
		catch (Exception e) {

			TestReport.addStep(tc_repo, "Create opportunity", "Create opportunity", "Opportunity SUCCESSFULL", "Opportunity NOT SUCCESSFULL", "");
			e.printStackTrace();
			Assert.fail();
	}
}
}



