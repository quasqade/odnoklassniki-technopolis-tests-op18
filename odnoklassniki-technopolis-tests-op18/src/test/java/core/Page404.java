package core;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;


/**
 * Представляет страницу 404
 */
public class Page404 extends PageBase {

  private static final By IMAGE = By.xpath("//*[@class='p404_logo']");

  public Page404(WebDriver driver) {
    super(driver);
  }

  @Override
  protected void check() {
    waitForVisibility(IMAGE, "Страница 404 не загрузилась");
  }
}
