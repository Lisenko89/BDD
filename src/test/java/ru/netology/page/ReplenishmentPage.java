package ru.netology.page;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Keys;
import ru.netology.data.DataHelper;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;

public class ReplenishmentPage {
    private final SelenideElement heading = $("[data-test-id=dashboard]");
    private final SelenideElement title = $("h1.heading");
    private final SelenideElement amountField = $("[data-test-id=amount] input");
    private final SelenideElement fromField = $("[data-test-id=from] input");
    private final SelenideElement transferButton = $("[data-test-id=action-transfer]");
    private final SelenideElement errorMessage= $("[data-test-id='error-message']");
    private final SelenideElement cancelButton = $("[data-test-id=action-cancel]");

    public ReplenishmentPage() {
        heading.shouldBe(visible);
        title.shouldHave(text("Пополнение карты"));
    }

    private void fieldClearing() {
        amountField.sendKeys(Keys.CONTROL + "a");
        amountField.sendKeys(Keys.DELETE);
        fromField.sendKeys(Keys.CONTROL + "a");
        fromField.sendKeys(Keys.DELETE);
    }

    public DashboardPage MakeValidTransfer(String amountToTransfer, DataHelper.CardIdInfo cardIdInfo) {
       makeTransfer(amountToTransfer,cardIdInfo);
        return new DashboardPage();
    }

    public void makeTransfer(String amountToTransfer, DataHelper.CardIdInfo cardIdInfo){
        amountField.setValue(amountToTransfer);
        fromField.setValue(cardIdInfo.getNumberCard());
        transferButton.click();
    }
    public void findErrorMessage(String expectedText){
        errorMessage.shouldHave(exactText(expectedText), Duration.ofSeconds(15)).shouldBe(visible);
    }
}
