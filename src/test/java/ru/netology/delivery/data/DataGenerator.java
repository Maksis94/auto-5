package ru.netology.delivery.data;

import com.github.javafaker.Faker;
import com.github.javafaker.Name;
import lombok.Value;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Random;

public class DataGenerator {
    private DataGenerator() {
    }

    public static String generateDate(int shift) {
        LocalDate date = LocalDate.now().plusDays(3).plus(Period.ofDays((new Random().nextInt(200))));
        return date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    public static String generateCity(String locale) {
        String[] list = new String[]{"Великий Новгород", "Москва", "Киров"};
        return list[new Random().nextInt(list.length)];
    }

    public static String generateName(String locale) {
        Faker faker = new Faker(new Locale(locale));
        Name name = faker.name();
        return name.lastName() + " " + name.firstName();
    }

    public static String generatePhone(String locale) {
        Faker faker = new Faker(new Locale(locale));
        return faker.phoneNumber().phoneNumber();
    }

    public static class Registration {
        private Registration() {
        }

        public static UserInfo generateUser(String locale) {
            return new UserInfo(
                    generateCity(locale),
                    generateName(locale),
                    generatePhone(locale)
            );
        }
    }

    @Value
    public static class UserInfo {
        String city;
        String name;
        String phone;
    }
}