package core.user;

import core.PageBase;
import core.groups.main.GroupMainPage;
import core.groups.settings.main.GroupType;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

/**
 * Представляет список групп на странице пользователя
 */
public class UserGroupsPage extends PageBase {

  private static final By CATEGORY_DROPDOWN = By
      .xpath("//select[contains(@id, 'pageMixedCategory')]");
  private static final By CATEGORY_PUBLIC_PAGE = By.xpath(".//*[contains(@data-l,'PAGE')]");
  private static final By CATEGORY_PUBLIC_GROUP = By.xpath(".//*[contains(@data-l, 'INTEREST')]");
  private static final By CREATE_GROUP_TOOLBAR = By.xpath(".//*[contains(@href,'st.layer.cmd=PopLayerCreateAltGroup')]");
  private static final By CONFIRM_GROUP_CREATE = By.id("hook_FormButton_button_create");
  private static final By GROUP_NAME = By.id("field_name");
  public static final By MODERATE_GROUPS = By.xpath(".//a[contains(@hrefattrs,'MODERATED_BY_USER')]");

  public UserGroupsPage(WebDriver driver) {
    super(driver);
  }

  protected void check() {
    waitForVisibility(CREATE_GROUP_TOOLBAR, "Не удалось загрузить страницу групп пользователя");
  }

  /**
   * Подтверждает создание группы в попапе
   */
  public void confirmGroupCreation() {
    click(CONFIRM_GROUP_CREATE);
  }

  /**
   * Вводит имя группы в поле в попапе
   *
   * @param name имя группы
   */
  public void inputGroupName(String name) {
    type(name, GROUP_NAME);
  }

  /**
   * Выбирает тип группы
   */
  public void selectGroupType(GroupType type) {
    switch (type) {
      case PAGE:
        click(CATEGORY_PUBLIC_PAGE);
        break;
      case GROUP:
        click(CATEGORY_PUBLIC_GROUP);
        break;
      case EVENT:
        break;
    }
  }


  /**
   * Выбирает категорию "Компьютеры и интернет"
   */
  public void selectCategoryComputers() {
    Select select = new Select(driver.findElement(CATEGORY_DROPDOWN));
    select.selectByVisibleText("Компьютер и интернет");
  }

  /**
   * Открывает попап создания группы в левом навбаре
   */
  public void createGroupByToolbar() {
    click(CREATE_GROUP_TOOLBAR);
  }

  /**
   * Открывает модерируемые группы
   */
  public void goToModerateGroups() {
    click(MODERATE_GROUPS);
  }


  public void deleteAllCreatedGroups() {
    goToModerateGroups();
    List<GroupCardModerateWrapper>  listCards = GroupCardListTransformer.collectGroupCardModerate(driver);
    while(listCards.size()!=0) {
        String groupUrl = "https://ok.ru/group/" + listCards.get(0).getGroupId();
        driver.navigate().to(groupUrl);
        GroupMainPage groupMainPage = new GroupMainPage(driver);
        groupMainPage.clickDeleteGroup();
        groupMainPage.clickConfirmDeleteGroup();
        goToModerateGroups();
      listCards = GroupCardListTransformer.collectGroupCardModerate(driver);
      }
    }
  }

