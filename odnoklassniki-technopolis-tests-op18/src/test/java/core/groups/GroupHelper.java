package core.groups;

import core.groups.settings.main.GroupType;
import core.user.UserGroupsPage;
import core.user.UserMainPage;
import core.groups.main.GroupMainPage;
import org.junit.Assert;
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
    userGroupsPage.selectGroupType(GroupType.PAGE);
    userGroupsPage.inputGroupName(name);
    userGroupsPage.selectCategoryComputers();
    userGroupsPage.confirmGroupCreation();

    return new GroupMainPage(driver);
  }

  /**
   * Возвращает ID группы, извлеченный из ссылки на нее
   * @param url ссылка на группу
   * @return ID
   */
  public static String getIDFromLink(String url){
    String[] splitUrl = url.split("/");
    for (int i = 0; i < splitUrl.length; i++) {
      if (splitUrl[i].equals("group"))
        return splitUrl[i+1];
    }
    Assert.fail("Не удалось извлечь ID группы из ссылки: " + url);
    return null;
  }
}
