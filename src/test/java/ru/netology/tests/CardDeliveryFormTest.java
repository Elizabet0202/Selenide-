package ru.netology.tests;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selenide.*;

public class CardDeliveryFormTest {

@BeforeEach
void setUp() {
    Configuration.headless = true; // headless режим
    open("http://localhost:9999"); // URL целевого сервиса, который запускается локально
}

private String generateDate(int daysToAdd) {
    return LocalDate.now().plusDays(daysToAdd).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
}

@Test
void shouldSubmitFormSuccessfully() {
    // Ввод города - административный центр субъекта РФ (например, "Москва")
    $("[data-test-id=city] input").setValue("Москва");

    // Ввод даты, минимум +3 дня от текущей даты
    $("[data-test-id=date] input").doubleClick().sendKeys(generateDate(3));

    // Ввод имени и фамилии - может быть "Иванов-Петров Иван"
    $("[data-test-id=name] input").setValue("Иванов-Петров Иван");

    // Ввод телефона: 11 цифр, плюс знак + в начале
    $("[data-test-id=phone] input").setValue("+79998887766");

    // Установка флажка согласия
    $("[data-test-id=agreement]").click();

    // Клик по кнопке "Заказать"
    $(".button").click();

    // Проверка появления загрузочного состояния (spinner или окно загрузки)
    $("[data-test-id=order-success]")
            .shouldBe(Condition.visible, Duration.ofSeconds(15)) // ожидание до 15 секунд
            .shouldHave(Condition.text("Ваша заявка успешно отправлена!"));
}
    }