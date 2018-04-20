package core.wrappers.links;

import core.helpers.GroupHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * Оборачивает группы в панели Ссылки на группы на странице группы
 */
public class MainGroupLinkWrapper {

  private static final By GROUP_LINK = By.xpath("//*[contains(@class, 'dblock')]");

  private WebElement wrappedElement;

  public MainGroupLinkWrapper(WebElement wrappedElement){
    this.wrappedElement = wrappedElement;
  }

  /**
   * Возвращает ID группы
   * @return айди
   */
  public String getGroupId(){
    WebElement link = wrappedElement.findElement(GROUP_LINK);
    return GroupHelper.getIDFromLink(link.getAttribute("href"));
  }

  public WebElement getClickableLink(){
    return wrappedElement.findElement(GROUP_LINK);
  }

}
