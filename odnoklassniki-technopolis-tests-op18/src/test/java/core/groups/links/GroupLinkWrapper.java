package core.groups.links;

import core.groups.GroupHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * Оборачивает карточку группы в разделе Ссылки
 */
public class GroupLinkWrapper {

  private static final By GROUP_LINK = By.xpath("//a[@data-l='t,visit']");

  private WebElement wrappedElement;

  public GroupLinkWrapper(WebElement wrappedElement){
    this.wrappedElement = wrappedElement;
  }


  /**
   * Возвращает идентификатор обернутой группы
   * @return строка с айди
   */
  public String getGroupId(){
    WebElement groupLink = wrappedElement.findElement(GROUP_LINK);
    String href = groupLink.getAttribute("href");
    return GroupHelper.getIDFromLink(href);
  }

}
