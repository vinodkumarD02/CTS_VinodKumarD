package POM;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import BasicUtilities.JustWait;
import BasicUtilities.ScreenShotss;
import BasicUtilities.logger;

public class MyAccountPage {
	
	WebDriver dr;
	JustWait wt; logger log; ScreenShotss ss; 
	
	public MyAccountPage(WebDriver dr)
	{
		this.dr = dr;
		wt = new JustWait(dr);
		log = new logger(dr);
		ss = new ScreenShotss(dr);
	}

	
	public String VerifyLogin()
	{
		By by_ele = By.xpath("//a[@class='account']//span");
		WebElement we = wt.WaitForElement(by_ele, 10);
		we = wt.elementToBeClickable(by_ele, 10);
		return we.getText();
	}
	
	public void CasualDresses()
	{
		Actions aa = new Actions(dr);
		By by_ele = By.xpath("//a[@title='Women']");
		WebElement we = wt.WaitForElement(by_ele, 10);
		aa.moveToElement(we).build().perform();
		by_ele = By.xpath("//a[@title='Casual Dresses']");
		we = wt.WaitForElement(by_ele, 10);
		we = wt.elementToBeClickable(by_ele, 10);
		we.click();
	}
	
	public void EveningDresses()
	{
		Actions aa = new Actions(dr);
		By by_ele = By.xpath("//a[@title='Women']");
		WebElement we = wt.WaitForElement(by_ele, 10);
		aa.moveToElement(we).build().perform();
		by_ele = By.xpath("//a[@title='Evening Dresses']");
		we = wt.WaitForElement(by_ele, 10);
		we = wt.elementToBeClickable(by_ele, 10);
		we.click();
	}
}
