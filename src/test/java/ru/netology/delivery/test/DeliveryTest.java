package ru.netology.delivery.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import ru.netology.delivery.data.DataGenerator;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class DeliveryTest {
    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    @Test
    @DisplayName("Should successful plan and replan meeting")
    void shouldSuccessfulPlanAndReplanMeeting() {
        var validUser = DataGenerator.Registration.generateUser("ru");
        var daysToAddForFirstMeeting = 4;
        var firstMeetingDate = DataGenerator.generateDate(daysToAddForFirstMeeting);
        var daysToAddForSecondMeeting = 7;
        var secondMeetingDate = DataGenerator.generateDate(daysToAddForSecondMeeting);

        $("[data-test-id=city] input").setValue(validUser.getCity());

        for (int i = 0; i < 10; i++) {
            $("[data-test-id=date] input").sendKeys(Keys.BACK_SPACE);
        }
        $("[data-test-id=date] input").setValue(firstMeetingDate);
        $("[data-test-id=name] input").setValue(validUser.getName());
        $("[data-test-id=phone] input").setValue(validUser.getPhone());
        $("[data-test-id=agreement]").click();
        $("form.form button.button").click();

        $("[data-test-id=success-notification]").shouldBe(visible, Duration.ofMillis(500));
        $("[data-test-id=success-notification] .notification__content").shouldHave(exactText("Встреча успешно запланирована на " + firstMeetingDate));

        for (int i = 0; i < 10; i++) {
            $("[data-test-id=date] input").sendKeys(Keys.BACK_SPACE);
        }
        $("[data-test-id=date] input").setValue(secondMeetingDate);
        $("form.form button.button").click();

        $("[data-test-id=replan-notification]").shouldBe(visible, Duration.ofMillis(500));
        $("[data-test-id=replan-notification] .notification__content").shouldHave(partialText("У вас уже запланирована встреча на другую дату. Перепланировать?"));

        $("[data-test-id=replan-notification] .notification__content > button.button .button__text").shouldHave(exactText("Перепланировать"));
        $("[data-test-id=replan-notification] .notification__content > button.button").click();

        $("[data-test-id=success-notification]").shouldNotBe(visible, Duration.ofMillis(500));
        $("[data-test-id=success-notification] .notification__content").shouldHave(exactText("Встреча успешно запланирована на " + secondMeetingDate));
    }
}
