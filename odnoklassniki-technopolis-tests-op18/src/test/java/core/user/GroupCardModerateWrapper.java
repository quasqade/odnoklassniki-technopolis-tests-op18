package core.user;

import core.groups.GroupHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

/**
 * Оборачивает карточку группы в разделе Модерируемых групп
 */
public class GroupCardModerateWrapper {

  private static final By GROUP_CARD_MODERATE_NAME = By
      .xpath(".//div[@data-l='groupCard,null']//div[@class='caption']//a");

  public WebElement getWrappedElement() {
    return wrappedElement;
  }

  private WebElement wrappedElement;

  public GroupCardModerateWrapper(WebElement wrappedElement) {
    this.wrappedElement = wrappedElement;
  }

  public String getName() {
    WebElement groupCard = wrappedElement.findElement(GROUP_CARD_MODERATE_NAME);
    String name = null;
    Assert.assertNull(name = groupCard.getText(), "Can not get group's name");
    return name;
  }

  public String getGroupId() {
    //WebElement groupCard = wrappedElement.findElement(GROUP_CARD_MODERATE_NAME);
    String href = wrappedElement.getAttribute("href");
    return GroupHelper.getIDFromLink(href);
  }
}