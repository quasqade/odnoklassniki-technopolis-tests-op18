package core.groups.links;

import core.PageBase;
import core.groups.main.GroupMainPage;
import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Представляет страницу Ссылки в группе
 */
public class GroupLinksPage extends PageBase {

  private static final By MIDDLE_BLOCK = By.xpath("//*[contains(@id, 'GroupsToGroupPage')]");
  private static final By ADD_LINK = By.xpath("//*[contains(@href, 'InviteGroupsOuter')]");
  private static final By GROUP_CARD = By.xpath("//*[contains(@data-l, 'groupCard')]");
  private static final By GROUP_LINK = By.xpath("//*[contains(@class, 'compact-profile') and contains(@hrefattrs, 'altGroupMain')]");

  private List<GroupLinkWrapper> groupLinkWrappers = new ArrayList<>();

  public GroupLinksPage(WebDriver driver) {
    super(driver);
    collectGroups();
  }

  @Override
  protected void check() {
    waitForVisibility(MIDDLE_BLOCK, "Не удалось загрузить страницу Ссылки");
  }

  /**
   * Нажимает добавить ссылку
   * @return страница оверлея добавления ссылки
   */
  public AddLinkOverlay addLink(){
    click(ADD_LINK);
    return new AddLinkOverlay(driver);
  }

  /**
   * Собирает список карточек групп на странице
   * @return true если нашлась хоть 1 карточка
   */
  public boolean collectGroups(){
    groupLinkWrappers = new ArrayList<>();
    List<WebElement> cardElements = driver.findElements(GROUP_CARD);
    for (WebElement cardElement: cardElements
    ) {
      groupLinkWrappers.add(new GroupLinkWrapper(cardElement));
    }
    return (groupLinkWrappers.size()>0);
  }

  /**
   * Ищет карточку группы с указанным ID в собранном списке врапперов
   * @param groupId идентификатор группы
   * @return наличие в списке
   */
  public boolean isGroupCardPresent(String groupId){
    for (GroupLinkWrapper wrapper: groupLinkWrappers
    ) {
      if (wrapper.getGroupId().equals(groupId))
        return true;
    }
    return false;
  }

  public GroupMainPage returnToGroupPage(){
    click(GROUP_LINK);
    return new GroupMainPage(driver);
  }
}
