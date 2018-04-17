package core.pages;

import com.google.common.base.Preconditions;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
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


  /**
   * Ожидание
   */
  public boolean explicitWait(final ExpectedCondition<?> condition, long maxCheckTimeInSeconds,
      long millisecondsBetweenChecks) {
    Preconditions.checkNotNull(condition, "Condition must be not null");
    Preconditions.checkArgument(TimeUnit.MINUTES.toSeconds(3) > maxCheckTimeInSeconds,
        "Max check time in seconds should be less than 3 minutes");
    checkConditionTimeouts(maxCheckTimeInSeconds, millisecondsBetweenChecks);
    try {
      // сбрасываем ожидания в 0
      driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
      // создаем эксплицитное ожидание
      WebDriverWait explicitWait = new WebDriverWait(driver, maxCheckTimeInSeconds,
          millisecondsBetweenChecks);
      // проверяем его
      explicitWait.until(condition);
      return true;
    } catch (Exception e) {
      return false;
    } finally {
      // при любом результате восстанавливаем значение имплицитного ожидания по умолчанию
      if (driver != null) {
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
      } else {
        throw new IllegalArgumentException("Driver shouldnt be null");
      }
    }
  }

  /**
   * Проверяет таймаут провекри условия и интервал между проверками: таймаут должен быть больше
   * нуля, интервал проверки должен быть больше нуля интервал между проверками умноженный на 1000
   * должен быть меньше таймаута проверки
   *
   * @param maxCheckTimeInSeconds максимальное время проверки в секундах
   * @param millisecondsBetweenChecks интервал между проверками в милисекундах
   */
  private void checkConditionTimeouts(long maxCheckTimeInSeconds, long millisecondsBetweenChecks) {
    Preconditions
        .checkState(maxCheckTimeInSeconds > 0, "maximum check time in seconds must be not 0");
    Preconditions.checkState(millisecondsBetweenChecks > 0,
        "milliseconds count between checks must be not 0");
    Preconditions.checkState(millisecondsBetweenChecks < (maxCheckTimeInSeconds * 1000),
        "Millis between checks must be less than max seconds to wait");
  }

  public void moveToElement(WebElement webElement) {
    new Actions(driver).moveToElement(webElement).build().perform();
  }

  protected boolean isElementVisible(By by) {
    try {
      return driver.findElement(by).isDisplayed();
    } catch (NoSuchElementException e) {
      return false;
    }
  }
}

