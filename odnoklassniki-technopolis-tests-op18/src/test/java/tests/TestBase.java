package tests;

import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public abstract class TestBase {

  protected String baseUrl;
  protected WebDriver driver;
  private List<WebDriver> drivers = new ArrayList<>();
  private StringBuffer verificationErrors = new StringBuffer();

  @Before
  public void setUp() throws Exception {
    init();
  }

  @After
  public void tearDown() throws Exception {
    stopAll();
  }

  public void init() {
    driver = new ChromeDriver();
    baseUrl = "https://ok.ru/";
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    driver.get(baseUrl);
    drivers.add(driver);
  }

  public void stop() {
    driver.quit();
    String verificationErrorString = verificationErrors.toString();
    if (!"".equals(verificationErrorString)) {
      fail(verificationErrorString);
    }
  }

  /**
   * Останавливает все созданнные драйверы
   */
  public void stopAll(){
    for (WebDriver driver: drivers
    ) {
      this.driver = driver;
      stop();
    }
  }

  public void switchDriver(WebDriver driver){
    if (!drivers.contains(driver))
      fail("Попытка остановить не инициализированный драйвер");
    else
      this.driver = driver;
  }
}
