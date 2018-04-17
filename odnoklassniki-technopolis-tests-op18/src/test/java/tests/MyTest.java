package tests;

import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

public class MyTest {
  private WebDriver driver;
  private String baseUrl;
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();

  @Before
  public void setUp() throws Exception {
    driver = new ChromeDriver();
    baseUrl = "https://ok.ru/";
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
  }

  @Test
  public void testMy() throws Exception {
    driver.get(baseUrl + "/dk?st.cmd=anonymMain&st.layer.cmd=PopLayerClose");
    driver.findElement(By.id("field_email")).clear();
    driver.findElement(By.id("field_email")).sendKeys("79219213098");
    driver.findElement(By.id("field_email")).clear();
    driver.findElement(By.id("field_email")).sendKeys("79219213098");
    driver.findElement(By.id("field_password")).clear();
    driver.findElement(By.id("field_password")).sendKeys("59pckgkC259C");
    driver.findElement(By.id("field_password")).clear();
    driver.findElement(By.id("field_password")).sendKeys("59pckgkC259C");
    driver.findElement(By.cssSelector("div.form-actions > div > input.button-pro.__wide")).click();
    driver.findElement(By.xpath(".//*[contains(@hrefattrs,'NavMenu_User_AltGroups')]")).click();
    driver.findElement(By.xpath(".//*[contains(@href,'st.layer.cmd=PopLayerCreateAltGroup')]")).click();
    driver.findElement(By.xpath(".//*[contains(@data-l ,'PAGE')]")).click();
    driver.findElement(By.id("field_name")).clear();
    driver.findElement(By.id("field_name")).sendKeys("MyTest");
    //new Select(driver.findElement(By.id("field_pageMixedCategory"))).selectByVisibleText("Компьютер и интернет");
    driver.findElement(By.id("hook_FormButton_button_create")).click();
    driver.findElement(By.cssSelector("span.mctc_navMenuDropdownSecLabelText")).click();
    driver.findElement(By.linkText("Ссылки")).click();
    driver.findElement(By.linkText("Добавить")).click();
    driver.findElement(By.cssSelector("div.avatar.user > img")).click();
    driver.findElement(By.id("hook_FormButton_button_invite")).click();
    driver.findElement(By.id("hook_FormButton_button_invite")).click();
  }

  @After
  public void tearDown() throws Exception {
    driver.quit();
    String verificationErrorString = verificationErrors.toString();
    if (!"".equals(verificationErrorString)) {
      fail(verificationErrorString);
    }
  }

  private boolean isElementPresent(By by) {
    try {
      driver.findElement(by);
      return true;
    } catch (NoSuchElementException e) {
      return false;
    }
  }

  private boolean isAlertPresent() {
    try {
      driver.switchTo().alert();
      return true;
    } catch (NoAlertPresentException e) {
      return false;
    }
  }

  private String closeAlertAndGetItsText() {
    try {
      Alert alert = driver.switchTo().alert();
      String alertText = alert.getText();
      if (acceptNextAlert) {
        alert.accept();
      } else {
        alert.dismiss();
      }
      return alertText;
    } finally {
      acceptNextAlert = true;
    }
  }
}
