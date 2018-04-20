package core.pages.groups.links;

import core.pages.PageBase;
import core.wrappers.links.AddLinkWrapper;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Представляет оверлей добавления ссылок в разделе Ссылки
 */
public class AddLinkOverlay extends PageBase{

  private static final By CARDS_TABLE = By.xpath("//*[@class='cardsTable']");
  private static final By GROUP_CARD = By.xpath("//*[@class='ifCard']");
  private static final By CONFIRM = By.xpath("//*[@name='button_invite']");
  private List<AddLinkWrapper> groupCards = new ArrayList<>();

  protected AddLinkOverlay(WebDriver driver) {
    super(driver);
    Assert.assertTrue("Не удалось собрать карточки групп в оверлее добавления ссылок",collectGroups());
  }

  @Override
  protected void check() {
    waitForVisibility(CARDS_TABLE, "Не удалось загрузить оверлей добавления ссылок");
  }

  /**
   * Собирает карточки групп в оверлее, возвращает true если карточек больше 0
   * @return успешность сбора карточек
   */
  public boolean collectGroups(){
    List<WebElement> cardElements = driver.findElements(GROUP_CARD);
    if (cardElements.size()==0)
      return false;
    groupCards = new ArrayList<>();
    for (WebElement cardElement: cardElements
    ) {
      groupCards.add(new AddLinkWrapper(cardElement));
    }
    return true;
  }

  /**
   * Выбирает или отменяет добавление ссылки для группы по айди
   * @param groupId идентификатор группы
   * @return true - группа выбрана, false - выделение отменено
   */
  public boolean selectByGroupId(String groupId){
    return selectByWrapper(getWrapperByGroupId(groupId));
  }

  /**
   * Возвращает состояние выделения группы
   * @param groupId идентификатор группы
   * @return true - выбрана, false - не выбрана
   */
  public boolean getSelectionStateByGroupId(String groupId){
    return isWrapperSelected(getWrapperByGroupId(groupId));
  }

  /**
   * Находит в списке собранных нужный враппер по айди
   * @param groupId идентификатор группы
   * @return враппер группы или падает если не находит
   */
  private AddLinkWrapper getWrapperByGroupId(String groupId){
    for (AddLinkWrapper wrapper: groupCards
        ) {
      if (wrapper.getGroupId().equals(groupId)){
        return wrapper;
      }
    }
    Assert.fail("Не удалось найти обертку по идентификатору группы");
    return null;
  }

  /**
   * Меняет состояние выделения по врапперу
   * @param wrapper враппер карточки
   * @return true - карточка выделена, false - выделение снято
   */
  private boolean selectByWrapper(AddLinkWrapper wrapper){
    click(wrapper.getClickableHook());
    return isWrapperSelected(wrapper);
  }

  /**
   * Возвращает состояние выделения по врапперу
   * @param wrapper враппер карточки
   * @return true - выделена, false - нет
   */
  private boolean isWrapperSelected(AddLinkWrapper wrapper){
    return wrapper.isSelected();
  }

  public GroupLinksPage confirmAndClose(){
    click(CONFIRM);
    return new GroupLinksPage(driver);
  }

}
