package ru.netology;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.selected;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class WebTest {
    @BeforeEach
    public void initEach() {
        open("http://localhost:9999");
    }

    // позитивные тесты
    @Test
    void correctValueTest() {
        $("[data-test-id=name] input").setValue("Пупкин Василий");
        $("[data-test-id=phone] input").setValue("+79270000000");
        $("[data-test-id=agreement]").click();
        $("button.button").click();
        $("[data-test-id=order-success]").shouldHave(exactText("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время."));
    }

    // негативные тесты
    @Test
    void emptyNameTest() {
        $("[data-test-id=name] input").setValue("");
        $("[data-test-id=phone] input").setValue("+79270000000");
        $("[data-test-id=agreement]").click();
        $("button.button").click();
        $("[data-test-id=name].input_invalid .input__sub").shouldHave(exactText("Поле обязательно для заполнения"));
    }
    @Test
    void nameWithLatinTest() {
        $("[data-test-id=name] input").setValue("sada");
        $("[data-test-id=phone] input").setValue("+79270000000");
        $("[data-test-id=agreement]").click();
        $("button.button").click();
        $("[data-test-id=name].input_invalid .input__sub").shouldHave(exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void nameWithSymvolsTest() {
        $("[data-test-id=name] input").setValue("Ма№ша");
        $("[data-test-id=phone] input").setValue("+79270000000");
        $("[data-test-id=agreement]").click();
        $("button.button").click();
        $("[data-test-id=name].input_invalid .input__sub").shouldHave(exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void emptyPhoneTest() {
        $("[data-test-id=name] input").setValue("Маша");
        $("[data-test-id=phone] input").setValue("");
        $("[data-test-id=agreement]").click();
        $("button.button").click();
        $("[data-test-id=phone].input_invalid .input__sub").shouldHave(exactText("Поле обязательно для заполнения"));
    }
    @Test
    void phoneLessTest() {
        $("[data-test-id=name] input").setValue("Маша");
        $("[data-test-id=phone] input").setValue("+7927000000");
        $("[data-test-id=agreement]").click();
        $("button.button").click();
        $("[data-test-id=phone].input_invalid .input__sub").shouldHave(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void phoneAboveTest() {
        $("[data-test-id=name] input").setValue("Маша");
        $("[data-test-id=phone] input").setValue("+792700000000");
        $("[data-test-id=agreement]").click();
        $("button.button").click();
        $("[data-test-id=phone].input_invalid .input__sub").shouldHave(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void phoneWitoutPlusTest() {
        $("[data-test-id=name] input").setValue("Маша");
        $("[data-test-id=phone] input").setValue("792700000000");
        $("[data-test-id=agreement]").click();
        $("button.button").click();
        $("[data-test-id=phone].input_invalid .input__sub").shouldHave(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void withoutAcceptTest() {
        $("[data-test-id=name] input").setValue("Маша");
        $("[data-test-id=phone] input").setValue("+79270000000");
        $("button.button").click();
        $("[data-test-id=agreement] input").shouldNotBe(selected);
        $("[data-test-id=agreement].input_invalid").isDisplayed();
    }

}
