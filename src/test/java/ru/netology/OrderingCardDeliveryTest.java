package ru.netology;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.Test;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selenide.*;

class RegistrationTest {

    @Test
    void shouldRegisterByCardDelivery() {

        // открываем страницу
        Selenide.open("http://localhost:9999");

        // дата: сегодня + 3 дня
        String date = LocalDate.now()
                .plusDays(3)
                .format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));

        // город
        $("[data-test-id=city] input").setValue("Москва");

        // очищаем поле даты (clear() тут НЕ работает)
        $("[data-test-id=date] input")
                .press("\u0001") // Ctrl + A
                .press("\u0008"); // Backspace
        $("[data-test-id=date] input").setValue(date);

        // имя
        $("[data-test-id=name] input").setValue("Иванов Иван");

        // телефон
        $("[data-test-id=phone] input").setValue("+79001234567");

        // чекбокс
        $("[data-test-id=agreement]").click();

        // кнопка
        $("button.button").click();

        // состояние загрузки (не более 15 сек)
        $("[data-test-id=notification]")
                .shouldBe(Condition.visible, Duration.ofSeconds(15));

        // проверка текста
        $("[data-test-id=notification] .notification__content")
                .shouldHave(Condition.text("Встреча успешно забронирована"));
    }
}
