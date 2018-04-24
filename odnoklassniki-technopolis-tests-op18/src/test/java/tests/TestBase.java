package tests;

import static org.junit.Assert.fail;

import core.groups.GoToGroupPromise;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
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
  private String rememberedUrl;

  protected static String getRandomId() {
    return UUID.randomUUID().toString().replaceAll("-", "");
  }

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
  public void stopAll() {
    for (WebDriver driver : drivers
        ) {
      this.driver = driver;
      stop();
    }
  }

  /**
   * Переключает текущий драйвер на другой драйвер (который сперва должен быть инициализирован
   * вызовом init()
   *
   * @param driver драйвер, на который нужно перейти
   */
  public void switchDriver(WebDriver driver) {
    if (!drivers.contains(driver)) {
      fail("Попытка переключиться на не инициализированный драйвер");
    } else {
      this.driver = driver;
    }
  }

  /**
   * Переходит на страницу группы с указанным ID
   */
  public GoToGroupPromise goToGroup(String groupId) {
    String groupUrl = "https://ok.ru/group/" + groupId;
    driver.navigate().to(groupUrl);
    return new GoToGroupPromise(driver);
  }

  /**
   * Обновляет страницу текущего драйвера
   */
  public void refresh() {
    driver.navigate().refresh();
  }

}
