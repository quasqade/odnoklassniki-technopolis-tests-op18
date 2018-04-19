package core.pages.groups.main;

import core.pages.PageBase;
import core.pages.groups.GroupTopicsPage;
import core.pages.groups.settings.main.GroupSettingsPage;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Представляет главную страницу группы
 */
public class GroupMainPage extends PageBase {

  private final static By GROUP_INFO_PANEL = By
      .xpath("//*[contains(@data-module, 'GroupInfoPanel')]");
  private final static By TOPIC_CREATE_BUTTON = By
      .xpath("//*[contains(@class, 'posting-form')]//*[contains(@class, 'input_placeholder')]");
  private final static By TOPIC_POPUP_TEXT_INPUT = By
      .xpath("//*[contains(@data-module, 'postingForm/mediaText')]");
  private final static By TOPIC_POPUP_CONFIRM = By.xpath("//*[contains(@class, 'posting_submit')]");
  private final static By OTHER_SECTIONS = By
      .xpath("//*[contains(@class, 'Dropdown') and contains(@id, 'otherSections')]");
  private final static By OTHER_SECTIONS_DROPDOWN = By.xpath(
      "//*[@data-module='SimplePopup' and contains(@data-trigger-selector, 'otherSections')]/parent::*");
  private final static By DROPDOWN_SETTINGS = By
      .xpath(".//a[contains(@hrefattrs,'AltGroupTopCardButtonsEdit')]");
  private final static By JOIN_BUTTON = By.xpath("//*[contains(@href, 'GroupJoin')]");
  private final static By GROUP_CARD_PENDING_JOIN = By
      .xpath("//*[contains(@class, 'dropdown') and contains(@class, 'disabled')]");
  private final static By INVITATIONS_PANEL = By
      .xpath("//*[contains(@id, 'PossibleFriendsPanel')]");
  private final static By JOIN_REQUESTS_COUNTER = By.xpath("//*[@id='joinRequestsCount']");
  private final static By ACCEPT_JOIN_REQUEST = By
      .xpath("//span[contains(@id, 'GroupJoinRequests')]");
  private final static By MEMBER_COUNTER = By.xpath("//*[@id='membersCountEl']");
  private final static By MAIN_DROPDOWN = By.xpath("//*[contains(@class, 'primary-dropdown')]");
  private final static By MEMBER_STATUS = By
      .xpath("//*[contains(@class, 'primary-dropdown')]//*[contains(@class, 'button')]");
  private final static By NAV_TOPICS = By.xpath("//*[contains(@data-l, 'Group_Forum')]");
  private final static By GROUP_NAME = By.xpath("//*[@class='mctc_name_tx']");
  private final static By GROUP_CATEGORY = By.xpath("//*[@class='group-info_category']");

  public GroupMainPage(WebDriver driver) {
    super(driver);
  }

  protected void check() {
    new WebDriverWait(driver, 10)
        .until(ExpectedConditions.visibilityOf(driver.findElement(GROUP_INFO_PANEL)));
  }

  /**
   * Нажимает создание новой темы
   */
  public void clickCreateNewTopic() {
    click(TOPIC_CREATE_BUTTON);
    new WebDriverWait(driver, 10)
        .until(ExpectedConditions.visibilityOfElementLocated(TOPIC_POPUP_CONFIRM));
  }

  /**
   * Печатает текст в попапе создания темы
   *
   * @param text вводимый текст
   */
  public void typeTextInNewTopic(String text) {
    type(text, TOPIC_POPUP_TEXT_INPUT);
  }

  /*
  Подтверждает создание темы в попапе
   */
  public void confirmTopicPublication() {
    click(TOPIC_POPUP_CONFIRM);
  }


  /**
   * Открывает выпадающий список "Ещё"
   */
  public void openOtherSections() {
    click(OTHER_SECTIONS);
    //проверяем, что меню открылось
    Assert.assertTrue("Выпадающее меню не появилось после нажатия Ещё",
        checkIfOtherSectionsIsOpen());
  }

  /**
   * Открывает выпадающий список Ещё и кликает настройки группы в выпадающем списке "Ещё"
   */
  public GroupSettingsPage openGroupSettings() {
    openOtherSections();
    click(DROPDOWN_SETTINGS);
    return new GroupSettingsPage(driver);
  }


  /**
   * Проверяет, открыто ли выпадающее меню "Ещё"
   *
   * @return true - открыто, false - нет
   */
  public boolean checkIfOtherSectionsIsOpen() {
    try {
      new WebDriverWait(driver, 5)
          .until(ExpectedConditions.visibilityOfElementLocated(OTHER_SECTIONS_DROPDOWN));
    } catch (TimeoutException te) {
      return false;
    }
    return true;
  }

  /**
   * Создает новый топик, проходя ввод текста в попапе и подтверждение
   *
   * @param s текст топика
   */
  public GroupTopicsPage createNewTopic(String s) {
    clickCreateNewTopic();
    typeTextInNewTopic(s);
    confirmTopicPublication();
    return new GroupTopicsPage(driver);
  }

  /**
   * Кликает присоединение к группе
   */
  public void joinGroup() {
    click(JOIN_BUTTON);
  }

  /**
   * Проверяет, ожидается ли запрос на вступление в группу
   */
  public boolean isInvitationPending() {
    return explicitWait(ExpectedConditions.visibilityOfElementLocated(GROUP_CARD_PENDING_JOIN), 5,
        500);
  }

  /**
   * Возвращает число запросов на вступление
   *
   * @return число запросов
   */
  public int getAmountOfPendingRequests() {
    if (!explicitWait(ExpectedConditions.visibilityOfElementLocated(JOIN_REQUESTS_COUNTER), 5,
        500)) {
      return 0;
    }

    return Integer.parseInt(driver.findElement(JOIN_REQUESTS_COUNTER).getText());
  }

  /**
   * Принимает первую заявку на вступление
   */
  public void acceptFirstJoinRequest() {
    click(ACCEPT_JOIN_REQUEST);
  }

  /**
   * Возвращает число участников, перед этим обновляя страницу чтобы счетчик был корректным
   *
   * @return число участниоков, 0 если что-то пошло не так
   */
  public int getAmountOfMembers() {
    driver.navigate().refresh();
    if (explicitWait(ExpectedConditions.visibilityOfElementLocated(MEMBER_COUNTER), 5, 500)) {
      return Integer.parseInt(driver.findElement(MEMBER_COUNTER).getText());
    }
    return 0;
  }

  /**
   * Проверяет, состоит ли участник в группе
   *
   * @return false если нет
   */
  public boolean isMemberDropdownPresent() {
    return explicitWait(ExpectedConditions.visibilityOfElementLocated(MAIN_DROPDOWN), 5, 500);
  }

  /**
   * Переходит к странице Темы
   *
   * @return страница Темы
   */
  public GroupTopicsPage goToTopics() {
    click(NAV_TOPICS);
    return new GroupTopicsPage(driver);
  }

  /**
   * Возвращает название группы из панели информации
   *
   * @return строка с названием
   */
  public String getGroupName() {
    waitForVisibility(GROUP_NAME, "Не удалось получить название группы");
    return driver.findElement(GROUP_NAME).getText();
  }

  /**
   * Возвращает категорию группы из панели информации
   *
   * @return строка с категорией
   */
  public String getGroupCategory() {
    waitForVisibility(GROUP_CATEGORY, "Не удалось получить категорию группы");
    return driver.findElement(GROUP_CATEGORY).getText();
  }

  /**
   * Возвращает статус пользователя из кнопки под логотипом
   *
   * @return строка со статусом
   */
  public String getUserStatus() {
    waitForVisibility(MEMBER_STATUS, "Не удалось получить статус пользователя");
    return driver.findElement(MEMBER_STATUS).getText();
  }

}
