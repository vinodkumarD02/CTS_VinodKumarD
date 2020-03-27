package TestNgClasses;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import BasicUtilities.Browsers;
import BasicUtilities.ScreenShotss;
import ExcelDataUtils.ReadExcel;
import POM.CartPage;
import POM.CasualDressesPage;
import POM.EveningDressesPage;
import POM.Home;
import POM.LoginAndRegister;
import POM.MyAccountPage;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.openqa.selenium.WebDriver;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class Chrome {
	WebDriver dr;
	Browsers br;Home hm; LoginAndRegister lg; ReadExcel re;
	MyAccountPage ma;CasualDressesPage cdp; CartPage cp; EveningDressesPage edp;ScreenShotss ss;
	
	@BeforeMethod
	public void BM() {
		br = new Browsers();
		dr = br.launchBrowser(br.Chrome,"http://automationpractice.com/index.php");
		ss = new ScreenShotss(dr);
		hm = new Home(dr);
		hm.ClickOnLoginLink();

	}
	
  @Test(dataProvider = "InvalidData")
  public void InvalidLogin(String username, String password, String Exp_res) {
	  
	  lg = new LoginAndRegister(dr);
	  lg.DoLogin(username, password);
	  String err =lg.InvalidEmailError();
	  ss.ScreenShott("error_msg.png");
	  SoftAssert sa = new SoftAssert();
	  sa.assertEquals(err, Exp_res);
	  sa.assertAll();
	  dr.quit();
  }
  @DataProvider
  public String[][] InvalidData() {
	  re = new ReadExcel();
	  re.get_data("Login Invalid Credentials",3,3);
	  return re.testdata;
  }
  
  @Test(dataProvider = "validData")
  public void validLogin(String username, String password, String Exp_res) {
	  
	  lg = new LoginAndRegister(dr);
	  lg.DoLogin(username, password);
	  ma= new MyAccountPage(dr);
	  String uname =ma.VerifyLogin();
	  ss.ScreenShott("AccountName.png");
	  SoftAssert sa = new SoftAssert();
	  sa.assertEquals(uname, Exp_res);
	  sa.assertAll();
	  dr.quit();
  }
  @DataProvider
  public String[][] validData() {
	  re = new ReadExcel();
	  re.get_data("Valid Credentials",3,3);
	  return re.testdata;
  }

  @Test(dataProvider = "AddToCart")
  public void AddToCart(String username, String password) {
  
	  lg = new LoginAndRegister(dr);
	  lg.DoLogin(username, password);
	  ma= new MyAccountPage(dr);
	  ma.CasualDresses();
	  cdp =new CasualDressesPage(dr);
	  cdp.CasualDressesAddtoCart();
	  float price = cdp.VerifyPrice();
	  System.out.println(price);
	  cdp.Checkout();
	  cp =new CartPage(dr);
	  int quantity= cp.Verifyquantity(1);
	  System.out.println(quantity);
	  float Excpected_total = (price*quantity)+2;
	  float Actual_total = cp.VerifyTotalPrice();
	  ss.ScreenShott("Cart.png");
	  SoftAssert sa = new SoftAssert();
	  sa.assertEquals(Actual_total, Excpected_total);
	  sa.assertAll(); 
	  dr.quit();
  }
  @DataProvider
  public String[][] AddToCart() {
	  re = new ReadExcel();
	  re.get_data("Valid Credentials",1,2);
	  return re.testdata;
  }
 
  @Test(dataProvider = "LoginData")
  public void ProductRemoval(String username, String password) {

  	lg = new LoginAndRegister(dr);
  	lg.DoLogin(username, password);
  	ma= new MyAccountPage(dr);
  	ma.CasualDresses();
  	cdp =new CasualDressesPage(dr);
	cdp.CasualDressesAddtoCart();
	 float price1 = cdp.VerifyPrice();
	  System.out.println(price1);
	  cdp.Checkout();
	  cp =new CartPage(dr);
	  int quantity1= cp.Verifyquantity(1);
	  System.out.println(quantity1);
	  cp.ContinueShopping();
	  edp = new EveningDressesPage(dr);
	  ma.EveningDresses();
	  edp.EveningDressesAddtoCart();
	  float price2= edp.VerifyPrice();
	  edp.Checkout();
	  int quantity2= cp.Verifyquantity(2);
	  float Excpected_total = (price1*quantity1)+(price2*quantity2)+2;
	  BigDecimal bd = new BigDecimal(Excpected_total).setScale(2, RoundingMode.HALF_UP);
	  Excpected_total = bd.floatValue();
	  float Actual_total = cp.VerifyTotalPrice();
	  
	boolean b=cp.RemoveProduct();
	ss.ScreenShott("Removed.png");
	SoftAssert sa = new SoftAssert();
	sa.assertEquals(Actual_total, Excpected_total);
	sa.assertFalse(b);	  
	sa.assertAll();
	dr.quit();
  }
  @DataProvider
  public String[][] LoginData() {
	  re = new ReadExcel();
	  re.get_data("Valid Credentials",1,2);
	  return re.testdata;
  }
  
  
  
}
