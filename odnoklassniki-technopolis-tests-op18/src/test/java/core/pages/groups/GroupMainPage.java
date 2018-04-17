package core.pages.groups;

import core.pages.PageBase;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Представляет главную страницу группы
 */
public class GroupMainPage extends PageBase {

  private final static By GROUP_INFO_PANEL = By.xpath("//*[contains(@data-module, 'GroupInfoPanel')]");
  private final static By TOPIC_CREATE_BUTTON = By.xpath("//*[contains(@class, 'posting-form')]//*[contains(@class, 'input_placeholder')]");
  private final static By TOPIC_POPUP_TEXT_INPUT = By.xpath("//*[contains(@data-module, 'postingForm/mediaText')]");
  private final static By TOPIC_POPUP_CONFIRM = By.xpath("//*[contains(@class, 'posting_submit')]");
  private final static By USER_FEED = By.xpath("//*[contains(@href, '/feed')]");
  private final static By OTHER_SECTIONS = By.xpath("//*[contains(@class, 'Dropdown') and contains(@id, 'otherSections')]");
  private final static By OTHER_SECTIONS_DROPDOWN = By.xpath("//*[@data-module='SimplePopup' and contains(@data-trigger-selector, 'otherSections')]/parent::*");
  private final static By DROPDOWN_SETTINGS = By.xpath("//*[contains(@hrefattrs, 'altGroupSettings')]");

  public GroupMainPage(WebDriver driver) {
    super(driver);
  }

  protected void check() {
    new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOf(driver.findElement(GROUP_INFO_PANEL)));
  }

  /**
   * Нажимает создание новой темы
   */
  public void clickCreateNewTopic(){
    click(TOPIC_CREATE_BUTTON);
    new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfElementLocated(TOPIC_POPUP_CONFIRM));
  }

  /**
   * Печатает текст в попапе создания темы
   * @param text вводимый текст
   */
  public void typeTextInNewTopic(String text){
    type(text, TOPIC_POPUP_TEXT_INPUT);
  }

  /*
  Подтверждает создание темы в попапе
   */
  public void confirmTopicPublication()
  {
    click(TOPIC_POPUP_CONFIRM);
  }

  /**
   * Возвращает на страницу пользователя
   */
  public void returnToUserFeed()
  {
    click(USER_FEED);
  }

  /**
   * Открывает выпадающий список "Ещё"
   */
  public void openOtherSections(){
    click(OTHER_SECTIONS);
  }

  /**
   * Открывает настройки группы в выпадающем списке "Ещё"
   */
  public void openGroupSettings(){
    click(DROPDOWN_SETTINGS);
  }


  /**
   * Проверяет, открыто ли выпадающее меню "Ещё"
   * @return true - открыто, false - нет
   */
  public boolean checkIfOtherSectionsIsOpen(){
    try{
    new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfElementLocated(OTHER_SECTIONS_DROPDOWN));
    }
    catch (TimeoutException te){
      return false;
    }
    return true;
  }

  public boolean isTopicPresentByText(String s){
    return isElementPresent(By.xpath("//*[contains(@class, 'media-text') and contains(text(), \'" + s + "\')]"));
  }
}
