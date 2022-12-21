import lombok.var;
import org.junit.jupiter.api.*;
import ru.netology.data.DataHelper;
import ru.netology.page.DashboardPage;
import ru.netology.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.*;
import static ru.netology.data.DataHelper.generateInvalidAmount;
import static ru.netology.data.DataHelper.generateValidAmount;

class TransferMoneyBetweenOwnCardsTest {

        @BeforeAll
        public static void loginToPersonalAccount() {
            //Вместо первых двух команд можно использовать эту:
            //var loginPage = open("http://localhost:9999", LoginPage.class);
            open("http://localhost:9999");
            var loginPage = new LoginPage();
            var authInfo = DataHelper.getUserAuthInfo();
            var verificationPage = loginPage.validLogin(authInfo);
            var verificationCode = DataHelper.getVerificationCodeFor();
            verificationPage.validVerify(verificationCode);
        }


        //Позитивные проверки:
        @Test   //Перевод с первой карты на вторую
        @DisplayName("Transfer money from the first card to the second card")
        public void shouldTransferFromFirstToSecond() {
            var dashboardPage = new DashboardPage();
            var firstCardId = DataHelper.getFirstCardIdInfo();
            var balanceFirstCard = dashboardPage.getCardBalance(firstCardId);
            var secondCardId = DataHelper.getSecondCardIdInfo();
            var balanceSecondCard = dashboardPage.getCardBalance(secondCardId);
            var amount = generateValidAmount(balanceFirstCard);
            var expectedBalanceFirstCard = balanceFirstCard - amount;
            var expectedBalanceSecondCard = balanceSecondCard + amount;
            var replenishmentPage = dashboardPage.transfer (secondCardId);
            dashboardPage = replenishmentPage.MakeValidTransfer(String.valueOf(amount), firstCardId);
            var actualBalanceFirstCard = dashboardPage.getCardBalance(firstCardId);
            var actualBalanceSecondCard = dashboardPage.getCardBalance(secondCardId);
            //Проверка зачисления на первую карту:
            assertEquals(expectedBalanceFirstCard, actualBalanceFirstCard);
            //Проверка списания со второй карты:
            assertEquals(expectedBalanceSecondCard, actualBalanceSecondCard);
        }


        @Test   //Перевод со второй карты на первую
        @DisplayName("Transfer money from the second card to the first card")
        public void shouldTransferFromSecondToFirst() {
            var dashboardPage = new DashboardPage();
            var firstCardId = DataHelper.getFirstCardIdInfo();
            var balanceFirstCard = dashboardPage.getCardBalance(firstCardId);
            var secondCardId = DataHelper.getSecondCardIdInfo();
            var balanceSecondCard = dashboardPage.getCardBalance(secondCardId);
            var amount = generateValidAmount(balanceSecondCard);
            var expectedBalanceFirstCard = balanceFirstCard + amount;
            var expectedBalanceSecondCard = balanceSecondCard - amount;
            var replenishmentPage = dashboardPage.transfer (firstCardId);
            dashboardPage = replenishmentPage.MakeValidTransfer(String.valueOf(amount), secondCardId);
            var actualBalanceFirstCard = dashboardPage.getCardBalance(firstCardId);
            var actualBalanceSecondCard = dashboardPage.getCardBalance(secondCardId);
            //Проверка зачисления на первую карту:
            assertEquals(expectedBalanceFirstCard, actualBalanceFirstCard);
            //Проверка списания со второй карты:
            assertEquals(expectedBalanceSecondCard, actualBalanceSecondCard);
        }

        //Негативные проверки:

        @Test   //Попытка перевода с первой карты на вторую с суммой перевода превышающей баланс первой карты
        @DisplayName("Transfer money from the first card to the second " +
                "with the transfer amount exceeding the balance of the first card")
        public void shouldTransferFromFirstToSecondNegativeAmount() throws InterruptedException {
            //Получение баланса по обеим картам и подготовка данных для перевода денег:
            var dashboardPage = new DashboardPage();
            var firstCardId = DataHelper.getFirstCardIdInfo();
            var balanceFirstCard = dashboardPage.getCardBalance(firstCardId);
            var secondCardId = DataHelper.getSecondCardIdInfo();
            var balanceSecondCard = dashboardPage.getCardBalance(secondCardId);
            var amount = generateInvalidAmount(balanceFirstCard);
            var replenishmentPage = dashboardPage.transfer(secondCardId);
            replenishmentPage.makeTransfer(String.valueOf(amount),firstCardId);
            replenishmentPage.findErrorMessage("Выполнена попытка перевода суммы, превышающей остаток на карте списания");
            var actualBalanceFirstCard = dashboardPage.getCardBalance(firstCardId);
            var actualBalanceSecondCard = dashboardPage.getCardBalance(secondCardId);
            assertEquals(balanceFirstCard, actualBalanceFirstCard);
            assertEquals(balanceSecondCard, actualBalanceSecondCard);
        }
    }
