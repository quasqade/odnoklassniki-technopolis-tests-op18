package core;

import com.google.common.base.Preconditions;
import core.user.UserMainPage;
import java.util.concurrent.TimeUnit;
import org.junit.Assert;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public abstract class PageBase {

  private final static By USER_PAGE_LINK = By.xpath("//*[contains(@href, '/feed')]");
  protected final WebDriver driver;
  private boolean acceptNextAlert = true;

  protected PageBase(WebDriver driver) {
    this.driver = driver;
    check();
  }

  protected abstract void check();

  protected void click(By xpath) {
    if (!explicitWait(ExpectedConditions.elementToBeClickable(xpath), 10, 500)) {
      Assert.fail("Элемент не кликабелен: " + xpath);
    }
    driver.findElement(xpath).click();
  }

  protected void click(WebElement element) {
    if (!explicitWait(ExpectedConditions.elementToBeClickable(element), 10, 500)) {
      Assert.fail("Элемент не кликабелен: " + element);
    }
    element.click();
  }

  protected void type(String name, By field_name) {
    driver.findElement(field_name).clear();
    driver.findElement(field_name).sendKeys(name);

  }

  protected void typeSlowly(String name, By field_name) {
    driver.findElement(field_name).clear();
    for (Character c : name.toCharArray()
        ) {
      try {
        Thread.sleep(10);
        driver.findElement(field_name).sendKeys(c + "");
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }

  }

  protected void scrollToElement(By locator) {
    WebElement element = driver.findElement(locator);
    ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(false);", element);
    try {
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  public void refreshWebPage() {
    driver.navigate().refresh();
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
  protected boolean explicitWait(final ExpectedCondition<?> condition, long maxCheckTimeInSeconds,
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

  /**
   * Возвращает на страницу пользователя
   */
  public UserMainPage returnToUserPage() {
    click(USER_PAGE_LINK);
    return new UserMainPage(driver);
  }

  /**
   * Ожидает видимости элемента по локатору, падает если не дожидается
   *
   * @param locator локатор искомого элемента
   * @param errorMessage сообщение об ошибке, если элемент не найдётся
   */
  protected void waitForVisibility(By locator, String errorMessage) {
    if (!explicitWait(ExpectedConditions.visibilityOfElementLocated(locator), 2, 1000)) {
      Assert.fail(errorMessage);
    }
  }
}

