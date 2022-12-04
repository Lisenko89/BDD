package ru.netology.data;

import lombok.Value;

import java.util.Random;

public class DataHelper {
    private DataHelper() {
    }

    @Value  //Дата класс AuthInfo и его методы
    public static class AuthInfo {
        private String login;
        private String password;
    }

    public static AuthInfo getUserAuthInfo() {
        return new AuthInfo("vasya", "qwerty123");
    }

    @Value  //Дата класс VerificationCode и его методы
    public static class VerificationCode {
        private String code;
    }

    public static VerificationCode getVerificationCodeFor() {
        return new VerificationCode("12345");
    }

    @Value  //Дата класс CardIdInfo и его методы
    public static class CardIdInfo {
        private String id;
        private String numberCard;
    }

    public static CardIdInfo getFirstCardIdInfo() {
        return new CardIdInfo( "92df3f1c-a033-48e6-8390-206f6b1f56c0", "5559 0000 0000 0002");
    }

    public static CardIdInfo getSecondCardIdInfo() {
        return new CardIdInfo("0f3f5c2a-249e-4c3d-8287-09f7a039391d","5559 0000 0000 0001");
    }

    public static int generateValidAmount(int balance){
        return new Random().nextInt(balance) + 1;
    }

    public static int generateInvalidAmount(int balance){
        return Math.abs(balance) + new Random().nextInt(10000);
    }
}
