package core.pages;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public abstract class PageBase {

  protected WebDriver driver;
  private boolean acceptNextAlert = true;

  public PageBase(WebDriver driver) {
    this.driver = driver;
    check();
  }

  protected abstract void check();

  public void click(By xpath) {
    new WebDriverWait(driver, 10).until(ExpectedConditions.elementToBeClickable(xpath));
    driver.findElement(xpath).click();
  }

  protected void type(String name, By field_name) {
    driver.findElement(field_name).clear();
    driver.findElement(field_name).sendKeys(name);
  }

  public boolean isElementPresent(By by) {
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
