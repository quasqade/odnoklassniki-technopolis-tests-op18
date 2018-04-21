package core.groups.links;


import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * Оборачивает карточку группы в оверлее добавления ссылок
 */
public class AddLinkWrapper {

  private static final By HOVER_HOOK = By.xpath("//*[contains(@id, 'hook_Invite')]");
  private static final By SELECTION = By.xpath("//*[@name='selid']");

  private WebElement wrappedElement;

  public AddLinkWrapper(WebElement wrappedElement){
    this.wrappedElement=wrappedElement;
  }

  /**
   * Возвращает кликабельный элемент, клик на который меняет состояние выбора
   * @return элемент
   */
  public WebElement getClickableHook(){
    return wrappedElement.findElement(HOVER_HOOK);
  }

  /**
   * Возвращает ID группы
   * @return строка с айди
   */
  public String getGroupId(){
    WebElement selection = wrappedElement.findElement(SELECTION);
    return selection.getAttribute("value");
  }

  /**
   * Возвращает состояние выбора группы
   * @return true - выбрана, false - нет
   */
  public boolean isSelected(){
    WebElement hook = wrappedElement.findElement(HOVER_HOOK);
    return hook.getAttribute("class").contains("ifSelected");
  }


}
