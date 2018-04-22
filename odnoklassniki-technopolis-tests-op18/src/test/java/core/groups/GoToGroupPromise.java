package core.groups;

import core.Page404;
import core.groups.main.GroupMainPage;
import org.openqa.selenium.WebDriver;

/**
 * Промис на переход в группу по ссылке с айди, может вернуть 404 или страницу группы
 */
public class GoToGroupPromise {

  private WebDriver driver;

  public GoToGroupPromise(WebDriver driver){
    this.driver = driver;
  }

  public GroupMainPage andGroupOpened(){
    return new GroupMainPage(driver);
  }

  public Page404 and404Opened(){
    return new Page404(driver);
  }


}
