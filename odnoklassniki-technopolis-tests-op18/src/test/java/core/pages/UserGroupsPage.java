package core.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

/**
 * Представляет список групп на странице пользователя
 */
public class UserGroupsPage extends PageBase {

  private static final By CATEGORY_DROPDOWN = By.xpath("//select[contains(@id, 'pageMixedCategory')]");
  private static final By CATEGORY_PUBLIC_PAGE = By.xpath(".//*[contains(@data-l,'PAGE')]");
  private static final By CATEGORY_PUBLIC_GROUP = By.xpath(".//*[contains(@data-l, 'INTEREST')]");

  public UserGroupsPage(WebDriver driver)
  {
    super(driver);
  }

  protected void check() {
  //TODO
  }

  /**
   * Подтверждает создание группы в попапе
   */
  public void confirmGroupCreation() {
      click(By.id("hook_FormButton_button_create"));
  }

  /**
   * Вводит имя группы в поле в попапе
   * @param name имя группы
   */
  public void inputGroupName(String name) {
    type(name, By.id("field_name"));
  }

  /**
   * Выбирает тип "публичная страница"
   */
  public void selectPublicPage() {
    click(CATEGORY_PUBLIC_PAGE);
  }

  /**
   * Выбирает тип "группа по интересам"
   */
  public void selectPublicGroup(){
    click(CATEGORY_PUBLIC_GROUP);
  }


  /**
   * Выбирает категорию "Компьютеры и интернет"
   */
  public void selectCategoryComputers(){
    Select select = new Select(driver.findElement(CATEGORY_DROPDOWN));
    select.selectByVisibleText("Компьютер и интернет");
  }

  /**
   * Открывает попап создания группы в левом навбаре
   */
  public void createGroupByToolbar() {
    click(By.xpath(".//*[contains(@href,'st.layer.cmd=PopLayerCreateAltGroup')]"));
  }

}
