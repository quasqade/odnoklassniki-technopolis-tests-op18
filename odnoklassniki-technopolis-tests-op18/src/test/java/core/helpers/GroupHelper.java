package core.helpers;

import core.pages.UserGroupsPage;
import core.pages.UserMainPage;
import core.pages.groups.main.GroupMainPage;
import org.openqa.selenium.WebDriver;

/**
 * Содержит некоторые полезные методы для автоматизации действий, которые не тестируются в каждом
 * кейсе.
 */

public class GroupHelper {

  /**
   * Создает публичную страницу категории интернет и компьютеры после логина пользователя
   *
   * @param name имя страницы
   * @param driver драйвер
   */
  public static GroupMainPage createPublicPage(WebDriver driver, String name) {
    UserGroupsPage userGroupsPage = new UserMainPage(driver).openGroupsByToolbar();
    userGroupsPage.createGroupByToolbar();
    userGroupsPage.selectPublicPage();
    userGroupsPage.inputGroupName(name);
    userGroupsPage.selectCategoryComputers();
    userGroupsPage.confirmGroupCreation();

    return new GroupMainPage(driver);
  }
}
