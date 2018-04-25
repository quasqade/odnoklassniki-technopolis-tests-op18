package core.user;

import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class GroupCardListTransformer {
  private static final By GROUP_CARD_MODERATE = By
      .xpath(".//div[@data-l='groupCard,null']//div[@class='caption']//a");

  /**
   * Создает список всех созданных групп
   *
   * @return список абстрактных объектов
   */
  public static List<GroupCardModerateWrapper> collectGroupCardModerate(WebDriver driver) {
    List<WebElement> groupCards = driver.findElements(GROUP_CARD_MODERATE);
    List<GroupCardModerateWrapper> groupCardWrapped = new ArrayList<>();
    for (WebElement groupCard : groupCards
        ) {
      groupCardWrapped.add(new GroupCardModerateWrapper(groupCard));
    }
    return groupCardWrapped;
  }

}
