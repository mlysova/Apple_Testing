package com.apple.pages.testing.browser;

import com.apple.pages.testing.utils.TestUtils;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.springframework.test.context.ActiveProfiles;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

@ActiveProfiles(profiles = "chrome")
public class ChromeBrowserApplePageTests extends BrowserApplePageBaseTests {

    @Test
    @Override
    public void testSearchButtonClick() {
        $(By.xpath("//a[@class='ac-gn-link ac-gn-link-search']")).click();

        $(By.id("ac-gn-searchform-input")).shouldBe(visible);
        assert TestUtils.is200CodeInResponseByQueryParam("link", "search - /us/search" , bmp);
    }

    protected String getBrowserName() {
        return "chrome";
    }

    protected String getPlatformName() {
        return "mac";
    }
}
