package core.groups;

import core.PageBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Представляет страницу товаров
 */
public class MenuOfGoodsGroupPage extends PageBase {

  private static final By IMAGE = By.xpath(".//*[@id='hook_Block_AltGroupAdverts']");

  public MenuOfGoodsGroupPage(WebDriver driver) {
    super(driver);
  }

  @Override
  protected void check() {
    waitForVisibility(IMAGE, "Страница товаров не прогрузилась");
  }
}

