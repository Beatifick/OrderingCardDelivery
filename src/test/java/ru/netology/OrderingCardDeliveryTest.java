package ru.netology;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.Test;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import org.openqa.selenium.Keys;

import static com.codeborne.selenide.Selenide.*;

class RegistrationTest {

    @Test
    void shouldRegisterByCardDelivery() {

        open("http://localhost:9999");
        String  date = LocalDate.now().plusDays(4).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id=city] input").setValue("Москва");
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(date);
        $("[data-test-id=name] input").setValue("Иванов Иван");
        $("[data-test-id=phone] input").setValue("+79001234567");
        $("[data-test-id=agreement]").click();
        $("button.button").click();
        $("[data-test-id=notification]").shouldBe(Condition.visible, Duration.ofSeconds(15));
        String notificationText = $("[data-test-id=notification] .notification__content").getText();
        $("[data-test-id=notification] .notification__content").shouldHave(Condition.text("Встреча успешно забронирована на " + date));
    }
}
